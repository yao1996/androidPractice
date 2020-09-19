package com.ykq.ykqfrost.service;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ykq.ykqfrost.utils.LogUtil;

/**
 * @author ykq
 * @date 2020/9/18
 */
public class InitService extends BaseService {

    private IBinder binder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("====>onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
