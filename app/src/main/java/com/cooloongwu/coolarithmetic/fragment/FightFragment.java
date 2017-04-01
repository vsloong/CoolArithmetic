package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.GradeAdapter;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.cooloongwu.coolarithmetic.utils.StartActivityUtils;

public class FightFragment extends BaseFragment implements View.OnClickListener {

    private GradeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fight, container, false);
        initToolBar(view);
        initViews(view);
        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("天天口算");
    }

    @Override
    protected void initViews(View view) {
        ImageView img_btn_rank = (ImageView) view.findViewById(R.id.img_btn_rank);
        RecyclerView view_recycler = (RecyclerView) view.findViewById(R.id.view_recycler);

        adapter = new GradeAdapter(getActivity());
        view_recycler.setAdapter(adapter);
        view_recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        img_btn_rank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_rank:
                StartActivityUtils.startRankActivity(getActivity());
                break;
            default:
                break;
        }
    }
}
