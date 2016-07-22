package com.wangxinarhat.gankmvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.utils.StringStyleUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang on 2016/7/22.
 */
public class HolderNormal extends BaseHolder {
    @Bind(R.id.tv_gank_title)
    TextView mTvTitle;

    public HolderNormal(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Gank gank) {
        mTvTitle.setText(StringStyleUtils.getGankInfoSequence( gank));
    }
}
