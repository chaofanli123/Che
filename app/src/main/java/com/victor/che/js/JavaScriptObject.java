package com.victor.che.js;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * WebView与android的API交互的接口
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/28 0028 13:25
 */
public class JavaScriptObject {
    private Activity mActivity;

    public JavaScriptObject(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 关闭界面
     */
    @JavascriptInterface
    public void close() {

    }


}
