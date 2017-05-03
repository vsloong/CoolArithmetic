package com.zxxxy.coolarithmetic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.adapter.ConversationAdapter;
import com.zxxxy.coolarithmetic.base.BaseFragment;
import com.zxxxy.coolarithmetic.entity.Conversation;
import com.zxxxy.coolarithmetic.utils.GreenDAOUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MsgConversationFragment extends BaseFragment {

    private RecyclerView view_recycler;
    private LinearLayout layout_no_conversation;
    private ConversationAdapter adapter;
    private List<Conversation> listData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg_conversation, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getConversationsList();
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);

        view_recycler = (RecyclerView) view.findViewById(R.id.view_recycler);
        layout_no_conversation = (LinearLayout) view.findViewById(R.id.layout_no_conversation);

        adapter = new ConversationAdapter(getActivity(), listData);
        view_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        view_recycler.setAdapter(adapter);
    }

    @Subscribe
    public void onEventMainThread(Conversation conversation) {
        adapter.notifyDataSetChanged();
    }

    private void getConversationsList() {
        List<Conversation> conversations = GreenDAOUtils.getDefaultDaoSession(getActivity())
                .getConversationDao()
                .queryBuilder()
                .build()
                .list();

        for (Conversation conversation : conversations) {
            Log.e("会话信息", conversation.getName());
        }

        if (conversations.isEmpty()) {
            layout_no_conversation.setVisibility(View.VISIBLE);
        } else {
            layout_no_conversation.setVisibility(View.GONE);
            listData.clear();
            listData.addAll(conversations);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
