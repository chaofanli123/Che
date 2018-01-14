package com.victor.che.ui;

import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.js.JavaScriptObject;
import com.victor.che.util.AppUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * webview的界面，加载网页需要  单个网页
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/28 0028 13:24
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.mWebview)
    WebView mWebview;

    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.iv_reload)
    View tv_reload;

    private String mUrl;// 上页传来的url

    private Map<String, String> additionalHttpHeaders;// 额外的请求头信息

    @Override
    public int getContentView() {
        return R.layout.activity_web_view;
    }

    /**
     * 重新加载
     */
    @OnClick(R.id.iv_reload)
    void doReload() {
        mWebview.loadUrl(mUrl, additionalHttpHeaders);
    }

    /**
     * 网络错误时重新加载
     */
    @OnClick(R.id.btn_network_error_reload)
    void reloadOnNetworkError() {
        initView();
         mWebview.loadUrl(mUrl);
    }

    @Override
    protected void initView() {

        // 获取上页传来的数据
        mUrl = getIntent().getStringExtra("mUrl");

        additionalHttpHeaders = new HashMap<String, String>();
        additionalHttpHeaders.put("app_version", MyApplication.versionName);// 版本名称
        additionalHttpHeaders.put("app_client_id", "1");// 替换之前的appid,爱车工匠值为1

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
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " cbw");
        // 自适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        // 设置本地调用对象及其接口
        mWebview.addJavascriptInterface(new JavaScriptObject(mActivity), "Android");
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url, additionalHttpHeaders);
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
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
                        mWebview.goBack(); // 后退
                        return true; // 已处理
                    }
                }
                return false;
            }
        });

        if (AppUtil.isNetworkAvailable(mContext)) {
            findViewById(R.id.common_network_error).setVisibility(View.GONE);
            findViewById(R.id.page_content).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.common_network_error).setVisibility(View.VISIBLE);
            findViewById(R.id.page_content).setVisibility(View.GONE);
            return;
        }

        mWebview.loadUrl(mUrl, additionalHttpHeaders);
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
