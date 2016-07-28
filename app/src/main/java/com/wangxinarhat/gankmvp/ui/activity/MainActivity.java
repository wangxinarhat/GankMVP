package com.wangxinarhat.gankmvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.interfaces.ItemType;
import com.wangxinarhat.gankmvp.interfaces.OnRecyclerViewItemClickListener;
import com.wangxinarhat.gankmvp.presenter.MainPresenter;
import com.wangxinarhat.gankmvp.ui.adapter.MainAdapter;
import com.wangxinarhat.gankmvp.ui.view.MainView;
import com.wangxinarhat.gankmvp.utils.DateUtil;

import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wang on 2016/7/22.
 */
public class MainActivity extends BaseSwipeRefreshActivity<MainPresenter> implements MainView<Gank>, OnRecyclerViewItemClickListener {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;


    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;


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
        setTitle(getString(R.string.app_name), false);


        initDrawerLayout();

    }

    private void initDrawerLayout() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }
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

    /**
     * pull to refresh
     */
    private void getData() {
        mPresenter.getData(new Date(System.currentTimeMillis()));
    }


    /**
     * get layout id for this activity
     *
     * @return id of layout
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    /**
     * show view for no data
     */
    @Override
    public void showEmptyView() {

    }

    /**
     * show error view
     *
     * @param throwable
     */
    @Override
    public void showErrorView(Throwable throwable) {

    }

    /**
     * init RecyclerView Widget
     */
    private void initRecycleView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainAdapter(this);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);


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
                        (mRecyclerView.getLayoutManager()).smoothScrollToPosition(mRecyclerView, null, 0);
                    }
                })
                .show();
    }


    @Override
    protected int getMenuRes() {
        return R.menu.menu_main;
    }


    @Override
    public void onItemClick(View itemView, int position, int itemViewType, Gank gank, View viewImage, View viewText) {
        ItemType itemTypeEnum = ItemType.valueOf(itemViewType);

        switch (itemTypeEnum) {
            case ITEM_TYPE_GIRL:

                jump2Girl(itemView, gank);
                break;

            case ITEM_TYPE_NORMAL:

                startActivity(ArticleDetailActivity.getIntent(gank.url, gank.desc));
                break;

            case ITEM_TYPE_CATEGOTY:

                break;


        }

    }

    private void jump2Girl(View itemView, Gank gank) {

        // Construct an Intent as normal
        Intent intent = GirlDetailActivity.getIntent(gank.url, DateUtil.toDate(gank.publishedAt));

        // BEGIN_INCLUDE(start_activity)
        /**
         * Now create an {@link android.app.ActivityOptions} instance using the
         * {@link ActivityOptionsCompat#makeSceneTransitionAnimation(Activity, Pair[])} factory
         * method.
         */
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,

                // Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                new Pair<View, String>(itemView.findViewById(R.id.iv_index_photo),
                        GirlDetailActivity.VIEW_NAME_HEADER_IMAGE),
                new Pair<View, String>(itemView.findViewById(R.id.tv_video_name),
                        GirlDetailActivity.VIEW_NAME_HEADER_TITLE));

        // Now we can start the Activity, providing the activity options as a bundle
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        // END_INCLUDE(start_activity)
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    /**
     * setting NavigationItemSelectedListener
     *
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_camera) {
                    // Handle the camera action
                } else if (id == R.id.nav_gallery) {

                } else if (id == R.id.nav_slideshow) {

                } else if (id == R.id.nav_manage) {

                } else if (id == R.id.nav_share) {

                } else if (id == R.id.nav_send) {

                }

                // Close the navigation drawer when an item is selected.
                item.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });

    }

}
