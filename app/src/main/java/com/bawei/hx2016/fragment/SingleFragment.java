package com.bawei.hx2016.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.hx2016.AddFriendActivity;
import com.bawei.hx2016.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :   郗琛
 * @date :   2016/12/23
 */

public class SingleFragment extends Fragment implements View.OnClickListener {

    private View viewRoot;
    private ListView friendLv;
    private List<String> usernames = new ArrayList<>();
    private BaseAdapter baseAdapter;
    private Button addFriendBut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.single_chat, null);
        initData();
        initView();
        return viewRoot;
    }

    private void initData() {
        try {
            usernames.clear();
            usernames.addAll(EMClient.getInstance().contactManager().getAllContactsFromServer());
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找控件
     */
    private void initView() {
        friendLv = (ListView) viewRoot.findViewById(R.id.friend_lv);
        addFriendBut = (Button) viewRoot.findViewById(R.id.add_friend_but);

        addFriendBut.setOnClickListener(this);


        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return usernames.size();
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
                textView.setText(usernames.get(position));
                return textView;
            }
        };
        friendLv.setAdapter(baseAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_friend_but:
                Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                getActivity().startActivity(intent);
        }
    }
}
