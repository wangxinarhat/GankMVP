package com.wangxinarhat.gankmvp.ui.view;

/**
 * Created by wang on 2016/7/21.
 */
public interface SwipeRefreshView {

    void getDataFinish();

    void showEmptyView();

    void showErrorView(Throwable throwable);

    void showRefresh();

    void hideRefresh();
}
