package com.zxxxy.coolarithmetic.sudoku.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    public static SharedPreferences getSharedPreferenced(Context context) {
        return context.getSharedPreferences(MyContant.SPNAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存boolean型到SharedPreferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        getSharedPreferenced(context).edit().putBoolean(key, value).commit();
    }

    /**
     * 从SharedPreferences读取boolean型
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferenced(context).getBoolean(key, defValue);
    }

    /**
     * 保存String型到SharedPreferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        getSharedPreferenced(context).edit().putString(key, value).commit();
    }

    /**
     * 从SharedPreferebces中获取String
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        return getSharedPreferenced(context).getString(key, defValue);
    }

    public static void saveInt(Context context, String key, int value) {
        getSharedPreferenced(context).edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSharedPreferenced(context).getInt(key, defValue);
    }

    public static void saveLong(Context context, String key, long value) {
        getSharedPreferenced(context).edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, long defValue) {
        return getSharedPreferenced(context).getLong(key, defValue);
    }
}
