/*
 *
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 * Copyright (C) 2015 GuDong <maoruibin9035@gmail.com>
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.wangxinarhat.gankmvp.utils;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;

import com.wangxinarhat.gankmvp.R;
import com.wangxinarhat.gankmvp.data.entity.Gank;
import com.wangxinarhat.gankmvp.global.BaseApplication;


public class StringStyleUtils {

    public static SpannableString format( String text, int style) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(BaseApplication.getApplication(), style), 0, text.length(), 0);
        return spannableString;
    }

    public static CharSequence getGankInfoSequence(Gank mGank) {
        SpannableStringBuilder builder = new SpannableStringBuilder(mGank.desc).append(
                StringStyleUtils.format( " (via. " + mGank.who + ")", R.style.ViaTextAppearance));
        return builder.subSequence(0, builder.length());
    }
}
