package com.wangxinarhat.gankmvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.base.BasePresenter;
import com.wangxinarhat.gankmvp.ui.view.SwipeRefreshView;

import butterknife.Bind;

/**
 * Created by wang on 2016/7/21.
 */
public abstract class BaseSwipeRefreshActivity<P extends BasePresenter> extends BaseActivity<P> implements SwipeRefreshView {

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSwipeLayout();
    }

    /**
     * init SwipeRefreshLayout Widget
     */
    private void initSwipeLayout() {
        if (mSwipeRefreshLayout == null) {
            throw new NullPointerException("please add a SwipeRefreshLayout in your layout.");
        }
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (prepareRefresh()) {
                    onRefreshStarted();
                } else {
                    hideRefresh();
                }
            }
        });
    }

    /**
     * pull to refresh
     */
    protected abstract void onRefreshStarted();


    /**
     * for Extension
     *
     * @return if true means refreshable
     */
    protected boolean prepareRefresh() {
        return true;
    }

    /**
     * cancel SwipeRefreshLayout refreshing status，deley 1000ms
     */
    @Override
    public void hideRefresh() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null != mSwipeRefreshLayout) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 1000);
    }


    /**
     * to get data finish
     */
    @Override
    public void getDataFinish() {
        hideRefresh();
    }


    /**
     * show loading data status
     */
    @Override
    public void showRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    /**
     * check refreshlayout is refreshing
     *
     * @return true -> refreshing
     */
    @CheckResult
    protected boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

}
