package com.androiddev.zf.devframe.network;

import android.util.Log;

import com.androiddev.zf.devframe.App;
import com.androiddev.zf.devframe.api.Api;
import com.androiddev.zf.devframe.api.Joke;
import com.androiddev.zf.devframe.subscribers.SimpleSubscriber;
import com.androiddev.zf.devframe.widget.Constants;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lin on 2017/2/24.
 */

public class RequestManager {

    private static final String TAG = "RequestManager";
    public static final String APP_ID = "29056";
    public static final String SIGN = "307d7e2831614e11a510b57f5e55f4e6";
    private final static int MAX_AGE = 2 * 60 * 60;  //缓存两个小时
    private final static int CACHE_SIZE = 10 * 1024 * 1024; // 缓存10m

    private Api mApi;
    private static RequestManager sRequestManager;

    public RequestManager() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .header("Accept", "application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        };

        // okhttp3打印请求log的拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (message.startsWith("{")) {
                    Log.d(TAG, message);
                } else {
                    if (message.contains("-->") || message.contains("<--")) {
                        Log.d(TAG, message);
                    }
                }
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance()));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addNetworkInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApi = retrofit.create(Api.class);
    }

    public static RequestManager getInstance() {
        if (sRequestManager == null) {
            synchronized (RequestManager.class) {
                if (sRequestManager == null) {
                    sRequestManager = new RequestManager();
                }
            }
        }
        return sRequestManager;
    }


    public Api getApi() {
        return mApi;
    }

    public void findJokeList(SimpleSubscriber<Joke> sub) {
        mApi.findJokeList(APP_ID, SIGN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public void findJokeList2(SimpleSubscriber<List<Joke.ShowapiResBodyBean.ContentlistBean>> sub) {
        mApi.findJokeList(APP_ID, SIGN)
                .map(new Func1<Joke, List<Joke.ShowapiResBodyBean.ContentlistBean>>() {
                    @Override
                    public  List<Joke.ShowapiResBodyBean.ContentlistBean> call(Joke joke) {
                        return joke.getShowapi_res_body().getContentlist();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
