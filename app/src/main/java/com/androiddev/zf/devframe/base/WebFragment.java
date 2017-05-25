package com.androiddev.zf.devframe.base;

//public class WebFragment extends MvpFragment {
//
//    protected ProgressBar progressBar;
//
//    protected WebView webView;
//
//    protected String url;
//
//    protected boolean isError;
//    private String mContent;
//
//
//    public static WebFragment newInstance() {
//        Bundle args = new Bundle();
//        WebFragment fragment = new WebFragment();
//        fragment.setArguments(args);
//        return fragment;
//
//    }
//
//    public WebViewClient getWebViewClient() {
//        return new WebViewClient() {
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                LogUtil.d("startUrl:", url);
//                webView.setVisibility(View.VISIBLE);
//                isError = false;
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LogUtil.d("url:", url);
//
//                return super.shouldOverrideUrlLoading(view, url);
//
//            }
//
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                updateProgressBar(100);
//            }
//
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                LogUtil.w("errorCode:", errorCode + "|failingUrl:" + failingUrl);
//                showReceiveError();
//            }
//
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
//                handler.cancel();
//                handler.proceed();
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                hideReceiveError();
//            }
//        };
//    }
//
//
//    private void showReceiveError() {
//        isError = true;
////        if(SystemUtils.isNetWorkActive(context)){
////            LogUtils.w("Page loading failed.");
////        }else{
////            LogUtils.w("Network unavailable.");
////        }
//        showError();
//    }
//
//    private void hideReceiveError() {
//        if (isError) {
//            showReceiveError();
//        } else {
//            webView.setVisibility(View.VISIBLE);
//        }
//
//    }
//
//    /**
//     * 加载url
//     *
//     * @param webView
//     * @param url
//     */
//    private void load(WebView webView, String url) {
//        LogUtil.d("url:", url);
//        if (!TextUtils.isEmpty(url)) {
//            webView.loadUrl(url);
//        }
//
//    }
//
//    private boolean isGoBack() {
//        return webView != null && webView.canGoBack();
//    }
//
//
//    private void updateProgressBar(int progress) {
//        updateProgressBar(true, progress);
//    }
//
//
//    private void updateProgressBar(boolean isVisibility, int progress) {
//
//        progressBar.setVisibility((isVisibility && progress < 100) ? View.VISIBLE : View.GONE);
//        progressBar.setProgress(progress);
//    }
//
//
//    @Override
//    protected void initView() {
//        mView.setFocusable(true);
//        mView.setFocusableInTouchMode(true);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setMax(100);
//        webView = (WebView) findViewById(R.id.webView);
//
//        WebSettings ws = webView.getSettings();
//        //是否允许脚本支持
//        ws.setJavaScriptEnabled(true);
//        ws.setDomStorageEnabled(true);
//        ws.setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.setHorizontalScrollBarEnabled(false);
//        if(!TextUtils.isEmpty(mContent)){
//            this.url = mContent;
//        }
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                updateProgressBar(newProgress);
//            }
//        });
//        webView.setWebViewClient(getWebViewClient());
//        load(webView, url);
//    }
//
//    @Override
//    protected void initData() {
//        mContent = getArguments().getString(Constants.DATA);
//    }
//
//    @Override
//    protected BasePresenter initPresenter() {
//        return null;
//    }
//
//    @Override
//    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
//        return inflater.inflate(R.layout.fragment_web, container);
//    }
//}

