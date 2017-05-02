package com.zxxxy.coolarithmetic.base;

import android.content.Context;

import com.zxxxy.coolarithmetic.utils.AsyncHttpClientUtils;
import com.zxxxy.coolarithmetic.utils.MD5Utils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * App的接口类，用来提供用户登录注册等的接口
 * Created by CooLoongWu on 2017-4-7 09:53.
 */

public class Api {

    private static final String URL_CREATE = "create.action";

    public static void register(Context context, String accid, String name, String password, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.add("accid", accid);
        params.add("name", name);
        params.add("token", MD5Utils.getMD5(password));
        AsyncHttpClientUtils.post(context, URL_CREATE, params, handler);
    }

    public static void login() {

    }
}
