package com.darsh.multipleimageselect.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.darsh.multipleimageselect.R;

/**
 * 日期：2017.01.03
 * <p>
 * 作者：xudiwei
 * <p>
 * 描述：基于glide的图片加载工具类
 */
public class ImageLoader {
    //
    private static final int DEFAULT_IMAGE = R.color.colorSettingItemRipple;
    private static final int ERROR_IMAGE = R.mipmap.ic_avatar_default;

    private static final int DEFAULT_AVATAR = R.mipmap.ic_avatar_default;
    private static final int ERROR_AVATAR = R.mipmap.ic_avatar_default;


    public static void loadToUrl(Context context, ImageView imageView, String url) {
        Glide.with(context).
                load(url)//图片的url
//                .centerCrop()//图片的显示方式。这里在是居中裁剪
                //.placeholder(DEFAULT_IMAGE) //默认的占位图片
                //.error(ERROR_IMAGE) //加载失败的图片
//                .crossFade(1000)//图片的加载效果，这里是淡入淡出，时间为1000毫秒
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略
                .into(imageView);//加载
    }

    public static void loadToUrl(Context context, ImageView imageView, String url, RequestListener listener) {
        Glide.with(context).
                load(url)//图片的url
                //.centerCrop()//图片的显示方式。这里在是居中裁剪
                //.placeholder(DEFAULT_IMAGE) //默认的占位图片
                //.error(ERROR_IMAGE) //加载失败的图片
//                .crossFade(1000)//图片的加载效果，这里是淡入淡出，时间为1000毫秒
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略
                .listener(listener)
                .into(imageView);//加载
    }

    public static void loadAvatar(Context context, ImageView imageView, String url) {
        Glide.with(context).
                load(url)//图片的url
                //.centerCrop()//图片的显示方式。这里在是居中裁剪
                //.placeholder(DEFAULT_AVATAR) //默认的占位图片
                //.error(ERROR_AVATAR) //加载失败的图片
                //.crossFade(1000)//图片的加载效果，这里是淡入淡出，时间为1000毫秒
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略
                .into(imageView);//加载
    }

    /**
     * 加载小图片
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadAvatarIcon(Context context, ImageView imageView, String url) {
        Glide.with(context).
                load(url)//图片的url
                //.centerCrop()//图片的显示方式。这里在是居中裁剪
                //.placeholder(DEFAULT_AVATAR) //默认的占位图片
                //.error(ERROR_AVATAR) //加载失败的图片
               // .override(300,300)  //指定图片大小
                //.crossFade(100)//图片的加载效果，这里是淡入淡出，时间为1000毫秒
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略
                .into(imageView);//加载
    }


    /**
     * 去除centerCrop
     *
     * @param context
     * @param imageView
     * @param url
     * @param listener
     */
    public static void loadAvatar(Context context, ImageView imageView, String url, RequestListener listener) {
        Glide.with(context).
                load(url)//图片的url
//                .centerCrop()//图片的显示方式。这里在是居中裁剪
                /*.placeholder(DEFAULT_IMAGE) //默认的占位图片
                .error(ERROR_IMAGE) //加载失败的图片
                .crossFade(1000)//图片的加载效果，这里是淡入淡出，时间为1000毫秒
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略
                .listener(listener)*/
                .into(imageView);//加载
    }

    /**
     * 通过回调获取Bitmap
     *
     * @param context
     * @param url
     * @param target
     * @return
     */
    public static void getBitmap(Context context, String url, SimpleTarget<Bitmap> target) {
        //Glide.with(context)
                //.load(url)
                //.asBitmap()
                //.into(target);

    }

}
