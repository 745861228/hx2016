package com.bawei.hx2016;

import android.os.Bundle;

import com.bawei.hx2016.base.BaseActivity;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.ui.EaseChatFragment;

/**
 * 群聊界面
 */
public class GroupMessageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manager);

        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID, getIntent().getStringExtra("groupId"));
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_group_manager, chatFragment).commit();
    }
}
