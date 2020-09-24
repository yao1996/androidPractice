package com.ykq.ykqfrost.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.aidl.Book;
import com.ykq.ykqfrost.aidl.IBookManager;
import com.ykq.ykqfrost.aidl.IOnNewBookArrivedListener;
import com.ykq.ykqfrost.service.InitService;
import com.ykq.ykqfrost.utils.LogUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ykq
 * @date 2020/9/23
 */
public class AIDLActivity extends BaseActivity {

    private static final int MSG_NEW_BOOK_ARRIVED = 1;
    private IOnNewBookArrivedListener listener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            LogUtil.d("onBookArrived: " + book);
            mHandler.obtainMessage(MSG_NEW_BOOK_ARRIVED, book);
        }
    };
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                bookManager.registerOnNewBookArrivedListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_NEW_BOOK_ARRIVED) {
                LogUtil.d("onBookArrived: " + msg.obj);
            }
        }
    };
    private IBookManager bookManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_service)
    void startService() {
        Intent intent = new Intent(this, InitService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);
    }

    @OnClick(R.id.get_book_list)
    void getBookList() {
        if (bookManager == null) {
            return;
        }
        try {
            List<Book> list = bookManager.getBookList();
            LogUtil.d("getFromServer: " + list.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.add_book)
    void addBook() {
        if (bookManager == null) {
            return;
        }
        try {
            bookManager.addBook(new Book(3, "c"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (bookManager != null && bookManager.asBinder().isBinderAlive()) {
            try {
                bookManager.unRegisterOnNewBookArrivedListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            unbindService(sc);
        }
        super.onDestroy();
    }

    public static void goToPage(Context context) {
        Intent intent = new Intent(context, AIDLActivity.class);
        context.startActivity(intent);
    }
}
