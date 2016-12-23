package com.bawei.hx2016;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.hx2016.base.BaseActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText nameEt;
    private EditText passwordEt;
    private Button commitBut;
    private Button login_but;

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE){
                        // 显示帐号在其他设备登录
                    }else{
                        //连接不到聊天服务器
                        if (NetUtils.hasNetwork(LoginActivity.this)) {
                        }

                        //当前网络不可用，请检查网络设置
                        else {
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加登陆状态监听
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());


        // 判断sdk是否登录成功过，并没有退出和被踢，否则跳转到登陆界面
        if (EMClient.getInstance().isLoggedInBefore()) {
            enterChatActivity();
            return;
        }

        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * 跳转到聊天界面
     */
    private void enterChatActivity() {
        enterActivity(MainActivity.class);
        finish();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        nameEt = (EditText) findViewById(R.id.name_et);
        passwordEt = (EditText) findViewById(R.id.password_et);
        commitBut = (Button) findViewById(R.id.commit_but);
        login_but = (Button) findViewById(R.id.login_but);

        login_but.setOnClickListener(this);
        commitBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String name = nameEt.getText().toString().trim();
        final String psd = passwordEt.getText().toString().trim();
        switch (v.getId()) {
            //注册按钮监听
            case R.id.commit_but:
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psd)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //注册失败会抛出HyphenateException
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            EMClient.getInstance().createAccount(name, psd);//同步方法
                            Log.i(TAG, "run: " + "注册成功！！！");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            Log.i(TAG, "run: " + "注册失败！" + e.getErrorCode() + "," + e.getMessage());
                        }
                    }
                }.start();
                break;
            //登陆按钮监听
            case R.id.login_but:
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psd)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                EMClient.getInstance().login(name, psd, new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                enterChatActivity();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                    }
                });

                break;
        }
    }
}
