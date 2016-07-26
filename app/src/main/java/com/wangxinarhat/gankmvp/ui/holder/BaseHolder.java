package com.wangxinarhat.gankmvp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.interfaces.OnHolderClickListener;

/**
 * Created by wang on 2016/7/22.
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected OnHolderClickListener mlistener;

    public BaseHolder(View itemView, OnHolderClickListener listener) {
        super(itemView);
        mlistener = listener;
        itemView.setOnClickListener(this);
    }

    /**
     * bind data to view
     *
     * @param gank item data
     */
    public void bindData(Gank gank) {
    }

    @Override
    public void onClick(View itemView) {
        onHolderClick(itemView, getLayoutPosition(), getItemViewType());
    }

    public void onHolderClick(View itemView, int position, int itemViewType) {

    }
}
