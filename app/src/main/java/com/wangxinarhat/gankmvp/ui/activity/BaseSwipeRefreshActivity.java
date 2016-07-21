package com.wangxinarhat.gankmvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.base.BasePresenter;
import com.wangxinarhat.gankmvp.ui.view.SwipeRefreshView;

import java.io.File;

import butterknife.BindView;

/**
 * Created by wang on 2016/7/21.
 */
public abstract class BaseSwipeRefreshActivity<P extends BasePresenter> extends BaseActivity<P> implements SwipeRefreshView {

    @BindView(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSwipeLayout();
    }

    private void initSwipeLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (prepareRefresh()) {
                    onRefreshedStarted();
                } else {
                    hideRefresh();
                }
            }
        });
    }

    protected abstract void onRefreshedStarted();


    protected boolean prepareRefresh() {
        return true;
    }

    /**
     * 让子弹飞一会儿
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
        }, 2000);
    }

    @Override
    public void getDataFinish() {
        hideRefresh();
    }

    @Override
    public void showRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    /**
     * check refreshlayout is refreshing
     * @return
     */
    @CheckResult
    protected boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    /*

    void getDataFinish();

    void showEmptyView();

    void showErrorView(Throwable throwable);

    void showRefresh();

    void hideRefresh();
*/
}
