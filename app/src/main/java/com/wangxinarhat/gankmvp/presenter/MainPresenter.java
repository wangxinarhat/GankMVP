package com.wangxinarhat.gankmvp.presenter;

import android.app.Activity;

import com.wangxinarhat.gankmvp.base.BasePresenter;
import com.wangxinarhat.gankmvp.data.GankData;
import com.wangxinarhat.gankmvp.data.Results;
import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.ui.view.MainView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Presenterfor MainActivity
 *
 * @author wangxinarhat
 *         creat at 2016/7/25
 */
public class MainPresenter extends BasePresenter<MainView> {
    private static final int DAY_OF_MILLISECOND = 24 * 60 * 60 * 1000;
    private Date mCurrentDate;
    List<Gank> mGankList = new ArrayList<>();

    private int mCountOfGetMoreDataEmpty = 0;


    /**
     * if execute getDataMore method more than once ,this flag will be true else false
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GankData, Results>() {
                    @Override
                    public Results call(GankData gankData) {
                        return gankData.results;
                    }
                })
                .map(new Func1<Results, List<Gank>>() {
                    @Override
                    public List<Gank> call(Results result) {
                        return addAllResults(result);
                    }
                })
                .subscribe(new Subscriber<List<Gank>>() {
                    @Override
                    public void onCompleted() {
                        // after get data complete, need put off time one day
                        mCurrentDate = new Date(date.getTime() - DAY_OF_MILLISECOND);
                        mView.getDataFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getDataFinish();
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GankData, Results>() {
                    @Override
                    public Results call(GankData gankData) {
                        return gankData.results;
                    }
                })
                .map(new Func1<Results, List<Gank>>() {
                    @Override
                    public List<Gank> call(Results result) {
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
                        mView.getDataFinish();
                    }

                    @Override
                    public void onError(Throwable e) {

                        mView.getDataFinish();
                    }

                    @Override
                    public void onNext(List<Gank> ganks) {
                        //when this day is weekend , the list will return empty(weekend has not gank info,the editors need rest)

                        if (ganks.isEmpty()) {
                            //record count of empty day
                            mCountOfGetMoreDataEmpty += 1;
                            //if empty day is more than five,it indicate has no more data to show
                            if (mCountOfGetMoreDataEmpty >= 5) {
                                mView.hasNoMoreData();
                            } else {
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


    private List<Gank> addAllResults(Results results) {
        mGankList.clear();
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.expandList != null) mGankList.addAll(results.expandList);
        if (results.recommendList != null) mGankList.addAll(results.recommendList);
        if (results.restList != null) mGankList.addAll(results.restList);
        // make meizi data is in first position
        if (results.welfareList != null) mGankList.addAll(0, results.welfareList);
        return mGankList;
    }


}
