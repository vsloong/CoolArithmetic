package com.zxxxy.coolarithmetic.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.base.BaseActivity;
import com.zxxxy.coolarithmetic.utils.GreenDAOUtils;
import com.zxxxy.coolarithmetic.utils.StartActivityUtils;

public class WrongActivity extends BaseActivity implements View.OnClickListener {


    private TextView text_count;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        long count = GreenDAOUtils.getDefaultDaoSession(WrongActivity.this)
                .getQuestionDao()
                .count();
        text_count.setText(String.valueOf(count));
        if (count == 0) {
            btn_start.setEnabled(false);
        } else {
            btn_start.setEnabled(true);
        }

    }

    @Override
    protected void initViews() {
        text_count = (TextView) findViewById(R.id.text_count);

        btn_start = (Button) findViewById(R.id.btn_start);
        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_start:
                StartActivityUtils.startPlayActivity(WrongActivity.this, -1, -1, false);
                break;
            default:
                break;
        }
    }
}
