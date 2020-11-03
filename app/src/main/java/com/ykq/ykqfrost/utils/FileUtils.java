package com.ykq.ykqfrost.utils;

import android.os.StatFs;

import com.ykq.ykqfrost.AppContext;

import java.io.File;

/**
 * @author ykq
 * @date 2020/11/3
 */
public class FileUtils {

    public static final String IMAGE_CACHE_DIR = AppContext.getContext().getCacheDir().getAbsolutePath() + File.separator + "image";

    /**
     * 判断剩余存储空间是否足够
     *
     * @param path         文件位置
     * @param requireSpace 所需空间, bytes
     * @return enough or not
     */
    public static boolean checkSpaceEnough(String path, long requireSpace) {
        StatFs statFs = new StatFs(path);
        long available = statFs.getAvailableBytes();
        return available > requireSpace;
    }

}
