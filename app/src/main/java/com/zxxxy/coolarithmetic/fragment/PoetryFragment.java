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

public class PoetryFragment extends BaseFragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poetry, container, false);

        initViews(view);
        return view;
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    public void onClick(View v) {

    }
}
