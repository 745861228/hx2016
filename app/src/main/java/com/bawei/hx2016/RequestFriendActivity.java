package com.bawei.hx2016;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.hx2016.base.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

public class RequestFriendActivity extends BaseActivity {

    private ListView requestFriendLv;
    private List<String> userNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_friend);
        initData();
        initView();
        initListener();
    }

    /**
     * 获取数据
     */
    private void initData() {
        String userName = getIntent().getStringExtra("userName");
        userNames.add(userName);
    }

    /**
     * 监听
     */
    private void initListener() {
        requestFriendLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestFriendActivity.this);
                builder.setTitle("是否同意添加为好友").setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            EMClient.getInstance().contactManager().acceptInvitation(userNames.get(position));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            EMClient.getInstance().contactManager().declineInvitation(userNames.get(position));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        requestFriendLv = (ListView) findViewById(R.id.request_friend_lv);


        requestFriendLv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return userNames.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView userNameTv = new TextView(RequestFriendActivity.this);
                userNameTv.setPadding(20, 20, 20, 20);
                userNameTv.setText(userNames.get(position) + "请求添加你为好友");
                return userNameTv;
            }
        });
    }
}
