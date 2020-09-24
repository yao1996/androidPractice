package com.ykq.ykqfrost.service;

import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.ykq.ykqfrost.aidl.Book;
import com.ykq.ykqfrost.aidl.IBookManager;
import com.ykq.ykqfrost.aidl.IOnNewBookArrivedListener;
import com.ykq.ykqfrost.utils.LogUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ykq
 * @date 2020/9/18
 */
public class InitService extends BaseService {
    private CopyOnWriteArrayList<Book> list = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> listeners = new RemoteCallbackList<>();

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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
