package com.cooloongwu.coolarithmetic;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cooloongwu.coolarithmetic.adapter.TabViewPagerAdapter;
import com.cooloongwu.coolarithmetic.fragment.FightFragment;

public class MainActivity extends AppCompatActivity {

    //Tab 文字
    public static final String[] TAB_TITLES = new String[]{"闯关", "PK", "班群", "我"};
    //Tab 图片
    public static final int[] TAB_IMGS = new int[]{R.drawable.selector_tab_homework, R.drawable.selector_tab_pk, R.drawable.selector_tab_rank, R.drawable.selector_tab_me};
    //Fragment 数组
    public static final Fragment[] TAB_FRAGMENTS = new Fragment[]{new FightFragment(), new FightFragment(), new FightFragment(), new FightFragment()};
    //Tab 数目
    public static int TAB_COUNT = TAB_TITLES.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
        initViews();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
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
            //方法一
            TabLayout.Tab tab = layout_tab.newTab();
            tab.setIcon(TAB_IMGS[i]);
            tab.setText(TAB_TITLES[i]);
            layout_tab.addTab(tab);
        }
    }

}
