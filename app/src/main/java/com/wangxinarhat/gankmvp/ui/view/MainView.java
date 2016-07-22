package com.wangxinarhat.gankmvp.ui.view;

import com.wangxinarhat.gankmvp.data.entity.Soul;

import java.util.List;

/**
 * Created by wang on 2016/7/21.
 */
public interface MainView<T extends Soul> extends SwipeRefreshView {

    void fillData(List<T> data);


    void appendMoreData2View(List<T> data);


    void hasNoMoreData();

    void showChangeLogInfo(String assetFileName);


}
