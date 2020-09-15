package com.ykq.ykqfrost.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ykq.ykqfrost.utils.LogUtil;

/**
 * @author ykq
 * @date 2019-12-27
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected void goBack() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("lifeCycle onCreate");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        LogUtil.d("lifeCycle onContentChanged");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LogUtil.d("lifeCycle onPostCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("lifeCycle onStart");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.d("lifeCycle onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d("lifeCycle onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.d("lifeCycle onConfigurationChanged");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("lifeCycle onResume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        LogUtil.d("lifeCycle onPostResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("lifeCycle onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("lifeCycle onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("lifeCycle onDestroy");
    }
}
