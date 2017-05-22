package com.androiddev.zf.devframe.data;

import android.support.v4.app.Fragment;

/**
 * Created by greedy on 2017/5/22.
 */

public class FragmentInfo {

    private Fragment fragment;
    private String name;

    private FragmentInfo() {

    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static FragmentInfo getFragmentWithName(Fragment fragment, String name) {
        FragmentInfo fragmentInfo = new FragmentInfo();
        fragmentInfo.setFragment(fragment);
        fragmentInfo.setName(name);
        return fragmentInfo;
    }
}
