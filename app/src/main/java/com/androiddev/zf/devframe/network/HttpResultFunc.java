package com.androiddev.zf.devframe.network;

import android.text.TextUtils;

import com.androiddev.zf.devframe.data.base.HttpResult;
import com.androiddev.zf.devframe.exception.ApiException;
import com.androiddev.zf.devframe.widget.Constants;

import rx.functions.Func1;


/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
    @Override
    public T call(HttpResult<T> httpResult) {
        if (!TextUtils.equals(httpResult.getCode(), Constants.SUCCESS_CODE)) {
            throw new ApiException(httpResult.getMsg());
        }
        return httpResult.getData();
    }
}
