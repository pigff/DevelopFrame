package com.androiddev.zf.devframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.base.presenter.imp.ListPresenter;
import com.androiddev.zf.devframe.base.view.IListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by greedy on 17/3/14.
 */

public abstract class BaseRecyclerFragment<T, V extends BaseQuickAdapter<T, ? extends BaseViewHolder>, P extends ListPresenter<T>>
        extends MvpFragment<P> implements BaseQuickAdapter.RequestLoadMoreListener, IListView<T> {

    protected RecyclerView mRecyclerView;
    private V mAdapter;
    protected int mPageNum;
    protected int mPageSize;
    protected boolean mIsLoad;
    private boolean mIsRefresh;

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_common_rv_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getData();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_common_fragment);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_common_fragment);
        if (mRecyclerView == null) {
            throw new IllegalStateException(
                    "The subclass of ToolbarActivity must contain a recyclerview.");
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager());
//        mRecyclerView.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    @Override
    public void initAdapter() {
        mAdapter = initRecyclerAdapter();
        if (canLoadMore()) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        }
        if (canLoadMore() && openLoadAnim()) {
            mAdapter.openLoadAnimation();
        }
    }

    @Override
    public void initData() {
        mPageNum = 0;
        mPageSize = 10;
        mIsLoad = false;
        mIsRefresh = false;
    }


    @Override
    protected boolean hasBaseLayout() {
        return true;
    }

//    @Override
//    public void loadEnd(boolean show) {
//        if (show) {
//            mAdapter.loadMoreEnd(false);
//        } else {
//            mAdapter.loadMoreEnd(true);
//        }
//    }

    @Override
    public boolean canLoadMore() {
        return false;
    }

    protected boolean openLoadAnim() {
        return false;
    }

    protected abstract V initRecyclerAdapter();

    protected V getAdapter() {
        if (mAdapter == null) {
            mAdapter = initRecyclerAdapter();
        }
        return mAdapter;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }


//    private void getNetData() {
//        if (!mIsLoad) {
//            mIsLoad = true;
//            getData();
//        }
//    }
}
