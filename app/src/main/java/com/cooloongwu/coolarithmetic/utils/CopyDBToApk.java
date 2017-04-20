package com.cooloongwu.coolarithmetic.utils;

import android.content.Context;
import android.util.Log;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by CooLoongWu on 2017-4-20 17:38.
 */

public class CopyDBToApk {

    //数据库的名称
    public static String DB_NAME = "questions.db";
    //数据库的地址
    private static String DB_PATH = "/data/data/com.cooloongwu.coolarithmetic/databases/";
//    private static String DB_PATH = "/data/user/0/com.cooloongwu.coolarithmetic/databases/";

    /**
     * 将数据库拷贝到相应目录
     */
    public static void initFile(Context context) {
        //判断数据库是否拷贝到相应的目录下
//        if (!new File(DB_PATH + DB_NAME).exists()) {
//            File dir = new File(DB_PATH);
//            if (!dir.exists()) {
//                dir.mkdir();
//            }

        //复制文件
        try {
            InputStream is = context.getResources().openRawResource(R.raw.questions);
            OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

            //用来复制文件
            byte[] buffer = new byte[1024];
            //保存已经复制的长度
            int length;

            //开始复制
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

            //刷新
            os.flush();
            //关闭
            os.close();
            is.close();
            Log.e("数据库", "转移成功");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("数据库", "转移失败" + e.toString());
        }
//        } else {
//            Log.e("数据库", "已存在");
//        }

    }
}
