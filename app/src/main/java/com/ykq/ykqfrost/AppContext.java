package com.ykq.ykqfrost;

import android.app.Application;

import com.ykq.ykqfrost.utils.LogUtil;

/**
 * @author ykq
 * @date 2019-12-24
 */
public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("AppContext start");
    }
}
