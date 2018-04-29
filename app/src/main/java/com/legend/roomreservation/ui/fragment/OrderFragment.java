package com.legend.roomreservation.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.legend.roomreservation.R;
import com.legend.roomreservation.bsae.BaseFragment;
import com.legend.roomreservation.bsae.ConnectUrl;
import com.legend.roomreservation.bsae.Constant;
import com.legend.roomreservation.customview.NoScrollListView;
import com.legend.roomreservation.entity.EventFlag;
import com.legend.roomreservation.entity.Order;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by father on 2017/12/30.
 * 说明：
 */

public class OrderFragment extends BaseFragment {
    private static final int SIZE = 10;


    @BindView(R.id.layout_swipe_refresh)
    SmartRefreshLayout mLayoutSwipeRefresh;
    @BindView(R.id.listview)
    NoScrollListView mListView;
    @BindView(R.id.null_bg)
    RelativeLayout mNullBg;
    @BindView(R.id.txt_nulll)
    TextView mTxtNulll;
    private View mView;

    private List<Order> mList;

    MyAdapter mAdapter;
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_order, null);
        ButterKnife.bind(this, mView);
        initView();
        initData();
        initListener();
        return mView;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag flag) {
        if (flag.getFlag().equals(Constant.EV_ORDER)) {
            mList.clear();
            httpGetList();
        }
    }

    private void initView() {

        mLayoutSwipeRefresh.setEnableLoadmore(false);
    }


    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MyAdapter(getActivity(), R.layout.item_order_list, mList);
        mListView.setAdapter(mAdapter);
        httpGetList();

    }


    private void initListener() {
        mLayoutSwipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                httpGetList();

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mList.get(position));
//                go(OrderDetailActivity.class, bundle);
            }
        });
    }


    private void httpGetList() {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.ORDER_LIST, RequestMethod.GET);
        request.add("userId", 1);
        mQueue.add(0, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 0) {
                    Gson gson = new Gson();
                    JSONObject object = response.get();
                    try {
                        int status = object.getInt("status");
                        if (status == 0) {
                            JSONArray array = object.getJSONArray("data");

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
                mLayoutSwipeRefresh.finishRefresh();
                if (adjustList(mList)) {
                    mNullBg.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mNullBg.setVisibility(View.VISIBLE);
                    mTxtNulll.setText(R.string.data_order_null);
                    mListView.setVisibility(View.GONE);
                }


            }
        });
    }


    private void httpChangeStatu(String no, int status, final int position) {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.ORDER_CHANGE_STATUS, RequestMethod.POST);
        request.add("orderNo", no);
        request.add("status", status);
        mQueue.add(0, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showWaiting(true, "正在处理");
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 0) {
                    Gson gson = new Gson();
                    JSONObject object = response.get();
                    try {
                        int status = object.getInt("status");
                        if (status == 0) {
                            mList.get(position).setStatus(2);
                            Log.e("状态", mList.get(position).getStatus() + "");
                            mAdapter.notifyDataSetChanged();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }


    class MyAdapter extends CommonAdapter<Order> {

        public MyAdapter(Context context, int layoutId, List datas) {
            super(context, layoutId, datas);
        }


        @Override
        protected void convert(ViewHolder holder, final Order item, final int position) {
            TextView nameTV = holder.getView(R.id.txt_business_name);
            TextView statusTV = holder.getView(R.id.txt_order_status);
            ImageView imageView = holder.getView(R.id.img_business_photo);
            TextView priceTV = holder.getView(R.id.txt_total_price);
            TextView timeTV = holder.getView(R.id.txt_created_at);
            TextView orderNoTV = holder.getView(R.id.txt_order_no);
            TextView confirmSendTV = holder.getView(R.id.btn_confirm_received);
            TextView payTV = holder.getView(R.id.btn_payment);
            TextView deleteTV = holder.getView(R.id.btn_order_delete);
            LinearLayout bottomLayout = holder.getView(R.id.item_order_bottom_layout);

            priceTV.setText(getString(R.string.label_price, item.getMoney()));
            timeTV.setText(item.getCreateTime());
            orderNoTV.setText("订单号：" + item.getOrderNo());
            switch (item.getStatus()) {//1已支付等待收货， 0 未支付 ，2 确认收货，订单结束
                case 0:
                    statusTV.setText("未支付");
                    bottomLayout.setVisibility(View.VISIBLE);
                    payTV.setVisibility(View.VISIBLE);
                    confirmSendTV.setVisibility(View.GONE);
                    deleteTV.setVisibility(View.GONE);
                    break;
                case 1:
                    bottomLayout.setVisibility(View.VISIBLE);
                    statusTV.setText("待收货");
                    payTV.setVisibility(View.GONE);
                    confirmSendTV.setVisibility(View.VISIBLE);
                    deleteTV.setVisibility(View.GONE);

                    break;
                case 2:
                    bottomLayout.setVisibility(View.VISIBLE);
                    statusTV.setText("已完成");
                    payTV.setVisibility(View.GONE);
                    confirmSendTV.setVisibility(View.GONE);
                    deleteTV.setVisibility(View.VISIBLE);
                    break;
            }


            confirmSendTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpChangeStatu(item.getOrderNo(), 2, position);
                }
            });
            payTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putFloat("price", item.getMoney());
                    bundle.putString("orderId", item.getOrderNo());
//                    go(PayWayActivity.class, bundle);
                }
            });
            nameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
//                    bundle.putInt("data", item.getBusiness().getId());
//                    go(BusinessDetailActivity.class, bundle);

                }
            });

            deleteTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpDelete(item.getOrderNo(),position);
                }
            });

            try {
//                ImageUtils.setDefaultNormalImage(imageView, item.getBusiness().getPicUrl(), R.drawable.pic_fail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void httpDelete(String  no, final int position) {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.ORDER_DELETE, RequestMethod.POST);
        request.add("orderNo", no);
//        request.add("userId", MyApplication.getUser().getId());
        mQueue.add(0, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showWaiting(true);
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 0) {
                    JSONObject object = response.get();
                    Log.e("删除", "onSucceed: " + object.toString());
                    try {
                        int status = object.getInt("status");
                        if (status == 0) {
                            mList.remove(position);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            showToast("删除失败，请重试！");
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



}
