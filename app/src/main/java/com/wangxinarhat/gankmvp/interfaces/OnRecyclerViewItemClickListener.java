package com.wangxinarhat.gankmvp.interfaces;

import android.view.View;

import com.wangxinarhat.gankmvp.data.entity.Gank;

/**
 * Created by wang on 2016/7/25.
 */
public interface OnRecyclerViewItemClickListener {


    void onItemClick(View itemView, int position, int itemViewType, Gank gank, View viewImage, View viewText);


}
