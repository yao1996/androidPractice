// IOnNewBookArrivedListener.aidl
package com.ykq.ykqfrost.aidl;

// Declare any non-default types here with import statements
import com.ykq.ykqfrost.aidl.Book;

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book book);

}
