package com.ykq.ykqfrost.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * @author ykq
 * @date 2020/11/9
 */
public class ProcessUtils {

    /**
     * 判断进程是否存活
     */
    public static boolean isProcessExist(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists;
        if (am != null) {
            lists = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : lists) {
                if (appProcess.pid == pid) {
                    return true;
                }
            }
        }
        return false;
    }
}
