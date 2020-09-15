package com.ykq.ykqfrost.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ykq.ykqfrost.R;

/**
 * @author ykq
 * @date 2020/9/15
 */
public class HalfActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_half);
        TextView close = findViewById(R.id.close);
        close.setOnClickListener(v -> goBack());
    }

    public static void goToPage(Context context) {
        Intent intent = new Intent(context, HalfActivity.class);
        context.startActivity(intent);
    }
}
