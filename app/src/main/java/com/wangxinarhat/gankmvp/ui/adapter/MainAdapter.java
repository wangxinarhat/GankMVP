package com.wangxinarhat.gankmvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.api.GankCategory;
import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.interfaces.ItemType;
import com.wangxinarhat.gankmvp.interfaces.OnHolderClickListener;
import com.wangxinarhat.gankmvp.interfaces.OnRecyclerViewItemClickListener;
import com.wangxinarhat.gankmvp.ui.activity.BaseActivity;
import com.wangxinarhat.gankmvp.ui.holder.BaseHolder;
import com.wangxinarhat.gankmvp.ui.holder.HolderCategory;
import com.wangxinarhat.gankmvp.ui.holder.HolderGirl;
import com.wangxinarhat.gankmvp.ui.holder.HolderNormal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wang on 2016/7/22.
 */
public class MainAdapter extends RecyclerView.Adapter<BaseHolder> implements OnHolderClickListener {
    /**
     * The listener that receives notifications when an item is clicked.
     */
    OnRecyclerViewItemClickListener mOnItemClickListener;
    private List<Gank> mGankList;
    private BaseActivity mActivity;

    public MainAdapter(BaseActivity activity) {
        mGankList = new ArrayList<>();
        mGankList.add(getDefGankGirl());
        mActivity = activity;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        BaseHolder holder;
        if (viewType == ItemType.ITEM_TYPE_GIRL.ordinal()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_girl, null);
            holder = new HolderGirl(view, this);

        } else if (viewType == ItemType.ITEM_TYPE_CATEGOTY.ordinal()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null);
            holder = new HolderCategory(view, this);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal, null);
            holder = new HolderNormal(view, this);
        }

        return holder;
    }


    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.bindData(mGankList.get(position));
    }

    @Override
    public int getItemCount() {
        return null == mGankList ? 0 : mGankList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Gank gank = mGankList.get(position);
        if (gank.isWelfare()) {
            return ItemType.ITEM_TYPE_GIRL.ordinal();
        } else if (gank.isHeader) {
            return ItemType.ITEM_TYPE_CATEGOTY.ordinal();
        } else {
            return ItemType.ITEM_TYPE_NORMAL.ordinal();
        }
    }


    /**
     * before add data , it will remove history data
     *
     * @param data
     */
    public void updateWithClear(List<Gank> data) {
        mGankList.clear();
        update(data);
    }

    /**
     * add data append to history data*
     *
     * @param data new data
     */
    public void update(List<Gank> data) {
        formatGankData(data);
        notifyDataSetChanged();
    }

    /**
     * filter list and add category entity into list
     *
     * @param data source data
     */
    private void formatGankData(List<Gank> data) {
        //Insert headers into list of items.
        String lastHeader = "";
        for (int i = 0; i < data.size(); i++) {
            Gank gank = data.get(i);
            String header = gank.type;
            if (!gank.isWelfare() && !TextUtils.equals(lastHeader, header)) {
                // Insert new header view.
                Gank gankHeader = gank.clone();
                lastHeader = header;
                gankHeader.isHeader = true;
                mGankList.add(gankHeader);
            }
            gank.isHeader = false;
            mGankList.add(gank);
        }
    }


    /**
     * get a init Gank entity
     *
     * @return gank entity
     */
    private Gank getDefGankGirl() {
        Gank gank = new Gank();
        gank.publishedAt = new Date(System.currentTimeMillis());
        gank.url = "empty";
        gank.type = GankCategory.福利.name();
        return gank;
    }


    public void setOnItemClickListener(@Nullable OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }



    @Override
    public void onHolderClick(View itemView, int position, int itemViewType, Gank gank, View viewImage, View viewText) {
        mOnItemClickListener.onItemClick(itemView, position, itemViewType, gank, viewImage, viewText);
    }
}
