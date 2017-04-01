package com.cooloongwu.coolarithmetic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cooloongwu.coolarithmetic.R;

/**
 * 年级的适配器
 * Created by CooLoongWu on 2017-3-31 15:03.
 */

public class AdvanceAdapter extends RecyclerView.Adapter<AdvanceAdapter.ViewHolder> {

    private Context context;
    private int[] ADVANCE_IMGS = {
            R.mipmap.img_advance_item_1,
            R.mipmap.img_advance_item_2,
            R.mipmap.img_advance_item_3,
            R.mipmap.img_advance_item_4,
            R.mipmap.img_advance_item_5,
            R.mipmap.img_advance_item_6,
            R.mipmap.img_advance_item_7,
            R.mipmap.img_advance_item_8,
            R.mipmap.img_advance_item_9,
            R.mipmap.img_advance_item_10
    };

    public AdvanceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_advance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.img_advance_level.setImageResource(
                position < 10 ?
                        ADVANCE_IMGS[position] :
                        ADVANCE_IMGS[position - 10]);
        holder.text_advance_level.setText(String.valueOf(position + 1));

        holder.text_advance_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //StartActivityUtils.startAdvanceActivity((MainActivity) context, position + 1);
                Toast.makeText(context, "点击了", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_advance_level;
        TextView text_advance_level;

        ViewHolder(View itemView) {
            super(itemView);
            img_advance_level = (ImageView) itemView.findViewById(R.id.img_advance_level);
            text_advance_level = (TextView) itemView.findViewById(R.id.text_advance_level);
        }
    }

}
