package com.wangxinarhat.gankmvp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.interfaces.OnRecyclerViewItemClickListener;

/**
 * Created by wang on 2016/7/22.
 */
public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnRecyclerViewItemClickListener mlistener;
    public BaseHolder(View itemView , OnRecyclerViewItemClickListener listener) {
        super(itemView);
        mlistener = listener;
        itemView.setOnClickListener(this);
    }

    /**
     * bind data to view
     *
     * @param gank item data
     */
    public  void bindData(Gank gank){}

    @Override
    public void onClick(View v) {
        mlistener.onItemClick(v,getLayoutPosition());
    }
}
