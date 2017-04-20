package com.cooloongwu.coolarithmetic.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.TabViewPagerAdapter;
import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.base.BaseActivity;
import com.cooloongwu.coolarithmetic.entity.MsgTypeEnum;
import com.cooloongwu.coolarithmetic.fragment.FightFragment;
import com.cooloongwu.coolarithmetic.fragment.MeFragment;
import com.cooloongwu.coolarithmetic.fragment.MsgFragment;
import com.cooloongwu.coolarithmetic.fragment.PKFragment;
import com.cooloongwu.coolarithmetic.utils.SendMsgUtils;
import com.cooloongwu.coolarithmetic.utils.StartActivityUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.friend.model.AddFriendNotify;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    //Tab 文字
    public static final String[] TAB_TITLES = new String[]{"闯关", "PK", "聊天", "我"};
    //Tab 图片
    public static final int[] TAB_IMGS = new int[]{R.drawable.selector_tab_homework, R.drawable.selector_tab_pk, R.drawable.selector_tab_rank, R.drawable.selector_tab_me};
    //Fragment 数组
    public static final Fragment[] TAB_FRAGMENTS = new Fragment[]{new FightFragment(), new PKFragment(), new MsgFragment(), new MeFragment()};
    //Tab 数目
    public static int TAB_COUNT = TAB_TITLES.length;

    private AlertDialog pkWaitingDialog;
    private AlertDialog pkRequestDialog;
    private AlertDialog pkPrepareDialog;
    TextView text_request_msg;

    public static String fromAccid;
    private int countdown = 10;
    private Button btn_waiting_cancel;

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
                String fromAcc = message.getFromAccount();

                MsgTypeEnum typeEnum = MsgTypeEnum.valueOf(jsonObject.getString("type"));
                switch (typeEnum) {
                    case PK:
                        Log.e("接收到的自定义PK消息", message.getContent());
                        MsgTypeEnum subtypeEnum = MsgTypeEnum.valueOf(jsonObject.getString("subtype"));
                        switch (subtypeEnum) {
                            case PK_REQUEST:
                                //如果当前页面上正在显示好友请求PK的对话框，那么拒绝下一个发来的PK请求
                                if (!(pkRequestDialog != null && pkRequestDialog.isShowing()) ||
                                        !(pkWaitingDialog != null && pkWaitingDialog.isShowing())) {
                                    fromAccid = fromAcc;
                                    showPKRequestDialog(jsonObject.getString("fromName"), jsonObject.getString("msg"));
                                } else {
                                    SendMsgUtils.sendPKMsg(fromAccid, AppConfig.getUserName(MainActivity.this), "", MsgTypeEnum.PK_AGREE);
                                }
                                break;
                            case PK_AGREE:
                                if (pkWaitingDialog != null && pkWaitingDialog.isShowing()) {
                                    text_request_msg.setText("对方接受了请求");
                                    timeHandler.removeMessages(1);
                                    btn_waiting_cancel.setEnabled(false);
                                    pkWaitingDialog.dismiss();

                                    showPKPrepareDialog();
                                }
                                break;
                            case PK_REJECT:
                                if (pkWaitingDialog != null && pkWaitingDialog.isShowing()) {
                                    text_request_msg.setText("对方拒绝了请求");
                                    timeHandler.removeMessages(1);
                                    btn_waiting_cancel.setEnabled(true);
                                }
                                break;
                            case PK_BUSY:
                                if (pkWaitingDialog != null && pkWaitingDialog.isShowing()) {
                                    text_request_msg.setText("对方正在挑战中");
                                    timeHandler.removeMessages(1);
                                    btn_waiting_cancel.setEnabled(true);
                                }
                                break;
                            case PK_CANCEL:
                                if (pkRequestDialog != null && pkRequestDialog.isShowing()) {
                                    pkRequestDialog.dismiss();
                                }
                                break;
                            default:
                                break;
                        }

                        break;
                    default:
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
        EventBus.getDefault().register(this);

        initViews();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, true);
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotificationObserver, true);
        NIMClient.getService(SystemMessageObserver.class).observeReceiveSystemMsg(systemMessageObserver, true);

        getAppInfo();
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
        EventBus.getDefault().unregister(this);
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, false);
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(customNotificationObserver, false);
        NIMClient.getService(SystemMessageObserver.class).observeReceiveSystemMsg(systemMessageObserver, false);
    }

    @Subscribe
    public void onEventMainThread(MsgTypeEnum msgTypeEnum) {
        if (msgTypeEnum == MsgTypeEnum.PK_REQUEST) {
            showPKWaitingDialog();
        }
    }

    /**
     * 展示接收到的PK请求
     *
     * @param fromName 对方的名称
     */
    private void showPKRequestDialog(String fromName, String declaration) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_pk_receive, null);
        builder.setView(view);
        builder.setCancelable(false);

        pkRequestDialog = builder.create();
        pkRequestDialog.show();
        TextView text_challenger_name = (TextView) view.findViewById(R.id.text_challenger_name);
        TextView text_challenger_declaration = (TextView) view.findViewById(R.id.text_challenger_declaration);
        Button btn_reject = (Button) view.findViewById(R.id.btn_reject);
        Button btn_agree = (Button) view.findViewById(R.id.btn_agree);

        text_challenger_name.setText(fromName);
        text_challenger_declaration.setText(declaration);

        btn_agree.setOnClickListener(this);
        btn_reject.setOnClickListener(this);
    }

    /**
     * 展示等待对方PK接受或者拒绝的对话框
     */
    public void showPKWaitingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_pk_request, null);
        text_request_msg = (TextView) view.findViewById(R.id.text_request_msg);
        btn_waiting_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_waiting_cancel.setOnClickListener(this);
        builder.setView(view);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        pkWaitingDialog = builder.create();
        pkWaitingDialog.show();

        timeHandler.sendEmptyMessageDelayed(1, 1000);
    }

    /**
     * 展示准备PK的对话框
     */
    public void showPKPrepareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_pk_prepare, null);

        builder.setView(view);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        pkPrepareDialog = builder.create();
        pkPrepareDialog.show();

        timeHandler.sendEmptyMessageDelayed(2, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_agree:
                SendMsgUtils.sendPKMsg(fromAccid, AppConfig.getUserName(this), "", MsgTypeEnum.PK_AGREE);
                pkRequestDialog.dismiss();

                showPKPrepareDialog();
                break;
            case R.id.btn_reject:
                SendMsgUtils.sendPKMsg(fromAccid, AppConfig.getUserName(this), "", MsgTypeEnum.PK_REJECT);
                pkRequestDialog.dismiss();
                break;
            case R.id.btn_cancel:
                if (pkWaitingDialog != null && pkWaitingDialog.isShowing()) {
                    pkWaitingDialog.dismiss();
                    SendMsgUtils.sendPKMsg(fromAccid, AppConfig.getUserName(this), "", MsgTypeEnum.PK_CANCEL);
                }
                countdown = 10;
                break;
            default:
                break;
        }
    }

    final Handler timeHandler = new Handler() {

        public void handleMessage(Message msg) {         // handle message
            switch (msg.what) {
                case 1:
                    countdown--;
                    text_request_msg.setText("等待对方应答中（" + countdown + "s）...");

                    if (countdown > 0) {
                        timeHandler.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        text_request_msg.setText("等待对方应答中...");
                        if (btn_waiting_cancel != null) {
                            btn_waiting_cancel.setEnabled(true);
                        }
                    }
                    break;
                case 2:
                    if (pkPrepareDialog != null) {
                        pkPrepareDialog.dismiss();
                    }
                    StartActivityUtils.startPlayActivity(MainActivity.this);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void getAppInfo() {
        // 获取packageManager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            Log.e("PackageInfo", packInfo.applicationInfo.dataDir);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
