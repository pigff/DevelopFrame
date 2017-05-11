package com.androiddev.zf.devframe.base.presenter.imp;

import com.androiddev.zf.devframe.base.presenter.INetPresenter;
import com.androiddev.zf.devframe.base.view.IListView;
import com.androiddev.zf.devframe.subscribers.SimpleSubListener;

import java.util.List;

/**
 * Created by greedy on 2017/5/11.
 */

public class ListPresenter<T> extends BasePresenter<IListView<T>> implements INetPresenter {

    private SimpleSubListener<List<T>> mSimpleSubListener;

    @Override
    public void getData() {
//        getView()
    }

    private SimpleSubListener<List<T>> getSimpleListener(final int pageNum, final int pageSize) {
        if (mSimpleSubListener == null) {
            mSimpleSubListener = new SimpleSubListener<List<T>>() {
                @Override
                public void onNext(List<T> t) {
                    if (pageNum == 0 && t.size() == 0) {
                        getView().showEmpty();
                        return;
                    }
                    getView().addData(t);
                    if (getView().canLoadMore()) {
                        if (t.size() <= pageSize) {
                            if (pageNum == 0) {
                                // 不显示没有更多
                                getView().loadEnd(true);
                            } else {
                                //显示没有更多
                                getView().loadEnd(false);
                            }
                        } else {
                            getView().loadComplete();
                            // 页码增加 需要在View层里面做处理
//                            pageNum++;
                        }
                    }
//                    mIsLoad = false;
                }

                @Override
                public void onError(Throwable throwable) {
                    if (pageNum == 0 ) {
                        getView().showError();
                    } else {
                        getView().loadError();
                    }
//                    mIsLoad = false;
                }
            };
        }
        return mSimpleSubListener;
    }
}
