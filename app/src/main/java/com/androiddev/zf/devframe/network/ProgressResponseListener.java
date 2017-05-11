package com.androiddev.zf.devframe.network;

/**
 * Created by lin on 2017/3/4.
 */

public interface ProgressResponseListener {

    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
