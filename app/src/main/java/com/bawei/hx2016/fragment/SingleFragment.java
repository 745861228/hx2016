package com.bawei.hx2016.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.hx2016.AddFriendActivity;
import com.bawei.hx2016.ChatActivity;
import com.bawei.hx2016.R;
import com.bawei.hx2016.base.BaseActivity;
import com.bawei.hx2016.bean.FriendBean;
import com.bawei.hx2016.utils.DBUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.xutils.db.Selector;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :   郗琛
 * @date :   2016/12/23
 */

public class SingleFragment extends Fragment implements View.OnClickListener {

    private View viewRoot;
    private ListView friendLv;
    private ArrayList<FriendBean> usernames = new ArrayList<>();
    private BaseAdapter baseAdapter;
    private Button addFriendBut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.single_chat, null);
        initView();
        return viewRoot;
    }

    private void initData() {
        usernames.clear();
        try {
            List<FriendBean> all = DBUtils.getDbUtilsInstance().findAll(FriendBean.class);//通过类型查找
            if (all == null)
                return;

            usernames.addAll(all);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        baseAdapter.notifyDataSetChanged();
    }

    /**
     * 查找控件
     */
    private void initView() {

        friendLv = (ListView) viewRoot.findViewById(R.id.friend_lv);
        addFriendBut = (Button) viewRoot.findViewById(R.id.add_friend_but);

        addFriendBut.setOnClickListener(this);
        friendLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getActivity(), ChatActivity.class);
                in.putExtra("userName", usernames.get(position).getName());
                startActivity(in);
            }
        });

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
                textView.setText(usernames.get(position).getName());
                textView.setPadding(20, 20, 20, 20);
                return textView;
            }
        };
        friendLv.setAdapter(baseAdapter);
        friendLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((BaseActivity) getActivity()).enterActivity(ChatActivity.class, "userName", usernames.get(position).getName());
            }
        });

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
