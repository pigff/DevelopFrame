package com.androiddev.zf.devframe.data.base;

/**
 * Created by greedy on 2017/5/17.
 */

public class BaseEvent<T> {

    protected T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
