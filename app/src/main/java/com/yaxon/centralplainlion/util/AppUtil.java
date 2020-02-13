package com.yaxon.centralplainlion.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.yaxon.centralplainlion.App;
import com.yaxon.centralplainlion.BuildConfig;


/**
 * Description:app 工具类
 * Created by kimiffy on 2019/3/11.
 */

public class AppUtil {

    private static Boolean isDebug = null;

    /**
     * 是否是debug模式
     *
     * @return
     */
    public static boolean isDebug() {
        return isDebug == null ? false : isDebug;
    }


    /**
     * 获取当前应用的包名
     */
    public static String getPackageName() {return BuildConfig.APPLICATION_ID;}

    /**
     * 获取当前应用的版本名
     */
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取当前应用的版本码
     */
    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * 获取当前应用的渠道名
     */
    public static String getProductFlavors() {
        return BuildConfig.FLAVOR;
    }

    /**
     * 同步是否是debug模式,需要在application中初始化
     * 适合多Module下使用
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }


    /**
     * 获取应用程序名称
     * @param context
     * @return
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo( context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
