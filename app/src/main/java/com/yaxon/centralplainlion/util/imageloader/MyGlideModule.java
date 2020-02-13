package com.yaxon.centralplainlion.util.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Description:Glide
 * Created by kimiffy on 2019/10/11.
 */
@GlideModule
public final class MyGlideModule extends AppGlideModule {


    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //自定义缓存目录
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,
                GlideCacheConfig.GLIDE_CACHE_DIR,
                GlideCacheConfig.GLIDE_CACHE_SIZE));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);

    }


}
