package com.wangxinarhat.gankmvp.ui.view;

import com.wangxinarhat.gankmvp.base.BaseView;

/**
 * Created by wang on 2016/7/25.
 */
public interface GrilDetailView extends BaseView {
    void saveSuccess(String message);
    void showFailInfo(String error);
}
