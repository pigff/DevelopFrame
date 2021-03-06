package com.androiddev.zf.devframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.base.presenter.imp.BasePresenter;
import com.androiddev.zf.devframe.base.view.IBaseView;
import com.androiddev.zf.devframe.utils.LogUtil;
import com.androiddev.zf.devframe.widget.EmptyLayout;
import com.trello.rxlifecycle.LifecycleTransformer;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by lin on 2017/5/11.
 */

public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment implements IBaseView, EmptyLayout.OnBaseLayoutClickListener {

    protected static final String TAG = "MvpFragment";
    protected View mView;
    private EmptyLayout mEmptyLayout;
    private boolean mIsInitialized;
    private P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = getFragmentView(inflater, container);
        LogUtil.d(TAG, getClass().getSimpleName());
        if (mView == null) {
            throw new IllegalStateException(
                    "The subclass of BaseFragment must implements method getFragmentView().");
        }
        init();

        if (hasBaseLayout()) {
            mEmptyLayout = (EmptyLayout) findViewById(R.id.emptylayout);
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
            mEmptyLayout.setOnBaseLayoutClickListener(this);
        }
        mIsInitialized = true;
        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (registEventBus()) {
            LogUtil.d(TAG, "regist bus");
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            if (mIsInitialized) {
                mIsInitialized = false;
                onLazyLoad();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (registEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mIsInitialized) {
                mIsInitialized = false;
                onLazyLoad();
            }
        }
    }

    @Override
    public void onClickRetry() {

    }

    @Override
    public void onClickEmpty() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindToLifecycle();
    }

    /**
     * 懒加载
     */
    protected void onLazyLoad() {

    }

    @Override
    public void showEmpty() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_DATA);
        }
    }

    @Override
    public void showError() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
        }
    }

    @Override
    public void showLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hide() {
        if (mEmptyLayout != null && mEmptyLayout.getEmptyStatus() != EmptyLayout.STATUS_HIDE) {
            mEmptyLayout.hide();
        }
    }


    private void init() {
        initData();
        initAdapter();
        initView();
        initListener();
    }

    /**
     * 初始化适配器
     */
    protected void initAdapter() {

    }

    /**
     * 初始化事件点击监听
     */
    protected void initListener() {

    }

    protected abstract void initView();

    protected abstract void initData();

    protected View findViewById(int id) {
        if (id < 0) {
            return null;
        }
        return mView.findViewById(id);
    }

    protected P getPresenter() {
        if (mPresenter == null) {
            mPresenter = initPresenter();
        }
        return mPresenter;
    }


    protected abstract P initPresenter();

    protected abstract View getFragmentView(LayoutInflater inflater, ViewGroup container);

    protected boolean registEventBus() {
        return false;
    }

    protected boolean hasBaseLayout() {
        return false;
    }

}
