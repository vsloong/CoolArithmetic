package com.zxxxy.coolarithmetic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zxxxy.coolarithmetic.entity.Question;
import com.zxxxy.coolarithmetic.fragment.QuestionFragment;

import java.util.List;

/**
 * Created by Wu_Shuailong on 2017-4-22.
 */

public class QuestionAdapter extends FragmentPagerAdapter {

    private List<Question> questions;

    public QuestionAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        this.questions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionFragment.newInstance(questions.get(position));
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}
