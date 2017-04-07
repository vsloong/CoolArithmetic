package com.cooloongwu.coolarithmetic.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.TabViewPagerAdapter;
import com.cooloongwu.coolarithmetic.base.BaseActivity;
import com.cooloongwu.coolarithmetic.fragment.FightFragment;
import com.cooloongwu.coolarithmetic.fragment.MeFragment;
import com.cooloongwu.coolarithmetic.fragment.MsgFragment;
import com.cooloongwu.coolarithmetic.fragment.PKFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.friend.FriendServiceObserve;
import com.netease.nimlib.sdk.friend.model.FriendChangedNotify;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends BaseActivity {

    //Tab 文字
    public static final String[] TAB_TITLES = new String[]{"闯关", "PK", "聊天", "我"};
    //Tab 图片
    public static final int[] TAB_IMGS = new int[]{R.drawable.selector_tab_homework, R.drawable.selector_tab_pk, R.drawable.selector_tab_rank, R.drawable.selector_tab_me};
    //Fragment 数组
    public static final Fragment[] TAB_FRAGMENTS = new Fragment[]{new FightFragment(), new PKFragment(), new MsgFragment(), new MeFragment()};
    //Tab 数目
    public static int TAB_COUNT = TAB_TITLES.length;

    private Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
            // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
            Log.e("接收到的消息", messages.get(0).getContent());
        }
    };

    private Observer<CustomNotification> customNotificationObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            // 在这里处理自定义通知。
            Log.e("自定义的通知消息", message.getContent());
            try {
                JSONObject jsonObject = new JSONObject(message.getContent());
                jsonObject.getString("type");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Observer<FriendChangedNotify> friendChangedNotifyObserver = new Observer<FriendChangedNotify>() {
        @Override
        public void onEvent(FriendChangedNotify friendChangedNotify) {
            Log.e("好友", friendChangedNotify.getAddedOrUpdatedFriends().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, true);
        // 如果有自定义通知是作用于全局的，不依赖某个特定的 Activity，那么这段代码应该在 Application 的 onCreate 中就调用
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotificationObserver, true);

        NIMClient.getService(FriendServiceObserve.class).observeFriendChangedNotify(friendChangedNotifyObserver, true);
    }

    @Override
    protected void initViews() {
        TabLayout layout_tab = (TabLayout) findViewById(R.id.layout_tab);
        setTabs(layout_tab);

        final ViewPager view_pager = (ViewPager) findViewById(R.id.view_pager);

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());

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

    private void setTabs(TabLayout layout_tab) {
        for (int i = 0; i < TAB_COUNT; i++) {

            TabLayout.Tab tab = layout_tab.newTab();
            //方法一，简单但是不能自定义
//            tab.setIcon(TAB_IMGS[i]);
//            tab.setText(TAB_TITLES[i]);

            //方法二，可以自定义
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_main_tab, null);
            tab.setCustomView(view);
            ImageView img_tab = (ImageView) view.findViewById(R.id.img_tab);
            img_tab.setImageResource(TAB_IMGS[i]);
            TextView text_tab = (TextView) view.findViewById(R.id.text_tab);
            text_tab.setText(TAB_TITLES[i]);

            layout_tab.addTab(tab);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, false);
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotificationObserver, false);
        NIMClient.getService(FriendServiceObserve.class).observeFriendChangedNotify(friendChangedNotifyObserver, false);
    }
}
