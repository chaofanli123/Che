package com.victor.che.ui;

import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.hjhrq1991.library.BridgeHandler;
import com.hjhrq1991.library.BridgeWebView;
import com.hjhrq1991.library.BridgeWebViewClient;
import com.hjhrq1991.library.CallBackFunction;
import com.hjhrq1991.library.DefaultHandler;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.victor.che.R;
import com.victor.che.api.Define;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.PayData;
import com.victor.che.event.AlipayReuseEnent;
import com.victor.che.event.PayAliMessage;
import com.victor.che.pay.wxpay.alipay.PayResult;
import com.victor.che.pay.wxpay.wxpay.Constants;
import com.victor.che.util.AbAppUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 商城界面
 */
public class ShoppingWebActivity extends BaseActivity {
    @BindView(R.id.btn_network_error_reload)
    Button btnNetworkErrorReload;
    @BindView(R.id.common_network_error)
    LinearLayout commonNetworkError;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.mWebview)
    BridgeWebView mWebview;
    @BindView(R.id.page_content)
    RelativeLayout pageContent;
    @BindView(R.id.iv_reload)
    ImageView ivReload;
    private String mUrl;
    private IWXAPI api;
    private int SDK_PAY_FLAG = 1;// 支付宝支付标记
    private AlipayReuseEnent alipayRrsuse;
    private String shopping_order_id;

    private SharedPreferences sp;

    @Override
    public int getContentView() {
        return R.layout.activity_shopping;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        super.initView();

        //显示刷新按钮

        sp = this.getSharedPreferences("paystyle",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();

        //存储
        edit.putString("paystyle", "webviwe");
        edit.commit();

        ivReload.setVisibility(View.VISIBLE);
        mUrl = getIntent().getStringExtra("mURL");
        alipayRrsuse = new AlipayReuseEnent();
        api = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID);
        if (AbAppUtil.haveNetworkConnection(mContext)) {
            mWebview.loadUrl(mUrl);
        } else {
            mWebview.loadUrl("file:///android_asset/error.html");
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
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置 缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
// 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
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


        // 设置具体WebViewClient
        mWebview.setWebViewClient(new MyWebViewClient(mWebview));

        mWebview.setDefaultHandler(new myHadlerCallBack());

        mWebview.setWebChromeClient(new WebChromeClient() {
            // 获取到html的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                return true;
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
        //加载本地网页
        //必须和js同名函数，注册具体执行函数，类似java实现类。
        /**
         *   微信支付 wxPayOnClick
         */
        mWebview.registerHandler("wxPayOnClick", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String str = "这是html返回给java的数据微信支付信息:" + data;
                // 例如你可以对原始数据进行处理
                PayData payData = JSON.parseObject(data, PayData.class);
                PayData.PayDicBean wxparams = payData.getPayDic();
                shopping_order_id = payData.getShopping_order_id();
                String appid = wxparams.getAppid();
                String partnerid = wxparams.getPartnerid();
                String prepayid = wxparams.getPrepayid();
                String noncestr = wxparams.getNoncestr();
                int timestamp = wxparams.getTimestamp();
                String packageValue = wxparams.packageValue;
                String sign = wxparams.getSign();
                if (wxparams == null) {
                    MyApplication.showToast("支付参数为空");
                    return;
                }
                if (api == null) {
                    api = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID);
                }
                try {
                    PayReq req = new PayReq();
                    req.appId = appid;
                    req.partnerId = partnerid;
                    req.prepayId = prepayid;
                    req.nonceStr = noncestr;
                    req.timeStamp = timestamp + "";
                    req.packageValue = packageValue;
                    req.sign = sign;
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    api.sendReq(req);
                } catch (Exception e) {
                    Log.i(TAG, "在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信" + e.getMessage());
                    MyApplication.showToast("异常：" + e.getMessage());
                }
                Log.i(TAG, str);
            }
        });
        /**
         *   支付宝支付
         */
        mWebview.registerHandler("aliPayOnClick", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String str = "这是html返回给java的数据支付宝支付信息:" + data;
                // 例如你可以对原始数据进行处理
                Log.i(TAG, str);
                PayAliMessage payData = JSON.parseObject(data, PayAliMessage.class);
                final String orderInfo = payData.getPayDic();
                shopping_order_id = payData.getShopping_order_id();
                //  final  String orderInfo="app_id=2017032406383486&method=alipay.trade.app.pay&timestamp=2017-04-24+15%3A20%3A01&charset=utf-8&version=1.0&notify_url=https%3A%2F%2Fxapitest.cheweifang.cn%2Fapp%2Fnotify%2Fali_notify%2Fnotify_url.php&biz_content=%7B%22body%22%3A%22494%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22out_trade_no%22%3A%22201704241520011493018401660%22%2C%22total_amount%22%3A0.01%2C%22subject%22%3A%22201704241520011493018401660%22%7D&sign_type=RSA2&sign=G8CobNDtKNpSeB%2FzDsWvId4bShakSlPZlC%2FIk1yWM6vVuWMZ4wospQrILmoUaUSsoIjwewmUY8v1T%2F4CvtqAQNjC1haOwAwSyiLYU7MBTOmVVxSug0e5v9ZDpJKMg9hg4ampM35FWTSAcqSkoYeFdebdPyh3hexU1lcYMr0Q%2FwNLgozLmu8VSeD7ICW6bVMSQw4INzBFwEFDB6cujVf4cKQOJQpWfyejAVa14RonmnnVceAOlPWZDgxVB5aP02y%2FZQJX%2BEpogTcCtfZVpdqxxoppFr4rXRJ9q9dpRsO%2B72BWbyTPwWGlSoq8LsghGyDXSJQUVgEeodvQm1v5ljTbTg%3D%3D";
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(ShoppingWebActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
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
    }

    /**
     * 微信支付成功以后
     *
     * @param reuseEnent
     */
    @Subscribe
    public void WeixinpayResultEvent(AlipayReuseEnent reuseEnent) {
        if (reuseEnent == null) {
            return;
        }
        alipayRrsuse = reuseEnent;
        mWebview.loadUrl(Define.MWEB_DOMAIN + "mall/#/order_detail/"
                + MyApplication.CURRENT_USER.provider_id + "/"
                + MyApplication.CURRENT_USER.staff_user_id + "/"
                + shopping_order_id);
    }
    /**
     * 刷新网页
     */
    @OnClick({R.id.iv_reload,R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_reload:
                mWebview.reload();
                break;
            case R.id.img_close:
               finish();
                break;
        }

    }

    /**
     * 自定义的WebViewClient
     */
    class MyWebViewClient extends BridgeWebViewClient {
        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
            //  onProgressChanged
        }

    }

    /**
     * 自定义回调
     */
    class myHadlerCallBack extends DefaultHandler {

        @Override
        public void handler(String data, CallBackFunction function) {
            if (function != null) {

            }
        }

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

    /**
     * 支付宝支付成功以后处理
     */
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (SDK_PAY_FLAG != msg.what) {// 非支付宝支付的回调
                return;
            }
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultStatus = payResult.getResultStatus();
            if ("9000".equalsIgnoreCase(resultStatus)) {// “9000”则代表支付成功
                // 访问服务器，确认支付结果
                alipayRrsuse.setPayState("1");

                mWebview.loadUrl(Define.MWEB_DOMAIN + "mall/#/order_detail/"
                        + MyApplication.CURRENT_USER.provider_id + "/"
                        + MyApplication.CURRENT_USER.staff_user_id + "/"
                        + shopping_order_id);

            } else {// 判断resultStatus 为非“9000”则代表可能支付失败
                if ("4006".equalsIgnoreCase(resultStatus)) {// 4006：订单支付失败 .“8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    // MyApplication.showToast("支付结果确认中");
                    alipayRrsuse.setPayState("0");
                } else if ("6001".equals(resultStatus)) {// 6001：用户中途取消支付操作
                    alipayRrsuse.setPayState("-1");

                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    // 字符串类型；错误码，取值范围如下：
                    // 9000：支付成功
                    // 8000：正在处理中
                    // 4000：系统异常
                    // 4001：数据格式不正确
                    // 4003：该用户绑定的支付宝账户被冻结或不允许支付
                    // 4004：该用户已解除绑定
                    // 4005：绑定失败或没有绑定
                    // 4006：订单支付失败
                    // 4010：重新绑定账户
                    // 6000：支付服务正在进行升级操作
                    // 6002： 网络连接出错
                    alipayRrsuse.setPayState("0");
                    mWebview.loadUrl(Define.MWEB_DOMAIN + "mall/#/order_detail/"
                            + MyApplication.CURRENT_USER.provider_id + "/"
                            + MyApplication.CURRENT_USER.staff_user_id + "/"
                            + shopping_order_id);
                }
            }

        }
    };

    @Override
    protected void onDestroy() {

        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebview.clearHistory();
            ((ViewGroup)mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview=null;
        }

        super.onDestroy();
    }
}

