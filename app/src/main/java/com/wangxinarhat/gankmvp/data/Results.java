package com.wangxinarhat.gankmvp.data;

import com.google.gson.annotations.SerializedName;
import com.wangxinarhat.gankmvp.data.entity.Gank;

import java.util.List;

/**
 * data list
 *
 * @author wangxinarhat
 *         creat at 2016/7/25
 */
public class Results {
    @SerializedName("Android")
    public List<Gank> androidList;
    @SerializedName("休息视频")
    public List<Gank> restList;
    @SerializedName("iOS")
    public List<Gank> iOSList;
    @SerializedName("福利")
    public List<Gank> welfareList;
    @SerializedName("拓展资源")
    public List<Gank> expandList;
    @SerializedName("瞎推荐")
    public List<Gank> recommendList;
    @SerializedName("App")
    public List<Gank> appList;
}
