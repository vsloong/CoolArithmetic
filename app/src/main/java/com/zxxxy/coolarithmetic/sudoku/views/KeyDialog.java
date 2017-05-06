package com.zxxxy.coolarithmetic.sudoku.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zxxxy.coolarithmetic.R;


/**
 * Author:ZengYinan zengyinanos@qq.com
 * Date:2016/6/30
 * Time:17:00
 * Desc:
 */
public class KeyDialog extends Dialog implements View.OnClickListener {

    private Button[] keys = new Button[9];//用来存放代表对话框当中按钮的对象
    private int[] used;
    private Context context;
    private OnSelectedListener listener;

    public KeyDialog(Context context, int[] used) {
        super(context);
        this.used = used;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        for (Button btn : keys) {
            btn.setOnClickListener(this);
        }
    }

    private void initData() {
        for (int i = 0; i < used.length; i++) {
            keys[used[i] - 1].setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {
        setContentView(R.layout.keypad);
        keys[0] = (Button) findViewById(R.id.keypad_1);
        keys[1] = (Button) findViewById(R.id.keypad_2);
        keys[2] = (Button) findViewById(R.id.keypad_3);
        keys[3] = (Button) findViewById(R.id.keypad_4);
        keys[4] = (Button) findViewById(R.id.keypad_5);
        keys[5] = (Button) findViewById(R.id.keypad_6);
        keys[6] = (Button) findViewById(R.id.keypad_7);
        keys[7] = (Button) findViewById(R.id.keypad_8);
        keys[8] = (Button) findViewById(R.id.keypad_9);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.keypad_1:
                listener.selected(1);
                break;
            case R.id.keypad_2:
                listener.selected(2);
                break;
            case R.id.keypad_3:
                listener.selected(3);
                break;
            case R.id.keypad_4:
                listener.selected(4);
                break;
            case R.id.keypad_5:
                listener.selected(5);
                break;
            case R.id.keypad_6:
                listener.selected(6);
                break;
            case R.id.keypad_7:
                listener.selected(7);
                break;
            case R.id.keypad_8:
                listener.selected(8);
                break;
            case R.id.keypad_9:
                listener.selected(9);
                break;
            default:
                break;
        }
        this.dismiss();
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * 提供一个返回数据接口
     */
    public interface OnSelectedListener {
        void selected(int tile);
    }
}
