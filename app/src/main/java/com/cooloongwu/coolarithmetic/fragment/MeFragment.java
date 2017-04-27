package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.cooloongwu.coolarithmetic.utils.AvatarUtils;
import com.cooloongwu.coolarithmetic.utils.GoLoginUtils;
import com.cooloongwu.coolarithmetic.utils.StartActivityUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private TextView text_profile_username;
    private TextView text_profile_school;
    private ImageView img_avatar;
    private Button btn_sign;

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
        text_profile_school = (TextView) view.findViewById(R.id.text_profile_school);

        img_avatar = (ImageView) view.findViewById(R.id.img_avatar);
        Picasso.with(getActivity())
                .load(AvatarUtils.getAvatar(AppConfig.getUserAccid(getActivity())))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img_avatar);

        Button btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_sign = (Button) view.findViewById(R.id.btn_sign);

        //如果签到时间大于一天就可以签到
        if (System.currentTimeMillis() - AppConfig.getUserSignTime(getActivity()) > 1000 * 60 * 60 * 24) {
            btn_sign.setEnabled(true);
        } else {
            btn_sign.setEnabled(false);
        }

        btn_sign.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    private void initData() {
        List<String> friends = new ArrayList<>();
        friends.add(AppConfig.getUserAccid(getActivity()));

        NIMClient.getService(UserService.class)
                .fetchUserInfo(friends)
                .setCallback(new RequestCallback<List<NimUserInfo>>() {
                    @Override
                    public void onSuccess(List<NimUserInfo> param) {
                        for (NimUserInfo userInfo : param) {
                            Log.e("我的信息", userInfo.getName());
                        }
                        if (!param.isEmpty()) {
                            text_profile_username.setText(param.get(0).getName());
                            AppConfig.setUserName(getActivity(), param.get(0).getName());
                            text_profile_school.setText(param.get(0).getAccount());
                        }

//                        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
//
//                        fields.put(UserInfoFieldEnum.AVATAR, AvatarUtils.getAvatar(param.get(0).getAccount()));
//                        NIMClient.getService(UserService.class).updateUserInfo(fields)
//                                .setCallback(new RequestCallbackWrapper<Void>() {
//                                    @Override
//                                    public void onResult(int code, Void result, Throwable exception) {
//
//                                    }
//                                });
                    }

                    @Override
                    public void onFailed(int code) {

                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                AppConfig.clearAllInfo(getActivity());
                getActivity().finish();
                break;
            case R.id.btn_sign:

                if (GoLoginUtils.isLogin()) {
                    AppConfig.setUserSignTime(getActivity(), System.currentTimeMillis());
                    btn_sign.setEnabled(false);
                    btn_sign.setText("今日已签到");
                    Toast.makeText(getActivity(), "今日已签到", Toast.LENGTH_SHORT).show();
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
}
