package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.ContactsAdapter;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

public class MsgContactsFragment extends BaseFragment {

    private ContactsAdapter adapter;
    private List<NimUserInfo> listData = new ArrayList<>();
    private RecyclerView view_recycler;
    private LinearLayout layout_no_contact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg_contacts, container, false);
        getFriendsList();
        initViews(view);
        return view;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        view_recycler = (RecyclerView) view.findViewById(R.id.view_recycler);
        layout_no_contact = (LinearLayout) view.findViewById(R.id.layout_no_contact);

        adapter = new ContactsAdapter(getActivity(), listData);
        view_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        view_recycler.setAdapter(adapter);

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
                            listData.clear();
                            listData.addAll(param);
                            adapter.notifyDataSetChanged();

                            if (param.isEmpty()) {
                                layout_no_contact.setVisibility(View.VISIBLE);
                            } else {
                                layout_no_contact.setVisibility(View.GONE);
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
