package com.cooloongwu.coolarithmetic.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.activity.MainActivity;
import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.entity.Advance;
import com.cooloongwu.coolarithmetic.utils.GreenDAOUtils;
import com.cooloongwu.coolarithmetic.utils.StartActivityUtils;
import com.cooloongwu.greendao.gen.AdvanceDao;

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

        final int grade = AppConfig.getUserGrade(context);

        Advance advance = GreenDAOUtils.getInstance(context).getAdvanceDao()
                .queryBuilder()
                .where(AdvanceDao.Properties.Grade.eq(position))
                .build()
                .unique();

        holder.img_grade.setImageResource(GRADE_IMGS[position]);
        holder.text_grade.setText(context.getResources().getStringArray(R.array.grade_name)[position]);
        holder.text_progress.setText(
                position <= grade ?
                        advance == null ? "1/20" : (advance.getAdvance() + 1) + "/20"
                        :
                        "未解锁"
        );
        holder.itemView.setBackground(
                position <= grade ?
                        ContextCompat.getDrawable(context, R.drawable.shape_orange) :
                        ContextCompat.getDrawable(context, R.drawable.shape_gray)
        );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position <= grade) {
                    StartActivityUtils.startAdvanceActivity((MainActivity) context, position);
                } else {
                    Toast.makeText(context, "您年级还不够解锁此等级哦", Toast.LENGTH_SHORT).show();
                }

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
