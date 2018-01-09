package com.victor.che.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.victor.che.R;
import com.victor.che.api.Define;
import com.victor.che.base.BaseActivity;
import com.victor.che.js.JavaScriptObject;
import com.victor.che.util.AbAppUtil;

import butterknife.BindView;

public class AboutusActivity extends BaseActivity {

    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.mWebview)
    WebView mWebview;
    private String mUrl;// 上页传来的url
    private boolean canGoback = true;//是否允许网页后退

    @Override
    public int getContentView() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("关于我们");
        // 获取上页传来的数据
        mUrl = Define.MWEB_DOMAIN+"web/aboutUs/aboutUs.html";
        Logger.e(mUrl);
        if (AbAppUtil.haveNetworkConnection(mContext)) {
            startWebView(mUrl);
        } else {
            mWebview.loadUrl("file:///android_asset/error.html");
        }
    }
    private void startWebView(String url) {

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception ex) {
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

        });
        mWebview.setWebChromeClient(new WebChromeClient() {

            // 获取到html的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //  setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        // 改写物理按键——返回的逻辑
        mWebview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack() && canGoback) {
                        mWebview.goBack(); // 后退
                        return true; // 已处理
                    }
                }
                return false;
            }
        });

        if (AbAppUtil.isNetworkAvailable(mContext)) {
            findViewById(R.id.common_network_error).setVisibility(View.GONE);
            findViewById(R.id.page_content).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.common_network_error).setVisibility(View.VISIBLE);
            findViewById(R.id.page_content).setVisibility(View.GONE);
            return;
        }
        WebSettings webSettings = mWebview.getSettings();
        // 设置编码
        webSettings.setDefaultTextEncodingName("utf-8");
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置支持js
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 关闭缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        // 设置UserAgent（安全起见）
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " cheweifang");
        // 自适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        // 设置本地调用对象及其接口
        mWebview.addJavascriptInterface(new JavaScriptObject(mActivity), "Android");

        // Javascript inabled on webview
        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.loadUrl(url);

    }

}
