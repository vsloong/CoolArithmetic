package com.zxxxy.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.adapter.MsgTabViewPagerAdapter;
import com.zxxxy.coolarithmetic.base.BaseFragment;

public class MsgFragment extends BaseFragment {

    //Tab 文字
    public static final String[] TAB_TITLES = new String[]{"消息", "好友"};
    //Fragment 数组
    public static final Fragment[] TAB_FRAGMENTS = new Fragment[]{new MsgConversationFragment(), new MsgContactsFragment()};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        initToolBar(view);
        initViews(view);
//        SendMsgUtils.sendTextMsg("2012329700030", "哇哈哈哈哇哈哈哈");
//        SendMsgUtils.sendCustomMsg("2012329700030", MsgTypeEnum.PK);
//        SendMsgUtils.sendAddFriendMsg("2012329700030", "敢跟我一决高下么，辣鸡！"); // 发起好友验证请求;
//        SendMsgUtils.sendAckAddFriendMsg("2013329700040", true);

        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("聊天");
    }

    @Override
    protected void initViews(View view) {
        TabLayout layout_tab = (TabLayout) view.findViewById(R.id.layout_tab);
        layout_tab.addTab(layout_tab.newTab().setText("消息"));
        layout_tab.addTab(layout_tab.newTab().setText("好友"));
        final ViewPager view_pager = (ViewPager) view.findViewById(R.id.view_pager);

        MsgTabViewPagerAdapter adapter = new MsgTabViewPagerAdapter(getChildFragmentManager());

        view_pager.setAdapter(adapter);
        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(layout_tab));
        layout_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
