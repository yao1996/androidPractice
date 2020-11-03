package com.ykq.ykqfrost.utils;

import com.ykq.ykqfrost.AppContext;

/**
 * @author ykq
 * @date 2020/11/3
 */
public class DisplayUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = AppContext.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
