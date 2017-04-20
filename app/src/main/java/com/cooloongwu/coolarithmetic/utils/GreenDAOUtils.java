package com.cooloongwu.coolarithmetic.utils;

import android.content.Context;
import android.util.Log;

import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.greendao.gen.AdvanceDao;
import com.cooloongwu.greendao.gen.DaoMaster;
import com.cooloongwu.greendao.gen.DaoSession;
import com.cooloongwu.greendao.gen.QuestionDao;

/**
 * 操作GreenDAO的类
 * Created by CooLoongWu on 2016-9-28 14:25.
 */

public class GreenDAOUtils {

    private static GreenDAOUtils instance;
    private static GreenDAOUtils questionInstance;
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

    /**
     * 取得DaoSession
     *
     * @return
     */
    public static DaoSession getDefaultDaoSession(Context context) {
        if (defaultDaoSession == null) {
            defaultDaoSession = getDefaultDaoMaster(context, AppConfig.getUserDB(context)).newSession();
        }
        return defaultDaoSession;
    }

    public static DaoSession getQuestionDaoSession(Context context) {
        if (questionDaoSession == null) {
            questionDaoSession = getQuestionDaoMaster(context, CopyDBToApk.DB_NAME).newSession();
        }
        return questionDaoSession;
    }

    public static GreenDAOUtils getInstance(Context context) {
        if (instance == null) {
            String dataBaseName = AppConfig.getUserDB(context);
            Log.e("加载的用户的数据库", dataBaseName);
            instance = new GreenDAOUtils(context, dataBaseName);
        }
        return instance;
    }

    public static GreenDAOUtils getQuestionInstance(Context context) {
        if (questionInstance == null) {
            String dataBaseName = CopyDBToApk.DB_NAME;
            Log.e("加载的用户的数据库", dataBaseName);
            questionInstance = new GreenDAOUtils(context, dataBaseName);
        }
        return questionInstance;
    }

    public GreenDAOUtils(Context context) {
        String dataBaseName = AppConfig.getUserDB(context);
        Log.e("加载的用户的数据库", dataBaseName);
        devOpenHelper = new DaoMaster.DevOpenHelper(context, dataBaseName, null);
    }

    public GreenDAOUtils(Context context, String dataBaseName) {
        Log.e("加载的用户的数据库", dataBaseName);
        devOpenHelper = new DaoMaster.DevOpenHelper(context, dataBaseName, null);
    }

    private DaoSession getDaoSession() {
        return new DaoMaster(devOpenHelper.getWritableDb()).newSession();
    }

    public AdvanceDao getAdvanceDao() {
        return getDaoSession().getAdvanceDao();
    }

    public QuestionDao getQuestionDao() {
        return getDaoSession().getQuestionDao();
    }

    public void clearAllData() {
        getAdvanceDao().deleteAll();
        getQuestionDao().deleteAll();
    }
}
