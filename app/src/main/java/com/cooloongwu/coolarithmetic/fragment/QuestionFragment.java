package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.cooloongwu.coolarithmetic.entity.Question;

public class QuestionFragment extends BaseFragment {

    private Question question;

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

        View view = inflater.inflate(R.layout.layout_play_question, container, false);
        initViews(view);
        return view;
    }

    @Override
    protected void initViews(View view) {
        TextView text_question_title = (TextView) view.findViewById(R.id.text_question_title);

        RadioButton radio_button_a = (RadioButton) view.findViewById(R.id.radio_button_a);
        RadioButton radio_button_b = (RadioButton) view.findViewById(R.id.radio_button_b);
        RadioButton radio_button_c = (RadioButton) view.findViewById(R.id.radio_button_c);
        RadioButton radio_button_d = (RadioButton) view.findViewById(R.id.radio_button_d);

        text_question_title.setText(String.valueOf(question.getQuestion()));

        radio_button_a.setText(String.valueOf(question.getAnswerA()));
        radio_button_b.setText(String.valueOf(question.getAnswerB()));
        radio_button_c.setText(String.valueOf(question.getAnswerC()));
        radio_button_d.setText(String.valueOf(question.getAnswerD()));
    }
}
