package com.cooloongwu.coolarithmetic.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.adapter.QuestionPagerAdapter;
import com.cooloongwu.coolarithmetic.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends BaseActivity implements View.OnClickListener {

    private TextView text_play_timer;
    private ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initViews();
    }

    @Override
    protected void initViews() {
        ImageView img_play_back = (ImageView) findViewById(R.id.img_play_back);
        text_play_timer = (TextView) findViewById(R.id.text_play_timer);

        RelativeLayout layout_avatar = (RelativeLayout) findViewById(R.id.layout_avatar);
        layout_avatar.setVisibility(View.GONE);

        view_pager = (ViewPager) findViewById(R.id.view_pager);

        List<View> views = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.layout_play, null);
        View view2 = inflater.inflate(R.layout.layout_play, null);
        views.add(view1);
        views.add(view2);

        view_pager.setAdapter(new QuestionPagerAdapter(views));

        img_play_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_play_back:
                finish();
                break;

        }
    }
}
