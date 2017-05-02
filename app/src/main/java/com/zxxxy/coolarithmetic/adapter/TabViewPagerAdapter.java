package com.zxxxy.coolarithmetic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zxxxy.coolarithmetic.activity.MainActivity;

/**
 * TabLayout和ViewPager的适配器
 * Created by CooLoongWu on 2017-3-31 10:38.
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {


    public TabViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainActivity.TAB_FRAGMENTS[position];
    }

    @Override
    public int getCount() {
        return MainActivity.TAB_COUNT;
    }
}
