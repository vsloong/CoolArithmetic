package com.zxxxy.coolarithmetic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.umeng.message.PushAgent;

/**
 * Activity的基类
 * Created by CooLoongWu on 2017-3-31 14:31.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
    }

    /**
     * 建议在这里初始化视图
     */
    protected void initViews() {
    }

    /**
     * 建议在这里初始化数据
     */
    protected void initData() {
    }
}
