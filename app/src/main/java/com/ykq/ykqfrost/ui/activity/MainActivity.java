package com.ykq.ykqfrost.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.ui.activity.stack.FirstActivity;

/**
 * @author ykq
 * @date 2019-12-27
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mHello = findViewById(R.id.hello);
        mHello.setOnClickListener(v -> FirstActivity.goToPage(MainActivity.this));
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
