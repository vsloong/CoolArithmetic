package com.zxxxy.coolarithmetic.sudoku.utils;

import android.content.Context;

/**
 * @author Administrator
 * @创建时间 2015-7-4 下午2:55:47
 * @描述 TODO
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-04 14:57:46 +0800 (Sat, 04 Jul 2015) $
 * @ 当前版本: $Rev: 9 $
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
