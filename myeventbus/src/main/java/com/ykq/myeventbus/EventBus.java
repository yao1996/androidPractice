package com.ykq.myeventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;
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
            if (subscribe == null) {
                continue;
            }
            SubscriberMethod subscriberMethod = new SubscriberMethod(o, method);
            Class[] classes = method.getParameterTypes();
            if (classes.length > 1) {
                throw new RuntimeException("参数长度不正确");
            }
            CopyOnWriteArrayList<SubscriberMethod> list = subscriptionsByEventType.get(classes[0]);
            if (list == null) {
                list = new CopyOnWriteArrayList<>();
            }
            if (!list.contains(subscriberMethod)) {
                list.add(subscriberMethod);
            }
            subscriptionsByEventType.put(classes[0], list);
        }
    }

    public void post(Object o) {
        CopyOnWriteArrayList<SubscriberMethod> list = subscriptionsByEventType.get(o.getClass());
        if (list == null) {
            return;
        }
        for (SubscriberMethod method : list) {
            try {
                method.getMethod().invoke(method.getO(), o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void unRegister(Object o) {
        Set<Class<?>> set = subscriptionsByEventType.keySet();
        for (Class clazz : set) {
            CopyOnWriteArrayList<SubscriberMethod> list = subscriptionsByEventType.get(clazz);
            if (list == null) {
                continue;
            }
            for (SubscriberMethod method : list) {
                if (method.getO().equals(o)) {
                    list.remove(method);
                }
            }
            subscriptionsByEventType.put(clazz, list);
        }
    }

}
