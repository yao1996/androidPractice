package com.ykq.ykqfrost.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.service.InitService;
import com.ykq.ykqfrost.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ykq
 * @date 2019-12-27
 */
public class MainActivity extends BaseActivity {

    private InitService initService;
    private ServiceConnection sc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtil.d("====> onServiceConnected");
                initService = (InitService) ((InitService.LocalBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.d("====> onServiceDisconnected");
            }
        };
        TextView mHello = findViewById(R.id.hello);
        mHello.setOnClickListener(v -> {
            // 隐式调用
            Intent intent = new Intent("com.ykq.ykqfrost.Main");
            intent.addCategory("com.ykq.ykqfrost");
            intent.setData(Uri.parse("ykq://third"));
            MainActivity.this.startActivity(intent);
        });
    }


    @OnClick(R.id.start_service)
    public void startService() {
        Intent intent = new Intent(this, InitService.class);
//        startService(intent);
        bindService(intent, sc, Context.BIND_AUTO_CREATE);
        LogUtil.d("====>111");
    }

    @OnClick(R.id.close_service)
    public void closeService() {
//        Intent intent = new Intent(this, InitService.class);
//        stopService(intent);
        unbindService(sc);
        LogUtil.d("====>222");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void goToPage(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
