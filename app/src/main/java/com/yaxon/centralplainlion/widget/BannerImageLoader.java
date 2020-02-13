package com.yaxon.centralplainlion.widget;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Description:轮播图图片加载器
 * Created by kimiffy on 2020/1/13.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        com.yaxon.centralplainlion.util.imageloader.ImageLoader.LoadImage(context, path, imageView);
    }
}
