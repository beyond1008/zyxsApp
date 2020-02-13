package com.yaxon.centralplainlion.http.upload;


import com.yaxon.centralplainlion.bean.BaseBean;

/**
 * Description:上传文件回调
 * Created by kimiffy on 2019/10/9.
 */

public interface UploadListener {

    void onSuccess(BaseBean bean);

    void onError(String error);
}
