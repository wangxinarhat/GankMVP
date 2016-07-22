package com.wangxinarhat.gankmvp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wangxinarhat.gankmvp.data.entity.Gank;

/**
 * Created by wang on 2016/7/22.
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder {
    public BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindData(Gank gank);
}
