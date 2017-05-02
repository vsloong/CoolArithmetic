package com.zxxxy.coolarithmetic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zxxxy.coolarithmetic.fragment.MsgFragment;

/**
 * TabLayout和ViewPager的适配器
 * Created by CooLoongWu on 2017-3-31 10:38.
 */

public class MsgTabViewPagerAdapter extends FragmentPagerAdapter {


    public MsgTabViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MsgFragment.TAB_FRAGMENTS[position];
    }

    @Override
    public int getCount() {
        return MsgFragment.TAB_FRAGMENTS.length;
    }
}
