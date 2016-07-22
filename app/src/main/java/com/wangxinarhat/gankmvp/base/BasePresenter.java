/*
 * Copyright 2016, The Android Open Source Project
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

package com.wangxinarhat.gankmvp.base;

import android.app.Activity;

import com.wangxinarhat.gankmvp.api.GankService;
import com.wangxinarhat.gankmvp.api.MainFactory;

public class BasePresenter<GV extends BaseView> {

    protected GV mView;
    /**
     * TODO 这里用是否用Activity待考证
     */
    protected Activity mContext;

    public static final GankService mGankService = MainFactory.getGankServiceInstance();

    public BasePresenter(Activity context, GV view) {
        mContext = context;
        mView = view;
    }
}
