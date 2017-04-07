package com.cooloongwu.coolarithmetic.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.base.BaseActivity;
import com.cooloongwu.coolarithmetic.utils.StartActivityUtils;

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
