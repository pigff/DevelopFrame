package com.androiddev.zf.devframe.subscribers;

import android.text.TextUtils;
import android.widget.Toast;

import com.fjrcloud.sciencepro.App;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by greedy on 2017/4/6.
 */

public class CompleteSubscriber<T> extends Subscriber<T> {

    private CompleteSubListener<T> completeSubListener;

    public CompleteSubscriber(CompleteSubListener<T> completeSubListener) {
        this.completeSubListener = completeSubListener;
    }

    @Override
    public void onCompleted() {
        if (completeSubListener != null) {
            completeSubListener.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(App.getInstance(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(App.getInstance(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.equals("查找失败，没有找到相关记录", e.getMessage())) {
                Toast.makeText(App.getInstance(), "没有找到相关记录", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(App.getInstance(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        if (completeSubListener != null) {
            completeSubListener.onError(e);
        }
    }

    @Override
    public void onNext(T t) {
        if (completeSubListener != null) {
            completeSubListener.onNext(t);
        }
    }
}
