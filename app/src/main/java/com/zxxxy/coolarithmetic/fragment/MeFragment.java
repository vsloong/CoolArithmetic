package com.zxxxy.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.squareup.picasso.Picasso;
import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.coolarithmetic.base.BaseFragment;
import com.zxxxy.coolarithmetic.utils.AvatarUtils;
import com.zxxxy.coolarithmetic.utils.DataUtils;
import com.zxxxy.coolarithmetic.utils.GoLoginUtils;
import com.zxxxy.coolarithmetic.utils.StartActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private TextView text_profile_username;
    private TextView text_profile_school;
    private ImageView img_avatar;
    private Button btn_sign;
    private final int[] LEVEL_IMGS = {
            R.mipmap.level_1,
            R.mipmap.level_2,
            R.mipmap.level_3,
            R.mipmap.level_4,
            R.mipmap.level_5,
            R.mipmap.level_6,
            R.mipmap.level_7,
            R.mipmap.level_8,
            R.mipmap.level_9,
            R.mipmap.level_10,
            R.mipmap.level_11,
            R.mipmap.level_12,
            R.mipmap.level_13,
            R.mipmap.level_14,
            R.mipmap.level_15,
            R.mipmap.level_16,
            R.mipmap.level_17,
            R.mipmap.level_18,
            R.mipmap.level_19,
            R.mipmap.level_20,
            R.mipmap.level_21,
            R.mipmap.level_22,
            R.mipmap.level_23,
            R.mipmap.level_24,
            R.mipmap.level_25,
    };

    ProgressBar progress_bar_exp;
    ImageView img_level_curr;
    ImageView img_level_next;
    TextView text_exp_progress;

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
        progress_bar_exp = (ProgressBar) view.findViewById(R.id.progress_bar_exp);
        img_level_curr = (ImageView) view.findViewById(R.id.img_level_curr);
        img_level_next = (ImageView) view.findViewById(R.id.img_level_next);
        text_exp_progress = (TextView) view.findViewById(R.id.text_exp_progress);

        RelativeLayout layout_add_friend = (RelativeLayout) view.findViewById(R.id.layout_add_friend);
        RelativeLayout layout_wrong = (RelativeLayout) view.findViewById(R.id.layout_wrong);
        layout_add_friend.setOnClickListener(this);
        layout_wrong.setOnClickListener(this);

        TextView text_num_play = (TextView) view.findViewById(R.id.text_num_play);
        TextView text_num_pk_win = (TextView) view.findViewById(R.id.text_num_pk_win);

        text_num_play.setText(String.valueOf(AppConfig.getUserPlayNum()));
        text_num_pk_win.setText(String.valueOf(AppConfig.getUserPKWinNum()));

        text_profile_username = (TextView) view.findViewById(R.id.text_profile_username);
        text_profile_school = (TextView) view.findViewById(R.id.text_profile_school);

        img_avatar = (ImageView) view.findViewById(R.id.img_avatar);
        Picasso.with(getActivity())
                .load(AvatarUtils.getAvatar(AppConfig.getUserAccid()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img_avatar);

        Button btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_sign = (Button) view.findViewById(R.id.btn_sign);

        //如果签到时间大于一天就可以签到
        if (!AppConfig.getUserSignTime().equals(DataUtils.getNowData())) {
            btn_sign.setEnabled(true);
        } else {
            btn_sign.setEnabled(false);
        }

        btn_sign.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        int level = AppConfig.getUserEXP() / 100;
        int exp = AppConfig.getUserEXP() % 100;

        progress_bar_exp.setProgress(exp);
        text_exp_progress.setText(exp + "/100");
        img_level_curr.setImageResource(LEVEL_IMGS[level]);
        img_level_next.setImageResource(LEVEL_IMGS[level + 1]);
    }

    private void initData() {
        List<String> friends = new ArrayList<>();
        friends.add(AppConfig.getUserAccid());

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
                            AppConfig.setUserAvatar(getActivity(), param.get(0).getAvatar());
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
                NIMClient.getService(AuthService.class).logout();
                AppConfig.clearAllInfo();
                getActivity().finish();
                StartActivityUtils.startLoginActivity(getActivity());
                break;
            case R.id.btn_sign:
                if (GoLoginUtils.isLogin()) {
                    AppConfig.setUserSignTime(DataUtils.getNowData());
                    AppConfig.setUserEV(100);
                    btn_sign.setEnabled(false);
                    btn_sign.setText("今日已签到");
                    Toast.makeText(getActivity(), "今日已签到", Toast.LENGTH_SHORT).show();
                } else {
                    showLoginDialog();
                }
                break;
            case R.id.layout_wrong:
                if (GoLoginUtils.isLogin()) {
                    StartActivityUtils.startWrongActivity(getActivity());
                } else {
                    showLoginDialog();
                }
                break;
            case R.id.layout_add_friend:
                if (GoLoginUtils.isLogin()) {
                    StartActivityUtils.startSearchActivity(getActivity());
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
