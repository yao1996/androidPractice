package com.ykq.myeventbus;

import java.lang.reflect.Method;

/**
 * @author ykq
 * @date 2019-12-24
 */
public class SubscriberMethod {

    private Object o;
    private Method method;
    private Class<?> paramType;

    public SubscriberMethod(Object o, Method method) {
        this.o = o;
        this.method = method;
    }

    public SubscriberMethod(Object o, Method method, Class<?> paramType) {
        this.o = o;
        this.method = method;
        this.paramType = paramType;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }
}
