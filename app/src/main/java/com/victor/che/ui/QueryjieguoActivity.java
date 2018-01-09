package com.victor.che.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.ShareInfo;
import com.victor.che.js.JavaScriptObject;
import com.victor.che.util.AbAppUtil;
import com.victor.che.util.AppUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.util.URLUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class QueryjieguoActivity extends BaseActivity {

    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar_right)
    ImageView topbarRight;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.mWebview)
    WebView mWebview;

    private ShareInfo shareInfo;// 分享内容
    private boolean canGoback = true;//是否允许网页后退
    private String mUrl;// 上页传来的url
    private String mTopbarTitle = "";// 上页传来的页面标题
    private String mShareUrl = "";// 上页传来的分享url
    private boolean mShowShare;// 是否隐藏分享按钮
    private String car_plate_no;// --传车牌号号
    private String mobile;

    private Map<String, String> additionalHttpHeaders;// 额外的请求头信息

    /**
     * 分享对话框
     */
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private View parentView;

    @Override
    public int getContentView() {
        return R.layout.activity_queryjieguo;
    }

    @Override
    protected void initView() {
        super.initView();
        mobile=getIntent().getStringExtra("mobile");
        parentView = View.inflate(mContext, R.layout.activity_queryjieguo, null);
        // 获取上页传来的数据
        mUrl = StringUtil.convertNull(getIntent().getStringExtra("mUrl"));
        mShowShare=getIntent().getBooleanExtra("mShowShare",false);
        car_plate_no = getIntent().getStringExtra("car_plate_no");
        if (StringUtil.isNotEmpty(car_plate_no)) {
            String urlDomain = URLUtil.getUrlDomain(mUrl);
            Map<String, String> requestParamMap = URLUtil.getRequestParamMap(mUrl);
            requestParamMap.put("car_plate_no", car_plate_no);
            mUrl = URLUtil.getUrl(urlDomain, requestParamMap);
        }
        additionalHttpHeaders = new HashMap<String, String>();
        Logger.e(mUrl);
        // 是否显示分享按钮
        topbarRight.setVisibility(mShowShare ? View.VISIBLE : View.GONE);
        /*
        获取信息
         */
        tvTopbarTitle.setText(car_plate_no);

        if(AbAppUtil.haveNetworkConnection(mContext)){
            startWebView(mUrl);
        } else {
            mWebview.loadUrl("file:///android_asset/error.html");
        }

        /**
         * 拍照，手机相册选择框
         */
        View view = View.inflate(mContext, R.layout.pop_share, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop = new PopupWindow(mContext);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
//        TextView bt1 = (TextView) view.findViewById(R.id.item_popupwindows_message);
        TextView bt2 = (TextView) view.findViewById(R.id.item_popupwindows_phone);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
//        //短信分享
//        bt1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                sendSMS();
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
        //电话分享
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (StringUtil.isEmpty(mobile)) {
                    Toast.makeText(mContext,"当前车辆没有手机号，无法分享",Toast.LENGTH_SHORT).show();
                }else {
                    AppUtil.call(mContext,mobile); //拨打电话
                    pop.dismiss();
                    ll_popup.clearAnimation();
                }

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

    }

    /**
     * 短信分享
     */
    private void sendSMS() {
        Uri smsToUri = Uri.parse(mUrl);
        Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        sendIntent.putExtra( "sms_body" , "点击链接查看保险查询结果:"+ mUrl );
        sendIntent.setType( "vnd.android-dir/mms-sms" );
        startActivityForResult(sendIntent, 1002 );
    }


    @OnClick(R.id.topbar_right)
    public void onViewClicked() {
        /**
         * 分享
         */
        /**
         * 显示选择框
         */
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

    }

    /**
     * 网络错误时重新加载
     *
     * @param view
     */
    public void reloadOnNetworkError(View view) {
        initView();
       mWebview.loadUrl(mUrl);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.e("onstart:" + mUrl);
        mWebview.loadUrl(mUrl, additionalHttpHeaders);
    }

    @Override
    public void onDestroy() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.onDestroy();
    }
    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link
//
//        mWebview.setWebViewClient(new WebViewClient() {
//            ProgressDialog progressDialog;
//
//            //If you will not use this method url links are opeen in new brower not in webview
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                view.loadUrl(url);
////                return true;
////            }
//            //If url has "tel:245678" , on clicking the number it will directly call to inbuilt calling feature of phone
//
//
//            public boolean shouldOverrideUrlLoading(WebView view ,String url){
//
//                if(url.startsWith("tel:")){
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
//                    startActivity(intent);
//                } else {
//
//                    view.loadUrl(url);
//
//                }
//                return true;
//            }
//
//            //Show loader on url load
//            public void onLoadResource (WebView view, String url) {
//                if (progressDialog == null) {
//                    // in standard case YourActivity.this
//                    progressDialog = new ProgressDialog(QueryjieguoActivity.this);
//                    progressDialog.setMessage("加载中...");
//                    progressDialog.show();
//                }
//            }
//            public void onPageFinished(WebView view, String url) {
//                try{
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }
//                }catch(Exception exception){
//                    exception.printStackTrace();
//                }
//            }
//
//        });
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

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        		//Additional Webview Properties
        	        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		        webView.getSettings().setDatabaseEnabled(true);
		        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
		        webView.getSettings().setAppCacheEnabled(true);
		        webView.getSettings().setLayoutAlgorithm(webView.getSettings().getLayoutAlgorithm().NORMAL);
		        webView.getSettings().setLoadWithOverviewMode(true);
		        webView.getSettings().setUseWideViewPort(false);
		        webView.setSoundEffectsEnabled(true);
		        webView.setHorizontalFadingEdgeEnabled(false);
		        webView.setKeepScreenOn(true);
		        webView.setScrollbarFadingEnabled(true);
		        webView.setVerticalFadingEdgeEnabled(false);






        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        mWebview.loadUrl(url);


    }



}

