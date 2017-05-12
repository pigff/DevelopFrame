package com.androiddev.zf.devframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.androiddev.zf.devframe.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by greedy on 17/3/14.
 */

public abstract class BaseRecyclerActivity<T> extends BaseToolbarActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    protected RecyclerView mRecyclerView;
    protected int mPageNum;
    protected int mPageSize;
    protected boolean mIsLoad;
//    private SimpleSubListener<List<T>> mSimpleSubListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    protected int provideContentView() {
        return R.layout.activity_common_rv_list;
    }

    @Override
    public void initData() {
        mPageNum = 0;
        mPageSize = 10;
        mIsLoad = false;
    }

    @Override
    public void initAdapter() {
        mAdapter = getAdapter();
        if (canLoadMore()) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        }
        if (canLoadMore() && openLoadAnim()) {
            mAdapter.openLoadAnimation();
        }
    }

    protected void setRvParams(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
        if (left != 0) {
            layoutParams.leftMargin = left;
        }
        if (top != 0) {
            layoutParams.topMargin = top;
        }
        if (right != 0) {
            layoutParams.rightMargin = right;
        }
        if (bottom != 0) {
            layoutParams.bottomMargin = bottom;
        }
        mRecyclerView.setLayoutParams(layoutParams);
    }

    @Override
    public void initView() {
        setTitle(getToolbarTitle());

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_common_activity);
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
    public void initListener() {

    }

//    protected SimpleSubListener<List<T>> getSimpleListener() {
//        if (mSimpleSubListener == null) {
//            mSimpleSubListener = new SimpleSubListener<List<T>>() {
//                @Override
//                public void onNext(List<T> t) {
//                    if (mPageNum == 0 && t.size() == 0) {
//                        showEmpty();
//                        return;
//                    }
//                    mAdapter.addData(t);
//                    if (canLoadMore()) {
//                        if (t.size() <= mPageSize) {
//                            if (mPageNum == 0) {
//                                mAdapter.loadMoreEnd(true);
//                            } else {
//                                mAdapter.loadMoreEnd();
//                            }
//                        } else {
//                            mAdapter.loadMoreComplete();
//                            mPageNum++;
//                        }
//                    }
//                    mIsLoad = false;
//                }
//
//                @Override
//                public void onError(Throwable throwable) {
//                    if (mPageNum == 0 && hasBaseLayout()) {
//                        showError();
//                    } else {
//                        mAdapter.loadMoreFail();
//                    }
//                    mIsLoad = false;
//                }
//            };
//        }
//        return mSimpleSubListener;
//    }

    @Override
    protected boolean hasBaseLayout() {
        return true;
    }

    protected abstract String getToolbarTitle();

    protected abstract BaseQuickAdapter<T, BaseViewHolder> getAdapter();

    protected abstract void getData();

    protected boolean canLoadMore() {
        return false;
    }

    protected boolean openLoadAnim() {
        return false;
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public void onLoadMoreRequested() {
        getNetData();
    }

    private void getNetData() {
        if (!mIsLoad) {
            mIsLoad = true;
            getData();
        }
    }
}
