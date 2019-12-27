package com.ykq.ykqfrost;

import android.app.Application;

import com.ykq.myeventbus.EventBus;
import com.ykq.myeventbus.Subscribe;

/**
 * @author ykq
 * @date 2019-12-24
 */
public class AppContext{

    @Subscribe
    public void test() {

    }

    public void test1() {

    }

    public static void main(String[] args) {
        AppContext context = new AppContext();
        EventBus.getDefault().register(context);
    }
}
