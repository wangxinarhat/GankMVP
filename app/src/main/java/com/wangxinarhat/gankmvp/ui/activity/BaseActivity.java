package com.wangxinarhat.gankmvp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.base.BasePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang on 2016/7/21.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initPresenter();
        checkPresenterIsNull();
        initToolBar();
    }

    public void setTitle(String strTitle, boolean showHome) {
        setTitle(strTitle);
        getSupportActionBar().setDisplayShowHomeEnabled(showHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuRes() < 0) {
            return false;
        } else {
            getMenuInflater().inflate(getMenuRes(), menu);
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * get menu res, for extension
     *
     * @return
     */
    protected int getMenuRes() {
        return -1;
    }

    /**
     * init presenter
     */
    protected abstract void initPresenter();

    /**
     * get layout id
     *
     * @return layout id for activity
     */
    protected abstract int getLayout();

    /**
     * init toolbar
     */
    private void initToolBar() {
        if (mToolbar == null) {
            throw new NullPointerException("please add a Toolbar in your layout.");
        }
        setSupportActionBar(mToolbar);
    }

    /**
     * check presenter
     */
    private void checkPresenterIsNull() {
        if (null == mPresenter) {
            throw new IllegalStateException("mPresenter is null!");
        }
    }

}
