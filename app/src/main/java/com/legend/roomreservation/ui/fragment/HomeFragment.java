package com.legend.roomreservation.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.legend.roomreservation.R;
import com.legend.roomreservation.bsae.BaseFragment;
import com.legend.roomreservation.bsae.ConnectUrl;
import com.legend.roomreservation.entity.Area;
import com.legend.roomreservation.entity.Requirement;
import com.legend.roomreservation.ui.activity.RoomListActivity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by JCY on 2018/4/25.
 * 说明：
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.txt_large)
    TextView mTxtLarge;
    @BindView(R.id.edit_num)
    EditText mEditNum;
    @BindView(R.id.flowlayout)
    TagFlowLayout mFlowLayout;
    private View mView;
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    private List<Area> mAreas;
    private List<Requirement> mRequirements;

    private OptionPicker picker;

    int areasPostion = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, mView);
        initView();
        initData();
        initListener();
        return mView;
    }

    private void initView() {

    }

    private void initData() {
        mAreas = new ArrayList<>();
        mRequirements = new ArrayList<>();
        httpGetAreaList();
        httpGetRequirementList();


    }

    private void initListener() {
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return false;
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.txt_large, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_large:
                if (adjustList(mAreas)) {
                    picker.show();
                }
                break;
            case R.id.btn_search:
                search();
                break;
        }
    }

    public void search() {
        Set<Integer> mSet = mFlowLayout.getSelectedList();
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        try {
            for (Integer pos : mSet) {
                object.put("id", mRequirements.get(pos).getId());
                object.put("name", mRequirements.get(pos).getName());
                array.put(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("area", mAreas.get(areasPostion));
        bundle.putInt("people", Integer.parseInt(mEditNum.getText().toString()));
        bundle.putString("requirement", array.toString());
        go(RoomListActivity.class, bundle);
    }

    public void initOptionPicker(String[] areas) {
        picker = new OptionPicker(getActivity(), areas);
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(areasPostion);
        picker.setCycleDisable(true);
        picker.setTextSize(14);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                areasPostion = index;
                mTxtLarge.setText(item);
            }
        });

    }


    private void httpGetAreaList() {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.AREA_LIST, RequestMethod.GET);
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
                            if (adjustList(mAreas)) {
                                String item[] = new String[mAreas.size()];
                                for (int i = 0; i < mAreas.size(); i++) {
                                    if (mAreas.get(i).getPeopleMax() > 9000) {
                                        item[i] = mAreas.get(i).getPeopleMin() + "人以上（" + mAreas.get(i).getArea() + "m²）";
                                    } else {
                                        item[i] = mAreas.get(i).getPeopleMin() + " ~ " + mAreas.get(i).getPeopleMax() + "人   （" + mAreas.get(i).getArea() + "m²）";
                                    }
                                }
                                Log.e("大小", "onSucceed: " + mAreas.size());
                                initOptionPicker(item);
                                mTxtLarge.setText(item[0]);
                            } else {
                                mTxtLarge.setText("不限");
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


    private void httpGetRequirementList() {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.AREA_REQUIREMENT, RequestMethod.GET);
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
                                Requirement info = gson.fromJson(array.getJSONObject(i).toString(), Requirement.class);
                                mRequirements.add(info);
                            }

                            if (adjustList(mRequirements)) {
                                String item[] = new String[mRequirements.size()];
                                for (int i = 0; i < mRequirements.size(); i++) {
                                    item[i] = mRequirements.get(i).getName();
                                }
                                initFlowlayout(item);
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

    private void initFlowlayout(String[] sArray) {
        mFlowLayout.setAdapter(new TagAdapter<String>(sArray) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_tag_name,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

    }
}
