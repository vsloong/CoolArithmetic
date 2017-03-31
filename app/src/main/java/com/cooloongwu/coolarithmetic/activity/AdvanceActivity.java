package com.cooloongwu.coolarithmetic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseActivity;

public class AdvanceActivity extends BaseActivity implements View.OnClickListener {

    private String[] GRADE_TITLES = {
            "一年级", "二年级", "三年级", "四年级", "五年级", "六年级"
    };
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
        ImageView img_back = (ImageView) findViewById(R.id.img_btn_back);
        text_grade = (TextView) findViewById(R.id.text_grade);
        text_progress = (TextView) findViewById(R.id.text_progress);

        img_back.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        grade = intent.getIntExtra("grade", 1);
    }

    private void setDataToViews() {
        text_grade.setText(GRADE_TITLES[grade - 1]);
        text_progress.setText("2/20");
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
