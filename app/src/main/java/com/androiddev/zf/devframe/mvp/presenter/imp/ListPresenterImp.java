package com.androiddev.zf.devframe.mvp.presenter.imp;

import com.androiddev.zf.devframe.api.Joke;
import com.androiddev.zf.devframe.base.presenter.imp.ListPresenter;
import com.androiddev.zf.devframe.network.RequestManager;
import com.androiddev.zf.devframe.subscribers.SimpleSubscriber;

/**
 * Created by greedy on 2017/5/11.
 */

public class ListPresenterImp extends ListPresenter<Joke.ShowapiResBodyBean.ContentlistBean> {
    @Override
    public void getData() {
        RequestManager.getInstance().findJokeList2(new SimpleSubscriber<>(getSimpleListener(0, 10)));
    }
}
