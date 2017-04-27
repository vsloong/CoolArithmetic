package com.cooloongwu.coolarithmetic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换的工具类
 * Created by CooLoongWu on 2017-4-27 10:36.
 */

public class DataUtils {

    public static String getTime(long time) {
        Date date = new Date(time);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return simpleDateFormat.format(date);
    }
}
