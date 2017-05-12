package com.zxxxy.coolarithmetic.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.adapter.ViewPagerAdapter;
import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.coolarithmetic.base.BaseActivity;
import com.zxxxy.coolarithmetic.utils.GoLoginUtils;
import com.zxxxy.coolarithmetic.utils.StartActivityUtils;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity implements View.OnClickListener {

    // 定义ViewPager对象
    private ViewPager viewPager;
    // 定义ViewPager适配器
    private ViewPagerAdapter vpAdapter;
    // 定义各个界面View对象
    private View view1, view2, view3, view4;
    // 定义底部小点图片
    private ImageView pointImage1, pointImage2, pointImage3, pointImage4;
    // 当前的位置索引值
    private int currIndex = 0;
    //最后一页的滑动
    private Boolean isScrolled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        AppConfig.setUserIsFirst(false);

        initViews();
        initData();
    }

    @Override
    protected void initViews() {
        // 实例化各个界面的布局对象
        LayoutInflater mLi = LayoutInflater.from(this);
        view1 = mLi.inflate(R.layout.guide_page1, null);
        view2 = mLi.inflate(R.layout.guide_page2, null);
        view3 = mLi.inflate(R.layout.guide_page3, null);
        view4 = mLi.inflate(R.layout.guide_page4, null);

        // 实例化ArrayList对象
        ArrayList<View> views = new ArrayList<>();

        // 将要分页显示的View装入数组中
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        // 实例化ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // 实例化ViewPager适配器
        vpAdapter = new ViewPagerAdapter(views);

        // 实例化底部小点图片对象
        pointImage1 = (ImageView) findViewById(R.id.page1);
        pointImage2 = (ImageView) findViewById(R.id.page2);
        pointImage3 = (ImageView) findViewById(R.id.page3);
        pointImage4 = (ImageView) findViewById(R.id.page4);

        Button btn_go = (Button) view4.findViewById(R.id.btn_go);
        Button btn_start = (Button) findViewById(R.id.btn_start);

        btn_go.setOnClickListener(this);
        btn_start.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        // 设置监听
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        // 设置适配器数据
        viewPager.setAdapter(vpAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
            case R.id.btn_start:
                goMain();

                break;
            default:
                break;
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    pointImage1.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_white));
                    pointImage2.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_gray));
                    break;
                case 1:
                    pointImage2.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_white));
                    pointImage1.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_gray));
                    pointImage3.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_gray));

                    break;
                case 2:
                    pointImage3.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_white));
                    pointImage2.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_gray));
                    pointImage4.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_gray));
                    break;
                case 3:
                    pointImage4.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_white));
                    pointImage3.setImageDrawable(getResources().getDrawable(
                            R.mipmap.dot_gray));
                    break;
                default:
                    break;

            }
            currIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isScrolled = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    isScrolled = true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isScrolled) {
                        goMain();
                    }
                    isScrolled = true;
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    /**
     * 自动登录，直接跳转到主页面
     */
    private void goMain() {
        if (GoLoginUtils.isLogin()) {
            LoginInfo info = new LoginInfo(AppConfig.getUserAccid(), AppConfig.getUserToken(GuideActivity.this));
            NIMClient.getService(AuthService.class).login(info).setCallback(new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo param) {
                    StartActivityUtils.startMainActivity(GuideActivity.this);
                    finish();
                }

                @Override
                public void onFailed(int code) {
                    Log.e("网易云信登录", "出错" + code);
                    goLogin();
                }

                @Override
                public void onException(Throwable exception) {
                    Log.e("网易云信登录", "出错" + exception.toString());
                    goLogin();
                }
            });
        } else {
            StartActivityUtils.startMainActivity(GuideActivity.this);
            finish();
        }
    }

    /**
     * 延迟1.5秒后，去登录页面手动登录
     */
    private void goLogin() {
        StartActivityUtils.startLoginActivity(GuideActivity.this);
        finish();
    }
}
