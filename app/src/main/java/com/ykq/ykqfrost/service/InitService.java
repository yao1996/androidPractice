package com.ykq.ykqfrost.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.aidl.Book;
import com.ykq.ykqfrost.aidl.IBookManager;
import com.ykq.ykqfrost.aidl.IOnNewBookArrivedListener;
import com.ykq.ykqfrost.ui.activity.MainActivity;
import com.ykq.ykqfrost.utils.LogUtil;
import com.ykq.ykqfrost.utils.ProcessUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ykq
 * @date 2020/9/18
 */
public class InitService extends BaseService {
    private CopyOnWriteArrayList<Book> list = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> listeners = new RemoteCallbackList<>();
    Handler handler = new Handler();

    private IBinder binder = new IBookManager.Stub() {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public IBinder asBinder() {
            return super.asBinder();
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            LogUtil.d("thread: " + Thread.currentThread().getName());
            return list;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            list.add(book);
            int length = listeners.beginBroadcast();
            for (int i = 0; i < length; i++) {
                IOnNewBookArrivedListener listener = listeners.getBroadcastItem(i);
                if (listener != null) {
                    listener.onNewBookArrived(book);
                }
            }
            listeners.finishBroadcast();
        }

        @Override
        public void registerOnNewBookArrivedListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.register(listener);
            LogUtil.d("listener list size :  " + listeners.beginBroadcast());
            listeners.finishBroadcast();
        }

        @Override
        public void unRegisterOnNewBookArrivedListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.unregister(listener);
            LogUtil.d("listener list size :  " + listeners.beginBroadcast());
            listeners.finishBroadcast();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("====>onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        list.add(new Book(1, "a"));
        list.add(new Book(2, "b"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startNotification();
        int pid = intent.getIntExtra("pid", 0);
        checkPid(pid);
        LogUtil.d("current pid:" + android.os.Process.myPid());
        return super.onStartCommand(intent, flags, startId);
    }

    private void startNotification() {
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, activityIntent, 0);
        Notification notification;
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel("ykqfrost", "ykqfrost", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
            notification = new Notification.Builder(getApplication(), "ykqfrost").setAutoCancel(true).
                    setSmallIcon(R.mipmap.ic_launcher).setTicker("前台Service启动").setContentTitle("前台Service运行中").
                    setContentText("这是一个正在运行的前台Service").setWhen(System.currentTimeMillis()).setContentIntent(pendingIntent).build();
        } else {
            notification = new Notification.Builder(getApplication()).setAutoCancel(true).
                    setSmallIcon(R.mipmap.ic_launcher).setTicker("前台Service启动").setContentTitle("前台Service运行中").
                    setContentText("这是一个正在运行的前台Service").setWhen(System.currentTimeMillis()).setContentIntent(pendingIntent).build();
        }
        startForeground(1, notification);
    }

    private void checkPid(int pid) {
        boolean isProcessExists = ProcessUtils.isProcessExist(this, pid);
        LogUtil.d("pid: " + pid + ", exists:" + isProcessExists);
        handler.postDelayed(() -> checkPid(pid), 5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
