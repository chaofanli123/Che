package com.victor.che.ui.my;

import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.bean.govPolicy;

import butterknife.BindView;

public class ZhengCheFaGuiActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar)
    RelativeLayout topbar;
    @BindView(R.id.tv_biaoti)
    TextView tvBiaoti;
    @BindView(R.id.tv_leixing)
    TextView tvLeixing;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    WebView tvContent;
    @BindView(R.id.tv_beizhuxingxi)
    TextView tvBeizhuxingxi;

    @Override
    public int getContentView() {
        return R.layout.activity_zheng_che_fa_gui;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("查看政策法规");
        init();
    }

    private void init() {
        loadData();
    }

    /**
     * 获取商家优惠券
     *
     * @param
     */
    private void loadData() {
        MyParams params = new MyParams();
        params.put("id", getIntent().getStringExtra("id"));
//        params.put("title", "");
//        params.put("type", "");
//        params.put("status", "");
        VictorHttpUtil.doPost(mContext, Define.URL_CHAKANZHENGCHEFAGUI + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        govPolicy notify = JSON.parseObject(element.body, govPolicy.class);
                        govPolicy.GovPolicyBean shopsCoupon = notify.getGovPolicy();
                        if (shopsCoupon.getType().equals("1")) {
                            tvLeixing.setText("部门文件");
                        } else if (shopsCoupon.getType().equals("2")) {
                            tvLeixing.setText("法规规章");
                        } else if (shopsCoupon.getType().equals("3")) {
                            tvLeixing.setText("规范性文件");
                        }else if (shopsCoupon.getType().equals("4")) {
                            tvLeixing.setText("政策解读");
                        }
                        tvBiaoti.setText(shopsCoupon.getTitle());
                        System.out.println(String.valueOf(Html.fromHtml(shopsCoupon.getContent()))+"**************");
                        WebSettings webSettings = tvContent.getSettings();
                        webSettings.setTextZoom(120);
                        //控制webView不要出现横向滚动条
                        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                        //此方法不支持4.4以后
                        webSettings.setUseWideViewPort(true);
                        webSettings.setJavaScriptEnabled(true);
                        tvContent.setWebViewClient(new MyWebViewClient());

                        tvContent.loadData(String.valueOf(Html.fromHtml(shopsCoupon.getContent())), "text/html; charset=UTF-8", null);
                        tvTime.setText(shopsCoupon.getCreateDate());
                    }
                });

    }
    //设置webview代理加载图片
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    private void imgReset() {
        tvContent.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                " img.style.maxWidth = '100%';img.style.height='auto';" +
                "}" +
                "})()");
    }
}
