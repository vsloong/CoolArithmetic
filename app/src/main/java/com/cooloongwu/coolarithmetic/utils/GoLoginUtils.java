package com.cooloongwu.coolarithmetic.utils;

import android.text.TextUtils;

import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.base.BaseApplication;

/**
 * Created by CooLoongWu on 2017-4-27 16:41.
 */

public class GoLoginUtils {

    public static boolean isLogin() {
        return !TextUtils.isEmpty(AppConfig.getUserAccid(BaseApplication.getInstance()));
    }


}
