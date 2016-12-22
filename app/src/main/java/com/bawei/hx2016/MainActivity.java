package com.bawei.hx2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bawei.hx2016.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private EditText nameEt;
    private EditText passwordEt;
    private Button commitBut;

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
    }
}
