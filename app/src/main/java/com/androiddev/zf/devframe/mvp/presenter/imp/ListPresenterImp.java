package com.androiddev.zf.devframe.mvp.presenter.imp;

import com.androiddev.zf.devframe.api.Joke;
import com.androiddev.zf.devframe.base.presenter.imp.ListPresenter;
import com.androiddev.zf.devframe.mvp.presenter.IListPresenter;
import com.androiddev.zf.devframe.network.RequestManager;
import com.androiddev.zf.devframe.subscribers.SimpleSubscriber;

import java.util.List;

/**
 * Created by greedy on 2017/5/11.
 */

public class ListPresenterImp extends ListPresenter<Joke.ShowapiResBodyBean.ContentlistBean> implements IListPresenter{
    @Override
    public void getData(int pageNum, int pageSize) {
        RequestManager.getInstance()
                .findJokeList2(pageNum, pageSize)
                .compose(getView().<List<Joke.ShowapiResBodyBean.ContentlistBean>>bindToLife())
                .subscribe(new SimpleSubscriber<>(getLoadSimpleListener()));
    }
}
