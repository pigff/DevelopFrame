package com.androiddev.zf.devframe.mvp.presenter;

import com.androiddev.zf.devframe.api.Joke;
import com.androiddev.zf.devframe.base.view.IBaseView;

/**
 * Created by greedy on 2017/5/11.
 */

public interface IMainView extends IBaseView{

    void showData(Joke joke);
}
