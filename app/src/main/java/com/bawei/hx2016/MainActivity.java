package com.bawei.hx2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.hx2016.base.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class MainActivity extends BaseActivity implements View.OnClickListener {

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
                try {
                    EMClient.getInstance().createAccount(nameEt.getText().toString().trim(), passwordEt.getText().toString().trim());//同步方法
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "注册失败！！", Toast.LENGTH_SHORT).show();
                }
                break;
            //登陆按钮监听
            case R.id.login_but:

                break;
        }
    }
}
