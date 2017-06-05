package com.androiddev.zf.devframe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.androiddev.zf.devframe.data.FragmentInfo;

import java.util.List;

/**
 * Created by greedy on 2017/5/22.
 */

public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> fragments;

    public BaseFragmentStateAdapter(FragmentManager fm, List<FragmentInfo> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TextUtils.isEmpty(fragments.get(position).getName()) ? "" : fragments.get(position).getName();
    }
}
