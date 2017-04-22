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
import com.cooloongwu.coolarithmetic.activity.AdvanceActivity;
import com.cooloongwu.coolarithmetic.entity.Advance;
import com.cooloongwu.coolarithmetic.utils.GreenDAOUtils;
import com.cooloongwu.coolarithmetic.utils.StartActivityUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 闯关的适配器
 * Created by CooLoongWu on 2017-3-31 15:03.
 */

public class AdvanceAdapter extends RecyclerView.Adapter<AdvanceAdapter.ViewHolder> {

    private Context context;
    private Advance advance;

    //闯关的背景图
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
            R.mipmap.img_advance_item_10,
            R.mipmap.img_advance_item_11,
            R.mipmap.img_advance_item_12,
            R.mipmap.img_advance_item_13,
            R.mipmap.img_advance_item_14,
            R.mipmap.img_advance_item_15,
            R.mipmap.img_advance_item_16
    };

    public AdvanceAdapter(Context context, Advance advance) {
        this.context = context;
        this.advance = advance;
    }

    //左 右 中间 三个属性的枚举类
    private enum ITEM_LOCATION {
        LEFT, CENTER, RIGHT
    }

    /**
     * 根据位置设置不同的布局类型
     *
     * @param position 位置
     * @return 枚举类型
     */
    @Override
    public int getItemViewType(int position) {
        position++;
        if (position % 2 == 0) {
            return ITEM_LOCATION.CENTER.ordinal();
        } else if ((position + 1) % 4 == 0) {
            return ITEM_LOCATION.LEFT.ordinal();
        } else {
            //(position - 1) % 4 == 0
            return ITEM_LOCATION.RIGHT.ordinal();
        }
    }

    /**
     * 根据不同的属性返回不同的布局文件，实现闯关的曲线
     *
     * @param parent   ViewGroup
     * @param viewType viewType
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_LOCATION.CENTER.ordinal()) {
            view = LayoutInflater.from(context).inflate(R.layout.item_advance_center, parent, false);
        } else if (viewType == ITEM_LOCATION.LEFT.ordinal()) {
            view = LayoutInflater.from(context).inflate(R.layout.item_advance_left, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_advance_right, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.img_advance_level.setImageResource(
                position < 16 ?
                        ADVANCE_IMGS[position] :
                        ADVANCE_IMGS[position - 16]);

        if ((position + 1) % 2 == 0) {
            holder.img_advance_line.setImageResource(
                    (position + 1) % 4 == 0 ?
                            R.mipmap.img_advance_line_center_4 :
                            R.mipmap.img_advance_line_center_2);
        }

        holder.text_advance_level.setText(String.valueOf(position + 1));


        holder.text_advance_level.setBackground(
                position <= advance.getAdvance() ?
                        ContextCompat.getDrawable(context, R.mipmap.icon_advance_btn_on) :
                        ContextCompat.getDrawable(context, R.mipmap.icon_advance_btn_disable)
        );

        //每个关卡按钮的点击事件
        holder.text_advance_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position <= advance.getAdvance()) {
                    StartActivityUtils.startPlayActivity((AdvanceActivity) context, 0, position);
                } else {
                    Toast.makeText(context, "请您闯完前面关卡再来吧", Toast.LENGTH_SHORT).show();

                    if (advance.getAdvance() < 20) {
                        advance.setAdvance(advance.getAdvance() + 1);
                    } else {
                        advance.setAdvance(19);
                    }
                    GreenDAOUtils.getDefaultDaoSession(context).getAdvanceDao().update(advance);

                    EventBus.getDefault().post(new Advance());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_advance_level;
        ImageView img_advance_line;
        TextView text_advance_level;

        ViewHolder(View itemView) {
            super(itemView);
            img_advance_level = (ImageView) itemView.findViewById(R.id.img_advance_level);
            img_advance_line = (ImageView) itemView.findViewById(R.id.img_advance_line);
            text_advance_level = (TextView) itemView.findViewById(R.id.text_advance_level);
        }
    }

}
