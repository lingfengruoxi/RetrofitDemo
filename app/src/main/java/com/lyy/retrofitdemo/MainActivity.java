package com.lyy.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jkyeo.basicparamsinterceptor.BasicParamsInterceptor;
import com.lyy.retrofitdemo.interfaces.MovieService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
    }

    public void onClick(View view) {
        /*
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
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(builder.build())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
         */

        MovieService movieService = RetrofitServiceManager.getInstance().create(MovieService.class);


        subscription = movieService.getTop250(0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieModel movieSubject) {
                        Log.d("result", movieSubject.getTitle());
                    }
                });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Call<MovieModel> call = movieService.getTop250(0, 20);
//                call.enqueue(new Callback<MovieModel>() {
//                    @Override
//                    public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                        Log.d("response", response.body().getTitle());
//                    }
//
//                    @Override
//                    public void onFailure(Call<MovieModel> call, Throwable t) {
//
//                    }
//                });

//            }
//        }).start();

    }


    @Override
    protected void onStop() {
        if (subscription!=null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onStop();
    }
}
