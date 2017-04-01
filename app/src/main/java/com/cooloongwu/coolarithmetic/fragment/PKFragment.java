package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseFragment;

public class PKFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pk, container, false);
        initToolBar(view);
        initViews(view);
        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("PK");
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
    }
}
