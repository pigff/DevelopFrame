package com.androiddev.zf.devframe.mvp;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.data.api.Joke;
import com.androiddev.zf.devframe.base.BaseRecyclerFragment;
import com.androiddev.zf.devframe.mvp.presenter.imp.ListPresenterImp;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by greedy on 2017/5/11.
 */

public class ListFragment extends BaseRecyclerFragment<Joke.ShowapiResBodyBean.ContentlistBean, ListFragment.ListAdapter, ListPresenterImp> {


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
    protected void loadData(int pageNum) {
        getPresenter().getData(pageNum, mPageSize);
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
