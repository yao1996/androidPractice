package com.ykq.ykqfrost.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.TextView;

import androidx.annotation.Nullable;

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
    }

    @OnClick(R.id.first_activity)
    public void goToFirstActivity() {
        FirstActivity.goToPage(this);
    }

    @OnClick(R.id.start_service)
    public void startService() {
        Intent intent = new Intent(this, InitService.class);
//        startService(intent);
        bindService(intent, sc, Context.BIND_AUTO_CREATE);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void goToPage(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
