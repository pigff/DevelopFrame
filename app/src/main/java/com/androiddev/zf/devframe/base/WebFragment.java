package com.androiddev.zf.devframe.base;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.base.presenter.imp.BasePresenter;
import com.androiddev.zf.devframe.utils.LogUtil;
import com.androiddev.zf.devframe.utils.constant.Constants;

import java.io.Serializable;

//TODO
public class WebFragment extends MvpFragment {

    protected ProgressBar progressBar;

    protected WebView webView;


    protected boolean isError;

    private WebContentEntity mContent;


    public static WebFragment newInstance(WebContentEntity entity) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.DATA, entity);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public WebViewClient getWebViewClient() {
        return new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtil.d("startUrl:", url);
                webView.setVisibility(View.VISIBLE);
                isError = false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.d("url:", url);

                return super.shouldOverrideUrlLoading(view, url);

            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                updateProgressBar(100);
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                LogUtil.w("errorCode:", errorCode + "|failingUrl:" + failingUrl);
                showReceiveError();
            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.cancel();
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideReceiveError();
            }
        };
    }


    private void showReceiveError() {
        isError = true;
        showError();
    }

    private void hideReceiveError() {
        if (isError) {
            showReceiveError();
        } else {
            webView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 加载url
     *
     * @param webView
     */
    private void load(WebView webView) {
        if (mContent == null) {
            return;
        }
        if (mContent.getType() == WebContentEntity.ContentType.CONTENT) {
            webView.loadData(mContent.getContent(), "text/html; charset=utf-8", "utf-8");
        } else {
            webView.loadUrl(mContent.getContent());
        }
    }

    private boolean isGoBack() {
        return webView != null && webView.canGoBack();
    }


    private void updateProgressBar(int progress) {
        updateProgressBar(true, progress);
    }


    private void updateProgressBar(boolean isVisibility, int progress) {
        progressBar.setVisibility((isVisibility && progress < 100) ? View.VISIBLE : View.GONE);
        progressBar.setProgress(progress);
    }


    @Override
    protected void initView() {
        hide();
        mView.setFocusable(true);
        mView.setFocusableInTouchMode(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        webView = (WebView) findViewById(R.id.webView);

        initWebViewSetting();
        load(webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSetting() {
        WebSettings ws = webView.getSettings();
        //是否允许脚本支持
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setHorizontalScrollBarEnabled(false);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                updateProgressBar(newProgress);
            }
        });
        webView.setWebViewClient(getWebViewClient());
    }

    @Override
    protected void initData() {
        mContent = (WebContentEntity) getArguments().getSerializable(Constants.DATA);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean hasBaseLayout() {
        return true;
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_web, container);
    }

    public static class WebContentEntity implements Serializable {

        private ContentType mType;
        private String content;


        private WebContentEntity() {

        }

        public ContentType getType() {
            return mType;
        }

        public void setType(ContentType type) {
            mType = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public static WebContentEntity newInstance(String content, ContentType type) {
            WebContentEntity entity = new WebContentEntity();
            entity.setContent(content);
            entity.setType(type);
            return entity;
        }

        public enum ContentType {
            URL, CONTENT
        }
    }
}

