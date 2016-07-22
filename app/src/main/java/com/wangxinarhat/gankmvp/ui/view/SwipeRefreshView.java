package com.wangxinarhat.gankmvp.ui.view;

import com.wangxinarhat.gankmvp.base.BaseView;

/**
 * Created by wang on 2016/7/21.
 */
public interface SwipeRefreshView extends BaseView{

    void getDataFinish();

    void showEmptyView();

    void showErrorView(Throwable throwable);

    void showRefresh();

    void hideRefresh();
}
