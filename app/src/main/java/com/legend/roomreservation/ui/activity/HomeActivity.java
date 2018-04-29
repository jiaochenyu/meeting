package com.legend.roomreservation.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.legend.roomreservation.R;
import com.legend.roomreservation.bsae.BaseActivity;
import com.legend.roomreservation.bsae.BaseAppManager;
import com.legend.roomreservation.ui.fragment.HomeFragment;
import com.legend.roomreservation.ui.fragment.OrderFragment;
import com.legend.roomreservation.utils.BottomNavigationViewHelper;
import com.legend.roomreservation.utils.DeviceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {
    private static final int HOME = 1;
    private static final int ORDER = 2;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.setting)
    Button mSetting;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fragment)
    FrameLayout mFragment;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    HomeFragment mHomeFragment;
     OrderFragment  mOrderFragment;
    private FragmentManager fragmentManager;

    int flagPage = HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BaseAppManager.getInstance().clearBackActivities();
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
        Log.e("设备id", DeviceUtil.getDeviceId());
    }
    private void initView() {
        mTvToolbarTitle.setText("首页");
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        showFragment(HOME);
    }

    private void initListener() {
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home://主页
                        showFragment(HOME);
                        mTvToolbarTitle.setText("首页");
                        flagPage = HOME;
                        return true;
                    case R.id.navigation_order://
                        showFragment(ORDER);
                        flagPage = ORDER;
                        mTvToolbarTitle.setText("订单");
                        return true;


                }
                return false;
            }
        });
    }


    private void showFragment(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏所有的Fragment
        hideFragment(fragmentTransaction);
        //显示指定的Fragment

        switch (index) {
            //首页
            case HOME:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.fragment, mHomeFragment);
                } else {
                    fragmentTransaction.show(mHomeFragment);
                }
                break;

            case ORDER:
                if (mOrderFragment == null) {
                    mOrderFragment = new OrderFragment();
                    fragmentTransaction.add(R.id.fragment, mOrderFragment);
                } else {
                    fragmentTransaction.show(mOrderFragment);
                }
                break;



        }

        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mOrderFragment != null) {
            fragmentTransaction.hide(mOrderFragment);
        }

    }

}
