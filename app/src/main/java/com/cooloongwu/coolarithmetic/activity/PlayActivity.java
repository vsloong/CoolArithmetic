package com.cooloongwu.coolarithmetic.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.QuestionAdapter;
import com.cooloongwu.coolarithmetic.base.BaseActivity;
import com.cooloongwu.coolarithmetic.entity.Question;
import com.cooloongwu.coolarithmetic.utils.DBService;

import java.util.List;


public class PlayActivity extends BaseActivity implements View.OnClickListener {

    private TextView text_play_timer;
    private ViewPager view_pager;
    private Button btn_submit;
    private AlertDialog playTimeOutDialog;

    private int time = 40;      //默认每关40秒
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getIntentData();
        initViews();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        int grade = intent.getIntExtra("grade", 0);
        int advance = intent.getIntExtra("advance", 0);
        getQuestions(grade, advance);

        //开始倒计时
        time -= advance;
        Log.e("第几关呢", "" + advance + "；时间" + time);
        new MyCountDownTimer(time * 1000, 1000).start();
    }

    @Override
    protected void initViews() {
        ImageView img_play_back = (ImageView) findViewById(R.id.img_play_back);
        text_play_timer = (TextView) findViewById(R.id.text_play_timer);

        RelativeLayout layout_avatar = (RelativeLayout) findViewById(R.id.layout_avatar);
        layout_avatar.setVisibility(View.GONE);

        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setAdapter(new QuestionAdapter(getSupportFragmentManager(), questions));
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("当前位置", position + "");
                if (position == 9) {
                    btn_submit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Button btn_next = (Button) findViewById(R.id.btn_next);
        Button btn_last = (Button) findViewById(R.id.btn_last);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        img_play_back.setOnClickListener(this);
        btn_last.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_play_back:
                finish();
                break;
            case R.id.btn_next:
                if (view_pager.getCurrentItem() < 9) {
                    view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                }
                break;
            case R.id.btn_last:
                if (view_pager.getCurrentItem() > 0) {
                    view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
                }
                break;
            case R.id.btn_submit:
                Toast.makeText(PlayActivity.this, "交卷", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_ok:
                Toast.makeText(PlayActivity.this, "交卷", Toast.LENGTH_SHORT).show();
                if (playTimeOutDialog != null) {
                    playTimeOutDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 展示等待对方PK接受或者拒绝的对话框
     */
    public void showTimeOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_play_time_out, null);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        builder.setView(view);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        playTimeOutDialog = builder.create();
        playTimeOutDialog.show();
    }

    private void getQuestions(int grade, int advance) {
        DBService dbService = new DBService();
        questions = dbService.getQuestion(grade, advance);
        if (questions != null && !questions.isEmpty()) {
            for (Question question : questions) {
                Log.e("题目", question.getQuestion() + "");
            }
        } else {
            Log.e("题目", "没有获取到题目数据");
        }
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            text_play_timer.setText(String.valueOf(time -= 1) + "s");
        }

        @Override
        public void onFinish() {
            text_play_timer.setText("0s");
            showTimeOutDialog();
        }
    }

}
