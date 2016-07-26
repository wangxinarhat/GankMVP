package com.wangxinarhat.gankmvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.interfaces.OnHolderClickListener;
import com.wangxinarhat.gankmvp.ui.widget.RatioImageView;
import com.wangxinarhat.gankmvp.utils.DateUtil;
import com.wangxinarhat.gankmvp.utils.GlideUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang on 2016/7/22.
 */
public class HolderGirl extends BaseHolder {
    @Bind(R.id.tv_video_name)
    TextView mTvTime;
    @Bind(R.id.iv_index_photo)
    RatioImageView mImageView;

    private Gank mGank;

    public HolderGirl(View itemView, OnHolderClickListener listener) {
        super(itemView, listener);
        ButterKnife.bind(this, itemView);
        mImageView.setOriginalSize(200, 100);
    }

    @Override
    public void bindData(Gank gank) {
        mGank = gank;
        mTvTime.setText(DateUtil.toDate(gank.publishedAt));

        GlideUtils.loadImage(gank.url, mImageView);

        mTvTime.setText(DateUtil.toDate(gank.publishedAt));
    }

    @Override
    public void onHolderClick(View itemView, int position, int itemViewType) {
        mlistener.onHolderClick(itemView, position, itemViewType, mGank, mImageView, mTvTime);
    }
}
