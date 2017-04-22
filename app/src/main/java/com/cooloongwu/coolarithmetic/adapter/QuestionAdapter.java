package com.cooloongwu.coolarithmetic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.cooloongwu.coolarithmetic.entity.Question;
import com.cooloongwu.coolarithmetic.fragment.QuestionFragment;

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
