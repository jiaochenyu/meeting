package com.legend.roomreservation.bsae;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.legend.roomreservation.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by JCY on 2017/12/23.
 * 说明：
 */

public class BaseFragment extends Fragment {
    public static final int NON_CODE = -1;
    private Dialog waitDialog;

    private Toast toast = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
    }


    protected boolean isBindEventBusHere() {
        return false;
    }


    public void initBackToolbar(String title) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public void showWaiting(boolean flag) {
        if (waitDialog != null && waitDialog.isShowing()) {
            return;
        }
        waitDialog = new Dialog(getActivity(), R.style.MyDialogStyle);
        waitDialog.setContentView(R.layout.dialog_waiting);
        waitDialog.setCancelable(flag);
        Window window = waitDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        window.setAttributes(params);


        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setGravity(Gravity.CENTER);
//        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        waitDialog.show();
    }


    public void showWaiting(boolean flag, String msg) {
        if (waitDialog != null && waitDialog.isShowing()) {
            return;
        }
        waitDialog = new Dialog(getActivity(), R.style.MyDialogStyle);
        waitDialog.setContentView(R.layout.dialog_waiting);
        waitDialog.setCancelable(flag);
        Window window = waitDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        window.setAttributes(params);

        TextView msgTV = waitDialog.findViewById(R.id.tipTextView);
        msgTV.setText(msg);

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setGravity(Gravity.CENTER);
//        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        waitDialog.show();
    }

    public void hideWaiting() {
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
    }

    // 判断list是否不为空且有值    true:不为空
    public boolean adjustList(List<?> list) {
        if (list != null && list.size() > 0 && (!list.isEmpty())) {
            return true;
        } else {
            return false;
        }
    }


    protected void go(Class<? extends Activity> clazz) {
        _goActivity(clazz, null, NON_CODE, false);
    }

    protected void go(Class<? extends Activity> clazz, Bundle bundle) {
        _goActivity(clazz, bundle, NON_CODE, false);
    }

    protected void goAndFinish(Class<? extends Activity> clazz) {
        _goActivity(clazz, null, NON_CODE, true);
    }

    protected void goAndFinish(Class<? extends Activity> clazz, Bundle bundle) {
        _goActivity(clazz, bundle, NON_CODE, true);
    }


    private void _goActivity(Class<? extends Activity> clazz, Bundle bundle, int requestCode, boolean finish) {
        if (null == clazz) {
            throw new IllegalArgumentException("you must pass a target activity where to go.");
        }
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        if (requestCode > NON_CODE) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
        if (finish) {
            getActivity().finish();
        }
    }

    public void showToast(String msg) {
        if (null != msg) {
            if (toast == null) {
                toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    public boolean isLogin() {
        if (MyApplication.getUser() == null ) {
            showToast("请登录！");
            return false;
        }
        return true;


    }


    @Override
    public void onPause() {
        super.onPause();
        if (toast != null) {
            toast.cancel();
        }
    }

    @Override
    public void onDestroy() {

        toast = null;
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
