/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wangxinarhat.gankmvp.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.global.BaseApplication;
import com.wangxinarhat.gankmvp.presenter.GirlDetailPresenter;
import com.wangxinarhat.gankmvp.ui.view.GrilDetailView;
import com.wangxinarhat.gankmvp.utils.AndroidUtils;

import butterknife.Bind;

/**
 * Our secondary Activity which is launched from {@link MainActivity}. Has a simple detail UI
 * which has a large banner image, title and body text.
 */
public class GirlDetailActivity extends BaseActivity<GirlDetailPresenter> implements GrilDetailView {


    private static final String EXTRA_BUNDLE_URL = "BUNDLE_URL";
    private static final String EXTRA_BUNDLE_TITLE = "BUNDLE_TITLE";


    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

    // View name of the header title. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

    @Bind(R.id.activity_girl_detail_photo)
    ImageView mPhoto;
    private String mUrl;





    public static Intent getIntent(String url, String title) {
        Intent intent = new Intent(BaseApplication.getApplication(), GirlDetailActivity.class);
        intent.putExtra(EXTRA_BUNDLE_URL, url);
        intent.putExtra(EXTRA_BUNDLE_TITLE, title);

        return intent;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // BEGIN_INCLUDE(detail_set_view_name)
        /**
         * Set the name of the view's which will be transition to, using the static values above.
         * This could be done in the layout XML, but exposing it via static variables allows easy
         * querying from other Activities
         */
        ViewCompat.setTransitionName(mPhoto, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(AndroidUtils.getTitleViewInToolbar(mToolbar), VIEW_NAME_HEADER_TITLE);
        // END_INCLUDE(detail_set_view_name)


        loadItem();
    }

    @Override
    protected void initPresenter() {

        mPresenter = new GirlDetailPresenter(this, this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_girl_detail;
    }

    private void loadItem() {
        // Set the title TextView to the item's name and author

        mUrl = getIntent().getStringExtra(EXTRA_BUNDLE_URL);
        setTitle(getIntent().getStringExtra(EXTRA_BUNDLE_TITLE), true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && addTransitionListener()) {
            // If we're running on Lollipop and we have added a listener to the shared element
            // transition, load the thumbnail. The listener will load the full-size image when
            // the transition is complete.
            loadThumbnail();
        } else {
            // If all other cases we should just load the full-size image now
            loadFullSizeImage();
        }
    }

    /**
     * Load the item's thumbnail image into our {@link ImageView}.
     */
    private void loadThumbnail() {
        Picasso.with(mPhoto.getContext())
                .load(mUrl)
                .noFade()
                .into(mPhoto);
    }

    /**
     * Load the item's full-size image into our {@link ImageView}.
     */
    private void loadFullSizeImage() {
        Picasso.with(mPhoto.getContext())
                .load(mUrl)
                .noFade()
                .noPlaceholder()
                .into(mPhoto);
    }

    /**
     * Try and add a {@link Transition.TransitionListener} to the entering shared element
     * {@link Transition}. We do this so that we can load the full-size image after the transition
     * has completed.
     *
     * @return true if we were successful in adding a listener to the enter transition
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        final Transition transition = getWindow().getSharedElementEnterTransition();

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage();

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
            return true;
        }

        // If we reach here then we have not added a listener
        return false;
    }

    @Override
    public void saveSuccess(String message) {

    }

    @Override
    public void showFailInfo(String error) {

    }
}
