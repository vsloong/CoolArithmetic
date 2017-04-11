package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;

public class MsgContactsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pk, container, false);
        getFriendsList();
        initViews(view);
        return view;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
    }

    private void getFriendsList() {
        List<String> friends = NIMClient.getService(FriendService.class).getFriendAccounts();

        if (!friends.isEmpty()) {
            NIMClient.getService(UserService.class)
                    .fetchUserInfo(friends)
                    .setCallback(new RequestCallback<List<NimUserInfo>>() {
                        @Override
                        public void onSuccess(List<NimUserInfo> param) {
                            for (NimUserInfo userInfo : param) {
                                Log.e("好友列表", userInfo.getName());
                            }
                        }

                        @Override
                        public void onFailed(int code) {

                        }

                        @Override
                        public void onException(Throwable exception) {

                        }
                    });
        } else {
            Log.e("好友列表", "没有好友");
        }
    }
}
