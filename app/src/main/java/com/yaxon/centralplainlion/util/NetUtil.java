package com.yaxon.centralplainlion.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;

import com.yaxon.centralplainlion.App;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;


/**
 * Description: 网络判断工具类
 * Created by kimiffy on 2019/2/25.
 */

public class NetUtil {
    /**
     * 判断是否有网络连接
     *
     * @return
     */

    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = null;
        if (mConnectivityManager != null) {
            mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
        }
        return mNetworkInfo != null && mNetworkInfo.isConnected();

    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = null;
            if (mConnectivityManager != null) {
                mWiFiNetworkInfo = mConnectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            }
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isConnected();
            }
        }
        return false;
    }

    /**
     * 手机信号是否可连接
     */
    public static boolean isMobileAva() {

        boolean hasMobileCon = false;

        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfos = new NetworkInfo[0];
        if (cm != null) {
            netInfos = cm.getAllNetworkInfo();
        }
        for (NetworkInfo net : netInfos) {
            String type = net.getTypeName();
            if (type.equalsIgnoreCase("MOBILE")) {
                if (net.isConnected()) {
                    hasMobileCon = true;
                }
            }
        }
        return hasMobileCon;
    }

    /**
     * wifi是否可连接
     * @return
     */
    public static boolean isWiFiActive() {
        ConnectivityManager connectivity = (ConnectivityManager) App.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getTypeName().equals("WIFI") && anInfo.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 获取当前网络连接的类型信息
     *
     * @return
     */
    public static int getConnectedType() {
        if (App.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = null;
            if (mConnectivityManager != null) {
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
