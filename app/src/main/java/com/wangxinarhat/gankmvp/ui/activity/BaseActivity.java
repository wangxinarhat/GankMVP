package com.wangxinarhat.gankmvp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wang on 2016/7/21.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    @BindView(R.id.toolbar)
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

    protected int getMenuRes() {
        return -1;
    }

    private void initToolBar() {
        if (mToolbar == null) {
            throw new NullPointerException("please add a Toolbar in your layout.");
        }
        setSupportActionBar(mToolbar);
    }

    private void checkPresenterIsNull() {
        if (null == mPresenter) {
            throw new IllegalStateException("mPresenter is null!");
        }
    }

    protected abstract void initPresenter();

    protected abstract int getLayout();
}
