package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.InsuranceQuoteDetail;
import com.victor.che.domain.QueryBaoxianHistory;
import com.victor.che.domain.QuoteListDetail;
import com.victor.che.util.CollectionUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/9 0009 13:19.
 * 详细报价
 */

public class QuoteDetailActivity extends BaseActivity {

    @BindView(R.id.iv_insurance_name)
    ImageView ivInsuranceName;

    @BindView(R.id.tv_insurance_name)
    TextView tvInsuranceName;
    @BindView(R.id.tv_car_no)
    TextView tvCarNo;
    @BindView(R.id.tv_car_brand_name)
    TextView tvCarBrandName;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_jqy_all_price)
    TextView tvJqxAllPrice;

    @BindView(R.id.tv_syx_all_price)
    TextView tvSyxAllPrice;

    @BindView(R.id.ry_jqx)
    RecyclerView ryJqx;

    @BindView(R.id.ry_syx)
    RecyclerView rySyx;

    @BindView(R.id.ry_insurance_end_time)
    RecyclerView ryInsuranceEndTime;

    @BindView(R.id.tv_insurance_all_price)
    TextView tvInsuranceAllPrice;

    @BindView(R.id.tv_insurance_save_price)
    TextView tvInsuranceSavePrice;

    @BindView(R.id.tv_actual_pay)
    TextView tvActualPay;



    private QueryBaoxianHistory queryBaoxianHistory;
    private InsuranceQuoteDetail quoteDetail;
    private QuoteListDetail quoteListDetail;
    private final int FROMJQX=1;
    private final int FROMSYX=2;

    @Override
    public int getContentView() {
        return R.layout.activity_quote_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("详细报价");

        queryBaoxianHistory = (QueryBaoxianHistory) getIntent().getSerializableExtra("queryBaoxianHistory");
        //        String insurance_company_name = getIntent().getStringExtra("insurance_company_name");
        //        discount = getIntent().getStringExtra("discount");
        quoteListDetail = (QuoteListDetail) getIntent().getSerializableExtra("quoteListDetail");
        Glide.with(mContext).load(quoteListDetail.insurance_company_icon).error(R.drawable.icon_baoxian_commpany).dontAnimate().into(ivInsuranceName);
        tvInsuranceName.setText(quoteListDetail.insurance_company_name);
        tvCarNo.setText(queryBaoxianHistory.car_plate_no);
        tvCarBrandName.setText(queryBaoxianHistory.license_brand_model);
        tvUserName.setText(queryBaoxianHistory.name);
        tvInsuranceSavePrice.setText(quoteListDetail.discount);


        ryJqx.setLayoutManager(new LinearLayoutManager(mContext));
        ryJqx.setHasFixedSize(true);
        ryJqx.setNestedScrollingEnabled(false);
        rySyx.setLayoutManager(new LinearLayoutManager(mContext));
        rySyx.setHasFixedSize(true);
        rySyx.setNestedScrollingEnabled(false);
        ryInsuranceEndTime.setLayoutManager(new LinearLayoutManager(mContext));
        ryInsuranceEndTime.setHasFixedSize(true);
        ryInsuranceEndTime.setNestedScrollingEnabled(false);

        //总保费
        tvInsuranceAllPrice.setText(quoteListDetail.total_price + "元");
        tvActualPay.setText(quoteListDetail.actual_total_price+"元");
        MyParams params = new MyParams();
        params.put("insurance_quote_id", quoteListDetail.insurance_quote_id);
        VictorHttpUtil.doGet(mContext, Define.url_insurance_quote_detail_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {

                    @Override
                    public void callbackSuccess(String url, Element element) {
                        Gson gson = new Gson();
                        quoteDetail = gson.fromJson(element.data, InsuranceQuoteDetail.class);
                        //                        quoteDetail = JSON.parseObject(element.data, InsuranceQuoteDetail.class);

                        if (quoteDetail == null) {
                            MyApplication.showToast("报价详情为空");
                            return;
                        }

                        double jqxAllPrice = 0;
                        double syxAllPrice = 0;
                        if (quoteDetail.insurance_categorys != null && quoteDetail.insurance_categorys.jqx != null && !CollectionUtil.isEmpty(quoteDetail.insurance_categorys.jqx.insurance_categorys)) {
                            for (InsuranceQuoteDetail.InsuranceCategory.InCategorys cate : quoteDetail.insurance_categorys.jqx.insurance_categorys) {
                                //计算交强险的保费小计
                                jqxAllPrice += Double.parseDouble(cate.premium);
                            }

                            //交强险列表

                            ryJqx.setAdapter(new JqxListAdapter(R.layout.item_baoxian_price_detail, quoteDetail.insurance_categorys.jqx.insurance_categorys,FROMJQX));
                        }
                        if (quoteDetail.insurance_categorys != null && quoteDetail.insurance_categorys.syx != null && !CollectionUtil.isEmpty(quoteDetail.insurance_categorys.syx.insurance_categorys)) {
                            for (InsuranceQuoteDetail.InsuranceCategory.InCategorys cate : quoteDetail.insurance_categorys.syx.insurance_categorys) {
                                //计算商业险的保费小计
                                syxAllPrice += Double.parseDouble(cate.premium);
                            }

                            //商业险列表

                            rySyx.setAdapter(new JqxListAdapter(R.layout.item_baoxian_price_detail, quoteDetail.insurance_categorys.syx.insurance_categorys,FROMSYX));
                        }

                        tvJqxAllPrice.setText(jqxAllPrice + "元");
                        tvSyxAllPrice.setText(syxAllPrice + "元");

                        if (!CollectionUtil.isEmpty(quoteDetail.insurance_end_time)) {
                            //保险到期时间列表

                            ryInsuranceEndTime.setAdapter(new InsuranceEndTimeAdapter(R.layout.item_insurance_end_time, quoteDetail.insurance_end_time));
                        }

                    }
                });

    }

    /**
     * 交强险/商业险适配器
     */
    private class JqxListAdapter extends QuickAdapter<InsuranceQuoteDetail.InsuranceCategory.InCategorys> {

        private int type;
        public JqxListAdapter(int layoutResId, List<InsuranceQuoteDetail.InsuranceCategory.InCategorys> data,int type) {
            super(layoutResId, data);
            this.type = type;
        }

        @Override
        protected void convert(BaseViewHolder helper, InsuranceQuoteDetail.InsuranceCategory.InCategorys item) {

            helper
                    .setText(R.id.tv_insurance_name, item.insurance_category_name)
                    .setText(R.id.tv_insurance_coverage, item.coverage+"万元")
                    .setVisible(R.id.tv_is_free,type==FROMSYX)
                    .setText(R.id.tv_is_free, item.is_free == 1 ? "不计" : "")
                    .setText(R.id.tv_insurance_premium, item.premium+"元");
        }
    }

    /**
     * 保险到期时间适配器
     */
    private class InsuranceEndTimeAdapter extends QuickAdapter<InsuranceQuoteDetail.InsuranceEndTime> {

        public InsuranceEndTimeAdapter(int layoutResId, List<InsuranceQuoteDetail.InsuranceEndTime> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, InsuranceQuoteDetail.InsuranceEndTime item) {
            helper
                    .setText(R.id.tv_insurance_time_name, item.name+"到期时间")
                    .setText(R.id.tv_commercial_time, item.used_by_time);
        }
    }


    @OnClick(R.id.btn_operate)
    void gotoPolicy() {
        //去出保单界面
        Bundle bundle = new Bundle();
        bundle.putSerializable("queryBaoxianHistory", queryBaoxianHistory);
        bundle.putInt("insurance_quote_id", quoteListDetail.insurance_quote_id);
        bundle.putString("actual_total_price", quoteListDetail.actual_total_price);
        MyApplication.openActivity(mContext, CommitPolicyActivity.class, bundle);
    }

}
