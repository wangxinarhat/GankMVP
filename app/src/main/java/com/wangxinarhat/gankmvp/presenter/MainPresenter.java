package com.wangxinarhat.gankmvp.presenter;

import android.app.Activity;

import com.wangxinarhat.gankmvp.base.BasePresenter;
import com.wangxinarhat.gankmvp.data.GankData;
import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.ui.view.MainView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by wang on 2016/7/22.
 */
public class MainPresenter extends BasePresenter<MainView> {
    private static final int DAY_OF_MILLISECOND = 24 * 60 * 60 * 1000;
    private Date mCurrentDate;
    List<Gank> mGankList = new ArrayList<>();

    private int mCountOfGetMoreDataEmpty = 0;


    /**
     *  if execute getDataMore method more than once ,this flag will be true else false
     */
    private boolean hasLoadMoreData = false;

    public MainPresenter(Activity context, MainView view) {
        super(context, view);
    }


    public void getData(final Date date) {
        mCurrentDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mGankService.getGankData(year, month, day)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GankData, GankData.Result>() {
                    @Override
                    public GankData.Result call(GankData gankData) {
                        return gankData.results;
                    }
                })
                .map(new Func1<GankData.Result, List<Gank>>() {
                    @Override
                    public List<Gank> call(GankData.Result result) {
                        return addAllResults(result);
                    }
                })
                .subscribe(new Subscriber<List<Gank>>() {
                    @Override
                    public void onCompleted() {
                        // after get data complete, need put off time one day
                        mCurrentDate = new Date(date.getTime() - DAY_OF_MILLISECOND);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Gank> ganks) {
                        // some day the data will be return empty like sunday, so we need get after day data
                        if (ganks.isEmpty()) {
                            getData(new Date(date.getTime() - DAY_OF_MILLISECOND));
                        } else {
                            mCountOfGetMoreDataEmpty = 0;
                            mView.fillData(ganks);
                        }
                        mView.getDataFinish();
                    }
                });
    }

    public void getDataMore() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mGankService.getGankData(year, month, day)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GankData, GankData.Result>() {
                    @Override
                    public GankData.Result call(GankData gankData) {
                        return gankData.results;
                    }
                })
                .map(new Func1<GankData.Result, List<Gank>>() {
                    @Override
                    public List<Gank> call(GankData.Result result) {
                        return addAllResults(result);
                    }
                })
                .subscribe(new Subscriber<List<Gank>>() {
                    @Override
                    public void onCompleted() {
                        // after get data complete, need put off time one day
                        mCurrentDate = new Date(mCurrentDate.getTime() - DAY_OF_MILLISECOND);
                        // now user has execute getMoreData so this flag will be set true
                        //and now when user pull down list we would not refill data
                        hasLoadMoreData = true;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Gank> ganks) {
                        //when this day is weekend , the list will return empty(weekend has not gank info,the editors need rest)
                        if (ganks.isEmpty()) {
                            //record count of empty day
                            mCountOfGetMoreDataEmpty += 1;
                            //if empty day is more than five,it indicate has no more data to show
                            if(mCountOfGetMoreDataEmpty>=5){
                                mView.hasNoMoreData();
                            }else{
                                // we need look forward data
                                getDataMore();
                            }
                        } else {
                            mCountOfGetMoreDataEmpty = 0;
                            mView.appendMoreData2View(ganks);
                        }
                        mView.getDataFinish();
                    }
                });
    }


    private List<Gank> addAllResults(GankData.Result results) {
        mGankList.clear();
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.拓展资源List != null) mGankList.addAll(results.拓展资源List);
        if (results.瞎推荐List != null) mGankList.addAll(results.瞎推荐List);
        if (results.休息视频List != null) mGankList.addAll(results.休息视频List);
        // make meizi data is in first position
        if (results.妹纸List != null) mGankList.addAll(0, results.妹纸List);
        return mGankList;
    }


}
