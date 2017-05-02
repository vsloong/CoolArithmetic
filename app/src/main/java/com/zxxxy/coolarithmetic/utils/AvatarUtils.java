package com.zxxxy.coolarithmetic.utils;

/**
 * 头像地址的存储类
 * Created by CooLoongWu on 2017-4-24 15:57.
 */

public class AvatarUtils {

    public static String getAvatar(String accid) {
        switch (accid) {
            case "2012329700030":
                return "http://dynamic-image.yesky.com/600x-/uploadImages/upload/20140912/upload/201409/tlyu1205hhyjpg.jpg";
            case "2013329700040":
                return "http://dynamic-image.yesky.com/600x-/uploadImages/upload/20140912/upload/201409/1qf5u0h5nwajpg.jpg";
            default:
                return "default";
        }
    }
}
