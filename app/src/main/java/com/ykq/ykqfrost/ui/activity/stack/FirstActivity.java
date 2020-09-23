package com.ykq.ykqfrost.ui.activity.stack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.ui.activity.BaseActivity;
import com.ykq.ykqfrost.utils.LogUtil;

/**
 * @author ykq
 * @date 2020/9/15
 */
@SuppressLint("SetTextI18n")
public class FirstActivity extends BaseActivity {

    private int num = 0;
    private TextView mHello;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d("task id " + getTaskId());
        mHello = findViewById(R.id.hello);
        mHello.setOnClickListener(v -> SecondActivity.goToPage(FirstActivity.this));
        mHello.setText("一：" + num);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        num++;
        mHello.setText("一：" + num);
    }

    public static void goToPage(Context context) {
        Intent intent = new Intent(context, FirstActivity.class);
        context.startActivity(intent);
    }

}
