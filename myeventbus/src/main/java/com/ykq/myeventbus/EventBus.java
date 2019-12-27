package com.ykq.myeventbus;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ykq
 * @date 2019-12-15
 */
public class EventBus {

    private volatile static EventBus instance;
    /**
     * 参数与方法
     */
    private HashMap<Class<?>, CopyOnWriteArrayList<SubscriberMethod>> subscriptionsByEventType = new HashMap<>();

    private EventBus() {
    }

    public static EventBus getDefault() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    public void register(Object o) {
        Class klass = o.getClass();
        Method[] methods = klass.getDeclaredMethods();
        for (Method method : methods) {
            Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe != null) {
                SubscriberMethod subscriberMethod = new SubscriberMethod(o, method);
                Class[] classes = method.getParameterTypes();
                if (classes.length == 1) {
                    CopyOnWriteArrayList<SubscriberMethod> list = subscriptionsByEventType.get(classes[0]);
                    if (list == null) {
                        list = new CopyOnWriteArrayList<>();
                    }
                    list.add(subscriberMethod);
                } else {
                    throw new RuntimeException("参数长度不正确");
                }
            }
        }
    }

}
