package com.ykq.ykqfrost.ui.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.aidl.Book;
import com.ykq.ykqfrost.aidl.IBookManager;
import com.ykq.ykqfrost.service.InitService;
import com.ykq.ykqfrost.ui.activity.stack.FirstActivity;
import com.ykq.ykqfrost.utils.LogUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ykq
 * @date 2019-12-27
 */
public class MainActivity extends BaseActivity {

    private InitService initService;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = bookManager.getBookList();
                LogUtil.d("remote: type: " + list.getClass().getCanonicalName() + "  " + list.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LogUtil.d("task id " + getTaskId());
        TextView mHello = findViewById(R.id.hello);
        mHello.setOnClickListener(v -> {
            // 隐式调用
            Intent intent = new Intent("com.ykq.ykqfrost.Main");
            intent.addCategory("com.ykq.ykqfrost");
            intent.setData(Uri.parse("ykq://third"));
            MainActivity.this.startActivity(intent);
        });
        ViewGroup parent = findViewById(R.id.linearLayout);
        View view = LayoutInflater.from(this).inflate(R.layout.inflate_test_btn, parent);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        LogUtil.d("====>" + lp.toString());
        view.invalidate();
        LogUtil.d("current pid:" + android.os.Process.myPid());
    }

    @OnClick(R.id.first_activity)
    public void goToFirstActivity() {
        FirstActivity.goToPage(this);
    }

    @OnClick(R.id.start_service)
    public void startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //使用兼容库就无需判断系统版本
            int hasPermission = ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.FOREGROUND_SERVICE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                //没有权限，向用户请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, 0);
                return;
            }
        }
        Intent intent = new Intent(this, InitService.class);
        int pid = android.os.Process.myPid();
        intent.putExtra("pid", pid);
        startService(intent);
//        bindService(intent, sc, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.close_service)
    public void closeService() {
//        Intent intent = new Intent(this, InitService.class);
//        stopService(intent);
        unbindService(sc);
    }

    @OnClick(R.id.go_to_aidl)
    void goToAidl() {
        AIDLActivity.goToPage(this);
    }

    @OnClick(R.id.go_to_photo_wall)
    void goToPhotoWall() {
        PhotoWallActivity.goToActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void goToPage(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意，执行操作
                startService();
            }
        }
    }
}
