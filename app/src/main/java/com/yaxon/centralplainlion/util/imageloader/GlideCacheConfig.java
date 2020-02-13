package com.yaxon.centralplainlion.util.imageloader;

/**
 * Description:Glide缓存配置文件
 * Created by kimiffy on 2019/10/11.
 */

class GlideCacheConfig {

    // 图片缓存最大容量，250M，根据自己的需求进行修改
    static final int GLIDE_CACHE_SIZE = 250 * 1000 * 1000;

    // 图片缓存子目录
    static final String GLIDE_CACHE_DIR = "wl_image_cache";
}
