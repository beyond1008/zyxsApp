package com.yaxon.centralplainlion;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.yaxon.centralplainlion.util.AppUtil;

/**
 * Description: application
 * Created by kimiffy on 2020/1/8.
 */
public class App extends Application {

    private static App myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        AppUtil.syncIsDebug(getApplicationContext());
        //初始化log
        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder()
                .tag("centralplainlion")
                .methodCount(1).showThreadInfo(true)
                .build()));
    }

    /**
     * 获取Application
     * @return
     */
    public static App getInstance() {
        return myApplication;
    }

    /**
     * 获取ApplicationContext
     * @return
     */
    public static Context getContext() {
        return myApplication.getApplicationContext();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
