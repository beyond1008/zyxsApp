package com.yaxon.centralplainlion.http.callback;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.yaxon.centralplainlion.bean.BaseBean;
import com.yaxon.centralplainlion.http.exception.APIException;
import com.yaxon.centralplainlion.http.exception.ErrorType;


import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;



public abstract class BaseObserver<T extends BaseBean> extends DisposableObserver<T> {


    @Override
    public void onNext(T baseModel) {
        //统一处理响应码
        int errorCode = baseModel.rc;
        HttpCodeEnum httpCode = HttpCodeEnum.getHttpCode(errorCode);
        switch (httpCode) {
            case RESULT_OK://请求成功
                onSuccess(baseModel);
                break;
            case RESULT_ERROR:
                onFailure(baseModel.errMsg, ErrorType.DATA_ERROR);
                break;
            default:
                onFailure(baseModel.errMsg,ErrorType.DATA_ERROR);
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        APIException be;
        if (e != null) {
            if (e instanceof HttpException) {
                //   HTTP错误
                be = new APIException(APIException.BAD_NETWORK_MSG, e, APIException.BAD_NETWORK);
                onFailure(be.getErrorMsg(),ErrorType.NET_ERROR);
            } else if (e instanceof ConnectException
                    || e instanceof UnknownHostException) {
                //   连接错误
                be = new APIException(APIException.CONNECT_ERROR_MSG, e, APIException.CONNECT_ERROR);
                onFailure(be.getErrorMsg(),ErrorType.NET_ERROR);
            } else if (e instanceof InterruptedIOException) {
                //  连接超时
                be = new APIException(APIException.CONNECT_TIMEOUT_MSG, e, APIException.CONNECT_TIMEOUT);
                onFailure(be.getErrorMsg(),ErrorType.NET_ERROR);
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                //  解析错误
                be = new APIException(APIException.PARSE_ERROR_MSG, e, APIException.PARSE_ERROR);
                onFailure(be.getErrorMsg(),ErrorType.DATA_ERROR);
            } else {
                be = new APIException(APIException.OTHER_MSG, e, APIException.OTHER);
                onFailure(be.getErrorMsg(),ErrorType.NET_ERROR);
            }
        } else {
            be = new APIException(APIException.OTHER_MSG, null, APIException.OTHER);
            onFailure(be.getErrorMsg(),ErrorType.DATA_ERROR);
        }

    }

    @Override
    public void onComplete() {


    }


    /**
     * 请求成功
     *
     * @param bean
     */
    public abstract void onSuccess(T bean);

    /**
     * 请求失败
     *
     * @param msg
     */
    public abstract void onFailure(String msg,ErrorType errorType);
}
