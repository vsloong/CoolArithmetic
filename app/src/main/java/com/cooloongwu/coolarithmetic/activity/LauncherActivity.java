package com.cooloongwu.coolarithmetic.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cooloongwu.coolarithmetic.R;
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
}
