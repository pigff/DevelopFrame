package com.androiddev.zf.devframe.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.utils.LogUtil;
import com.androiddev.zf.devframe.widget.Constants;
import com.androiddev.zf.devframe.widget.EmptyLayout;

import org.greenrobot.eventbus.EventBus;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by lin on 2017/2/23.
 */

public abstract class BaseMFragment extends Fragment implements IBaseView, EmptyLayout.OnBaseLayoutClickListener {

    private static final String TAG = "BaseMFragment";

    private CompositeSubscription mCompositeSubscription;

    protected View mView;
    private EmptyLayout mEmptyLayout;
    private boolean mIsInitialized;

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
            mEmptyLayout.setOnBaseLayoutClickListener(this);
        }
        mIsInitialized = true;
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
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
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
        if (mEmptyLayout != null) {
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

    protected void initView() {

    }

    protected void initData() {

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

    protected void openActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    protected void openActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(Constants.PARAM, bundle);
        startActivity(intent);
    }

    protected void showShortToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    protected View findViewById(int id) {
        if (id < 0) {
            return null;
        }
        return mView.findViewById(id);
    }


    protected abstract View getFragmentView(LayoutInflater inflater, ViewGroup container);

    protected boolean registEventBus() {
        return false;
    }

    protected boolean hasBaseLayout() {
        return false;
    }

}
