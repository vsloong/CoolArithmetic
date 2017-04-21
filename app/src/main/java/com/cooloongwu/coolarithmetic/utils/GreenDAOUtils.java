package com.cooloongwu.coolarithmetic.utils;

import android.content.Context;

import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.greendao.gen.DaoMaster;
import com.cooloongwu.greendao.gen.DaoSession;

/**
 * 操作GreenDAO的类
 * Created by CooLoongWu on 2016-9-28 14:25.
 */

public class GreenDAOUtils {

    private DaoMaster.DevOpenHelper devOpenHelper;

    private static DaoMaster defaultDaoMaster;
    private static DaoMaster questionDaoMaster;
    // 默认DB
    private static DaoSession defaultDaoSession;
    // 拷贝的db
    private static DaoSession questionDaoSession;

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

    private static DaoMaster getQuestionDaoMaster(Context context, String dbName) {
        if (dbName == null)
            return null;
        if (questionDaoMaster == null) {
            questionDaoMaster = obtainMaster(context, dbName);
        }
        return questionDaoMaster;
    }

    public static DaoSession getDefaultDaoSession(Context context) {
        if (defaultDaoSession == null) {
            defaultDaoSession = getDefaultDaoMaster(context, AppConfig.getUserDB(context)).newSession();
        }
        return defaultDaoSession;
    }

    public static DaoSession getQuestionDaoSession(Context context) {
        if (questionDaoSession == null) {
            questionDaoSession = getQuestionDaoMaster(context, AppConfig.questionsDB).newSession();
        }
        return questionDaoSession;
    }

}
