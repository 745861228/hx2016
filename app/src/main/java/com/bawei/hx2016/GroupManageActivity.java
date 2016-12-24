package com.bawei.hx2016;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.hx2016.base.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.id;

public class GroupManageActivity extends BaseActivity implements View.OnClickListener {

    private TextView groupMessage_tv;
    private ListView groupMember_list;
    private Button groupClearRecord_but;
    private Button groupChangeName_but;
    private Button groupBlackList_but;
    private Button groupShield_but;
    private Button grougDissolve_but;
    private String groupId;
    private List<String> groupMemberList;
    private boolean isGroupShield = true;
    private String owner;
    private Button groupAddMember_but;
    private Button groupKickOutMember_but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manage);
        initView();
        //监听事件
        initListener();
        initDatas();
    }

    /**
     * 监听事件
     */
    private void initListener() {
        groupClearRecord_but.setOnClickListener(this);      //清空聊天记录
        groupChangeName_but.setOnClickListener(this);       //改变群名称
        groupBlackList_but.setOnClickListener(this);        //黑名单列表
        groupShield_but.setOnClickListener(this);           //屏蔽群消息
        grougDissolve_but.setOnClickListener(this);         //解散该群
        groupAddMember_but.setOnClickListener(this);        //添加群成员
        groupKickOutMember_but.setOnClickListener(this);    //踢出群成员
    }

    /**
     * 初始化控件
     */
    private void initView() {
        groupMessage_tv = (TextView) findViewById(R.id.groupMessage_tv);
        groupMember_list = (ListView) findViewById(R.id.groupMember_list);
        groupClearRecord_but = (Button) findViewById(R.id.groupClearRecord_but);
        groupChangeName_but = (Button) findViewById(R.id.groupChangeName_but);
        groupBlackList_but = (Button) findViewById(R.id.groupBlackList_but);
        groupShield_but = (Button) findViewById(R.id.groupShield_but);
        grougDissolve_but = (Button) findViewById(R.id.grougDissolve_but);
        groupAddMember_but = (Button) findViewById(R.id.groupAddMember_but);
        groupKickOutMember_but = (Button) findViewById(R.id.groupKickOutMember_but);
    }

    /**
     * 获取该群信息
     */
    private void initDatas() {
        //获取该群id
        groupId = getIntent().getStringExtra("groupId");
        //根据群组ID从本地获取群组基本信息
        EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
        String groupName = group.getGroupName();        //获取群名称

        //获取群成员
        groupMemberList = group.getMembers();

        //设置群成员列表适配器
        setListAdapter();

        //获取群主
        owner = group.getOwner();
        groupMessage_tv.setText(groupName + "(" + groupMemberList.size() + "人" + ")");

    }

    /**
     * 设置群成员列表
     */
    private void setListAdapter() {
        BaseAdapter baseAdapter =  new BaseAdapter() {
            @Override
            public int getCount() {
                return groupMemberList.size();
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
                TextView textView = new TextView(GroupManageActivity.this);

                if (groupMemberList.get(position).equals(owner)){
                    textView.setText(groupMemberList.get(position)+"(群主)");
                }else {
                    textView.setText(groupMemberList.get(position));
                }
                return textView;
            }
        };
        groupMember_list.setAdapter(baseAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.groupClearRecord_but:            //清空聊天记录
                showClearRecordWindow();
                break;

            case R.id.groupChangeName_but:             //改变群名称
                showChangeNameWindow();
                break;

            case R.id.groupBlackList_but:              //黑名单列表

                break;

            case R.id.groupShield_but:                 //屏蔽消息
                /**
                 * 屏蔽群消息后，就不能接收到此群的消息（还是群里面的成员，但不再接收消息）
                 * @param groupId， 群ID
                 * @throws EasemobException
                 */
                if (isGroupShield){
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().groupManager().blockGroupMessage(groupId);//需异步处理
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    Toast.makeText(GroupManageActivity.this, "您已经屏蔽该群！！！", Toast.LENGTH_SHORT).show();
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().groupManager().unblockGroupMessage(groupId);//需异步处理
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    Toast.makeText(GroupManageActivity.this, "您已经取消屏蔽该群！！！", Toast.LENGTH_SHORT).show();
                }
                isGroupShield = !isGroupShield;
                break;

            case R.id.grougDissolve_but:               //解散群聊
                try {
                    EMClient.getInstance().groupManager().destroyGroup(groupId);//需异步处理
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(GroupManageActivity.this, MainActivity.class);
                intent.putExtra("grougDissolve",101);
                startActivity(intent);
                break;

            case R.id.groupAddMember_but:           //添加群成员

                break;

            case R.id.groupKickOutMember_but:       //踢出群成员

                break;
        }
    }

    /**
     * 清除聊天记录弹出窗
     */
    private void showClearRecordWindow() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(GroupManageActivity.this);
        builder.setCancelable(false);
        builder.setTitle("提示");
        builder.setMessage("确定清空此群的聊天记录吗？");
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
        builder.show();
    }

    /**
     * 改变群名称弹出框
     */
    private void showChangeNameWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View textEntryView = inflater.inflate(
                R.layout.group_change_name_alertdalog, null);
        final EditText edtInput = (EditText) textEntryView.findViewById(R.id.edtInput);
        final AlertDialog.Builder builder = new AlertDialog.Builder(GroupManageActivity.this);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("改变群名称");
        builder.setView(textEntryView);
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final String groupNewName = edtInput.getText().toString();
                        new Thread() {
                            @Override
                            public void run() {
                                //groupId 需要改变名称的群组的id
                                //changedGroupName 改变后的群组名称
                                try {
                                    EMClient.getInstance().groupManager().changeGroupName(groupId, groupNewName);//需异步处理
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            groupMessage_tv.setText(groupNewName + "(" + groupMemberList.size() + "人" + ")");
                                        }
                                    });
                                } catch (HyphenateException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
        builder.show();
    }
}
