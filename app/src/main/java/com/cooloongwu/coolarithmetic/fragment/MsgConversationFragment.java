package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseFragment;

public class MsgConversationFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pk, container, false);
        initViews(view);
        return view;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
    }
}
