package com.wangxinarhat.gankmvp.api;

import com.wangxinarhat.gankmvp.data.GankData;
import com.wangxinarhat.gankmvp.data.PrettyGirlData;
import com.wangxinarhat.gankmvp.data.休息视频Data;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * data api
 * @author wangxinarhat
 * creat at 2016/7/25
 */
public interface GankService {

    /**
     * 妹子data
     *
     * @param pagesize
     * @param page
     * @return
     */
    @GET("data/福利/{pagesize}/{page}")
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pagesize") int pagesize, @Path("page") int page);

    /**
     * 休息视频data
     *
     * @param pagesize
     * @param page
     * @return
     */
    @GET("data/休息视频/{pagesize}/{page}")
    Observable<休息视频Data> get休息视频Data(@Path("pagesize") int pagesize, @Path("page") int page);

    /**
     * gank daily data
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

}
