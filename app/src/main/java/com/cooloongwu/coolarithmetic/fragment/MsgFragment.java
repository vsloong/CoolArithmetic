package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.cooloongwu.coolarithmetic.entity.MsgTypeEnum;
import com.cooloongwu.coolarithmetic.utils.SendMsgUtils;

public class MsgFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        initToolBar(view);
        initViews(view);
        SendMsgUtils.sendTextMsg("2012329700030", "哇哈哈哈哇哈哈哈");
        SendMsgUtils.sendCustomMsg("2012329700030", MsgTypeEnum.PK);
        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("聊天");
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
    }

}
