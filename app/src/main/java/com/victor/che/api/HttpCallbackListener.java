package com.victor.che.api;

/**
 * http回调的接口
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016-3-13 下午9:46:27
 */
public interface HttpCallbackListener<T> {

    /**
     * 无网络
     */
    void callbackNoNetwork(String url);

    /**
     * json格式错误
     *
     * @param url
     */
    void callbackErrorJSONFormat(String url);

    /**
     * 访问失败
     *
     * @param element
     */
    void callbackError(String url, T element);

    /**
     * 访问成功
     *
     * @param element
     */
    void callbackSuccess(String url, T element);

    /**
     * 访问失败
     *
     * @param url
     * @param statusCode 状态码
     * @param content    返回内容
     * @param error      错误
     */
    void onFaliure(String url, int statusCode, String content, Throwable error);
}
