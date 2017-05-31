package com.androiddev.zf.devframe.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * 图片加载辅助类
 * glide
 */

public class ImageUtils {
    /**
     * @param url       图片地址或者path
     * @param imageView
     */
    public static void display(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).centerCrop().into(imageView);
    }

    /**
     * @param res       本地图片资源地址
     * @param imageView
     */
    public static void display(int res, ImageView imageView) {
        Glide.with(imageView.getContext()).load(res).centerCrop().into(imageView);
    }

    /**
     * 带占位图和错误图片的加载本地图片资源
     *
     * @param res
     * @param imageView
     * @param placeImg
     * @param errorImg
     */
    public static void display(int res, ImageView imageView, int placeImg, int errorImg) {
        Glide.with(imageView.getContext())
                .load(res)
                .placeholder(placeImg)
                .error(errorImg)
                .centerCrop()
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 带占位图和错误图片的加载网络图片
     *
     * @param imageView
     * @param placeImg
     * @param errorImg
     */
    public static void display(String url, ImageView imageView, int placeImg, int errorImg) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(placeImg)
                .error(errorImg)
                .centerCrop()
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 带占位图、错误图片和图片转换的加载网络图片
     *
     * @param imageView
     * @param placeImg
     * @param errorImg
     */
    public static void displayWithTransForm(String url, ImageView imageView, int placeImg
            , int errorImg, BitmapTransformation transformation) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(placeImg)
                .error(errorImg)
                .centerCrop()
                .dontAnimate()
                .bitmapTransform(transformation)
                .into(imageView);
    }

    /**
     * 带占位图、错误图片和图片转换的加载本地图片资源
     *
     * @param imageView
     * @param placeImg
     * @param errorImg
     */
    public static void displayWithTransForm(int res, ImageView imageView, int placeImg
            , int errorImg, BitmapTransformation transformation) {
        Glide.with(imageView.getContext())
                .load(res)
                .placeholder(placeImg)
                .error(errorImg)
                .centerCrop()
                .dontAnimate()
                .bitmapTransform(transformation)
                .into(imageView);
    }

    public static void displayWithTransForm(String url, ImageView imageView, BitmapTransformation transformation) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .bitmapTransform(transformation)
                .into(imageView);
    }

    public static void displayWithTransForm(int res, ImageView imageView, BitmapTransformation transformation) {
        Glide.with(imageView.getContext())
                .load(res)
                .centerCrop()
                .bitmapTransform(transformation)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param imageView
     */
    public static void displayCircle(String url, final ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
//                imageView.setImageDrawable(circularBitmapDrawable);
                setDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * @param url       地址
     * @param imageView
     * @param radio     圆角弧度
     */
    public static void displayFillet(String url, final ImageView imageView, final float... radio) {
        Glide.with(imageView.getContext()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable filletBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                filletBitmapDrawable.setCornerRadius(null != radio && radio.length != 0 ? radio[0] : 15);
                setDrawable(filletBitmapDrawable);
            }

        });
    }

    public static void getNetBitmap(Context context, String url, final LoadSuccess handler) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                handler.result(resource);
            }
        });
    }

    interface LoadSuccess {
        void result(Bitmap bitmap);
    }
}
