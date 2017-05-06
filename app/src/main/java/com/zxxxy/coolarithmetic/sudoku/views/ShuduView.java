package com.zxxxy.coolarithmetic.sudoku.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.activity.PlanActivity;
import com.zxxxy.coolarithmetic.sudoku.logics.Game;
import com.zxxxy.coolarithmetic.sudoku.utils.MyContant;
import com.zxxxy.coolarithmetic.sudoku.utils.SharedPreferencesUtils;

import java.util.Random;

/**
 * Author:ZengYinan zengyinanos@qq.com
 * Date:2016/6/29
 * Time:19:50
 * Desc:
 */
public class ShuduView extends View {

    //单元格的宽度和高度
    private float width;
    private float height;
    public Game game;
    private Context context;
    private int selectedX;
    private int selectedY;
    private String continueGame = "";

    public ShuduView(Context context) {
        this(context, null);
    }

    public ShuduView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShuduView(Context context, AttributeSet attrs, String continueGame) {
        this(context, attrs);
        this.context = context;
        this.continueGame = continueGame;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //计算当前单元格的宽度和高度
        this.width = w / 9f;
        this.height = h / 9f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint testPaint = new Paint();
        testPaint.setColor(Color.BLACK);

        Paint backgoundPaint = new Paint();
        backgoundPaint.setColor(getResources().getColor(R.color.shudu_background));
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgoundPaint);

        Paint darkPaint = new Paint();
        darkPaint.setColor(getResources().getColor(R.color.shudu_dark));

        Paint hilitePaint = new Paint();
        hilitePaint.setColor(getResources().getColor(R.color.shudu_hlllte));

        Paint lightPaint = new Paint();
        lightPaint.setColor(getResources().getColor(R.color.shudu_light));

        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);//横线
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilitePaint);
            canvas.drawLine(i * width, 0, i * width, getHeight(), lightPaint);//竖线
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilitePaint);
        }

        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0) {
                continue;
            }
            canvas.drawLine(0, i * height, getWidth(), i * height, darkPaint);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilitePaint);
            canvas.drawLine(i * width, 0, i * width, getHeight(), darkPaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilitePaint);
        }

        Paint numberPaint = new Paint();
        numberPaint.setColor(Color.BLACK);
        numberPaint.setStyle(Paint.Style.STROKE);
        numberPaint.setTextSize(height * 0.75f);
        numberPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics metrics = numberPaint.getFontMetrics();
        float x = width / 2;
        float y = (metrics.ascent + metrics.descent) / 2 - height / 2;//这个是字的一半的高度

        if (game == null) {
            game = new Game(continueGame);
        }
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //Log.d("ShuduView", "tile:" + game.getTilesString(i, j));
                numberPaint.setColor(Color.argb(255, random.nextInt(255) + 80, random.nextInt(255) + 80, random.nextInt(255) + 80));
                canvas.drawText(game.getTilesString(i, j), i * width + x, j * height - y, numberPaint);
            }
        }

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }
        selectedX = (int) (event.getX() / width);
        selectedY = (int) (event.getY() / height);

        if (game.isSet(selectedX, selectedY)) {
            Toast.makeText(context, "此位置上已经有数字了", Toast.LENGTH_SHORT).show();
            return super.onTouchEvent(event);
        }

        int used[] = game.calculateUsedTiles(selectedX, selectedY);//已经使用过的数据

        if (used.length == 9) {
            showDialog("游戏结束", "该位置上已经没有可以填入的数字了!");
        } else {
            //生成一个Dialog
            KeyDialog dialog = new KeyDialog(context, used);
            dialog.setTitle("请选择要填入的数字");
            dialog.setOnSelectedListener(new KeyDialog.OnSelectedListener() {
                @Override
                public void selected(int tile) {
                    //将剩余空格进行减1操作
                    Game.count--;
                    game.setTile(selectedX, selectedY, tile);
                    invalidate();
                    if (Game.count == 0) {
                        //胜利
                        showDialog("胜利啦", "恭喜您以全部填写正确");
                    }
                }
            });
            dialog.show();
        }

        return true;
    }

    public void showDialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game.reStart();
                invalidate();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferencesUtils.saveString(context, MyContant.CONTINUEGAME, "");
                ((PlanActivity) context).finish();
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
