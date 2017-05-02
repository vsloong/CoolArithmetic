package com.zxxxy.coolarithmetic.utils;

import android.content.Context;

import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.greendao.gen.DaoMaster;
import com.zxxxy.greendao.gen.DaoSession;

/**
 * 操作GreenDAO的类
 * Created by CooLoongWu on 2016-9-28 14:25.
 */

public class GreenDAOUtils {

    private static DaoMaster defaultDaoMaster;
    // 默认DB
    private static DaoSession defaultDaoSession;

    private static DaoMaster obtainMaster(Context context, String dbName) {
        return new DaoMaster(new DaoMaster.DevOpenHelper(context, dbName, null).getWritableDatabase());
    }

    private static DaoMaster getDefaultDaoMaster(Context context, String dbName) {
        if (dbName == null)
            return null;
        if (defaultDaoMaster == null) {
            defaultDaoMaster = obtainMaster(context, dbName);
        }
        return defaultDaoMaster;
    }

    public static DaoSession getDefaultDaoSession(Context context) {
        if (defaultDaoSession == null) {
            defaultDaoSession = getDefaultDaoMaster(context, AppConfig.getUserDB(context)).newSession();
        }
        return defaultDaoSession;
    }

}
