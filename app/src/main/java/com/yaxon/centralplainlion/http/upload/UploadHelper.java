package com.yaxon.centralplainlion.http.upload;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description: 上传工具
 * Created by kimiffy on 2019/10/9.
 */

public class UploadHelper {
    private static UploadHelper instance;
    private UploadListener mUploadListener;

    public static UploadHelper getInstance() {
        synchronized (UploadHelper.class) {
            if (instance == null) {
                instance = new UploadHelper();
            }
        }
        return instance;
    }

    private UploadHelper() {   }

    /**
     * 上传单个文件(图文混传)
     *
     * @param map            参数
     * @param fileUrl        上传文件的全路径
     * @param uploadListener
     */
    public void uploadFile(Map<String, String> map, String fileUrl, UploadListener uploadListener) {
        this.mUploadListener = uploadListener;
        File file = new File(fileUrl);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        HashMap<String, RequestBody> requestMap = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            RequestBody toRequestBody = toRequestBody(value);
            requestMap.put(key, toRequestBody);
        }
//        ApiManager.getApiService().uploadPhoto(requestMap, body).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new Observer<BaseBean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseBean bean) {
//                        if (null != mUploadListener) {
//                            mUploadListener.onSuccess(bean);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (null != mUploadListener) {
//                            mUploadListener.onError(e.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }


    private RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

}
