package com.bawei.hx2016;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bawei.hx2016.base.BaseActivity;
import com.bawei.hx2016.fragment.SingleFragment;

public class MainActivity extends BaseActivity {

    private ViewPager mainPageVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mainPageVp = (ViewPager) findViewById(R.id.main_page_vp);

        mainPageVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragment(position);
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    private Fragment getFragment(int position) {
        if (position == 0) {
            return new SingleFragment();
        } else if (position == 1) {
            return new Fragment();
        }
        return new Fragment();
    }
}
