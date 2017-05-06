package com.zxxxy.coolarithmetic.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.squareup.picasso.Picasso;
import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.adapter.RankAdapter;
import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.coolarithmetic.base.BaseActivity;
import com.zxxxy.coolarithmetic.entity.Rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RankActivity extends BaseActivity {

    private RankAdapter adapter;
    private List<Rank> listData = new ArrayList<>();
    private TextView text_rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        initToolBar();
        initViews();
        getFriendsList();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("排名");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initViews() {
        ImageView img_avatar = (ImageView) findViewById(R.id.img_avatar);
        RecyclerView view_recycler = (RecyclerView) findViewById(R.id.view_recycler);

        TextView text_name = (TextView) findViewById(R.id.text_name);
        text_rank = (TextView) findViewById(R.id.text_rank);
        TextView text_score = (TextView) findViewById(R.id.text_score);


        Picasso.with(this)
                .load(AppConfig.getUserAvatar())
                .error(R.mipmap.ic_launcher)
                .into(img_avatar);

        text_name.setText(AppConfig.getUserName());
        text_rank.setText("2398");
        text_score.setText(String.valueOf(AppConfig.getUserEXP()));

        view_recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RankAdapter(this, listData);
        view_recycler.setAdapter(adapter);
    }

    private void getFriendsList() {
        List<String> friends = NIMClient.getService(FriendService.class).getFriendAccounts();

        if (friends != null && !friends.isEmpty()) {
            NIMClient.getService(UserService.class)
                    .fetchUserInfo(friends)
                    .setCallback(new RequestCallback<List<NimUserInfo>>() {
                        @Override
                        public void onSuccess(List<NimUserInfo> param) {

                            for (NimUserInfo userInfo : param) {
                                Log.e("好友列表", userInfo.getName());
                                Rank rank = new Rank();
                                rank.setAvatar(userInfo.getAvatar());
                                rank.setName(userInfo.getName());
                                //Log.e("该好友的扩展字段", String.valueOf(userInfo.getExtensionMap().containsKey("EXP")));
                                if (userInfo.getExtensionMap() != null) {
                                    String temp = String.valueOf(userInfo.getExtensionMap().values());

                                    Log.e("好友列表其他参数", temp);//[810]
                                    Log.e("好友列表其他参数", userInfo.getExtensionMap().toString());
                                    String regEx = "[^0-9]";
                                    Pattern p = Pattern.compile(regEx);
                                    Matcher m = p.matcher(temp);
                                    int exp = Integer.parseInt(m.replaceAll("").trim());
                                    rank.setExp(exp);
                                } else {
                                    rank.setExp(0);
                                    Log.e("好友列表其他参数", "没有参数");
                                }

                                listData.add(rank);
                            }
                            Rank myRank = new Rank();
                            myRank.setName(AppConfig.getUserName());
                            myRank.setAvatar(AppConfig.getUserAvatar());
                            myRank.setExp(AppConfig.getUserEXP());
                            listData.add(myRank);

                            //list按照分数排序
                            Collections.sort(listData, new Comparator<Rank>() {
                                @Override
                                public int compare(Rank o1, Rank o2) {
                                    return o1.getExp() > o2.getExp() ? -1 : 1;
                                }
                            });

                            //得到自己的分数排名
                            text_rank.setText(String.valueOf(listData.indexOf(myRank) + 1));

                            adapter.notifyDataSetChanged();
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
