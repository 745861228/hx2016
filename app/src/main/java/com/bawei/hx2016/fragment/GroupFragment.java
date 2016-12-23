package com.bawei.hx2016.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.bawei.hx2016.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupInfo;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiKe on 2016/12/23.
 */

public class GroupFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button addGroupBut;
    private Button searchGroupBut;
    private ListView group_chat_lv;
    private ArrayList<EMGroup> emGroupList = new ArrayList<>();


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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //添加群
            case R.id.addGroup_but:

                break;
            //新建群
            case R.id.newGroup_but:

                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }

    private void initDatas() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                    emGroupList.addAll(EMClient.getInstance().groupManager().getJoinedGroupsFromServer());//需异步处理
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
