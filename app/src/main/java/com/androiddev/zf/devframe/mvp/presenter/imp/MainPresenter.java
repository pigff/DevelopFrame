package com.androiddev.zf.devframe.mvp.presenter.imp;

import com.androiddev.zf.devframe.api.Joke;
import com.androiddev.zf.devframe.base.presenter.imp.BasePresenter;
import com.androiddev.zf.devframe.mvp.presenter.IMainPresenter;
import com.androiddev.zf.devframe.mvp.presenter.IMainView;
import com.androiddev.zf.devframe.network.RequestManager;
import com.androiddev.zf.devframe.subscribers.SimpleSubListener;
import com.androiddev.zf.devframe.subscribers.SimpleSubscriber;

/**
 * Created by greedy on 2017/5/11.
 */

public class MainPresenter extends BasePresenter<IMainView> implements IMainPresenter {

    @Override
    public void getData() {
        RequestManager.getInstance()
                .findJokeList()
                .compose(getView().<Joke>bindToLife())
                .subscribe(new SimpleSubscriber<>(new SimpleSubListener<Joke>() {
                    @Override
                    public void onNext(Joke joke) {
                        getView().showData(joke);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }
                }));
    }
}
