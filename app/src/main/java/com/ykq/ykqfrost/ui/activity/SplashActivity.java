package com.ykq.ykqfrost.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ykq.myeventbus.EventBus;
import com.ykq.myeventbus.Subscribe;
import com.ykq.ykqfrost.bean.MessageEvent;

/**
 * @author ykq
 * @date 2020-01-07
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.goToPage(this);
        finish();
    }

    @Subscribe
    public void onEvent(MessageEvent msg) {
        Toast.makeText(this, "eventBus", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unRegister(this);
    }
}
