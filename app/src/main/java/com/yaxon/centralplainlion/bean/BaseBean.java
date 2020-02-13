package com.yaxon.centralplainlion.bean;

/**
 * Description:请求数据包装类
 * Created by kimiffy on 2019/3/12.
 */

public class BaseBean<T>{

    /**
     * 自定义错误码
     */
    public int rc;
    /**
     * 消息提示
     */
    public String errMsg;

    /**
     * 泛型实体类
     */
    public T data;
}
