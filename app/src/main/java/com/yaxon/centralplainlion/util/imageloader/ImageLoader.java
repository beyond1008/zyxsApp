package com.yaxon.centralplainlion.util.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

/**
 * Description:图片加载工具
 * Created by kimiffy on 2019/10/11.
 */

public class ImageLoader {


    /**
     * 常规使用
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadImage(Context context, Object url, ImageView imageView) {
        GlideApp.with(context).load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }


    /**
     * 自定义RequestOptions使用
     *
     * @param context        上下文
     * @param url            图片链接
     * @param requestOptions
     * @param imageView      目标view
     */
    public static void LoadImage(Context context, Object url, ImageView imageView, RequestOptions requestOptions) {
        GlideApp.with(context).load(url)
                .apply(requestOptions)
                .into(imageView);
    }


    /**
     * 需要回调时使用
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 回调
     */
    public static void LoadImage(Context context, Object url, ImageView imageView, RequestListener<Drawable> listener) {
        GlideApp.with(context).load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .listener(listener)
                .into(imageView);
    }

    /**
     * 圆形裁剪
     */
    public static void LoadCircleImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);

    }

    /**
     * 圆角裁剪
     */
    public static void LoadRoundImage(Context context, String url, ImageView imageView, int radius) {
        GlideApp.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)).diskCacheStrategy(DiskCacheStrategy.ALL))//圆角半径
                .into(imageView);
    }

}
