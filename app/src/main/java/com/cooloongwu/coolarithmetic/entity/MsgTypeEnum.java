package com.cooloongwu.coolarithmetic.entity;

/**
 * 枚举类
 * Created by CooLoongWu on 2017-4-10 17:26.
 */

public enum MsgTypeEnum {
    PK,
    PK_REQUEST, //接收到PK请求
    PK_AGREE,   //接收到同意PK消息
    PK_REJECT,  //接收到拒绝PK消息

}
