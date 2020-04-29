package com.ykq.myeventbus;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author ykq
 * @date 2019-12-24
 */
public class SubscriberMethod {

    private Object o;
    private Method method;

    public SubscriberMethod(Object o, Method method) {
        this.o = o;
        this.method = method;
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

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;
        SubscriberMethod that = (SubscriberMethod) o1;
        return o.equals(that.o) &&
                method.equals(that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(o, method);
    }
}
