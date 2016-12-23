package com.bawei.hx2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bawei.hx2016.base.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class AddFriendActivity extends BaseActivity implements View.OnClickListener {

    private EditText addFriendEt;
    private Button addFriendBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();


    }

    /**
     * 初始化控件
     */
    private void initView() {
        addFriendEt = (EditText) findViewById(R.id.addFriend_et);
        addFriendBut = (Button) findViewById(R.id.addFriend_but);

        addFriendBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //确认添加好友监听事件
            case R.id.addFriend_but:
                String addUserName = addFriendEt.getText().toString().trim();
                if (!TextUtils.isEmpty(addUserName)) {
                    //参数为要添加的好友的username和添加理由
                    try {
                        EMClient.getInstance().contactManager().addContact(addUserName, "123");
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
