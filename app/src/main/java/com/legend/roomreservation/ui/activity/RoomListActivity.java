package com.legend.roomreservation.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.legend.roomreservation.R;
import com.legend.roomreservation.bsae.BaseActivity;
import com.legend.roomreservation.bsae.ConnectUrl;
import com.legend.roomreservation.entity.Area;
import com.legend.roomreservation.entity.Room;
import com.legend.roomreservation.utils.ImageUtils;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomListActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.setting)
    Button mSetting;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.layout_swipe_refresh)
    SmartRefreshLayout mLayoutSwipeRefresh;

    List<Room> mList;
    MyAdapter mAdapter;
    @BindView(R.id.txt_nulll)
    TextView mTxtNulll;
    @BindView(R.id.null_bg)
    RelativeLayout mNullBg;
    @BindView(R.id.txt_distance)
    TextView mTxtDistance;
    @BindView(R.id.txt_price)
    TextView mTxtPrice;
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    String conditionArray = "";
    Area mArea;
    int people = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mArea = (Area) extras.getSerializable("area");
            conditionArray = extras.getString("requirement");
            people = extras.getInt("people");
        }
    }

    private void initView() {
        mTvToolbarTitle.setText("查找");
        mLayoutSwipeRefresh.setEnableLoadmore(false);
        initBackToolbar(true);
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MyAdapter(this, R.layout.item_room, mList);
        mListView.setAdapter(mAdapter);
        httpGetList("");
    }


    private void initListener() {
        mLayoutSwipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                httpGetList("");

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
//                bundle.putInt("data", mList.get(position).getId());
                go(RoomDetailActivity.class, bundle);
            }
        });
    }

    private void httpGetList(String flag) {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.SEARCH, RequestMethod.POST);
        request.add("people", people);
        request.add("min", mArea.getPeopleMin());
        request.add("max", mArea.getPeopleMax());
        request.add("mList", conditionArray);
        if (flag.equals("D")){
            request.add("distance", 0);
        }else if (flag.equals("P")){
            request.add("price", 0);
        }


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
                            for (int i = 0; i < array.length(); i++) {
                                Room info = gson.fromJson(array.getJSONObject(i).toString(), Room.class);
                                mList.add(info);
                            }
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
                    mTxtNulll.setText(R.string.data_condition_null);
                    mListView.setVisibility(View.GONE);
                }
            }
        });
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

    @OnClick({R.id.txt_distance, R.id.txt_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_distance:
                mList.clear();
                httpGetList("D");
                break;
            case R.id.txt_price:
                mList.clear();
                httpGetList("P");
                break;
        }
    }

    class MyAdapter extends CommonAdapter<Room> {

        public MyAdapter(Context context, int layoutId, List datas) {
            super(context, layoutId, datas);
        }


        @SuppressLint("StringFormatMatches")
        @Override
        protected void convert(ViewHolder holder, final Room item, int position) {
            ImageView imageViewTV = holder.getView(R.id.img_photo);
            TextView nameTV = holder.getView(R.id.txt_name);
            TextView largeTV = holder.getView(R.id.txt_large);
            TextView contentTV = holder.getView(R.id.txt_content);
            TextView priceTV = holder.getView(R.id.txt_price);
            TextView distanceTV = holder.getView(R.id.txt_distance);
            try {
                nameTV.setText(item.getName());
                largeTV.setText(item.getPeoplenumMin() + " - " + item.getPeoplenumMax() + "人");
                contentTV.setText(item.getAddressDetail());
                priceTV.setText(item.getPrice() + "元/天");
                distanceTV.setText(item.getAddressKm() + "KM");
                ImageUtils.setDefaultNormalImage(imageViewTV, item.getPicUrl(), R.drawable.pic_fail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
