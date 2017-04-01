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

public class AdvanceActivity extends BaseActivity implements View.OnClickListener {

    private int grade = 1;

    private TextView text_grade;
    private TextView text_progress;

    private AdvanceAdapter adapter;

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
        RecyclerView view_recycler = (RecyclerView) findViewById(R.id.view_recycler);
        text_grade = (TextView) findViewById(R.id.text_grade);
        text_progress = (TextView) findViewById(R.id.text_progress);

        adapter = new AdvanceAdapter(AdvanceActivity.this);
        view_recycler.setAdapter(adapter);
        view_recycler.setLayoutManager(new LinearLayoutManager(AdvanceActivity.this));


        img_back.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        grade = intent.getIntExtra("grade", 1);
    }

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
