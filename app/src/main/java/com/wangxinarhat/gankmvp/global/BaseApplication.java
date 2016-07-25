package com.wangxinarhat.gankmvp.global;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.wangxinarhat.gankmvp.BuildConfig;

/**
 * Base Application
 * @author wangxinarhat 
 * creat at 2016/7/25
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

        //只有调试模式下 才启用日志输出
        if (BuildConfig.DEBUG) {
            Logger.init("GankMVP").hideThreadInfo().setMethodCount(0);
        } else {
            Logger.init("GankMVP").setLogLevel(LogLevel.NONE);
        }
    }
}
