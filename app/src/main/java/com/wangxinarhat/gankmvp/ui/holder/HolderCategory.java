package com.wangxinarhat.gankmvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.data.entity.Gank;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang on 2016/7/22.
 */
public class HolderCategory extends BaseHolder {
    @Bind(R.id.tv_category)
    TextView mTvCategory;
    public HolderCategory(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Gank gank) {

        mTvCategory.setText(gank.type);
    }
}
