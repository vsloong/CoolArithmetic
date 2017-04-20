package com.cooloongwu.coolarithmetic.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.entity.Question;
import com.cooloongwu.coolarithmetic.utils.GreenDAOUtils;
import com.cooloongwu.greendao.gen.QuestionDao;

import java.util.List;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //initToolbar();
        initData();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("考试");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        List<Question> questions = GreenDAOUtils.getQuestionInstance(this).getQuestionDao()
                .queryBuilder()
                .where(QuestionDao.Properties.Grade.eq(0), QuestionDao.Properties.Advance.eq(0))
                .build().list();

        if (questions.isEmpty()) {
            Log.e("题目", "没有");
        } else {
            for (Question question : questions) {
                Log.e("题目", question.getQuestion());
            }
        }

    }
}
