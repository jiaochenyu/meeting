package com.legend.roomreservation.bsae;

import android.app.Application;

import com.legend.roomreservation.entity.User;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;

/**
 * Created by JCY on 2018/4/15.
 * 说明：
 */

public class MyApplication extends Application {
    private static MyApplication inst;
    private static User mUser;


    @Override
    public void onCreate() {
        super.onCreate();
        inst = MyApplication.this;

        InitializationConfig config = InitializationConfig.newBuilder(this)
//                .addHeader("Content-Type", "multipart/form-data; boundary=-----------------------------264141203718551") // 全局请求头。
                .connectionTimeout(30 * 1000)
                .readTimeout(30 * 1000)
                .retry(10)
                .build();
        NoHttp.initialize(config);
        Logger.setDebug(true);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("NoHttpSample");
    }
    public static MyApplication getInst() {
        return inst;
    }

    public static User getUser() {
//        User user = (User) SPUtils.get(MyApplication.getInst(), "userInfo", User.class);
//        if (user == null) {
//            return mUser;
//        } else {
//            return user;
//        }
        return mUser;
//        User user = (User) SPUtils.get(MyApplication.getInst(), "userInfo", new TypeToken<User>() {
//        }.getType());

    }
    public static void setUser(User user) {
//        SPUtils.put(MyApplication.getInst(), "userInfo", user);
        mUser = user;
    }
}
