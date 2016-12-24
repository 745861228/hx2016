package com.bawei.hx2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.hx2016.base.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import static com.hyphenate.chat.a.a.a.f;

/**
 * 群聊界面
 */
public class GroupMessageActivity extends BaseActivity implements View.OnClickListener {

    private TextView groupName_tv;
    private Button groupManager_but;
    private ListView groupMessage_list;
    private EditText groupMessage_et;
    private Button groupMessageSend_but;
    private EMGroup group;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manager);

        initView();
        initDatas();
        initListener();
    }

    /**
     * 按钮监听事件
     */
    private void initListener() {
        groupManager_but.setOnClickListener(this);
    }

    /**
     * 获取该群的对应想信息
     */
    private void initDatas() {
        groupId = getIntent().getStringExtra("groupId");
        //根据群组ID从本地获取群组基本信息

        try {
            group = EMClient.getInstance().groupManager().getGroup(groupId);
            String groupName = group.getGroupName();
            groupName_tv.setText(groupName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        groupName_tv = (TextView) findViewById(R.id.groupName_tv);
        groupManager_but = (Button) findViewById(R.id.groupManager_but);
        groupMessage_list = (ListView) findViewById(R.id.groupMessage_list);
        groupMessage_et = (EditText) findViewById(R.id.groupMessage_et);
        groupMessageSend_but = (Button) findViewById(R.id.groupMessageSend_but);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //群管理按钮监听事件
            case R.id.groupManager_but:
                Intent intent = new Intent(GroupMessageActivity.this,GroupManageActivity.class);
                intent.putExtra("groupId",groupId);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            group = EMClient.getInstance().groupManager().getGroup(groupId);
            String groupName = group.getGroupName();
            groupName_tv.setText(groupName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
