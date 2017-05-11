package com.androiddev.zf.devframe.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.utils.ViewUtils;


/**
 * time: 15/6/9
 * description:
 *
 * @author sunjianfei
 */
public class BaseLayout extends RelativeLayout implements View.OnClickListener {
    public static final String TAG_EMPTY = "empty";
    public static final String TAG_ERROR = "error";

    private View mEmptyView;
    private View mErrorView;
    private View mContentView;
    private View mProgressBar;

    private OnBaseLayoutClickListener mOnBaseLayoutClickListener;

    public BaseLayout(Context context) {
        super(context);
    }

    /**
     *
     * @param context
     * @param empty    若为空则使用默认的
     * @param error     同上
     * @param content   不能为空
     * @param progressbar   同上上
     * @param progressbarBg 进度条显示的颜色
     * @param onBaseLayoutClickListener    页面的点击事件
     */
    public BaseLayout(Context context, View empty, View error, @NonNull View content,
                      View progressbar, int progressbarBg, OnBaseLayoutClickListener onBaseLayoutClickListener) {
        this(context);
        mEmptyView = empty;
        mErrorView = error;
        mContentView = content;
        mProgressBar = progressbar;
        this.mOnBaseLayoutClickListener = onBaseLayoutClickListener;
        //1.将conentview添加到relativelayout
        if (content == null) {
            throw new IllegalArgumentException("The content view must not null ");
        }
        LayoutParams contentParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        addView(content, contentParams);
        //2.将空界面界面添加
        if (empty == null) {
            empty = inflate(context, R.layout.vw_empty, null);
        }
        mEmptyView = empty;
        LayoutParams emptyParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        addView(mEmptyView, emptyParams);
        //3.添加错误界面
        if (error == null) {
            error = inflate(context, R.layout.vw_error, null);
        }
        mErrorView = error;
        LayoutParams errorParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        addView(mErrorView, errorParams);
        //4.添加进度条
        if (progressbar == null) {
            progressbar = inflate(context, R.layout.vw_progressbar, null);
        }
        if (progressbarBg > 0) {
            progressbar.setBackgroundResource(progressbarBg);
        }
        mProgressBar = progressbar;
        LayoutParams pbParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        addView(mProgressBar, pbParams);
        //5.添加事件监听
        mEmptyView.setTag(TAG_EMPTY);
        mErrorView.setTag(TAG_ERROR);
        mProgressBar.setOnClickListener(this);
        mEmptyView.setOnClickListener(this);
        mErrorView.setOnClickListener(this);
        //一开始默认显示的内容页面
        showContentView();
    }

    public void setEmptyView(Context context, int resId) {
        removeView(mEmptyView);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mEmptyView = inflater.inflate(resId, null);
        FrameLayout frameLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams emptyViewParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        emptyViewParams.gravity = Gravity.CENTER;
        LayoutParams emptyLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        frameLayout.addView(mEmptyView, emptyViewParams);
        addView(frameLayout, emptyLayoutParams);
        mEmptyView.setTag(TAG_EMPTY);
        mEmptyView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //防止多次点击
        ViewUtils.setDelayedClickable(v, 500);
        if (mOnBaseLayoutClickListener != null) {
            Object object = v.getTag();
            if (null != object) {
                String tag = v.getTag().toString();
                if (TAG_EMPTY.equals(tag)) {
                    mOnBaseLayoutClickListener.onClickEmpty();
                }
                if (TAG_ERROR.equals(tag)) {
                    mOnBaseLayoutClickListener.onClickRetry();
                }
            }
        }
    }

    /**
     * 依次表示
     * 显示内容页面
     * 显示进度条
     * 显示空界面
     * 显示错误界面
     */
    public void showContentView() {
        mContentView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    public void showProgressView() {
        mContentView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void showEmptyView() {
        mContentView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    public void showErrorView() {
        mContentView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * 利用建造者模式来构建一个BaseLayout
     */
    public static class Builder {
        private Context context;
        private LayoutInflater inflater;
        private View contentView;
        private View emptyView;
        private View errorView;
        private View progressBarView;
        private int progressBarViewBg;
        private OnBaseLayoutClickListener onBaseLayoutClickListener;

        public Builder(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public Builder setContentView(int resId) {
            //todo 不能为null
            contentView = inflater.inflate(resId, null);
            return this;
        }

        public Builder setContentView(View view) {
            contentView = view;
            return this;
        }

        public Builder setProgressBarViewBg(int resId) {
            progressBarViewBg = resId;
            return this;
        }

        public Builder setEmptyView(int resId) {
            emptyView = inflater.inflate(resId, null);
            return this;
        }

        public Builder setEmptyView(View view) {
            emptyView = view;
            return this;
        }

        public Builder setErrorView(int resId) {
            errorView = inflater.inflate(resId, null);
            return this;
        }

        public Builder setErrorView(View view) {
            errorView = view;
            return this;
        }

        public Builder setProgressView(int resId) {
            progressBarView = inflater.inflate(resId, null);
            return this;
        }

        public Builder setProgressView(View view) {
            progressBarView = view;
            return this;
        }

        public Builder setOnBaseLayoutClickListener(OnBaseLayoutClickListener onBaseLayoutClickListener) {
            this.onBaseLayoutClickListener = onBaseLayoutClickListener;
            return this;
        }

        public BaseLayout build() {
            return new BaseLayout(context, emptyView, errorView, contentView, progressBarView, progressBarViewBg, onBaseLayoutClickListener);
        }
    }

    /**
     * 依次表示网络错误点击事件
     *
     * 空页面点击事件
     */
    public static interface OnBaseLayoutClickListener {
        void onClickRetry();

        void onClickEmpty();
    }


}
