package com.cooloongwu.coolarithmetic.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.activity.MainActivity;
import com.cooloongwu.coolarithmetic.utils.StartActivityUtils;

/**
 * 年级的适配器
 * Created by CooLoongWu on 2017-3-31 15:03.
 */

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private Context context;
    private int[] GRADE_IMGS = {
            R.mipmap.icon_grade_1,
            R.mipmap.icon_grade_2,
            R.mipmap.icon_grade_3,
            R.mipmap.icon_grade_4,
            R.mipmap.icon_grade_5,
            R.mipmap.icon_grade_6
    };

    private String[] GRADE_TITLES = {
            "一年级", "二年级", "三年级", "四年级", "五年级", "六年级"
    };


    public GradeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fight_grade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.img_grade.setImageResource(GRADE_IMGS[position]);
        holder.text_grade.setText(GRADE_TITLES[position]);
        holder.text_progress.setText(position < 4 ? "1/32" : "未解锁");
        holder.itemView.setBackground(
                position < 4 ?
                        ContextCompat.getDrawable(context, R.drawable.shape_orange) :
                        ContextCompat.getDrawable(context, R.drawable.shape_gray)
        );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtils.startAdvanceActivity((MainActivity) context, position + 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_grade;
        TextView text_grade;
        TextView text_progress;

        ViewHolder(View itemView) {
            super(itemView);
            img_grade = (ImageView) itemView.findViewById(R.id.img_grade);
            text_grade = (TextView) itemView.findViewById(R.id.text_grade);
            text_progress = (TextView) itemView.findViewById(R.id.text_progress);
        }
    }

}
