package com.victor.che.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.victor.che.domain.QueryPolicyHistory;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ImageLoaderUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/11 0011 10:16.
 * 保单详情
 */

public class PolicyDetailActivity extends BaseActivity {

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
    @BindView(R.id.ry_jqx)
    RecyclerView ryJqx;
    @BindView(R.id.tv_syx_all_price)
    TextView tvSyxAllPrice;
    @BindView(R.id.ry_syx)
    RecyclerView rySyx;
    @BindView(R.id.tv_insurance_all_price)
    TextView tvInsuranceAllPrice;
    @BindView(R.id.tv_insurance_save_price)
    TextView tvInsuranceSavePrice;
    @BindView(R.id.ry_insurance_end_time)
    RecyclerView ryInsuranceEndTime;
    @BindView(R.id.tv_consignee_name)
    TextView tvConsigneeName;
    @BindView(R.id.tv_consignee_mobile)
    TextView tvConsigneeMobile;
    @BindView(R.id.tv_consignee_address)
    TextView tvConsigneeAddress;
    @BindView(R.id.tv_actual_pay)
    TextView tvActualPay;
    private QueryPolicyHistory queryPolicyHistory;
    private InsuranceQuoteDetail quoteDetail;
    private final int FROMJQX=1;
    private final int FROMSYX=2;

    @Override
    public int getContentView() {
        return R.layout.activity_policy_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("详细报价");

        queryPolicyHistory = (QueryPolicyHistory) getIntent().getSerializableExtra("queryPolicyHistory");

        ImageLoaderUtil.display(mContext, ivInsuranceName, queryPolicyHistory.insurance_company_icon,0,0);
        tvInsuranceName.setText(queryPolicyHistory.insurance_company_name);
        tvCarNo.setText(queryPolicyHistory.car_plate_no);
        tvCarBrandName.setText(queryPolicyHistory.license_brand_model);
        tvUserName.setText(queryPolicyHistory.owner_name);
        tvInsuranceAllPrice.setText(queryPolicyHistory.total_price+"元");
        tvInsuranceSavePrice.setText(queryPolicyHistory.discount);
        tvActualPay.setText(queryPolicyHistory.actual_total_price+"元");

        tvConsigneeName.setText(queryPolicyHistory.name);
        tvConsigneeMobile.setText(queryPolicyHistory.mobile);
        tvConsigneeAddress.setText(queryPolicyHistory.address);

        ryJqx.setLayoutManager(new LinearLayoutManager(mContext));
        ryJqx.setHasFixedSize(true);
        ryJqx.setNestedScrollingEnabled(false);
        rySyx.setLayoutManager(new LinearLayoutManager(mContext));
        rySyx.setHasFixedSize(true);
        rySyx.setNestedScrollingEnabled(false);
        ryInsuranceEndTime.setLayoutManager(new LinearLayoutManager(mContext));
        ryInsuranceEndTime.setHasFixedSize(true);
        ryInsuranceEndTime.setNestedScrollingEnabled(false);

        MyParams params = new MyParams();
        params.put("insurance_quote_id",queryPolicyHistory.insurance_quote_id);
        VictorHttpUtil.doGet(mContext, Define.url_insurance_quote_detail_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {

                    @Override
                    public void callbackSuccess(String url, Element element) {
                        Gson gson = new Gson();
                        quoteDetail = gson.fromJson(element.body,InsuranceQuoteDetail.class);
                        //                        quoteDetail = JSON.parseObject(element.data, InsuranceQuoteDetail.class);

                        if (quoteDetail ==null) {
                            MyApplication.showToast("报价详情为空");
                            return;
                        }

                        double jqxAllPrice = 0;
                        double syxAllPrice = 0;
                        if(quoteDetail.insurance_categorys!=null && quoteDetail.insurance_categorys.jqx!=null && !CollectionUtil.isEmpty(quoteDetail.insurance_categorys.jqx.insurance_categorys)){
                            for(InsuranceQuoteDetail.InsuranceCategory.InCategorys cate: quoteDetail.insurance_categorys.jqx.insurance_categorys ){
                                //计算交强险的保费小计
                                jqxAllPrice += Double.parseDouble(cate.premium);
                            }

                            //交强险列表
                            ryJqx.setLayoutManager(new LinearLayoutManager(mContext));
                            ryJqx.setAdapter(new JqxListAdapter(R.layout.item_baoxian_price_detail, quoteDetail.insurance_categorys.jqx.insurance_categorys,FROMJQX));
                        }
                        if(quoteDetail.insurance_categorys!=null && quoteDetail.insurance_categorys.syx!=null && !CollectionUtil.isEmpty(quoteDetail.insurance_categorys.syx.insurance_categorys)){
                            for(InsuranceQuoteDetail.InsuranceCategory.InCategorys cate: quoteDetail.insurance_categorys.syx.insurance_categorys ){
                                //计算商业险的保费小计
                                syxAllPrice += Double.parseDouble(cate.premium);
                            }

                            //商业险列表
                            rySyx.setLayoutManager(new LinearLayoutManager(mContext));
                            rySyx.setAdapter(new JqxListAdapter(R.layout.item_baoxian_price_detail, quoteDetail.insurance_categorys.syx.insurance_categorys,FROMSYX));
                        }

                        tvJqxAllPrice.setText(jqxAllPrice+"元");
                        tvSyxAllPrice.setText(syxAllPrice+"元");

                        if(!CollectionUtil.isEmpty(quoteDetail.insurance_start_time)){
                            //保险到期时间列表
                            ryInsuranceEndTime.setLayoutManager(new LinearLayoutManager(mContext));
                            ryInsuranceEndTime.setAdapter(new InsuranceEndTimeAdapter(R.layout.item_insurance_end_time, quoteDetail.insurance_start_time));
                        }

                    }
                });
    }


    /**
     * 交强险/商业险适配器
     */
    private class JqxListAdapter extends QuickAdapter<InsuranceQuoteDetail.InsuranceCategory.InCategorys>{

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
     * 保险生效时间适配器
     */
    private class InsuranceEndTimeAdapter extends QuickAdapter<InsuranceQuoteDetail.InsuranceStartTime> {

        public InsuranceEndTimeAdapter(int layoutResId, List<InsuranceQuoteDetail.InsuranceStartTime> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, InsuranceQuoteDetail.InsuranceStartTime item) {
            helper
                    .setText(R.id.tv_insurance_time_name,item.name+"生效时间")
                    .setText(R.id.tv_commercial_time,item.restart_by_time);
        }
    }
}
