package com.victor.che.ui;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.js.JavaScriptObject;
import com.victor.che.util.AbAppUtil;
import com.victor.che.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanggaoWebActivity extends BaseActivity {

    @BindView(R.id.btn_network_error_reload)
    Button btnNetworkErrorReload;
    @BindView(R.id.common_network_error)
    LinearLayout commonNetworkError;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.mWebview)
    WebView mWebview;
    @BindView(R.id.page_content)
    RelativeLayout pageContent;
    private String mUrl;// 上页传来的url
    private boolean canGoback = true;//是否允许网页后退
    @Override
    public int getContentView() {
        return R.layout.activity_guanggao_web;
    }

    @Override
    protected void initView() {
        super.initView(); // 获取上页传来的数据
        mUrl = getIntent().getStringExtra("mUrl");
        Log.e("获取上页传来的数据",mUrl);

        if(AbAppUtil.haveNetworkConnection(mContext)){
            if ("www.baidu.com".equals(mUrl)) {
                mUrl="https://"+mUrl;
                startWebView(mUrl);
            }else {
                startWebView(mUrl);
            }

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
                 setTitle(title);
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

    @OnClick({R.id.img_close, R.id.iv_reload,R.id.btn_network_error_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.iv_reload:  //重新加载
                mWebview.reload();
                break;
            /**
             * 网络错误时重新加载
             */
            case R.id.btn_network_error_reload:
                initView();
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mWebview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebview.onPause();
    }

    @Override
    public void onDestroy() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.onDestroy();
    }

    /**
     * 系统自动的返回按钮处理
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * 返回按钮返回网页的上一页
     *
     * @param view
     */
    @Override
    public void back(View view) {
        // super.back(view);
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
