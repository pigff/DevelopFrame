package com.androiddev.zf.devframe.api;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by lin on 2017/2/24.
 */

public interface Api {

    @GET("341-3")
    Observable<Object> FindJokeList();
}
