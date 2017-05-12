package com.androiddev.zf.devframe.mvp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.api.Joke;
import com.androiddev.zf.devframe.base.BaseRecyclerFragment;
import com.androiddev.zf.devframe.base.presenter.imp.ListPresenter;
import com.androiddev.zf.devframe.mvp.presenter.imp.ListPresenterImp;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by greedy on 2017/5/11.
 */

public class ListFragment extends BaseRecyclerFragment<Joke.ShowapiResBodyBean.ContentlistBean, ListFragment.ListAdapter, ListPresenter<Joke.ShowapiResBodyBean.ContentlistBean>> {


    @Override
    protected void onLazyLoad() {
        getPresenter().getData();
    }

    @Override
    public boolean canLoadMore() {
        return true;
    }

    @Override
    protected ListAdapter getRecyclerAdapter() {
        return new ListAdapter(R.layout.list_item);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected ListPresenter<Joke.ShowapiResBodyBean.ContentlistBean> initPresenter() {
        return new ListPresenterImp();
    }


    public class ListAdapter extends BaseQuickAdapter<Joke.ShowapiResBodyBean.ContentlistBean, BaseViewHolder> {
        public ListAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Joke.ShowapiResBodyBean.ContentlistBean item) {
            helper.setText(R.id.tv_list, item.getTitle());
        }
    }
}
