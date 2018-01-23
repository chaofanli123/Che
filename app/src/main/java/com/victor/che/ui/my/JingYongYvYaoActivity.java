package com.victor.che.ui.my;

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
import com.victor.che.bean.ckyvyao;

import butterknife.BindView;

public class JingYongYvYaoActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar)
    RelativeLayout topbar;
    @BindView(R.id.tv_yaowumingcheng)
    TextView tvYaowumingcheng;
    @BindView(R.id.tv_yinwenming)
    TextView tvYinwenming;
    @BindView(R.id.tv_bieming)
    TextView tvBieming;
    @BindView(R.id.tv_yinyongyiju)
    TextView tvYinyongyiju;

    @Override
    public int getContentView() {
        return R.layout.activity_jing_yong_yv_yao;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("查看鱼药信息");
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
        VictorHttpUtil.doPost(mContext, Define.URL_CHAKANJINGYONGYVYAO + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        ckyvyao notify = JSON.parseObject(element.body, ckyvyao.class);
                        ckyvyao.FishDrugBean fishDrug = notify.getFishDrug();
                        tvYaowumingcheng.setText(Html.fromHtml(fishDrug.getMedicineName()));
                        tvYinwenming.setText(Html.fromHtml(fishDrug.getEnglishName()));
                        tvBieming.setText(Html.fromHtml(fishDrug.getAnotherName()));
                        tvYinyongyiju.setText(Html.fromHtml(fishDrug.getReferenceBasis()));
                    }
                });

    }
}
