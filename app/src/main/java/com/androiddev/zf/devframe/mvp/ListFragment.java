package com.androiddev.zf.devframe.mvp;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.api.Joke;
import com.androiddev.zf.devframe.base.BaseRecyclerFragment;
import com.androiddev.zf.devframe.mvp.presenter.imp.ListPresenterImp;
import com.androiddev.zf.devframe.widget.Constants;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by greedy on 2017/5/11.
 */

public class ListFragment extends BaseRecyclerFragment<Joke.ShowapiResBodyBean.ContentlistBean, ListFragment.ListAdapter, ListPresenterImp> {

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        getPresenter().getData(mPageNum, mPageSize);
    }

    @Override
    public boolean canLoadMore() {
        return true;
    }

    @Override
    protected boolean canRefresh() {
        return true;
    }

    @Override
    protected ListAdapter initRecyclerAdapter() {
        return new ListAdapter(R.layout.list_item);
    }

    @Override
    protected ListPresenterImp initPresenter() {
        return new ListPresenterImp();
    }

    @Override
    protected void loadMoreData() {
        getPresenter().getData(mPageNum, mPageSize);
    }

    @Override
    protected void refreshData() {
        getPresenter().getData(Constants.DEFAULT_PAGENUM, mPageSize);
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
