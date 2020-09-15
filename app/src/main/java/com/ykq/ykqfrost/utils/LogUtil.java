package com.ykq.ykqfrost.utils;

import android.util.Log;

/**
 * @author ykq
 * @date 2020/9/15
 * 日志
 */
public class LogUtil {

    private static final String TAG = "YkqFrost";

    public static void d(String msg) {
        Log.d(TAG, buildMessage(false, msg));
    }

    public static void dStack(String msg) {
        Log.d(TAG, buildMessage(true, msg));
    }

    private static String buildMessage(boolean logStack, String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(caller.getClassName())
                .append(".")
                .append(caller.getMethodName())
                .append("(): [")
                .append(caller.getLineNumber())
                .append("]")
                .append(msg).
                append("\n");

        if (logStack) {
            for (StackTraceElement ste : stackTraceElements) {
                sb.append(ste).append("\n");
            }
        }
        return sb.toString();
    }
}
