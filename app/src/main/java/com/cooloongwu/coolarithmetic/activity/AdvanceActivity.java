package com.cooloongwu.coolarithmetic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.AdvanceAdapter;
import com.cooloongwu.coolarithmetic.base.BaseActivity;

/**
 * 闯关Activity
 */
public class AdvanceActivity extends BaseActivity implements View.OnClickListener {

    private int grade = 1;

    private TextView text_grade;
    private TextView text_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);

        getIntentData();
        initViews();
        setDataToViews();
    }

    @Override
    protected void initViews() {
        ImageView img_btn_back = (ImageView) findViewById(R.id.img_btn_back);

        RecyclerView view_recycler = (RecyclerView) findViewById(R.id.view_recycler);
        text_grade = (TextView) findViewById(R.id.text_grade);
        text_progress = (TextView) findViewById(R.id.text_progress);

        //初始化线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(AdvanceActivity.this);
        //列表从底部开始加载，并自动定位到底部
        layoutManager.setReverseLayout(true);
        //给RecyclerView设置布局管理器
        view_recycler.setLayoutManager(layoutManager);

        //初始化闯关的适配器
        AdvanceAdapter adapter = new AdvanceAdapter(AdvanceActivity.this);
        //给RecyclerView设置适配器
        view_recycler.setAdapter(adapter);

        img_btn_back.setOnClickListener(this);
    }

    /**
     * 得到从上一个Activity传来的数据
     */
    private void getIntentData() {
        Intent intent = getIntent();
        grade = intent.getIntExtra("grade", 1);
    }

    /**
     * 将数据设置到视图控件上
     */
    private void setDataToViews() {
        text_grade.setText(getResources().getStringArray(R.array.grade_name)[grade - 1]);
        text_progress.setText(R.string.advance_num);
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
}
