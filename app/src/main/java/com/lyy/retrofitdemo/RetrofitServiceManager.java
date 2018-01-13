package com.lyy.retrofitdemo;

import com.jkyeo.basicparamsinterceptor.BasicParamsInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/3.
 */

public class RetrofitServiceManager {

    private static RetrofitServiceManager retrofitServiceManager = null;
    private final Retrofit retrofit;

    public RetrofitServiceManager() {
        //公共参数拦截器
        BasicParamsInterceptor interceptor = new BasicParamsInterceptor.Builder()
                .addHeaderParam("userName", "")
                .addParam("version", "1.1")
                .build();

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        builder.connectTimeout(1000, TimeUnit.SECONDS); //超时连接
        builder.readTimeout(1000, TimeUnit.SECONDS);  //读操作 超时
        builder.writeTimeout(1000, TimeUnit.SECONDS);

        String BASE_URL = "https://api.douban.com/v2/movie/";
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(builder.build())
                .build();
    }

    public static RetrofitServiceManager getInstance() {
        if (retrofitServiceManager == null) {
            retrofitServiceManager = new RetrofitServiceManager();
        }
        return retrofitServiceManager;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

}
