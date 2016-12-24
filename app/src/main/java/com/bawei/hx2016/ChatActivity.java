package com.bawei.hx2016;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bawei.hx2016.base.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

public class ChatActivity extends BaseActivity{

    private Button chat_contentBut;
    private ListView chatList;
    private EditText chat_contentEt;
    private String userName;
    private Button userNameBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userNameBut = (Button) findViewById(R.id.username_but);
        userName = getIntent().getStringExtra("userName");
        userNameBut.setText(userName);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        chat_contentBut = (Button) findViewById(R.id.commit_but);
        chatList = (ListView) findViewById(R.id.chat_list);
        chat_contentEt = (EditText) findViewById(R.id.chat_content_et);
    }
}
