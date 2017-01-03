package com.bawei.hx2016;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bawei.hx2016.base.BaseActivity;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.ui.EaseChatFragment;


public class ChatActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, getIntent().getStringExtra("userName"));
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_chat, chatFragment).commit();
    }
}
