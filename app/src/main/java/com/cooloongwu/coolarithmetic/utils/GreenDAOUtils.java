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
    private DaoMaster.DevOpenHelper devOpenHelper;

    public static GreenDAOUtils getInstance(Context context) {
        if (instance == null) {
            instance = new GreenDAOUtils(context);
        }
        return instance;
    }

    public GreenDAOUtils(Context context) {
        String dataBaseName = AppConfig.getUserDB(context);
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
