package com.cooloongwu.coolarithmetic.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.base.BaseFragment;
import com.cooloongwu.coolarithmetic.utils.AvatarUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private TextView text_profile_username;
    private TextView text_profile_school;
    private ImageView img_avatar;

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
                .into(img_avatar);

        Button btn_logout = (Button) view.findViewById(R.id.btn_logout);
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
                        text_profile_username.setText(param.get(0).getName());
                        AppConfig.setUserName(getActivity(), param.get(0).getName());
                        text_profile_school.setText(param.get(0).getAccount());

                        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);

                        fields.put(UserInfoFieldEnum.AVATAR, AvatarUtils.getAvatar(param.get(0).getAccount()));
                        NIMClient.getService(UserService.class).updateUserInfo(fields)
                                .setCallback(new RequestCallbackWrapper<Void>() {
                                    @Override
                                    public void onResult(int code, Void result, Throwable exception) {

                                    }
                                });
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
            default:
                break;
        }
    }
}
