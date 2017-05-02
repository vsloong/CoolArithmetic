package com.zxxxy.coolarithmetic.utils;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5Utils
 * Created by CooLoongWu on 2017-4-7 09:59.
 */

public class MD5Utils {

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            String result = new BigInteger(1, md.digest()).toString(16);
            Log.e("密码的MD5", result);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e("密码的MD5", "失败");
            return "";
        }
    }
}
