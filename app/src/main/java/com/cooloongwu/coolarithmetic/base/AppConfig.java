package com.cooloongwu.coolarithmetic.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.preference.PreferenceManager;

/**
 * App的各种配置类
 * Created by CooLoongWu on 2016-9-14 13:50.
 */
public class AppConfig {

    public static final String FILE_SAVE_PATH = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath()
            + "/CoolArithmetic/";

    private static final String defaultDB = "CoolArithmetic";
    public static final String questionsDB = "questions.db";

    public static final String appKey = "2db673f68e572a2e14cc780871103773";
    public static final String appSecret = "85aac77482c1";
    public static final String nonce = "0123456789";

    /**
     * 存储修改用户id，默认为空
     */
    private static final String USER_ID = "user_id";
    private static final int defaultUserId = 0;

    /**
     * 存储修改用户id，默认为空
     */
    private static final String USER_ACCID = "user_accid";
    private static final String defaultUserAccid = "";

    /**
     * 存储修改用户年级，默认为一年级
     */
    private static final String USER_GRADE = "user_grade";
    private static final int defaultUserGrade = 0;

    /**
     * 存储修改用户昵称，默认为空
     */
    private static final String USER_NAME = "user_name";
    private static final String defaultUserName = "";

    /**
     * 存储修改用户性别，默认为空
     */
    private static final String USER_SEX = "user_sex";
    private static final String defaultUserSex = "";

    /**
     * 存储修改用户头像，默认为空
     */
    private static final String USER_AVATAR = "user_avatar";
    private static final String defaultUserAvatar = "";

    /**
     * 存储修改用户Token，默认为空
     */
    private static final String USER_TOKEN = "user_token";
    private static final String defaultUserToken = "";

    /**
     * 存储修改用户登录时间
     */
    private static final String USER_LOGIN_TIME = "user_login_time";
    private static final long defaultUserLoginTime = 0;


    /**
     * 存储修改用户签到时间
     */
    private static final String USER_SIGN_TIME = "user_sign_time";
    private static final long defaultUserSignTime = 0;

    /**
     * 存储修改用户经验值
     */
    private static final String USER_EXP = "user_EXP";
    private static final int defaultUserEXP = 0;

    /**
     * 存储修改用户活力值
     */
    private static final String USER_EV = "user_EV";
    private static final int defaultUserEV = 100;

    /**
     * 获取用户的数据库（为每一个用户建立一个数据库）
     *
     * @param context 上下文
     * @return 数据库名
     */
    public static String getUserDB(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String accid = preferences.getString(USER_ACCID, defaultUserAccid);
        return defaultDB + accid + ".db";
    }

    /**
     * 存储获取用户的ID
     *
     * @param context 上下文
     * @param userId  userId
     */
    public static void setUserId(Context context, int userId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(USER_ID, userId).apply();
    }

    public static int getUserId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(USER_ID, defaultUserId);
    }

    /**
     * 存储获取用户的ACCID
     *
     * @param context   上下文
     * @param userAccid userAccid
     */
    public static void setUserAccid(Context context, String userAccid) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_ACCID, userAccid).apply();
    }

    public static String getUserAccid(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(USER_ACCID, defaultUserAccid);
    }

    /**
     * 存储获取用户的年级
     *
     * @param context   上下文
     * @param userGrade userGrade
     */
    public static void setUserGrade(Context context, int userGrade) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(USER_GRADE, userGrade).apply();
    }

    public static int getUserGrade(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(USER_GRADE, defaultUserGrade);
    }


    /**
     * 存储获取用户的昵称
     *
     * @param context  上下文
     * @param userName 昵称
     */
    public static void setUserName(Context context, String userName) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_NAME, userName).apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(USER_NAME, defaultUserName);
    }

    /**
     * 存储获取用户的性别
     *
     * @param context 上下文
     * @param sex     性别
     */
    public static void setUserSex(Context context, String sex) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_SEX, sex).apply();
    }

    public static String getUserSex(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(USER_SEX, defaultUserSex);
    }

    /**
     * 存储获取用户的头像url
     *
     * @param context 上下文
     * @param url     图片地址
     */
    public static void setUserAvatar(Context context, String url) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_AVATAR, url).apply();
    }

    public static String getUserAvatar(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(USER_AVATAR, defaultUserAvatar);
    }

    /**
     * 存储获取用户的token
     *
     * @param context 上下文
     * @param token   token
     */
    public static void setUserToken(Context context, String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_TOKEN, token).apply();
    }

    public static String getUserToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(USER_TOKEN, defaultUserToken);
    }

    /**
     * 存储获取用户的登录时间
     *
     * @param context 上下文
     * @param time    token
     */
    public static void setUserLoginTime(Context context, long time) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putLong(USER_LOGIN_TIME, time).apply();
    }

    public static long getUserLoginTime(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(USER_LOGIN_TIME, defaultUserLoginTime);
    }

    /**
     * 存储获取用户的签到时间
     *
     * @param context 上下文
     * @param time    token
     */
    public static void setUserSignTime(Context context, long time) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putLong(USER_SIGN_TIME, time).apply();
    }

    public static long getUserSignTime(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(USER_SIGN_TIME, defaultUserSignTime);
    }

    /**
     * 存储获取用户的经验值
     *
     * @param exp 经验值
     */
    public static void setUserEXP(int exp) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        preferences.edit().putInt(USER_EXP, exp).apply();
    }

    public static void increaseUserEXP(int exp) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        preferences.edit().putInt(USER_EXP, getUserEXP() + exp).apply();
    }

    public static int getUserEXP() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        return preferences.getInt(USER_EXP, defaultUserEXP);
    }

    /**
     * 存储获取用户的活力值
     *
     * @param ev 活力值
     */
    public static void setUserEV(int ev) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        preferences.edit().putInt(USER_EV, ev).apply();
    }

    public static void increaseUserEV(int ev) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        preferences.edit().putInt(USER_EV, (getUserEV() + ev) > 100 ? 100 : getUserEV() + ev).apply();
    }

    public static void decreaseUserEV(int ev) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        preferences.edit().putInt(USER_EV, getUserEV() - ev).apply();
    }

    public static int getUserEV() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        return preferences.getInt(USER_EV, defaultUserEV);
    }

    /**
     * 得到程序的版本号
     *
     * @param context 上下文
     * @return 正常情况下返回版本号，出错等情况返回空
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 得到程序数据库的存储路径
     *
     * @param context 上下文
     * @return 正常情况下返回版本号，出错等情况返回空
     */
    public static String getDataDir(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 清空所有的用户信息
     *
     * @param context 上下文
     */
    public static void clearAllInfo(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().clear().apply();
    }
}
