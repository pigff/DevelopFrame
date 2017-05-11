package com.androiddev.zf.devframe.test;

import android.support.v7.widget.RecyclerView;

import com.androiddev.zf.devframe.base.BaseRecyclerFragment;
import com.androiddev.zf.devframe.base.presenter.imp.ListPresenter;

/**
 * Created by greedy on 2017/5/11.
 */

public class TestActivity extends BaseRecyclerFragment<String, TestAdapter, ListPresenter<String>> {


    @Override
    protected TestAdapter getRecyclerAdapter() {
        return null;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected ListPresenter<String> initPresenter() {
        return null;
    }
}
