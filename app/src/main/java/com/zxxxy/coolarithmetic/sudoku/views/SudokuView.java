package com.zxxxy.coolarithmetic.sudoku.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.activity.PlanActivity;
import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.coolarithmetic.sudoku.logics.Game;
import com.zxxxy.coolarithmetic.sudoku.utils.MyContant;
import com.zxxxy.coolarithmetic.sudoku.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:ZengYinan zengyinanos@qq.com
 * Date:2016/6/29
 * Time:19:50
 * Desc:
 */
public class SudokuView extends View {

    //单元格的宽度和高度
    private float width;
    private float height;
    public Game game;
    private Context context;
    private int selectedX;
    private int selectedY;
    private String continueGame = "";

    Paint numberPaint = new Paint();
    Paint lightPaint = new Paint();
    Paint hilitePaint = new Paint();
    Paint darkPaint = new Paint();
    Paint highlightPaint = new Paint();

    public SudokuView(Context context) {
        this(context, null);
    }

    public SudokuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuView(Context context, AttributeSet attrs, String continueGame) {
        this(context, attrs);
        this.context = context;
        this.continueGame = continueGame;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        //计算当前单元格的宽度和高度
        this.width = w / 9f;
        this.height = w / 9f;
        super.onSizeChanged(w, h, oldW, oldH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        darkPaint.setColor(ContextCompat.getColor(context, R.color.shudu_dark));
        hilitePaint.setColor(ContextCompat.getColor(context, R.color.shudu_hlllte));
        lightPaint.setColor(ContextCompat.getColor(context, R.color.shudu_light));

        for (int i = 0; i < 10; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);//横线
            canvas.drawLine(i * width, 0, i * width, getHeight(), lightPaint);//竖线

            if (i == 0) {
                canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilitePaint);
                canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilitePaint);
            } else if (i % 3 == 0) {
                canvas.drawLine(0, i * height - 1, getWidth(), i * height - 1, hilitePaint);
                canvas.drawLine(i * width - 1, 0, i * width - 1, getHeight(), hilitePaint);
            }
        }

        //绘制当前要高亮显示的区域
        if (PlanActivity.canInput) {
            highlightPaint.setColor(ContextCompat.getColor(context, R.color.shudu_light));
            Rect rect = new Rect((int) (selectedX * width), (int) (selectedY * height), (int) ((selectedX + 1) * width), (int) ((selectedY + 1) * height));
            Log.e("高亮显示", "" + (int) (selectedX * width) + "；" + (int) (selectedY * height) + "；" + (int) ((selectedX + 1) * width) + "；" + (int) ((selectedY + 1) * height));
            canvas.drawRect(rect, highlightPaint);
        }


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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //Log.d("SudokuView", "tile:" + game.getTilesString(i, j));
                numberPaint.setColor(ContextCompat.getColor(context, R.color.text_white));
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

        Log.e("点击的位置", "X" + selectedX + "；Y" + selectedY);

        if (game.isSet(selectedX, selectedY)) {
            Toast.makeText(context, "此位置上已经有数字了", Toast.LENGTH_SHORT).show();
            return super.onTouchEvent(event);
        }

        int used[] = game.calculateUsedTiles(selectedX, selectedY);//已经使用过的数据

        if (used.length == 9) {
            showDialog(false, "已经没有可以填入的数字了!");
        } else {
            //设置该位置高亮显示
            PlanActivity.canInput = true;

            Paint highlightPaint = new Paint();
            highlightPaint.setColor(ContextCompat.getColor(context, R.color.shudu_light));

            Rect rect = new Rect((int) (selectedX * width), (int) (selectedY * height), (int) ((selectedX + 1) * width), (int) ((selectedY + 1) * height));
            Log.e("高亮显示", "" + (int) (selectedX * width) + "；" + (int) (selectedY * height) + "；" + (int) ((selectedX + 1) * width) + "；" + (int) ((selectedY + 1) * height));
            Canvas canvas = new Canvas();
            canvas.drawRect(rect, highlightPaint);
            invalidate();

            //生成一个Dialog
//            KeyDialog dialog = new KeyDialog(context, used);
//            dialog.setTitle("请选择要填入的数字");
//            dialog.setOnSelectedListener(new KeyDialog.OnSelectedListener() {
//                @Override
//                public void selected(int tile) {
//                    //将剩余空格进行减1操作
//                    Game.count--;
//                    game.setTile(selectedX, selectedY, tile);
//                    invalidate();
//                    if (Game.count == 0) {
//                        //胜利
//                        showDialog("胜利啦", "恭喜您以全部填写正确");
//                    }
//                }
//            });
//            dialog.show();
        }

        return true;
    }

    public void setNumber(int selectedNum) {
        Game.count--;
        game.setTile(selectedX, selectedY, selectedNum);
        invalidate();
        if (Game.count == 0) {
            //胜利
            showDialog(true, "恭喜您全部填写正确！");
            AppConfig.increaseUserEXP(1000 / PlanActivity.time);

            //更新用户数据
            Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
            fields.put(UserInfoFieldEnum.EXTEND, String.valueOf(AppConfig.getUserEXP()));
            NIMClient.getService(UserService.class).updateUserInfo(fields)
                    .setCallback(new RequestCallbackWrapper<Void>() {
                        @Override
                        public void onResult(int code, Void result, Throwable exception) {
                            Log.e("更新用户数据结果", "" + code);
                        }
                    });
        }
    }

    public void showDialog(boolean isSuccess, String msg) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_result_sudoku, null);
        builder.setView(view);
        builder.setCancelable(false);

        final android.support.v7.app.AlertDialog resultDialog = builder.create();
        resultDialog.show();

        ImageView img_result = (ImageView) view.findViewById(R.id.img_result);
        if (isSuccess) {
            img_result.setImageResource(R.mipmap.pk_result_success_hint);
        }
        TextView text_msg = (TextView) view.findViewById(R.id.text_msg);
        Button btn_finish = (Button) view.findViewById(R.id.btn_finish);
        Button btn_restart = (Button) view.findViewById(R.id.btn_restart);

        text_msg.setText(msg);

        btn_finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.saveString(context, MyContant.CONTINUEGAME, "");
                ((PlanActivity) context).finish();
                resultDialog.dismiss();
            }
        });
        btn_restart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                game.reStart();
                invalidate();
                resultDialog.dismiss();
            }
        });
    }

}
