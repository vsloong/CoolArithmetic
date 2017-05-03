package com.zxxxy.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.adapter.PKFriendAdapter;
import com.zxxxy.coolarithmetic.base.BaseFragment;
import com.zxxxy.coolarithmetic.utils.GoLoginUtils;
import com.zxxxy.coolarithmetic.utils.StartActivityUtils;

import java.util.List;

public class PKFragment extends BaseFragment implements View.OnClickListener {

    private AlertDialog friendsDialog;

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
                if (GoLoginUtils.isLogin()) {
                    searchFriendToPK();
                } else {
                    showLoginDialog();
                }
                break;
            default:
                break;
        }
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.dialog_login, null);

        Button btn_no = (Button) view.findViewById(R.id.btn_no);
        Button btn_yes = (Button) view.findViewById(R.id.btn_yes);

        builder.setView(view);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog loginDialog = builder.create();
        loginDialog.show();

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
                StartActivityUtils.startLoginActivity(getActivity());
                (getActivity()).finish();
            }
        });
    }

    private void searchFriendToPK() {
        List<String> friends = NIMClient.getService(FriendService.class).getFriendAccounts();
        if (friends != null) {

            NIMClient.getService(UserService.class)
                    .fetchUserInfo(friends)
                    .setCallback(new RequestCallback<List<NimUserInfo>>() {
                        @Override
                        public void onSuccess(List<NimUserInfo> param) {
                            for (NimUserInfo userInfo : param) {
                                Log.e("好友列表", userInfo.getName());
                            }
                            showFriendsDialog(param);
                        }

                        @Override
                        public void onFailed(int code) {

                        }

                        @Override
                        public void onException(Throwable exception) {

                        }
                    });
        } else {
            Toast.makeText(getActivity(), "你还没有好友哦，先去添加好友吧", Toast.LENGTH_SHORT).show();
        }
    }

    private void showFriendsDialog(List<NimUserInfo> param) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.dialog_pk_select_friend, null);
        builder.setView(view);

        friendsDialog = builder.create();
        friendsDialog.show();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.view_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        PKFriendAdapter adapter = new PKFriendAdapter(getActivity(), param);
        adapter.setOnDialogDismissListener(new PKFriendAdapter.OnDialogDismissListener() {
            @Override
            public void onDismiss() {
                friendsDialog.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
