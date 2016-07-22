package com.wangxinarhat.gankmvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.data.entity.Gank;
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

    public HolderGirl(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mImageView.setOriginalSize(200,100);
    }

    @Override
    public void bindData(Gank gank) {
        mTvTime.setText(DateUtil.toDate(gank.publishedAt));

        GlideUtils.loadImage(gank.url,mImageView);

        mTvTime.setText(DateUtil.toDate(gank.publishedAt));

    }
}
