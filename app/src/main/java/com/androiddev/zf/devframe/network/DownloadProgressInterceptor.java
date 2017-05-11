package com.androiddev.zf.devframe.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by lin on 2017/3/4.
 * 带进度的拦截器
 */

public class DownloadProgressInterceptor implements Interceptor {
    private ProgressResponseListener mProgressResponseListener;

    public DownloadProgressInterceptor(ProgressResponseListener progressResponseListener) {
        mProgressResponseListener = progressResponseListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new ProgressResponseBody(response.body(), mProgressResponseListener))
                .build();
    }
}
