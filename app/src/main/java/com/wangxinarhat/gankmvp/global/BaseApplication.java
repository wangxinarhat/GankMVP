package com.wangxinarhat.gankmvp.global;

import android.app.Application;

/**
 * Created by wang on 2016/7/22.
 */
public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    public static synchronized BaseApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mApplication == null) {
            mApplication = this;
        }
    }
}
