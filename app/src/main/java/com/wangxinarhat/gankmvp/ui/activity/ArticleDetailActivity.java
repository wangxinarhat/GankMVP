package com.wangxinarhat.gankmvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.global.BaseApplication;
import com.wangxinarhat.gankmvp.presenter.ArticlePresenter;
import com.wangxinarhat.gankmvp.ui.view.ArticleView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang on 2016/7/28.
 */
public class ArticleDetailActivity extends BaseSwipeRefreshActivity<ArticlePresenter> implements ArticleView {

    private static final String EXTRA_URL = "URL";
    private static final String EXTRA_TITLE = "TITLE";

    @Bind(R.id.activity_article_detail_webview)
    WebView mWebView;

    public static Intent getIntent( String url, String title) {
        Intent intent = new Intent(BaseApplication.getApplication(), ArticleDetailActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(EXTRA_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);

        if(!TextUtils.isEmpty(title)){
            setTitle(title,true);
        }
        mPresenter.setUpWebView(mWebView);
        mPresenter.loadUrl(mWebView,url);

    }

    @Override
    protected void onRefreshStarted() {
        refresh();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new ArticlePresenter(this, this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_article_detail;
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(Throwable throwable) {

    }

    @Override
    public void showLoadErrorMessage(String message) {
        Snackbar.make(mWebView,message,Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) mWebView.destroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onPause() {
        if (mWebView != null) mWebView.onPause();
        super.onPause();
    }



    private void refresh(){
        mWebView.reload();
    }
}
