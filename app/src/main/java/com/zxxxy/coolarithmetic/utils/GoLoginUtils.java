package com.zxxxy.coolarithmetic.utils;

import android.text.TextUtils;

import com.zxxxy.coolarithmetic.base.AppConfig;

/**
 * Created by CooLoongWu on 2017-4-27 16:41.
 */

public class GoLoginUtils {

    public static boolean isLogin() {
        return !TextUtils.isEmpty(AppConfig.getUserAccid());
    }


}
