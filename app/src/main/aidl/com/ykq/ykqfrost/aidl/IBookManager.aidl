// IBookManager.aidl
package com.ykq.ykqfrost.aidl;

import com.ykq.ykqfrost.aidl.Book;
import com.ykq.ykqfrost.aidl.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {

    List<Book> getBookList();

    void addBook(in Book book);

    void registerOnNewBookArrivedListener(in IOnNewBookArrivedListener listener);

    void unRegisterOnNewBookArrivedListener(in IOnNewBookArrivedListener listener);
}
