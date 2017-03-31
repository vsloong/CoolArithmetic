package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.GradeAdapter;
import com.cooloongwu.coolarithmetic.base.BaseFragment;

public class FightFragment extends BaseFragment {

    private GradeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fight, container, false);
        initViews(view);
        return view;
    }

    @Override
    protected void initViews(View view) {
        RecyclerView view_recycler = (RecyclerView) view.findViewById(R.id.view_recycler);

        adapter = new GradeAdapter(getActivity());
        view_recycler.setAdapter(adapter);
        view_recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

}
