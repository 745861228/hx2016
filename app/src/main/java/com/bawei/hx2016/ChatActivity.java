package com.bawei.hx2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bawei.hx2016.base.BaseActivity;

public class ChatActivity extends BaseActivity {

    private Button chat_contentBut;
    private ListView chatList;
    private EditText chat_contentEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
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
