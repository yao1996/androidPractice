package com.ykq.ykqfrost.ui.activity.stack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.ui.activity.BaseActivity;

/**
 * @author ykq
 * @date 2020/9/15
 */
public class ThirdActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mHello = findViewById(R.id.hello);
        mHello.setText("三");
        mHello.setOnClickListener(v -> ThirdActivity.goToPage(ThirdActivity.this));
    }

    public static void goToPage(Context context) {
        Intent intent = new Intent(context, ThirdActivity.class);
        context.startActivity(intent);
    }
}
