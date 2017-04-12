package com.cooloongwu.coolarithmetic.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.base.BaseActivity;
import com.cooloongwu.coolarithmetic.utils.StartActivityUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        goNext();
    }

    private void goNext() {

        if (TextUtils.isEmpty(AppConfig.getUserToken(LauncherActivity.this))) {
            goLogin();
        } else {
            goMain();
        }

    }

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

    private void goMain() {
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


    }
}
