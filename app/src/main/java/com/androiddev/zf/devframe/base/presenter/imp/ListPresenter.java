package com.androiddev.zf.devframe.base.presenter.imp;

import com.androiddev.zf.devframe.base.view.IListView;
import com.androiddev.zf.devframe.subscribers.SimpleSubListener;

import java.util.List;

/**
 * Created by greedy on 2017/5/11.
 */

public abstract class ListPresenter<T> extends BasePresenter<IListView<T>> {

    private SimpleSubListener<List<T>> mSimpleSubListener;


    protected SimpleSubListener<List<T>> getLoadSimpleListener() {
        if (mSimpleSubListener == null) {
            mSimpleSubListener = new SimpleSubListener<List<T>>() {
                @Override
                public void onNext(List<T> t) {
                    getView().loadData(t);
                }

                @Override
                public void onError(Throwable throwable) {
                    getView().loadError();
                }
            };
        }
        return mSimpleSubListener;
    }
}
