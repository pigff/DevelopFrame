package com.androiddev.zf.devframe.subscribers;

import android.widget.Toast;

import com.androiddev.zf.devframe.App;
import com.androiddev.zf.devframe.base.presenter.imp.BasePresenter;
import com.androiddev.zf.devframe.utils.LogUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by greedy on 2017/4/6.
 */

public class SimpleSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "SimpleSubscriber";

    private SimpleSubListener<T> mSimpleSubListener;

    public SimpleSubscriber(SimpleSubListener<T> simpleSubListener) {
        mSimpleSubListener = simpleSubListener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(App.getInstance(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(App.getInstance(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof BasePresenter.MvpViewNotAttachedException) {
            LogUtil.d(TAG, e.getMessage());
        } else {
            Toast.makeText(App.getInstance(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (mSimpleSubListener != null) {
            mSimpleSubListener.onError(e);
        }
    }

    @Override
    public void onNext(T t) {
        if (mSimpleSubListener != null) {
            mSimpleSubListener.onNext(t);
        }
    }
}
