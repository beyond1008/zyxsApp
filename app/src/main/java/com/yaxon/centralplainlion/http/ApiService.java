package com.yaxon.centralplainlion.http;


import com.yaxon.centralplainlion.bean.BaseBean;
import com.yaxon.centralplainlion.util.AppUtil;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Description: api 接口
 * Created by kimiffy on 2019/2/25.
 */

public interface ApiService {

    /**
     * 正式服务器地址
     */
    String SERVER_ADDRESS_RELEASE = "http://117.25.162.67:15751/";

    /**
     * 测试服务器地址
     */
    String SERVER_ADDRESS_DEBUG = "http://117.25.162.67:15751/";

    /**
     * 服务器地址
     */
    String SERVER_ADDRESS = AppUtil.isDebug() ? SERVER_ADDRESS_DEBUG : SERVER_ADDRESS_RELEASE;


    /**
     * 注册
     */
    @POST("register.do")
    Observable<BaseBean> register(@Body Map<String, String> map);

}
