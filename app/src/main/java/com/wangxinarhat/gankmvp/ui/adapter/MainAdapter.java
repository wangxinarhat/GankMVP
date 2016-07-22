package com.wangxinarhat.gankmvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.api.GankCategory;
import com.wangxinarhat.gankmvp.data.entity.Gank;
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
public class MainAdapter extends RecyclerView.Adapter<BaseHolder> {

    private List<Gank> mGankList;

    public MainAdapter() {
        mGankList = new ArrayList<>();
        mGankList.add(getDefGankGirl());

    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ItemType.ITEM_TYPE_GIRL.ordinal()) {
            return new HolderGirl(LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_item_girl, null));
        }else if(viewType == ItemType.ITEM_TYPE_CATEGOTY.ordinal()){
            return new HolderCategory(LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_item_category, null));
        } else {
            return new HolderNormal(LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_item_normal, null));
        }
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.bindData(mGankList.get(position));
    }

    @Override
    public int getItemCount() {
        return null==mGankList?0:mGankList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Gank gank = mGankList.get(position);
        if (gank.is妹子()) {
            return ItemType.ITEM_TYPE_GIRL.ordinal();
        } else if (gank.isHeader) {
            return ItemType.ITEM_TYPE_CATEGOTY.ordinal();
        } else {
            return ItemType.ITEM_TYPE_NORMAL.ordinal();
        }
    }

    /**
     * the type of RecycleView item
     */
    private enum ItemType {
        ITEM_TYPE_GIRL,
        ITEM_TYPE_NORMAL,
        ITEM_TYPE_CATEGOTY;
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
            if (!gank.is妹子() && !TextUtils.equals(lastHeader, header)) {
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

}
