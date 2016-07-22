package com.wangxinarhat.gankmvp.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Glide 工具类
 * Created by sacowiw on 16/1/28.
 */
public class GlideUtils {
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        try {
            Glide.with(context)
                    .load(imageUrl)
                    .crossFade()
                    .dontAnimate()
//                    .placeholder(R.drawable.image_default)
                    .into(imageView);
        } catch (Exception e) {

        }


    }

    public static void loadImage(String imageUrl, ImageView imageView) {
        try {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .crossFade()
                    .dontAnimate()
//                    .placeholder(R.drawable.image_default)
                    .into(imageView);
        } catch (Exception e) {

        }

    }

    public static void loadAvatar(Context context, String imageUrl, ImageView imageView) {
        try {
            Glide.with(context)
                    .load(imageUrl)
                    .dontAnimate()
//                    .placeholder(R.drawable.fragment_mine_avatar)
                    .into(imageView);
        } catch (Exception e) {


        }


    }

    public static void loadAvatar(String imageUrl, ImageView imageView) {
        try {

            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .dontAnimate()
//                    .placeholder(R.drawable.fragment_mine_avatar)
                    .into(imageView);
        } catch (Exception e) {

        }

    }

    public static void loadImage(@IdRes int resId, ImageView imageView) {
        try {
            Glide.with(imageView.getContext())
                    .load(resId)
                    .dontAnimate()
//                    .placeholder(R.drawable.fragment_mine_avatar)
                    .into(imageView);
        } catch (Exception e) {

        }


    }

}
