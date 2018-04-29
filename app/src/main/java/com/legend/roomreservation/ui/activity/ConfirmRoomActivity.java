package com.legend.roomreservation.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.legend.roomreservation.R;
import com.legend.roomreservation.bsae.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmRoomActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.setting)
    Button mSetting;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_name)
    EditText mEditName;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.txt_time_start)
    TextView mTxtTimeStart;
    @BindView(R.id.txt_time_end)
    TextView mTxtTimeEnd;
    @BindView(R.id.txt_price)
    TextView mTxtPrice;
    @BindView(R.id.edit_remake)
    EditText mEditRemake;
    @BindView(R.id.cancel)
    AppCompatButton mCancel;
    @BindView(R.id.yes)
    AppCompatButton mYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_room);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initBackToolbar(true);
        mTvToolbarTitle.setText("确认订单");
    }

    @OnClick({R.id.cancel, R.id.yes, R.id.txt_time_start, R.id.txt_time_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                break;
            case R.id.yes:
                go(PayWayActivity.class);
                break;
            case R.id.txt_time_start:
                break;
            case R.id.txt_time_end:
                break;
        }
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


}
