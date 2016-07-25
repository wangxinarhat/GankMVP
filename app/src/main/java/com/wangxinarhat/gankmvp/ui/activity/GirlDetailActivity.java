package com.wangxinarhat.gankmvp.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.global.BaseApplication;
import com.wangxinarhat.gankmvp.presenter.GirlDetailPresenter;
import com.wangxinarhat.gankmvp.ui.view.GrilDetailView;
import com.wangxinarhat.gankmvp.utils.AndroidUtils;

import butterknife.Bind;

/**
 * Created by wang on 2016/7/25.
 */
public class GirlDetailActivity extends BaseActivity<GirlDetailPresenter> implements GrilDetailView {


    private static final String EXTRA_BUNDLE_URL = "BUNDLE_URL";
    private static final String EXTRA_BUNDLE_TITLE = "BUNDLE_TITLE";

    private static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    private static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";


    @Bind(R.id.activity_girl_detail_photo)
    ImageView mPhoto;
    private String mUrl;

    public static void gotoActivity(BaseActivity activity, String url, String title, final View viewImage, final View viewText) {
        Intent intent = new Intent(BaseApplication.getApplication(), GirlDetailActivity.class);
        intent.putExtra(EXTRA_BUNDLE_URL, url);
        intent.putExtra(EXTRA_BUNDLE_TITLE, title);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, new Pair<View, String>(viewImage, VIEW_NAME_HEADER_IMAGE), new Pair<View, String>(viewText, VIEW_NAME_HEADER_TITLE));


        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mUrl = getIntent().getStringExtra(EXTRA_BUNDLE_URL);
        setTitle(getIntent().getStringExtra(EXTRA_BUNDLE_TITLE), true);

        ViewCompat.setTransitionName(mPhoto, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(AndroidUtils.getTitleViewInToolbar(mToolbar), VIEW_NAME_HEADER_TITLE);
    }

    private void loadItem() {
        if (AndroidUtils.isAndroidL() && addTransitionListener()) {
            loadThumbnail();
        } else {
            loadFullSizeImage();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        final Transition transition = getWindow().getSharedElementEnterTransition();
        if (null != transition) {
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    loadFullSizeImage();
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
            return true;
        }
        return false;
    }

    /**
     * Load the item's thumbnail image into our {@link ImageView}.
     */
    private void loadThumbnail() {
        Picasso.with(this)
                .load(mUrl)
                .noFade()
                .into(mPhoto);
    }

    private void loadFullSizeImage() {
        Picasso.with(this)
                .load(mUrl)
                .noFade()
                .noPlaceholder()
                .into(mPhoto);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GirlDetailPresenter(this, this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_girl_detail;
    }

    @Override
    public void saveSuccess(String message) {

    }

    @Override
    public void showFailInfo(String error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelRequest(mPhoto);
    }

}
