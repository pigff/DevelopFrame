package com.androiddev.zf.devframe.base.view;

import java.util.List;

/**
 * Created by greedy on 2017/5/11.
 */

public interface IListView<T> extends IBaseView {

    void addData(List<T> t);

    void setNewData(List<T> t);

    void loadComplete();

    void loadError();

    void loadEnd(boolean show);

    boolean canLoadMore();
}
