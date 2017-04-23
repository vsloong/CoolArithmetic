package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.activity.PlayActivity;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.cooloongwu.coolarithmetic.entity.Question;

public class QuestionFragment extends BaseFragment {

    private Question question;
    private View view;

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("question", question);
        //fragment保存参数，传入一个Bundle对象
        questionFragment.setArguments(bundle);
        return questionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            //取出保存的值
            question = (Question) getArguments().getSerializable("question");
        }

        view = inflater.inflate(R.layout.layout_play_question, container, false);
        initViews(view);
        return view;
    }

    @Override
    protected void initViews(final View view) {
        TextView text_question_title = (TextView) view.findViewById(R.id.text_question_title);

        RadioGroup radio_group = (RadioGroup) view.findViewById(R.id.radio_group);

        RadioButton radio_button_a = (RadioButton) view.findViewById(R.id.radio_button_a);
        RadioButton radio_button_b = (RadioButton) view.findViewById(R.id.radio_button_b);
        RadioButton radio_button_c = (RadioButton) view.findViewById(R.id.radio_button_c);
        RadioButton radio_button_d = (RadioButton) view.findViewById(R.id.radio_button_d);

        text_question_title.setText(String.valueOf(question.getQuestionId() + 1) + "、" + String.valueOf(question.getQuestion()));

        radio_button_a.setText("A、" + String.valueOf(question.getAnswerA()));
        radio_button_b.setText("B、" + String.valueOf(question.getAnswerB()));
        radio_button_c.setText("C、" + String.valueOf(question.getAnswerC()));
        radio_button_d.setText("D、" + String.valueOf(question.getAnswerD()));

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int myAnswer = group.indexOfChild(view.findViewById(checkedId));
                Log.e("答案选择", "选择了第" + myAnswer + "个答案");
                PlayActivity.myAnswers.add(question.getQuestionId(), myAnswer);
            }
        });
    }
}
