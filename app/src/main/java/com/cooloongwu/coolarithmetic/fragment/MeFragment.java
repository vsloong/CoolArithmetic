package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends BaseFragment {

    private TextView text_profile_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        initViews(view);
        initData();
        return view;
    }

    @Override
    protected void initViews(View view) {
        text_profile_username = (TextView) view.findViewById(R.id.text_profile_username);
    }

    private void initData() {
        List<String> friends = new ArrayList<>();
        friends.add(AppConfig.getUserAccid(getActivity()));
        List<NimUserInfo> users = NIMClient.getService(UserService.class).getUserInfoList(friends);
        for (NimUserInfo userInfo : users) {
            text_profile_username.setText(userInfo.getName());

            AppConfig.setUserName(getActivity(), userInfo.getName());
            AppConfig.setUserAccid(getActivity(), userInfo.getAccount());
        }

    }
}
