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

package com.wangxinarhat.gankmvp.data.entity;


import com.google.gson.annotations.SerializedName;
import com.wangxinarhat.gankmvp.api.GankCategory;

import java.io.Serializable;
import java.util.Date;

/**
 * model class for a Gank.
 *
 * @author wangxinarhat
 *         creat at 2016/7/25
 */
public class Gank extends BaseEntity implements Cloneable, Serializable {
    @SerializedName("_id")
    public String id;
    public boolean used;
    public String type;
    public String url;
    public String who;
    public String desc;
    public Date updatedAt;
    public Date createdAt;
    public Date publishedAt;

    /**
     * this item is header type of gank or not,if true, this item will show category name
     */
    public boolean isHeader;

    public boolean isWelfare() {
        return type.equals(GankCategory.福利.name());
    }

    @Override
    public Gank clone() {
        Gank gank = null;
        try {
            gank = (Gank) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return gank;
    }
}
