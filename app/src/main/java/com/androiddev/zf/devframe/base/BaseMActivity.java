package com.androiddev.zf.devframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.utils.LogUtil;
import com.androiddev.zf.devframe.widget.EmptyLayout;

import org.greenrobot.eventbus.EventBus;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lin on 2017/2/23.
 */

public abstract class BaseMActivity extends BaseActivity implements IBaseView, EmptyLayout.OnBaseLayoutClickListener {

    private static final String TAG = "BaseMActivity";
    private CompositeSubscription mCompositeSubscription;

    protected EmptyLayout mEmptyLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, getClass().getSimpleName());
        setContentView(provideContentView());
        if (hasBaseLayout()) {
            mEmptyLayout = (EmptyLayout) findViewById(R.id.emptylayout);
            mEmptyLayout.setOnBaseLayoutClickListener(this);
        }

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (registEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
        if (registEventBus()) {
            EventBus.getDefault().unregister(this);
        }
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
        if (mEmptyLayout != null) {
            mEmptyLayout.hide();
        }
    }


    protected CompositeSubscription getCompositeSubscription() {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    protected void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
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

    protected void initData() {

    }

    protected void initView() {

    }

    private void init() {
        initData();
        initAdapter();
        initView();
        initListener();
    }


    @Override
    public void onClickRetry() {

    }

    @Override
    public void onClickEmpty() {

    }

    protected boolean registEventBus() {
        return false;
    }

    protected abstract int provideContentView();

    protected boolean hasBaseLayout() {
        return false;
    }
}
