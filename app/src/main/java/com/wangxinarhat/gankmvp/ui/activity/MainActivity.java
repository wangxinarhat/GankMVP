package com.wangxinarhat.gankmvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.presenter.MainPresenter;
import com.wangxinarhat.gankmvp.ui.adapter.MainAdapter;
import com.wangxinarhat.gankmvp.ui.view.MainView;

import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wang on 2016/7/22.
 */
public class MainActivity extends BaseSwipeRefreshActivity<MainPresenter>implements MainView<Gank> {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    /**
     * the flag of has more data or not
     */
    private boolean mHasMoreData = true;

    @Override
    protected void onRefreshStarted() {
        getData();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecycleView();
        setTitle(getString(R.string.app_name),false);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // make swipeRefreshLayout visible manually
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showRefresh();
            }
        }, 568);
        getData();
    }
    private void getData() {
        mPresenter.getData(new Date(System.currentTimeMillis()));
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(Throwable throwable) {

    }

    private void initRecycleView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainAdapter();

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 4;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    showRefresh();
                    mPresenter.getDataMore();
                } else if (!mHasMoreData) {
                    hasNoMoreData();
                }
            }
        });
    }


    @Override
    public void fillData(List<Gank> data) {
        mAdapter.updateWithClear(data);
    }

    @Override
    public void appendMoreData2View(List<Gank> data) {
        mAdapter.update(data);
    }

    @Override
    public void hasNoMoreData() {
        mHasMoreData = false;
        Snackbar.make(mRecyclerView, "没有更多数据咯", Snackbar.LENGTH_LONG)
                .setAction("发回顶部", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (mRecyclerView.getLayoutManager()).smoothScrollToPosition(mRecyclerView,null,0);
                    }
                })
                .show();
    }

    @Override
    public void showChangeLogInfo(String assetFileName) {

    }

    @Override
    protected int getMenuRes() {
        return  R.menu.menu_main;
    }
}
