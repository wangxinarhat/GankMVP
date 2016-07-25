package com.wangxinarhat.gankmvp.ui.view;

import com.wangxinarhat.gankmvp.data.entity.BaseEntity;

import java.util.List;

/**
 * Created by wang on 2016/7/21.
 */
public interface MainView<T extends BaseEntity> extends SwipeRefreshView {

    /**
     * fill data, apply to refresh
     *
     * @param data
     */
    void fillData(List<T> data);


    /**
     * append data, apply to load more
     *
     * @param data
     */
    void appendMoreData2View(List<T> data);

    /**
     * If there is more data
     */
    void hasNoMoreData();

}
