package com.androiddev.zf.devframe.data.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lin on 2017/2/24.
 */

public interface Api {

    @GET("341-3")
    Observable<Joke> findJokeList(@Query("showapi_appid") String appid, @Query("showapi_sign") String sign);

    @GET("341-3")
    Observable<Joke> findJokeList(@Query("showapi_appid") String appid, @Query("showapi_sign") String sign
                                , @Query("page") int pageNum, @Query("maxResult") int pageSize);
}
