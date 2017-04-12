package com.cooloongwu.coolarithmetic.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.TabViewPagerAdapter;
import com.cooloongwu.coolarithmetic.base.BaseActivity;
import com.cooloongwu.coolarithmetic.entity.MsgTypeEnum;
import com.cooloongwu.coolarithmetic.fragment.FightFragment;
import com.cooloongwu.coolarithmetic.fragment.MeFragment;
import com.cooloongwu.coolarithmetic.fragment.MsgFragment;
import com.cooloongwu.coolarithmetic.fragment.PKFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.friend.model.AddFriendNotify;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

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
            Log.e("接收到的文字消息", messages.get(0).getContent());
        }
    };

    private Observer<CustomNotification> customNotificationObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            // 在这里处理自定义通知。
            Log.e("接收到的自定义消息", message.getContent());
            try {
                JSONObject jsonObject = new JSONObject(message.getContent());
                String fromAccid = message.getFromAccount();

                MsgTypeEnum typeEnum = MsgTypeEnum.valueOf(jsonObject.getString("type"));
                switch (typeEnum) {
                    case PK:

                        showPKDialog(fromAccid);
                        break;
                    default:
                        showPKDialog(fromAccid);
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Observer<SystemMessage> systemMessageObserver = new Observer<SystemMessage>() {
        @Override
        public void onEvent(SystemMessage systemMessage) {
            Log.e("接收到的添加好友消息", systemMessage.getContent());
            if (systemMessage.getType() == SystemMessageType.AddFriend) {
                AddFriendNotify attachData = (AddFriendNotify) systemMessage.getAttachObject();
                // 针对不同的事件做处理
                if (attachData != null) {
                    if (attachData.getEvent() == AddFriendNotify.Event.RECV_ADD_FRIEND_DIRECT) {
                        // 对方直接添加你为好友
                        Log.e("接收到的添加好友消息", "对方直接添加你为好友");
                    } else if (attachData.getEvent() == AddFriendNotify.Event.RECV_AGREE_ADD_FRIEND) {
                        // 对方通过了你的好友验证请求
                        Log.e("接收到的添加好友消息", "对方通过了你的好友验证请求");
                    } else if (attachData.getEvent() == AddFriendNotify.Event.RECV_REJECT_ADD_FRIEND) {
                        // 对方拒绝了你的好友验证请求
                        Log.e("接收到的添加好友消息", "对方拒绝了你的好友验证请求");
                    } else if (attachData.getEvent() == AddFriendNotify.Event.RECV_ADD_FRIEND_VERIFY_REQUEST) {
                        // 对方请求添加好友，一般场景会让用户选择同意或拒绝对方的好友请求。
                        // 通过message.getContent()获取好友验证请求的附言
                        Log.e("接收到的添加好友消息", "对方请求添加好友");
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, true);
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotificationObserver, true);
        NIMClient.getService(SystemMessageObserver.class).observeReceiveSystemMsg(systemMessageObserver, true);
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
        NIMClient.getService(SystemMessageObserver.class).observeReceiveSystemMsg(systemMessageObserver, false);
    }


    private void showPKDialog(String fromAccid) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_receive_pk);

        TextView text_challenger_name = (TextView) window.findViewById(R.id.text_challenger_name);
        TextView text_challenger_declaration = (TextView) window.findViewById(R.id.text_challenger_declaration);


    }

}
