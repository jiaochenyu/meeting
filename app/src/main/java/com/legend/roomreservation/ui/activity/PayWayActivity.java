package com.legend.roomreservation.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.legend.roomreservation.R;
import com.legend.roomreservation.bsae.BaseActivity;
import com.legend.roomreservation.bsae.ConnectUrl;
import com.legend.roomreservation.bsae.Constant;
import com.legend.roomreservation.entity.EventFlag;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayWayActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.setting)
    Button mSetting;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_icon)
    ImageView mImgIcon;
    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.radio)
    RadioButton mRadio;
    @BindView(R.id.divider)
    View mDivider;
    @BindView(R.id.img_icon2)
    ImageView mImgIcon2;
    @BindView(R.id.txt_name2)
    TextView mTxtName2;
    @BindView(R.id.radio2)
    RadioButton mRadio2;
    @BindView(R.id.btn_payment)
    AppCompatButton mBtnPayment;
    @BindView(R.id.pay1)
    LinearLayout mPay1;
    @BindView(R.id.pay2)
    LinearLayout mPay2;

    int pay;
    float price = 0.0f;
    String orderId;
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_way);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            price = extras.getFloat("price");
            orderId = extras.getString("orderId");
            Log.e("paywayactivity", "getBundleExtras: " + price);
        }
    }

    @SuppressLint("StringFormatInvalid")
    private void initView() {
        mTvToolbarTitle.setText("支付");
        initBackToolbar(true);
        mBtnPayment.setText(getString(R.string.btn_payment, price));
    }

    private void initData() {

    }

    private void initListener() {

    }

    @OnClick({R.id.pay1, R.id.pay2, R.id.btn_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay1:
                pay = 1;
                updateUi();
                break;
            case R.id.pay2:
                pay = 2;
                updateUi();
                break;
            case R.id.btn_payment:
                httpPay();
                break;
        }
    }

    private void updateUi() {
        if (pay == 1) {
            mRadio.setChecked(true);
            mRadio2.setChecked(false);
        } else if (pay == 2) {
            mRadio.setChecked(false);
            mRadio2.setChecked(true);
        }
    }

    private void httpPay() {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.ORDER_PAY, RequestMethod.POST);
        request.add("orderNo", orderId);
        mQueue.add(0, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showWaiting(true, "正在支付。。。");
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 0) {
                    Gson gson = new Gson();
                    JSONObject object = response.get();
                    try {
                        int status = object.getInt("status");
                        if (status == 0) {
                            showToast("支付成功！");
                            httpClearCart();
                            EventBus.getDefault().post(new EventFlag(Constant.EV_ORDER));
                            go(MainActivity.class);
                        } else {
                            showToast("支付失败请重试！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

            }

            @Override
            public void onFinish(int what) {
                hideWaiting();
            }
        });
    }


    private void httpClearCart() {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.CART_DELETE, RequestMethod.GET);
//        request.add("userId", MyApplication.getUser().getId());
        mQueue.add(0, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 0) {
                    JSONObject object = response.get();
                    Log.e("删除", "onSucceed: " + object.toString());
                    try {
                        int status = object.getInt("status");
                        if (status == 0) {

                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

            }

            @Override
            public void onFinish(int what) {


            }
        });
    }
}
