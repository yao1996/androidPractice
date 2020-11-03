package com.ykq.ykqfrost.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ykq
 * @date 2020/11/3
 */
public class ImageLoader {

    private static final LruCache<String, Bitmap> mMemoryCache;
    private static DiskLruCache mDiskCache;
    private static final int CORE_THREAD_SIZE = Runtime.getRuntime().availableProcessors() + 1;
    private static final int MAX_THREAD_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;
    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_THREAD_SIZE, MAX_THREAD_SIZE, 10L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    });
    // 缓存大小50MB
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    // 初始化2个缓存
    static {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        // 进程可用内存的1/8
        int cacheMemory = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 单位与maxMemory的单位一致
                return value.getByteCount();
            }
        };
        File diskCacheDir = new File(FileUtils.IMAGE_CACHE_DIR);
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (FileUtils.checkSpaceEnough(diskCacheDir.getAbsolutePath(), DISK_CACHE_SIZE)) {
            try {
                mDiskCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将bitmap设置到imageView上
     *
     * @param uri            图片地址
     * @param imageView      view
     * @param requiredWidth  所需最小宽度，进行压缩，为0时不压缩
     * @param requiredHeight 所需最小高度，进行压缩，为0时不压缩
     */
    public static void bindBitmap(String uri, ImageView imageView, int requiredWidth, int requiredHeight) {
        // 用于判断view是否仍需该图片，防止recyclerView等地方复用view后图片修改
        imageView.setTag(uri);
        Bitmap bitmap = loadBitmapFromMemory(uri);
        if (bitmap != null) {
            LogUtil.d("====> getImageFromMemory");
            mHandler.post(() -> imageView.setImageBitmap(bitmap));
            return;
        }
        THREAD_POOL_EXECUTOR.execute(() -> {
            Bitmap b = loadBitmapFromDiskCache(uri, requiredWidth, requiredHeight);
            if (b == null) {
                b = loadBitmapFromHttp(uri, requiredWidth, requiredHeight);
                LogUtil.d("====> getImageFromHttp");
            } else {
                LogUtil.d("====> getImageFromDisk");
            }
            if (b != null && imageView.getTag() == uri) {
                Bitmap finalB = b;
                mHandler.post(() -> {
                    LogUtil.d("====> setImage");
                    imageView.setImageBitmap(finalB);
                });
            }
        });
    }

    private static Bitmap loadBitmapFromHttp(String urlString, int requiredWidth, int requiredHeight) {
        // 不能在主线程下载图片
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread");
        }
        // 剩余空间不足时未初始化
        if (mDiskCache == null) {
            return null;
        }
        downloadToDiskCache(urlString);
        return loadBitmapFromDiskCache(urlString, requiredWidth, requiredHeight);
    }

    /**
     * 从文件缓存获取图片
     *
     * @param requiredWidth  所需最小宽度，进行压缩，为0时不压缩
     * @param requiredHeight 所需最小高度，进行压缩，为0时不压缩
     * @return 图片
     */
    private static Bitmap loadBitmapFromDiskCache(String urlString, int requiredWidth, int requiredHeight) {
        // 不能在主线程从磁盘加载图片
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread");
        }
        String key = MD5Util.hashUrlKey(urlString);
        Bitmap bitmap = null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskCache.get(key);
            if (snapshot != null) {
                FileInputStream fis = (FileInputStream) snapshot.getInputStream(0);
                FileDescriptor fd = fis.getFD();
                if (requiredWidth == 0 && requiredHeight == 0) {
                    bitmap = BitmapFactory.decodeFileDescriptor(fd, null, null);
                } else {
                    bitmap = ImageResizer.decodeSampledImageFromFileDescriptor(fd, requiredWidth, requiredHeight);
                }
                addToMemoryCache(key, bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static Bitmap loadBitmapFromMemory(String uri) {
        String key = MD5Util.hashUrlKey(uri);
        if (mMemoryCache == null) {
            return null;
        }
        return mMemoryCache.get(key);
    }

    /**
     * 下载url文件内容至DiskLRUCache缓存
     */
    private static void downloadToDiskCache(String urlString) {
        try {
            DiskLruCache.Editor editor = mDiskCache.edit(MD5Util.hashUrlKey(urlString));
            if (editor == null) {
                return;
            }
            OutputStream os = editor.newOutputStream(0);
            boolean b = downloadUrlToStream(urlString, os);
            if (b) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache == null || bitmap == null) {
            return;
        }
        mMemoryCache.put(key, bitmap);
    }

    /**
     * 下载文件内容至OutputStream
     */
    private static boolean downloadUrlToStream(String urlString, OutputStream os) {
        HttpURLConnection connection = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(connection.getInputStream());
            bos = new BufferedOutputStream(os);
            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            close(bis);
            close(bos);
        }
        return false;
    }

    /**
     * 关闭流
     *
     * @param closeable 流
     */
    private static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
