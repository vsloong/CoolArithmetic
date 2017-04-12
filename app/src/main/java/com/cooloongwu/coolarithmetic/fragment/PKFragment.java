package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.cooloongwu.coolarithmetic.entity.MsgTypeEnum;
import com.cooloongwu.coolarithmetic.utils.SendMsgUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;

import java.util.List;

public class PKFragment extends BaseFragment implements View.OnClickListener {

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
        Button btn_pk_system = (Button) view.findViewById(R.id.btn_pk_system);
        Button btn_pk_friend = (Button) view.findViewById(R.id.btn_pk_friend);

        btn_pk_system.setOnClickListener(this);
        btn_pk_friend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pk_friend:
                searchFriendToPK();
                break;
            default:
                break;
        }
    }

    private void searchFriendToPK() {
        List<String> friends = NIMClient.getService(FriendService.class).getFriendAccounts();
        SendMsgUtils.sendCustomMsg(friends.get(0), MsgTypeEnum.PK);
    }
}
