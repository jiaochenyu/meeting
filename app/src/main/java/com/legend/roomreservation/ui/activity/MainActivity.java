package com.legend.roomreservation.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.legend.roomreservation.R;
import com.legend.roomreservation.bsae.BaseActivity;
import com.legend.roomreservation.bsae.ConnectUrl;
import com.legend.roomreservation.bsae.Constant;
import com.legend.roomreservation.entity.Area;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.setting)
    Button mSetting;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_large)
    TextView mTxtLarge;
    @BindView(R.id.edit_num)
    EditText mEditNum;
    @BindView(R.id.flowlayout)
    TagFlowLayout mFlowLayout;
    List<Area> mAreas;
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {

    }

    private void initData() {
        mFlowLayout.setAdapter(new TagAdapter<String>(Constant.CONDITIONs) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_tag_name,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        Set<Integer> mSet = mFlowLayout.getSelectedList();


    }

    private void initListener() {
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                showToast(position + "");
                return false;
            }
        });

        Set<Integer> mSet = mFlowLayout.getSelectedList();
        for (Integer pos : mSet) {
//            Log.e("setPosition", "jugeData: "+ pos);

        }

    }



    private void httpGetAreaList() {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.AREA_LIST, RequestMethod.GET);
//        request.add("page", page);
//        request.add("size", SIZE);
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
                                Area info = gson.fromJson(array.getJSONObject(i).toString(), Area.class);
                                mAreas.add(info);
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
              
            }
        });
    }


}
