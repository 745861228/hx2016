package com.bawei.hx2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.hx2016.base.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

public class NewGroupChatActivity extends BaseActivity implements View.OnClickListener {

    private EditText newGroupName_et;
    private EditText newGroupInfo_et;
    private Button newGroupCommit_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_chat);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        newGroupName_et = (EditText) findViewById(R.id.newGroup_name_et);
        newGroupInfo_et = (EditText) findViewById(R.id.newGroup_info_et);
        newGroupCommit_but = (Button) findViewById(R.id.newGroup_commit_but);

        //设置点击事件
        newGroupCommit_but.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newGroup_commit_but:
        /**
         * 创建群组
         * @param groupName 群组名称
         * @param desc 群组简介
         * @param allMembers 群组初始成员，如果只有自己传空数组即可
         * @param reason 邀请成员加入的reason
         * @param option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
         * @return 创建好的group
         * @throws HyphenateException
         */
                //群名称和群简介
                String newGroupName = newGroupName_et.getText().toString().trim();
                String newGroupInfo = this.newGroupInfo_et.getText().toString().trim();

                if (!TextUtils.isEmpty(newGroupName)&&!TextUtils.isEmpty(newGroupInfo)){
                    EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
                    option.maxUsers = 200;
                    option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                    try {
                        EMClient.getInstance().groupManager().createGroup(newGroupName, newGroupInfo, new String[]{}, "", option);
                        finish();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(this, "请完善群信息！！", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
