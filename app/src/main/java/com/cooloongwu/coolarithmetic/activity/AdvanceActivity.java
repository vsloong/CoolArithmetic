package com.cooloongwu.coolarithmetic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.AdvanceAdapter;
import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.base.BaseActivity;
import com.cooloongwu.coolarithmetic.entity.Advance;
import com.cooloongwu.coolarithmetic.utils.GreenDAOUtils;
import com.cooloongwu.greendao.gen.AdvanceDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 闯关Activity
 */
public class AdvanceActivity extends BaseActivity implements View.OnClickListener {

    private int grade = 0;

    private TextView text_grade;
    private TextView text_progress;
    private TextView text_ev_progress;
    private ProgressBar progress_bar_ev;

    private Advance advance;
    private AdvanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        EventBus.getDefault().register(this);
        getIntentData();
        initViews();
        setDataToViews();
    }

    @Override
    protected void initViews() {
        ImageView img_btn_back = (ImageView) findViewById(R.id.img_btn_back);
        progress_bar_ev = (ProgressBar) findViewById(R.id.progress_bar_ev);
        progress_bar_ev.setProgress(AppConfig.getUserEV());

        RecyclerView view_recycler = (RecyclerView) findViewById(R.id.view_recycler);
        text_grade = (TextView) findViewById(R.id.text_grade);
        text_progress = (TextView) findViewById(R.id.text_progress);
        text_ev_progress = (TextView) findViewById(R.id.text_ev_progress);
        text_ev_progress.setText(AppConfig.getUserEV() + "/100");

        //初始化线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(AdvanceActivity.this);
        //列表从底部开始加载，并自动定位到底部
        layoutManager.setReverseLayout(true);
        //给RecyclerView设置布局管理器
        view_recycler.setLayoutManager(layoutManager);

        //初始化闯关的适配器
        adapter = new AdvanceAdapter(AdvanceActivity.this, advance);
        //给RecyclerView设置适配器
        view_recycler.setAdapter(adapter);

        img_btn_back.setOnClickListener(this);
    }

    /**
     * 得到从上一个Activity传来的数据
     */
    private void getIntentData() {
        Intent intent = getIntent();
        grade = intent.getIntExtra("grade", 0);

        advance = GreenDAOUtils.getDefaultDaoSession(AdvanceActivity.this).getAdvanceDao()
                .queryBuilder()
                .where(AdvanceDao.Properties.Grade.eq(grade))
                .build().unique();

        if (advance == null) {
            Advance temp = new Advance();
            temp.setGrade(grade);
            temp.setAdvance(0);
            advance = temp;
            GreenDAOUtils.getDefaultDaoSession(AdvanceActivity.this).getAdvanceDao().insert(temp);
        }
    }

    /**
     * 将数据设置到视图控件上
     */
    private void setDataToViews() {
        text_grade.setText(getResources().getStringArray(R.array.grade_name)[grade]);
        text_progress.setText((advance.getAdvance() + 1) + "/20");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progress_bar_ev.setProgress(AppConfig.getUserEV());
        text_ev_progress.setText(AppConfig.getUserEV() + "/100");
    }

    @Subscribe
    public void onEventMainThread(Advance advance) {
        adapter.notifyDataSetChanged();
        text_progress.setText((advance.getAdvance() + 1) + "/20");
        progress_bar_ev.setProgress(AppConfig.getUserEV());
        text_ev_progress.setText(AppConfig.getUserEV() + "/100");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
