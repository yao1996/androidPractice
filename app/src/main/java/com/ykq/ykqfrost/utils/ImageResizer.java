package com.ykq.ykqfrost.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * @author ykq
 * @date 2020/11/3
 * 图片合适大小
 */
public class ImageResizer {

    public static Bitmap decodeSampledImageFromFileDescriptor(FileDescriptor fd, int requiredWidth, int requiredHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = getInSampleSize(options, requiredWidth, requiredHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 获取压缩率
     * 为保证清晰度，压缩后的图片宽和高都需大于所需的宽高
     *
     * @param options 从中获取图像初始宽高
     * @return 压缩比例，最终宽高都将除以该数
     */
    private static int getInSampleSize(BitmapFactory.Options options, int requiredWidth, int requiredHeight) {
        int inSampleSize = 2;
        int originWidth = options.outWidth / inSampleSize;
        int originHeight = options.outHeight / inSampleSize;
        while (originWidth > requiredWidth && originHeight > requiredHeight) {
            inSampleSize *= 2;
            originHeight /= 2;
            originWidth /= 2;
        }
        return inSampleSize / 2;
    }

}
