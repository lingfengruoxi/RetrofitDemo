package com.lyy.retrofitdemo.interfaces;

import com.lyy.retrofitdemo.MovieModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/1/2.
 */

public interface MovieService {
    //臭臭是个大笨蛋
    @GET("top250")
    Observable<MovieModel> getTop250(@Query("start") int start, @Query("count")int count);
}
