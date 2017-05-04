package com.zxxxy.coolarithmetic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.squareup.picasso.Picasso;
import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.adapter.QuestionAdapter;
import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.coolarithmetic.base.BaseActivity;
import com.zxxxy.coolarithmetic.entity.Advance;
import com.zxxxy.coolarithmetic.entity.MsgTypeEnum;
import com.zxxxy.coolarithmetic.entity.Question;
import com.zxxxy.coolarithmetic.utils.AvatarUtils;
import com.zxxxy.coolarithmetic.utils.DBService;
import com.zxxxy.coolarithmetic.utils.GreenDAOUtils;
import com.zxxxy.coolarithmetic.utils.SendMsgUtils;
import com.zxxxy.greendao.gen.AdvanceDao;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PlayActivity extends BaseActivity implements View.OnClickListener {

    private TextView text_play_timer;
    private ViewPager view_pager;
    private Button btn_submit;
    private AlertDialog playTimeOutDialog;

    private int time = 40;              //默认每关40秒
    private int advance = 0;            //默认闯关是第一关
    private boolean isPK = false;       //默认不是PK
    private boolean isCorrect = false;       //默认不是扫除错题
    private List<Question> questions;
    private MyCountDownTimer countDownTimer;

    private static List<Integer> answers = new ArrayList<>();
    public static List<Integer> myAnswers = new ArrayList<>();

    private AlertDialog pkResultDialog;
    private TextView text_rate_other;
    private TextView text_time_other;
    private ImageView img_result;
    private String otherGrades = "";
    private String otherTime = "";
    private int grades = 0;     //分数
    private int grade = 0;      //年级


    private Observer<CustomNotification> customNotificationObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            // 在这里处理自定义通知。
            Log.e("PlayActivity接收到的自定义消息", message.getContent());
            try {
                JSONObject jsonObject = new JSONObject(message.getContent());
                MsgTypeEnum typeEnum = MsgTypeEnum.valueOf(jsonObject.getString("type"));
                switch (typeEnum) {
                    case PK:
                        Log.e("接收到的自定义PK消息", message.getContent());
                        MsgTypeEnum subtypeEnum = MsgTypeEnum.valueOf(jsonObject.getString("subtype"));
                        switch (subtypeEnum) {
                            case PK_RESULT:
                                String str = jsonObject.getString("msg");
                                String result[] = str.split("-");
                                Log.e("对方的结果", "正确数：" + result[0] + "时间：" + result[1]);
                                if (pkResultDialog != null && pkResultDialog.isShowing()) {
                                    text_rate_other.setText(result[0] + "%");
                                    text_time_other.setText(result[1] + "s");

                                    if (grades < Integer.parseInt(result[0])) {
                                        img_result.setImageResource(R.mipmap.pk_result_fail_hint);
                                    } else if (grades == Integer.parseInt(result[0])) {
                                        if ((40 - advance - time) > Integer.parseInt(result[1])) {
                                            img_result.setImageResource(R.mipmap.pk_result_fail_hint);
                                        }
                                    }
                                } else {
                                    otherGrades = result[0];
                                    otherTime = result[1];
                                }
                                break;
                            default:
                                break;
                        }

                        break;
                    default:
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    static {
        //设置默认没有选择答案，所以为-1
        for (int i = 0; i < 10; i++) {
            PlayActivity.myAnswers.add(i, -1);
        }
        for (int i = 0; i < 10; i++) {
            answers.add(i, -1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotificationObserver, true);

        getIntentData();
        initViews();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        grade = intent.getIntExtra("grade", 0);
        advance = intent.getIntExtra("advance", 0);
        isPK = intent.getBooleanExtra("isPK", false);


        //如果是改错，那么就去查询错题数据库。否则查询题目数据库
        if (grade == -1 && advance == -1) {
            isCorrect = true;
            advance = 20;       //为了下面计算不出问题
            time = 20;
            getWrongQuestions();
        } else {
            time -= advance;
            getQuestions(grade, advance);
        }

        //开始倒计时
        countDownTimer = new MyCountDownTimer(time * 1000, 1000);
        countDownTimer.start();
    }

    @Override
    protected void initViews() {
        ImageView img_play_back = (ImageView) findViewById(R.id.img_play_back);
        text_play_timer = (TextView) findViewById(R.id.text_play_timer);

        RelativeLayout layout_avatar = (RelativeLayout) findViewById(R.id.layout_avatar);
        if (!isPK) {
            layout_avatar.setVisibility(View.GONE);
        } else {
            ImageView img_avatar_mine = (ImageView) findViewById(R.id.img_avatar_mine);
            ImageView img_avatar_other = (ImageView) findViewById(R.id.img_avatar_other);

            Picasso.with(this)
                    .load(AvatarUtils.getAvatar(AppConfig.getUserAccid()))
                    .into(img_avatar_mine);

            Picasso.with(this)
                    .load(AvatarUtils.getAvatar(MainActivity.fromAccid))
                    .into(img_avatar_other);
        }

        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setAdapter(new QuestionAdapter(getSupportFragmentManager(), questions));
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("onPageScrolled", position + "");
                if (position == questions.size() - 1 || questions.size() == 1) {
                    btn_submit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("onPageSelected", position + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("PageScrollStateChanged", state + "");
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
                Toast.makeText(PlayActivity.this, "答题中不可退出", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_next:
                if (view_pager.getCurrentItem() < questions.size() - 1) {
                    view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                }
                break;
            case R.id.btn_last:
                if (view_pager.getCurrentItem() > 0) {
                    view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
                }
                break;
            case R.id.btn_submit:
                submit();
                break;
            case R.id.btn_ok:
                showResult();
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
                answers.add(question.getQuestionId(), question.getAnswer());
                Log.e("题目", question.getQuestion() + "的答案是" + question.getAnswer());
            }
        } else {
            Log.e("题目", "没有获取到题目数据");
        }
    }

    private void getWrongQuestions() {
        questions = GreenDAOUtils.getDefaultDaoSession(PlayActivity.this).getQuestionDao()
                .queryBuilder()
                .limit(5)
                .list();

        if (questions != null && !questions.isEmpty()) {
            for (Question question : questions) {
                answers.add(question.getQuestionId(), question.getAnswer());
                Log.e("错题本题目", question.getQuestion() + "的答案是" + question.getAnswer());
            }
        } else {
            Log.e("错题本题目", "没有获取到题目数据");
        }
    }

    private void submit() {
//        if (checkAllAnswerIsSelected()) {
        countDownTimer.cancel();
        showResult();
//        } else {
//            Toast.makeText(PlayActivity.this, "还有未完成的题目，确定交卷么", Toast.LENGTH_SHORT).show();
//        }
    }

    private boolean checkAllAnswerIsSelected() {
        for (int i = 0; i < questions.size() - 1; i++) {
            if (myAnswers.get(i) == -1) {
                return false;
            }
        }
        return true;
    }

    private void showResult() {
        int rightAnswer = 0;
//        for (int i = 0; i < questions.size(); i++) {
//            if (answers.get(i) == myAnswers.get(i)) {
//                Log.e("交卷结果", "第" + i + "题正确");
//                rightAnswer++;
//
//                GreenDAOUtils.getDefaultDaoSession(PlayActivity.this).getQuestionDao()
//                        .delete(questions.get(i));
//            } else {
//                questions.get(i).setSelectedAnswer(myAnswers.get(i));
//                Log.e("交卷结果", "第" + i + "题错误，题目：" + questions.get(i).getQuestion()
//                        + "，正确答案：第" + questions.get(i).getAnswer() + "个"
//                        + "，我的答案：第" + questions.get(i).getSelectedAnswer() + "个");
//
//                //如果不是扫除错题那么就将这道错题录入数据库。否则不再重复录入。同时也是为了解决一个如下的问题（当然可以通过修改ID来解决）
//                // android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: QUESTION._id(Sqlite code 1555),(OS error - 2:No such file or directory)
//                if (!isCorrect) {
//                    GreenDAOUtils.getDefaultDaoSession(PlayActivity.this).getQuestionDao()
//                            .insert(questions.get(i));
//                }
//            }
//        }

        for (Question question : questions) {
            if (answers.get(question.getQuestionId()) == myAnswers.get(question.getQuestionId())) {
                Log.e("交卷结果", "第" + question.getQuestionId() + "题正确");
                rightAnswer++;

                GreenDAOUtils.getDefaultDaoSession(PlayActivity.this).getQuestionDao()
                        .delete(question);
            } else {
                question.setSelectedAnswer(myAnswers.get(question.getQuestionId()));
                Log.e("交卷结果", "第" + question.getQuestionId() + "题错误，题目：" + question.getQuestion()
                        + "，A：" + question.getAnswerA()
                        + "，B：" + question.getAnswerB()
                        + "，C：" + question.getAnswerC()
                        + "，D：" + question.getAnswerD()
                        + "，正确答案：第" + (question.getAnswer() + 1) + "个"
                        + "，我的答案：第" + (question.getSelectedAnswer() + 1) + "个");

                //如果不是扫除错题那么就将这道错题录入数据库。否则不再重复录入。同时也是为了解决一个如下的问题（当然可以通过修改ID来解决）
                // android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: QUESTION._id(Sqlite code 1555),(OS error - 2:No such file or directory)
                if (!isCorrect) {
                    question.setId(null);
                    GreenDAOUtils.getDefaultDaoSession(PlayActivity.this).getQuestionDao()
                            .insert(question);
                }
            }
        }

        grades = rightAnswer * 10;
        //增加经验值
        AppConfig.increaseUserEXP(grades);
        if (isPK) {
            showPKResultDialog(rightAnswer * 10);
            SendMsgUtils.sendPKMsg(MainActivity.fromAccid, AppConfig.getUserAccid(), (rightAnswer * 10) + "-" + (40 - advance - time), MsgTypeEnum.PK_RESULT);
        } else {
            showResultDialog(rightAnswer * 10);

            if (rightAnswer >= 8) {
                if (advance < 20) {
                    Advance advanceBean = GreenDAOUtils.getDefaultDaoSession(PlayActivity.this).getAdvanceDao()
                            .queryBuilder()
                            .where(AdvanceDao.Properties.Grade.eq(grade))
                            .build().unique();
                    if (advance == advanceBean.getAdvance()) {
                        advanceBean.setAdvance(advance + 1);
                        GreenDAOUtils.getDefaultDaoSession(PlayActivity.this).getAdvanceDao().update(advanceBean);
                        EventBus.getDefault().post(advanceBean);
                    }

                }
            }

        }
    }

    private void showResultDialog(int grades) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_play_result, null);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);

        TextView text_title = (TextView) view.findViewById(R.id.text_title);
        TextView text_grades = (TextView) view.findViewById(R.id.text_grades);
        TextView text_correct_rate = (TextView) view.findViewById(R.id.text_correct_rate);
        TextView text_time = (TextView) view.findViewById(R.id.text_time);
        TextView text_fraction = (TextView) view.findViewById(R.id.text_fraction);

        text_title.setText("限时：" + (40 - advance) + "秒      题数：+" + questions.size() + "题");
        text_grades.setText(String.valueOf(grades));
        text_correct_rate.setText(grades + "%");
        text_time.setText((40 - advance - time) + "s");
        text_fraction.setText(String.valueOf(grades));

        builder.setView(view);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog resultDialog = builder.create();
        resultDialog.show();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultDialog.dismiss();
                finish();
            }
        });
    }

    private void showPKResultDialog(int grades) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_play_pk_result, null);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);

        img_result = (ImageView) view.findViewById(R.id.img_result);

        TextView text_points = (TextView) view.findViewById(R.id.text_points);
        text_points.setText("+" + grades);

        TextView text_rate_mine = (TextView) view.findViewById(R.id.text_rate_mine);
        TextView text_time_mine = (TextView) view.findViewById(R.id.text_time_mine);
        text_rate_other = (TextView) view.findViewById(R.id.text_rate_other);
        text_time_other = (TextView) view.findViewById(R.id.text_time_other);

        text_rate_mine.setText(grades + "%");
        text_time_mine.setText((40 - advance - time) + "s");

        text_rate_other.setText(TextUtils.isEmpty(otherGrades) ? "对方还未完成" : otherGrades + "%");
        text_time_other.setText(TextUtils.isEmpty(otherTime) ? "对方还未完成" : otherTime + "s");

        if (!TextUtils.isEmpty(otherGrades)) {
            if (grades < Integer.parseInt(otherGrades)) {
                img_result.setImageResource(R.mipmap.pk_result_fail_hint);
            } else if (grades == Integer.parseInt(otherGrades)) {
                if ((40 - advance - time) > Integer.parseInt(otherTime)) {
                    img_result.setImageResource(R.mipmap.pk_result_fail_hint);
                }
            }
        }


        ImageView img_avatar_mine = (ImageView) view.findViewById(R.id.img_avatar_mine);
        ImageView img_avatar_other = (ImageView) view.findViewById(R.id.img_avatar_other);

        Picasso.with(this)
                .load(AvatarUtils.getAvatar(AppConfig.getUserAccid()))
                .into(img_avatar_mine);

        Picasso.with(this)
                .load(AvatarUtils.getAvatar(MainActivity.fromAccid))
                .into(img_avatar_other);

        builder.setView(view);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        pkResultDialog = builder.create();
        pkResultDialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pkResultDialog.dismiss();
                finish();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotificationObserver, false);
        countDownTimer.cancel();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(PlayActivity.this, "答题中不可退出", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
