package com.legend.roomreservation.ui.activity.photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.legend.roomreservation.R;
import com.legend.roomreservation.customview.FixMultiViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigPhotoActivity extends FragmentActivity {

    @BindView(R.id.viewpager)
    FixMultiViewPager mViewpager;
    List<String> picStrings = new ArrayList<>();
    int position = 0;

    List<BigPhotoFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photo);
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        picStrings = (List<String>) bundle.getSerializable("pathList");
        position = bundle.getInt("position", 0);
    }

    private void initView() {
        for (int i = 0; i < picStrings.size(); i++) {
            BigPhotoFragment fm = BigPhotoFragment.newInstance(picStrings.get(i));
            mFragments.add(fm);
        }
        mViewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        mViewpager.setCurrentItem(position);
    }

    private void initListener() {

    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }


}
