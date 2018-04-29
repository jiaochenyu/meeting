package com.legend.roomreservation.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.legend.roomreservation.R;
import com.legend.roomreservation.bsae.BaseActivity;
import com.legend.roomreservation.bsae.ConnectUrl;
import com.legend.roomreservation.ui.activity.photo.BigPhotoActivity;
import com.legend.roomreservation.utils.NetworkImageHolderView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DateTimePicker;

public class RoomDetailActivity extends BaseActivity implements OnItemClickListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.setting)
    Button mSetting;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.layout_swipe_refresh)
    SmartRefreshLayout mLayoutSwipeRefresh;
    @BindView(R.id.submit)
    Button mSubmit;
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.txt_time_start)
    TextView mTxtTimeStart;
    @BindView(R.id.txt_time_end)
    TextView mTxtTimeEnd;
    private List<String> mBannerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvToolbarTitle.setText("详情");
        initBackToolbar(true);
        initBanner();
    }

    private void initBanner() {
        mBannerList = new ArrayList<>();
        mBannerList.add(ConnectUrl.IMAGE_BANNER_URL);
        mBannerList.add(ConnectUrl.IMAGE_BANNER_URL2);
        mBannerList.add(ConnectUrl.IMAGE_BANNER_URL3);
        mConvenientBanner.setPages(new CBViewHolderCreator() {

            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, mBannerList)
                .startTurning(2000)
                .setPageIndicator(new int[]{R.drawable.circle_black, R.drawable.circle_topic})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnPageChangeListener(this).setOnItemClickListener(this);
    }

    @OnClick({R.id.setting, R.id.submit, R.id.txt_date, R.id.txt_time_start, R.id.txt_time_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting:
                break;
            case R.id.submit:
                go(ConfirmRoomActivity.class);

                break;
            case R.id.txt_date:

                break;
            case R.id.txt_time_start:
                onYearMonthDayTimePicker(0);
                break;
            case R.id.txt_time_end:
                onYearMonthDayTimePicker(1);
                break;
        }
    }

    public void onYearMonthDayTimePicker(final int view) {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        Calendar calendar  = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        picker.setDateRangeStart(year, month, day);
        picker.setDateRangeEnd(2025, 11, 11);
        picker.setTimeRangeStart(00, 00);
        picker.setTimeRangeEnd(23, 59);
        picker.setTopLineColor(0x99FF0000);
//        picker.setLabelTextColor(0xFFFF0000);
        picker.setDividerColor(0xFFFF0000);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                if (view == 0) {
                    mTxtTimeStart.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                }else {
                    mTxtTimeEnd.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                }
            }
        });
        picker.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("pathList", (Serializable) mBannerList);
        bundle.putInt("position",position);
        go(BigPhotoActivity.class,bundle);
    }
}
