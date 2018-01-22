package com.victor.che.ui.my;

import android.os.Bundle;
import android.text.Html;
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
import butterknife.ButterKnife;

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
    TextView tvContent;
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
        params.put("JSESSIONID", MyApplication.CURRENT_USER.JSESSIONID);//
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
                        tvContent.setText(Html.fromHtml(shopsCoupon.getContent()));
                        tvTime.setText(shopsCoupon.getCreateDate());
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
