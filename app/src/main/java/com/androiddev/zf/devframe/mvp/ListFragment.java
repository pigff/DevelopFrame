package com.androiddev.zf.devframe.mvp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.api.Joke;
import com.androiddev.zf.devframe.base.BaseRecyclerFragment;
import com.androiddev.zf.devframe.mvp.presenter.imp.ListPresenterImp;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by greedy on 2017/5/11.
 */

public class ListFragment extends BaseRecyclerFragment<Joke.ShowapiResBodyBean.ContentlistBean, ListFragment.ListAdapter, ListPresenterImp> {


    @Override
    public void initData() {
        super.initData();
        mPageNum = 0;
    }

    @Override
    protected void onLazyLoad() {
        getPresenter().getData(mPageNum, mPageSize);
    }

    @Override
    public void addData(List<Joke.ShowapiResBodyBean.ContentlistBean> data) {
        getAdapter().addData(data);
    }

    @Override
    public void loadComplete() {
        getAdapter().loadMoreComplete();
        mIsLoad = false;
        mPageNum++;
    }

    @Override
    public void loadError() {
        getAdapter().loadMoreFail();
        mIsLoad = false;
    }

    @Override
    public void loadEnd(boolean show) {
        if (show) {
            getAdapter().loadMoreEnd(true);
        } else {
            getAdapter().loadMoreEnd(false);
        }
        mIsLoad = false;
    }

    @Override
    public boolean canLoadMore() {
        return true;
    }

    @Override
    protected ListAdapter initRecyclerAdapter() {
        return new ListAdapter(R.layout.list_item);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected ListPresenterImp initPresenter() {
        return new ListPresenterImp();
    }

    @Override
    public void onLoadMoreRequested() {
        if (!mIsLoad) {
            mIsLoad = true;
            getPresenter().getData(mPageNum, mPageSize);
        }
    }


    class ListAdapter extends BaseQuickAdapter<Joke.ShowapiResBodyBean.ContentlistBean, BaseViewHolder> {
        public ListAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Joke.ShowapiResBodyBean.ContentlistBean item) {
            helper.setText(R.id.tv_list, item.getTitle());
        }
    }
}
