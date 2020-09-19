package com.ykq.ykqfrost.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;

import com.ykq.ykqfrost.utils.LogUtil;

/**
 * @author ykq
 * @date 2020/9/18
 */
public abstract class BaseService extends Service {

    public class LocalBinder extends Binder {
        public BaseService getService() {
            return BaseService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("====> onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("====> onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.d("====> onDestroy");
        super.onDestroy();
    }
}
