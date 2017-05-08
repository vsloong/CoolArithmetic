package com.zxxxy.coolarithmetic.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.base.BaseActivity;
import com.zxxxy.coolarithmetic.sudoku.utils.MyContant;
import com.zxxxy.coolarithmetic.sudoku.views.SudokuView;

public class PlanActivity extends BaseActivity implements View.OnClickListener {

    private SudokuView sudokuView;
    public static boolean canInput = false;

    private Handler handler = new Handler();
    private TextView text_timer;
    private int time = 0;

    @Override
    public void finishActivity(int requestCode) {
        super.finishActivity(requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        initViews();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void initViews() {
        super.initViews();
        String gameData = getIntent().getStringExtra(MyContant.CONTINUEGAME);
        sudokuView = new SudokuView(this, null, gameData);

        LinearLayout layout_sudoku = (LinearLayout) findViewById(R.id.layout_sudoku);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_sudoku.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.height = params.width;
        Log.e("数独视图的宽高 ", "width：height：" + params.width);
        layout_sudoku.setLayoutParams(params);
        layout_sudoku.addView(sudokuView);

        text_timer = (TextView) findViewById(R.id.text_timer);

        ImageView img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        Button btn_1 = (Button) findViewById(R.id.btn_1);
        Button btn_2 = (Button) findViewById(R.id.btn_2);
        Button btn_3 = (Button) findViewById(R.id.btn_3);
        Button btn_4 = (Button) findViewById(R.id.btn_4);
        Button btn_5 = (Button) findViewById(R.id.btn_5);
        Button btn_6 = (Button) findViewById(R.id.btn_6);
        Button btn_7 = (Button) findViewById(R.id.btn_7);
        Button btn_8 = (Button) findViewById(R.id.btn_8);
        Button btn_9 = (Button) findViewById(R.id.btn_9);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            int[] gameData = sudokuView.game.getSudoku();
//            //把数据转换成String类型存储起来
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0; i < gameData.length; i++) {
//                stringBuilder.append(String.valueOf(gameData[i]));
//            }
//            //存储
//            SharedPreferencesUtils.saveString(UIUtils.getContext(), MyContant.CONTINUEGAME, stringBuilder.toString());
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_1:
                setNumber(1);
                break;
            case R.id.btn_2:
                setNumber(2);
                break;
            case R.id.btn_3:
                setNumber(3);
                break;
            case R.id.btn_4:
                setNumber(4);
                break;
            case R.id.btn_5:
                setNumber(5);
                break;
            case R.id.btn_6:
                setNumber(6);
                break;
            case R.id.btn_7:
                setNumber(7);
                break;
            case R.id.btn_8:
                setNumber(8);
                break;
            case R.id.btn_9:
                setNumber(9);
                break;
            default:
                break;
        }
    }

    private void setNumber(int number) {
        if (canInput) {
            sudokuView.setNumber(number);
            canInput = false;
        } else {
            Toast.makeText(PlanActivity.this, "请选择一个位置", Toast.LENGTH_SHORT).show();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time++;
            String minute = String.valueOf(time / 60);
            String second = String.valueOf(time % 60);

            if (minute.length() == 1) {
                minute = "0" + minute;
            }

            if (second.length() == 1) {
                second = "0" + second;
            }
            text_timer.setText(minute + ":" + second);
            handler.postDelayed(this, 1000);
        }
    };
}
