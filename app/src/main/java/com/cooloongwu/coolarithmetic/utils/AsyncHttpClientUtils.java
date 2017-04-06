package com.cooloongwu.coolarithmetic.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Date;

/**
 * 网络请求类
 * Created by CooLoongWu on 2016-9-26 14:55.
 */

public class AsyncHttpClientUtils {

    private static AsyncHttpClient clientGeneral;
    private static final int CONNECT_TIME = 8 * 1000;
    private static final int RESPONSE_TIME = 10 * 1000;


    private static final String SERVER_HTTP = "/create.action";

    /**
     * 设置静态Client
     *
     * @param client 客户端
     */
    public static void setClientGeneral(AsyncHttpClient client) {
        clientGeneral = client;
        clientGeneral.setConnectTimeout(CONNECT_TIME);
        clientGeneral.setResponseTimeout(RESPONSE_TIME);
    }

    /**
     * get方法
     *
     * @param context 上下文
     * @param url     接口
     * @param handler 处理
     */
    public static void get(Context context, String url, AsyncHttpResponseHandler handler) {
        clientGeneral.get(context, SERVER_HTTP + url, handler);
    }

    /**
     * post方法
     *
     * @param context 上下文
     * @param url     接口地址
     * @param params  参数
     * @param handler 处理
     */
    public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler handler) {

        String appKey = "2db673f68e572a2e14cc780871103773";
        String appSecret = "85aac77482c1";
        String nonce = "0123456789";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);

        clientGeneral.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        clientGeneral.addHeader("AppKey", appKey);
        clientGeneral.addHeader("Nonce", nonce);
        clientGeneral.addHeader("CurTime", curTime);
        clientGeneral.addHeader("CheckSum", checkSum);

        clientGeneral.post(context, SERVER_HTTP + url, params, handler);
    }

    /**
     * 下载文件
     *
     * @param context 上下文
     * @param url     地址
     * @param handler 处理
     */
    public static void download(Context context, String url, FileAsyncHttpResponseHandler handler) {
        clientGeneral.get(context, url, handler);
    }

}
