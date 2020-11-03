package com.ykq.ykqfrost;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.ykq.ykqfrost.utils.LogUtil;

/**
 * @author ykq
 * @date 2019-12-24
 */
public class AppContext extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("AppContext start");
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
