package com.zxxxy.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.adapter.GradeAdapter;
import com.zxxxy.coolarithmetic.base.BaseFragment;
import com.zxxxy.coolarithmetic.entity.Advance;
import com.zxxxy.coolarithmetic.utils.StartActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class FightFragment extends BaseFragment implements View.OnClickListener {

    private GradeAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

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

    @Subscribe
    public void onEventMainThread(Advance advance) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
