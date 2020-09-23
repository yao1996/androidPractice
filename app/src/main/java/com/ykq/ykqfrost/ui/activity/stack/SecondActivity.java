package com.ykq.ykqfrost.ui.activity.stack;

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
public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d("task id " + getTaskId());
        TextView mHello = findViewById(R.id.hello);
        mHello.setText("二");
        mHello.setOnClickListener(v -> FirstActivity.goToPage(SecondActivity.this));
    }

    public static void goToPage(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }

}
