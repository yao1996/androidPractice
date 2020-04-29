package com.ykq.ykqfrost.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ykq.myeventbus.EventBus;
import com.ykq.myeventbus.Subscribe;
import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.bean.MessageEvent;

/**
 * @author ykq
 * @date 2020-01-07
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.Splash_logo);
        setContentView(R.layout.activity_splash);
        EventBus.getDefault().register(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.goToPage(SplashActivity.this);
                finish();
            }
        }, 1500);
    }

    @Subscribe
    public void onEvent(MessageEvent msg) {
        Toast.makeText(this, "eventBus", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("====>", "destroy");
        EventBus.getDefault().unRegister(this);
    }
}
