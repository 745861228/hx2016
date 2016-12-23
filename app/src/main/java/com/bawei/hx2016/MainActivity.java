package com.bawei.hx2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.hx2016.base.BaseActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText nameEt;
    private EditText passwordEt;
    private Button commitBut;
    private Button login_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
        switch (v.getId()){
            //注册按钮监听
            case R.id.commit_but:
                //注册失败会抛出HyphenateException
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            EMClient.getInstance().createAccount(nameEt.getText().toString().trim(), passwordEt.getText().toString().trim());//同步方法
                            Log.i(TAG, "run: "+"注册成功！！！");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            Log.i(TAG, "run: "+"注册失败！"+e.getErrorCode()+","+e.getMessage());
                        }
                    }
                }.start();
                break;
            //登陆按钮监听
            case R.id.login_but:
                EMClient.getInstance().login(nameEt.getText().toString().trim(),passwordEt.getText().toString().trim(),new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.this.enterActivity(ChatActivity.class);
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
