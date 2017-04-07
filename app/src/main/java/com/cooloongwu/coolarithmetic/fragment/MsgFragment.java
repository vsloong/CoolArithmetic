package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MsgFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        initToolBar(view);
        initViews(view);
        test();
        sendCommentMsg();
        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("聊天");
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
    }

    private void test() {
        // 创建文本消息
        IMMessage message = MessageBuilder.createTextMessage(
                "2012329700030", // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                "怎么了啊，日了狗了"// 文本内容
        );
        // 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        NIMClient.getService(MsgService.class).sendMessage(message, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Log.e("消息发送", "onSuccess");
            }

            @Override
            public void onFailed(int code) {
                Log.e("消息发送", "onFailed");
            }

            @Override
            public void onException(Throwable exception) {
                Log.e("消息发送", "onException");
            }
        });

    }

    private void sendCommentMsg() {
        //只有接收方当前在线才会收到，如果发送方发送时，指定的接收者不在线，这条通知将会被丢弃。
        // 构造自定义通知，指定接收者
        CustomNotification notification = new CustomNotification();
        notification.setSessionId("2012329700030");
        notification.setSessionType(SessionTypeEnum.P2P);

        // 构建通知的具体内容。为了可扩展性，这里采用 json 格式，以 "id" 作为类型区分。
        // 这里以类型 “1” 作为“正在输入”的状态通知。
        JSONObject json = new JSONObject();
        try {
            json.put("type", "pk");
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
}
