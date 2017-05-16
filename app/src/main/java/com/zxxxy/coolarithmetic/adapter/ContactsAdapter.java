package com.zxxxy.coolarithmetic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxxxy.coolarithmetic.R;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 联系人的适配器
 * Created by CooLoongWu on 2017-3-31 15:03.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context context;
    private List<NimUserInfo> listData; //联系人的列表数据

    public ContactsAdapter(Context context, List<NimUserInfo> listData) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.text_name.setText(listData.get(position).getName());

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
            }
        });


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_avatar;
        TextView text_name;

        ViewHolder(View itemView) {
            super(itemView);
            img_avatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            text_name = (TextView) itemView.findViewById(R.id.text_name);
        }
    }

}
