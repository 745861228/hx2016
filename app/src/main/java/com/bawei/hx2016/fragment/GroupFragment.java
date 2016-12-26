package com.bawei.hx2016.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.hx2016.AddGroupActivity;
import com.bawei.hx2016.GroupMessageActivity;
import com.bawei.hx2016.NewGroupChatActivity;
import com.bawei.hx2016.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by LiKe on 2016/12/23.
 */

public class GroupFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button addGroupBut;
    private Button searchGroupBut;
    private ListView group_chat_lv;
    private ArrayList<EMGroup> emGroupList = new ArrayList<>();
    private BaseAdapter baseAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.group_chat, null);
        initView();
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        addGroupBut = (Button) view.findViewById(R.id.addGroup_but);
        searchGroupBut = (Button) view.findViewById(R.id.newGroup_but);
        group_chat_lv = (ListView) view.findViewById(R.id.group_chat_lv);

        //监听事件
        addGroupBut.setOnClickListener(this);
        searchGroupBut.setOnClickListener(this);
        //设置列表适配器
        initGroupListAdapter();
    }

    /**
     * 群组数据列表适配器
     */
    private void initGroupListAdapter() {
        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return emGroupList.size();
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
                TextView textView = new TextView(getActivity());
                textView.setTextSize(30);
                textView.setText(emGroupList.get(position).getGroupName());
                return textView;
            }
        };

        group_chat_lv.setAdapter(baseAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //添加群
            case R.id.addGroup_but:
                startActivity(new Intent(getActivity(), AddGroupActivity.class));
                break;
            //新建群
            case R.id.newGroup_but:
                startActivity(new Intent(getActivity(), NewGroupChatActivity.class));
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //条目监听事件，进去对应的群界面
        group_chat_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupMessageActivity.class);
                intent.putExtra("groupId",emGroupList.get(position).getGroupId());
                getActivity().startActivity(intent);
            }
        });
    }

    private void initDatas() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                    emGroupList.clear();
                    emGroupList.addAll(EMClient.getInstance().groupManager().getJoinedGroupsFromServer());//需异步处理
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            baseAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        emGroupList.clear();
    }
}
