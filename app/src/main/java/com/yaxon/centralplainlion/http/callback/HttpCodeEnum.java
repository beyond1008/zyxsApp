package com.yaxon.centralplainlion.http.callback;

/**
 * Description:请求数据 响应码枚举
 * Created by kimiffy on 2019/3/12.
 */

public enum HttpCodeEnum {

    RESULT_ERROR(0, "请求失败"),
    RESULT_OK(1, "请求成功");
    // TODO: 2019/9/21 根据服务端 设置更多响应码
    private String tipMsg;
    private int statusCode;

    HttpCodeEnum(int statusCode, String tipMsg) {
        this.tipMsg = tipMsg;
        this.statusCode = statusCode;
    }

    public String getTipMsg() {
        return tipMsg;
    }

    public void setTipMsg(String tipMsg) {
        this.tipMsg = tipMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * 通过code获取枚举对象
     *
     * @param errorCode
     * @return
     */
    public static HttpCodeEnum getHttpCode(int errorCode) {
        HttpCodeEnum[] statusCodes = HttpCodeEnum.values();
        for (HttpCodeEnum statusCode : statusCodes) {
            if (statusCode.getStatusCode() == errorCode) {
                return statusCode;
            }
        }
        return HttpCodeEnum.RESULT_ERROR;
    }


}
