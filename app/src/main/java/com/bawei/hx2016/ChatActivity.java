package com.bawei.hx2016;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.bawei.hx2016.adapater.ChatRecyalerViewAdapater;
import com.bawei.hx2016.base.BaseActivity;
import com.bawei.hx2016.interfaces.SingleCharListener;
import com.bawei.hx2016.services.MyService;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView chat_recyclerView;
    private EditText chatContentEt;
    private String userName;
    private Button userNameBut;
    /**
     * 所有会话消息
     */
    List<EMMessage> messages = new ArrayList<>();
    private Button chatSendBut;
    private ChatRecyalerViewAdapater chatRecyalerViewAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
        initChatListAdapter();
        initListener();
    }

    /**
     * 设置聊天界面listAdapter
     */
    private void initChatListAdapter() {
        chat_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        chatRecyalerViewAdapater = new ChatRecyalerViewAdapater(ChatActivity.this, messages);
        chat_recyclerView.setAdapter(chatRecyalerViewAdapater);
    }


    /**
     * 服务的中间人
     */
    private MyService.MyBinder myBinder;
    /**
     * myService的连接
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
            myBinder.setShow(true);
            myBinder.setSingleCharListener(new SingleCharListener() {
                @Override
                public void receiveMessage(List<EMMessage> messagesList) {
                    for (int i = 0; i < messagesList.size(); i++) {
                        if (messagesList.get(i).getUserName().equals(userName)) {
                            messages.add(messagesList.get(i));
                        }
                    }
                    notifyMessage();
                    Log.d("AAAAAA","*****ha");
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 注册监听
     */
    private void initListener() {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, conn, 0);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userName = getIntent().getStringExtra("userName");
        userNameBut.setText(userName);

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userName);
        //获取此会话的所有消息
        if (conversation != null) {
            List<EMMessage> allMessages = conversation.getAllMessages();
            if (allMessages != null) {
                messages.addAll(allMessages);
            }
        }
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
//        List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        chat_recyclerView = (RecyclerView) findViewById(R.id.chat_recyclerView);
        userNameBut = (Button) findViewById(R.id.username_but);
        chatContentEt = (EditText) findViewById(R.id.chat_content_et);
        chatSendBut = (Button) findViewById(R.id.chat_send_but);

        //监听
        chatSendBut.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_send_but:
                String content = chatContentEt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, userName);
                messages.add(message);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                chatContentEt.setText("");
                notifyMessage();
                break;
        }
    }

    /**
     * 刷新消息
     */
    private void notifyMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatRecyalerViewAdapater.notifyDataSetChanged();
                chat_recyclerView.scrollToPosition(messages.size()-1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBinder.setShow(false);
        unbindService(conn);
    }
}
