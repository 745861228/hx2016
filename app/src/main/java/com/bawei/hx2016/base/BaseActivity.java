package com.bawei.hx2016.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected void enterActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    public void enterActivity(Class activity, String key, String value) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(key, value);
        startActivity(intent);
    }
}
