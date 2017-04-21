package com.cooloongwu.coolarithmetic.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by WangZH on 2016/12/19.
 */

public class AssetsDatabaseManager {
    private static String tag = "AssetsDatabase"; // for LogCat
    private static String databasepath = "/data/data/%s/databases"; // %s is packageName

    // Context of application
    private Context context = null;

    // Singleton Pattern
    private static AssetsDatabaseManager mInstance = null;

    /**
     * Initialize AssetsDatabaseManager
     *
     * @param context, context of application
     */
    public static void initManager(Context context) {
        if (mInstance == null) {
            mInstance = new AssetsDatabaseManager(context);
        }
    }

    /**
     * Get a AssetsDatabaseManager object
     *
     * @return, if success return a AssetsDatabaseManager object, else return null
     */
    public static AssetsDatabaseManager getManager() {
        return mInstance;
    }

    private AssetsDatabaseManager(Context context) {
        this.context = context;
    }

    /**
     * Get a assets database, if this database is opened this method is only return a copy of the opened database
     *
     * @param dbfile, the assets file which will be opened for a database
     * @return, if success it return a SQLiteDatabase object else return null
     */
    public SQLiteDatabase getDatabase(String dbfile) {
        if (context == null) {
            return null;
        }
        Log.e(tag, String.format("Create database %s", dbfile));
        String spath = getDatabaseFilepath();
        String sfile = getDatabaseFile(dbfile);
        File file = new File(sfile);
        if (!file.exists()) {
            file = new File(spath);
            if (!file.exists() && !file.mkdirs()) {
                Log.e(tag, "Create \"" + spath + "\" fail!");
                return null;
            }
            if (!copyAssetsToFilesystem(dbfile, sfile)) {
                Log.e(tag, String.format("Copy %s to %s fail!", dbfile, sfile));
                return null;
            }

            Log.e(tag, "copy success");
        }

        SQLiteDatabase db = SQLiteDatabase.openDatabase(sfile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return db;
    }

    public boolean initDataBase(List<String> dbName) {
        for (int i = 0; i < dbName.size(); i++) {
            //文件未复制到data目录，则复制进去
            String spath = getDatabaseFilepath();
            String sfile = getDatabaseFile(dbName.get(i));
            File file = new File(sfile);
//            if (!file.exists()) {
            file = new File(spath);
            if (!file.exists() && !file.mkdirs()) {
                Log.e(tag, "Create \"" + spath + "\" fail!");
                continue;
            }
            if (!copyAssetsToFilesystem(dbName.get(i), sfile)) {
                Log.e(tag, String.format("Copy %s to %s fail!", dbName.get(i), sfile));
                continue;
            }
            Log.e(tag, "copy success");
//            }
        }
        return true;
    }

    private String getDatabaseFilepath() {
        return String.format(databasepath, context.getApplicationInfo().packageName);
    }

    private String getDatabaseFile(String dbfile) {
        return getDatabaseFilepath() + "/" + dbfile;
    }

    private boolean copyAssetsToFilesystem(String assetsSrc, String des) {
        Log.e(tag, "Copy " + assetsSrc + " to " + des);
        InputStream istream = null;
        OutputStream ostream = null;
        try {
            istream = context.getClass().getClassLoader().getResourceAsStream("assets/" + CopyDBToApk.DB_NAME);
            ostream = new FileOutputStream(des);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = istream.read(buffer)) > 0) {
                ostream.write(buffer, 0, length);
            }
            istream.close();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (istream != null)
                    istream.close();
                if (ostream != null)
                    ostream.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
