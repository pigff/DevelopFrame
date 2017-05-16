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
import com.androiddev.zf.devframe.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by greedy on 17/3/14.
 */

public abstract class BaseRecyclerFragment<T, V extends BaseQuickAdapter<T, ? extends BaseViewHolder>, P extends ListPresenter<T>>
        extends MvpFragment<P> implements IListView<T> {


    public static final int DEFAULT_PAGENUM = 0;
    public static final int DEFAULT_PAGESIZE = 20;

    public static final int NORMAL_STATUS = 1000;
    public static final int LOAD_STATUS = 1001;
    public static final int REFRESH_STATUS = 1002;

    protected RecyclerView mRecyclerView;
    private V mAdapter;
    protected int mPageNum;
    protected int mPageSize;

    private int mStatus;

    private SwipeRefreshLayout mRefreshLayout;

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
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_common_fragment);
        if (mRecyclerView == null || mRefreshLayout == null) {
            throw new IllegalStateException(
                    "The subclass of ToolbarActivity must contain recyclerview and refreshLayout.");
        }
        initRecyclerView();

        if (canRefresh()) {
            initRefreshLayout();
        } else {
            mRefreshLayout.setEnabled(false);
        }
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mStatus == NORMAL_STATUS) {
                    mStatus = REFRESH_STATUS;
//                    refreshData();
                    loadData(DEFAULT_PAGENUM);
                }
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager());
//        mRecyclerView.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    @Override
    public void initAdapter() {
        mAdapter = initRecyclerAdapter();
        if (canLoadMore()) {
            mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (mStatus == NORMAL_STATUS) {
                        mStatus = LOAD_STATUS;
//                        loadMoreData();
                        loadData(mPageNum);
                    }
                }
            }, mRecyclerView);
        }
        if (canLoadMore() && openLoadAnim()) {
            mAdapter.openLoadAnimation();
        }
    }

    @Override
    public void initData() {
        mPageNum = DEFAULT_PAGENUM;
        mPageSize = DEFAULT_PAGESIZE;
        mStatus = NORMAL_STATUS;
    }

    @Override
    protected final void onLazyLoad() {
        mStatus = LOAD_STATUS;
        loadData(mPageNum);
    }

    @Override
    protected boolean hasBaseLayout() {
        return true;
    }

    @Override
    public void loadData(List<T> data) {
        LogUtil.d(TAG, "status: " + mStatus);
        if (mPageNum == 0 && data.size() == 0) {
            showEmpty();
            if (mStatus == REFRESH_STATUS) {
                finishRefresh();
            }
            mStatus = NORMAL_STATUS;
            return;
        }
        if (mStatus == REFRESH_STATUS) {
            finishRefresh();
            mPageNum = DEFAULT_PAGENUM + 1;
            getAdapter().setNewData(data);
        } else if (mStatus == LOAD_STATUS) {
            getAdapter().addData(data);
            if (canLoadMore()) {
                if (data.size() < mPageSize) {
                    if (mPageNum == DEFAULT_PAGENUM) {
                        // 不显示没有更多
                        getAdapter().loadMoreEnd(true);
                    } else {
                        //显示没有更多
                        getAdapter().loadMoreEnd();
                    }
                } else {
                    getAdapter().loadMoreComplete();
                    // 页码增加 需要在View层里面做处理
//                            pageNum++;
                }
            }
            mPageNum++;
        }
        mStatus = NORMAL_STATUS;
    }

    @Override
    public void loadError() {
        LogUtil.d(TAG, "status: " + mStatus);
        if (mStatus == REFRESH_STATUS) {
            finishRefresh();
            if (mPageNum == DEFAULT_PAGENUM) {
                showError();
            }
        }
        if (mStatus == LOAD_STATUS) {
            if (mPageNum == DEFAULT_PAGENUM) {
                showError();
            } else {
                getAdapter().loadMoreFail();
            }
        }
        mStatus = NORMAL_STATUS;
    }

    /**
     * @return
     */
    protected boolean canLoadMore() {
        return false;
    }

    /**
     * @return
     */
    protected boolean canRefresh() {
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

    protected void finishRefresh() {
        if (canRefresh() && mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }


//    protected void loadMoreData(){
//
//    }
//
//    protected void refreshData() {
//
//    }

    protected void loadData(int pageNum) {
    }

}
