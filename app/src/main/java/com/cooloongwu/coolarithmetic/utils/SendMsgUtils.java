package com.cooloongwu.coolarithmetic.utils;

import android.util.Log;

import com.cooloongwu.coolarithmetic.entity.MsgTypeEnum;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 发送消息的类
 * Created by CooLoongWu on 2017-4-10 17:24.
 */

public class SendMsgUtils {

    public static void sendCustomMsg(String accid, MsgTypeEnum type) {
        //只有接收方当前在线才会收到，如果发送方发送时，指定的接收者不在线，这条通知将会被丢弃。
        // 构造自定义通知，指定接收者
        CustomNotification notification = new CustomNotification();
        notification.setSessionId(accid);
        notification.setSessionType(SessionTypeEnum.P2P);

        // 构建通知的具体内容。为了可扩展性，这里采用 json 格式
        JSONObject json = new JSONObject();
        try {
            json.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notification.setContent(json.toString());

        // 发送自定义通知
        NIMClient.getService(MsgService.class).sendCustomNotification(notification).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Log.e("自定义消息发送", "onSuccess");
            }

            @Override
            public void onFailed(int code) {
                Log.e("自定义消息发送", "onFailed");
            }

            @Override
            public void onException(Throwable exception) {
                Log.e("自定义消息发送", "onException");
            }
        });
    }

    public static void sendTextMsg(String accid, String content) {
        // 创建文本消息
        IMMessage message = MessageBuilder.createTextMessage(
                accid,                // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P,            // 聊天类型，单聊或群组
                content             // 文本内容
        );
        // 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        NIMClient.getService(MsgService.class).sendMessage(message, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Log.e("文字消息发送", "onSuccess");
            }

            @Override
            public void onFailed(int code) {
                Log.e("文字消息发送", "onFailed");
            }

            @Override
            public void onException(Throwable exception) {
                Log.e("文字消息发送", "onException");
            }
        });
    }

    /**
     * 添加好友请求
     *
     * @param accid 账户
     * @param msg   认证消息
     */
    public static void sendAddFriendMsg(String accid, String msg) {
        final VerifyType verifyType = VerifyType.VERIFY_REQUEST; // 发起好友验证请求
        NIMClient.getService(FriendService.class).addFriend(new AddFriendData(accid, verifyType, msg))
                .setCallback(new RequestCallback<Void>() {

                    @Override
                    public void onSuccess(Void param) {
                        Log.e("添加好友消息发送", "onSuccess");
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.e("添加好友消息发送", "onFailed");
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.e("添加好友消息发送", "onException");
                    }
                });
    }

    /**
     * 应答添加好友的请求
     *
     * @param accid 账户
     * @param ack   true表示同意，false表示拒绝
     */
    public static void sendAckAddFriendMsg(String accid, boolean ack) {
        NIMClient.getService(FriendService.class).ackAddFriendRequest(accid, ack);
    }
}
