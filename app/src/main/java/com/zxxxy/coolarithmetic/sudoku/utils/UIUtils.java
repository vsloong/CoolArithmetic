package com.zxxxy.coolarithmetic.sudoku.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import com.zxxxy.coolarithmetic.base.BaseApplication;

/**
 * Author:ZengYinan zengyinanos@qq.com
 * Date:2016/6/14
 * Time:17:13
 * Desc: 和UI相关的工具类
 */
public class UIUtils {
    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getInstance();
    }

    /**
     * 得到Resource对象
     *
     * @return
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到String.xml中的字符串，带占位符
     *
     * @param resId
     * @return
     */
    public static String getString(int resId, Object... formatArgs) {
        return getResource().getString(resId, formatArgs);
    }

    /**
     * 得到String.xml中的字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        return getResource().getStringArray(resId);
    }

    /**
     * 得到color.xml中的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResource().getColor(colorId);
    }

    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }


    /**
     * 获取系统版本
     *
     * @return
     */
    public static int getVersion() {
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        return version;
    }
}
