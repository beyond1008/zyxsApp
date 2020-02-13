package com.yaxon.centralplainlion.util.imageloader;

import android.os.Looper;


import com.yaxon.centralplainlion.App;
import com.yaxon.centralplainlion.util.AppUtil;

import java.io.File;
import java.math.BigDecimal;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:Glide缓存工具
 * Created by kimiffy on 2019/10/11.
 */

public class GlideCacheUtil {

    private static GlideCacheUtil instance;

    public static GlideCacheUtil getInstance() {
        if (null == instance) {
            instance = new GlideCacheUtil();
        }
        return instance;
    }

    // 获取Glide磁盘缓存大小
    public String getCacheSize() {
        try {
            return getFormatSize(getFolderSize(new File(App.getContext().getCacheDir() + "/" + GlideCacheConfig.GLIDE_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
            return "0MB";
        }
    }

    // 清除图片磁盘缓存
    public boolean clearDiskCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        GlideApp.get(App.getContext()).clearDiskCache();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            } else {
                GlideApp.get(App.getContext()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public boolean clearMemoryCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                GlideApp.get(App.getContext()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 清除Glide磁盘缓存，并删除缓存文件夹
    public boolean cleanCacheDisk() {
        return deleteFolderFile(App.getContext().getCacheDir() + "/" + GlideCacheConfig.GLIDE_CACHE_DIR, true);
    }


    // 获取指定文件夹内所有文件大小的和
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    // 格式化单位
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // 按目录删除文件夹文件方法
    private boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (File file1 : files) {
                    deleteFolderFile(file1.getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    if (file.listFiles().length == 0) {
                        file.delete();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
