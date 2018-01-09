package com.victor.che.api;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.victor.che.app.MyApplication;

/**
 * 回调的简单空实现
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016-3-13 下午9:47:52
 */
public class BaseHttpCallbackListener<T> implements HttpCallbackListener<T> {

    private Context context;

    @Override
    public void callbackNoNetwork(String url) {

    }

    @Override
    public void callbackErrorJSONFormat(String url) {

    }

    @Override
    public void callbackError(String url, T obj) {
        if (obj instanceof Element) {
            Element element2 = (Element) obj;
            MyApplication.showToast(element2.msg);
        }
    }

    @Override
    public void callbackSuccess(String url, T element) {

    }

    @Override
    public void onFaliure(String url, int statusCode, String content, Throwable error) {
        Logger.e(error, url, statusCode);
        MyApplication.showToast("连接超时，请稍后重试");
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
