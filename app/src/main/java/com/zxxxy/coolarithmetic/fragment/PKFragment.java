package com.zxxxy.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;
import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.activity.MainActivity;
import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.coolarithmetic.base.BaseFragment;
import com.zxxxy.coolarithmetic.entity.MsgTypeEnum;
import com.zxxxy.coolarithmetic.utils.GoLoginUtils;
import com.zxxxy.coolarithmetic.utils.SendMsgUtils;
import com.zxxxy.coolarithmetic.utils.StartActivityUtils;

import org.greenrobot.eventbus.EventBus;

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
                if (GoLoginUtils.isLogin()) {
                    EventBus.getDefault().post(MsgTypeEnum.PK_REQUEST);
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
            MainActivity.fromAccid = friends.get(0);
            SendMsgUtils.sendPKMsg(friends.get(0), AppConfig.getUserName(getActivity()), "来PK啊，辣鸡", MsgTypeEnum.PK_REQUEST);
        }
    }
}
