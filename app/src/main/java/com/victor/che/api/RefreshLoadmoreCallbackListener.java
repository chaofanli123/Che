package com.victor.che.api;

import com.victor.che.util.PtrHelper;

/**
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月24日 下午2:46:13
 */
public class RefreshLoadmoreCallbackListener<T> extends BaseHttpCallbackListener<T> {

    private PtrHelper mPtrHelper;

    public RefreshLoadmoreCallbackListener(PtrHelper mPtrHelper) {
        this.mPtrHelper = mPtrHelper;
    }

    @Override
    public void callbackError(String url, T element) {
        super.callbackError(url, element);
        // 结束刷新
        if (mPtrHelper != null) {
            mPtrHelper.refreshComplete();
            mPtrHelper.loadMoreComplete();
        }
    }

    @Override
    public void callbackErrorJSONFormat(String url) {
        super.callbackErrorJSONFormat(url);
        // 结束刷新
        if (mPtrHelper != null) {
            mPtrHelper.refreshComplete();
            mPtrHelper.loadMoreComplete();
        }
    }

    @Override
    public void callbackNoNetwork(String url) {
        super.callbackNoNetwork(url);
        // 结束刷新
        if (mPtrHelper != null) {
            mPtrHelper.refreshComplete();
            mPtrHelper.loadMoreComplete();
        }
    }

    @Override
    public void onFaliure(String url, int statusCode, String content, Throwable error) {
        super.onFaliure(url, statusCode, content, error);
        // 结束刷新
        if (mPtrHelper != null) {
            mPtrHelper.refreshComplete();
            mPtrHelper.loadMoreComplete();
        }
    }
}
