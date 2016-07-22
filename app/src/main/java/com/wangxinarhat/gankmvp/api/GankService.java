package com.wangxinarhat.gankmvp.api;

import com.wangxinarhat.gankmvp.data.GankData;
import com.wangxinarhat.gankmvp.data.PrettyGirlData;
import com.wangxinarhat.gankmvp.data.休息视频Data;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wang on 2016/7/22.
 */
public interface GankService {

    @GET("/data/福利/{pagesize}/{page}")
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pagesize") int pagesize, @Path("page") int page);

    @GET("/data/休息视频/{pagesize}/{page}")
    Observable<休息视频Data> get休息视频Data(@Path("pagesize") int pagesize, @Path("page")int page);

    @GET("/day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year")int year, @Path("month")int month, @Path("day")int day);
}
