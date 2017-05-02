package com.zxxxy.coolarithmetic.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.coolarithmetic.base.BaseActivity;
import com.zxxxy.coolarithmetic.utils.GoLoginUtils;
import com.zxxxy.coolarithmetic.utils.StartActivityUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        goMain();
        //goNext();
    }

    /**
     * 根据本地是否存储了用户的Token来判断是自动登录还是去注册或者手动登录
     */
    private void goNext() {
        if (TextUtils.isEmpty(AppConfig.getUserToken(LauncherActivity.this))) {
            goLogin();
        } else {
            goMain();
        }
    }

    /**
     * 延迟1.5秒后，去登录页面手动登录
     */
    private void goLogin() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                StartActivityUtils.startLoginActivity(LauncherActivity.this);
                finish();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 1500);
    }

    /**
     * 自动登录，直接跳转到主页面
     */
    private void goMain() {

        if (GoLoginUtils.isLogin()) {
            LoginInfo info = new LoginInfo(AppConfig.getUserAccid(LauncherActivity.this), AppConfig.getUserToken(LauncherActivity.this));
            NIMClient.getService(AuthService.class).login(info).setCallback(new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo param) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            StartActivityUtils.startMainActivity(LauncherActivity.this);
                            finish();
                        }
                    };
                    Handler handler = new Handler();
                    handler.postDelayed(runnable, 1500);
                }

                @Override
                public void onFailed(int code) {
                    goLogin();
                }

                @Override
                public void onException(Throwable exception) {
                    Log.e("登录", "出错" + exception.toString());
                    goLogin();
                }
            });
        } else {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    StartActivityUtils.startMainActivity(LauncherActivity.this);
                    finish();
                }
            };
            Handler handler = new Handler();
            handler.postDelayed(runnable, 1500);
        }
    }
}
