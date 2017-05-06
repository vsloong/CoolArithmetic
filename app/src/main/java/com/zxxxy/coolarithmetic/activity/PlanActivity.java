package com.zxxxy.coolarithmetic.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.base.BaseActivity;
import com.zxxxy.coolarithmetic.sudoku.utils.MyContant;
import com.zxxxy.coolarithmetic.sudoku.utils.SharedPreferencesUtils;
import com.zxxxy.coolarithmetic.sudoku.utils.UIUtils;
import com.zxxxy.coolarithmetic.sudoku.views.ShuduView;

public class PlanActivity extends BaseActivity {

    private ShuduView shuduView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        initToolbar();

        String gameData = getIntent().getStringExtra(MyContant.CONTINUEGAME);
        shuduView = new ShuduView(this, null, gameData);
        setContentView(shuduView);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("学霸计划");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int[] gameData = shuduView.game.getSudoku();
            //把数据转换成String类型存储起来
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < gameData.length; i++) {
                stringBuilder.append(String.valueOf(gameData[i]));
            }
            //存储
            SharedPreferencesUtils.saveString(UIUtils.getContext(), MyContant.CONTINUEGAME, stringBuilder.toString());
        }
        return super.onKeyDown(keyCode, event);
    }
}
