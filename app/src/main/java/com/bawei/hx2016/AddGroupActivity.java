package com.bawei.hx2016;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bawei.hx2016.adapater.RecyclerAdapter;
import com.bawei.hx2016.base.BaseActivity;
import com.bawei.hx2016.holde.RecyclerHolder;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroupInfo;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;


public class AddGroupActivity extends BaseActivity implements View.OnClickListener {

    private EditText add_group_search_et;
    private Button add_group_search_but;
    private RecyclerView add_group_rv;
    private ArrayList<EMGroupInfo> emGroupInfoList = new ArrayList<>();
    private RecyclerAdapter<EMGroupInfo> recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        initView();
        initDates();
    }

    /**
     * 初始化数据
     */
    private void initDates() {
        new Thread() {
            @Override
            public void run() {
                //获取公开群列表
                //pageSize为要取到的群组的数量，cursor用于告诉服务器从哪里开始取
                try {
                    final EMCursorResult<EMGroupInfo> result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(10, (emGroupInfoList.size() + 1) + "");//需异步处理
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            emGroupInfoList.addAll(result.getData());
                            recyclerAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        add_group_search_et = (EditText) findViewById(R.id.add_group_search_et);
        add_group_search_but = (Button) findViewById(R.id.add_group_search_but);
        add_group_rv = (RecyclerView) findViewById(R.id.add_group_rv);

        add_group_search_but.setOnClickListener(this);

        //设置适配器
        add_group_rv.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter<EMGroupInfo>(this, emGroupInfoList, R.layout.chatrecyclerview_item) {

            @Override
            public void convert(RecyclerHolder holder, EMGroupInfo data, int position) {
                holder.setText(R.id.chat_recyclerView_tv, data.getGroupName());

            }
        };
        add_group_rv.setAdapter(recyclerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.add_group_search_but:
                initDates();
                break;
        }
    }
}
