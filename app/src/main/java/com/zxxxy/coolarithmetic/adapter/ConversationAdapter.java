package com.zxxxy.coolarithmetic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.entity.Conversation;
import com.zxxxy.coolarithmetic.utils.DataUtils;
import com.zxxxy.coolarithmetic.utils.GreenDAOUtils;
import com.zxxxy.coolarithmetic.utils.StartActivityUtils;
import com.zxxxy.greendao.gen.ConversationDao;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 聊天会话的适配器
 * Created by CooLoongWu on 2017-3-31 15:03.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private Context context;
    private List<Conversation> listData; //会话的列表数据

    public ConversationAdapter(Context context, List<Conversation> listData) {
        this.context = context;
        this.listData = listData;
    }

    /**
     * 创建联系人每一条Item的布局
     *
     * @param parent   ViewGroup
     * @param viewType 没有Type
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_conversation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.conversation_name.setText(listData.get(position).getName());
        holder.conversation_time.setText(DataUtils.getTime(listData.get(position).getTime()));
        holder.conversation_content.setText(listData.get(position).getContent());


        if (listData.get(position).getUnReadNum() > 0) {
            holder.conversation_unread.setVisibility(View.VISIBLE);
            holder.conversation_unread.setText(String.valueOf(listData.get(position).getUnReadNum()));
        } else {
            holder.conversation_unread.setVisibility(View.GONE);
        }

        String avatar = TextUtils.isEmpty(listData.get(position).getAvatar()) ? "default" : listData.get(position).getAvatar();

        //使用Picasso框架加载网络图片到图片视图上
        Picasso.with(context)
                .load(avatar)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.img_avatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("system".equals(listData.get(position).getType())) {
                    Conversation conversation = GreenDAOUtils.getDefaultDaoSession(context).getConversationDao()
                            .queryBuilder()
                            .where(ConversationDao.Properties.Type.eq("system"))
                            .build()
                            .unique();
                    conversation.setUnReadNum(0);

                    //将未读消息置为0
                    GreenDAOUtils.getDefaultDaoSession(context).getConversationDao().update(conversation);
                    notifyDataSetChanged();
                    StartActivityUtils.startWebViewActivity((Activity) context, listData.get(position).getRemark());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_avatar;
        TextView conversation_name;
        TextView conversation_time;
        TextView conversation_content;
        TextView conversation_unread;

        ViewHolder(View itemView) {
            super(itemView);
            img_avatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            conversation_name = (TextView) itemView.findViewById(R.id.conversation_name);
            conversation_time = (TextView) itemView.findViewById(R.id.conversation_time);
            conversation_content = (TextView) itemView.findViewById(R.id.conversation_content);
            conversation_unread = (TextView) itemView.findViewById(R.id.conversation_unread);
        }
    }

}
