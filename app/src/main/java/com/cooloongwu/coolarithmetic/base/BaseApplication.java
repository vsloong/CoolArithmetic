package com.cooloongwu.coolarithmetic.base;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.activity.LauncherActivity;
import com.cooloongwu.coolarithmetic.entity.Conversation;
import com.cooloongwu.coolarithmetic.utils.AsyncHttpClientUtils;
import com.cooloongwu.coolarithmetic.utils.GreenDAOUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Application的基类，用来初始化网易云信，AsyncHttpClient等SDK
 * Created by CooLoongWu on 2017-3-31 14:32.
 */

public class BaseApplication extends Application {

    /**
     * 单例对象
     */
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        NIMClient.init(this, null, options());

        AsyncHttpClientUtils.setClientGeneral(new AsyncHttpClient());

        initFile();
        initUPush();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private void initUPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("友盟", "注册成功：" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("友盟", "注册失败：" + s + "；" + s1);
            }
        });

//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//            @Override
//            public void handleMessage(Context context, UMessage uMessage) {
//                //点击了通知后才有操作
//                Log.e("友盟消息处理", "handleMessage");
//                super.handleMessage(context, uMessage);
//            }
//        };
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);


        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                Log.e("友盟消息处理", "getNotification" + msg.toString());
                Log.e("友盟消息分析", "title：" + msg.title);
                Log.e("友盟消息分析", "text：" + msg.text);
                Log.e("友盟消息分析", "url：" + msg.extra.get("url"));

                Conversation conversation = new Conversation();
                conversation.setType("system");
                conversation.setName("系统消息");
                conversation.setUnReadNum(1);
                conversation.setContent(msg.title);

                GreenDAOUtils.getDefaultDaoSession(getApplicationContext())
                        .getConversationDao()
                        .insert(conversation);

                return super.getNotification(context, msg);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
    }

    /**
     * 将数据库拷贝到相应目录
     */
    private void initFile() {

        //数据库的路径
        String DB_PATH = "/data/data/com.cooloongwu.coolarithmetic/databases/";

        //判断数据库是否拷贝到相应的目录下
//        if (!new File(DB_PATH + AppConfig.questionsDB).exists()) {
        File dir = new File(DB_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }

        //复制文件
        try {
            InputStream is = getBaseContext().getAssets().open(AppConfig.questionsDB);
            OutputStream os = new FileOutputStream(DB_PATH + AppConfig.questionsDB);

            //用来复制文件
            byte[] buffer = new byte[1024];
            //保存已经复制的长度
            int length;

            //开始复制
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

            //刷新
            os.flush();
            //关闭
            os.close();
            is.close();
            Log.e("DataBase", "Copy Success");

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DataBase", "Copy Error" + e.toString());
        }
//        } else {
//            Log.e("DataBase", "Already Exist");
//        }
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = LauncherActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.ic_launcher;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        options.sdkStorageRootPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = 480 / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.mipmap.ic_launcher;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }
        };
        return options;
    }

}
