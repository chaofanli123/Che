package com.victor.che.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.domain.PayConfirmData;
import com.victor.che.domain.PayPolicyAliData;
import com.victor.che.domain.PayPolicyWXData;
import com.victor.che.domain.QueryBaoxianHistory;
import com.victor.che.pay.wxpay.alipay.PayResult;
import com.victor.che.pay.wxpay.wxpay.Constants;
import com.victor.che.pay.wxpay.wxpay.WXPayParams;
import com.victor.che.pay.wxpay.wxpay.WXPayUtil;
import com.victor.che.util.AbViewHolder;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.BottomDialogFragment;
import com.victor.che.widget.ClearEditText;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/9 0009 15:57.
 * 出保单,填写邮寄地址
 */

public class CommitPolicyActivity extends BaseActivity {

    @BindView(R.id.tv_car_no)
    TextView tvCarNo;
    @BindView(R.id.tv_car_brand_name)
    TextView tvCarBrandName;
    @BindView(R.id.tv_actual_pay)
    TextView tvActualPay;
    @BindView(R.id.et_car_user_name)
    ClearEditText etCarUserName;
    @BindView(R.id.et_car_user_card)
    ClearEditText etCarUserCard;
    @BindView(R.id.et_consignee_name)
    ClearEditText etConsigneeName;
    @BindView(R.id.et_consignee_mobile)
    ClearEditText etConsigneeMobile;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.et_address_detail)
    ClearEditText etAddressDetail;
    private QueryBaoxianHistory queryBaoxianHistory;
    private int insurance_quote_id;

    private final int[] PAY_TYPE_ICONS = {R.drawable.ic_paytype_wxpay, R.drawable.ic_paytype_alipay};
    private final String[] PAY_TYPE_NAMES = {"微信支付", "支付宝"};
    private PayTypeListAdapter payTypeListAdapter;
    private String actual_total_price;
    private int SDK_PAY_FLAG = 1;// 支付宝支付标记
    private IWXAPI api;
    private int order_id;

    private SharedPreferences sp;
    @Override
    public int getContentView() {
        return R.layout.activity_commit_policy;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("填写保单邮寄地址");
        sp = this.getSharedPreferences("paystyle",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        //存储
        edit.putString("paystyle", "bendi");
        edit.commit();


        api = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID);

        queryBaoxianHistory = (QueryBaoxianHistory) getIntent().getSerializableExtra("queryBaoxianHistory");
        insurance_quote_id = getIntent().getIntExtra("insurance_quote_id", -1);
        actual_total_price = getIntent().getStringExtra("actual_total_price");

        tvCarNo.setText(queryBaoxianHistory.car_plate_no);
        tvCarBrandName.setText(queryBaoxianHistory.license_brand_model);
        tvActualPay.setText(actual_total_price +"元");
        etCarUserName.setText(queryBaoxianHistory.name);


        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPicker cityPicker = new CityPicker.Builder(mContext)
                        .textSize(20)
                        .cancelTextColor("#45c018")
                        .confirTextColor("#45c018")
                        .titleBackgroundColor("#ffffff")
                        .onlyShowProvinceAndCity(false)
                        .province("河南省")
                        .city("郑州市")
//                        .district("金水区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(false)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .build();
                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        tvCategory.setText(citySelected[0] + "-" + citySelected[1] + "-" + citySelected[2]);
                    }
                });
            }
        });
    }


    @OnClick(R.id.btn_operate)
    void commitPolicy(){
        //提交并支付保费

        if(TextUtils.isEmpty(etCarUserName.getText().toString().trim())){
            MyApplication.showToast("车主姓名不能为空");
            return;
        }
        if(!StringUtil.isSfz(etCarUserCard.getText().toString().trim())){
            MyApplication.showToast("身份证号格式不对");
            return;
        }
        if(TextUtils.isEmpty(etConsigneeName.getText().toString().trim())){
            MyApplication.showToast("收货人姓名不能为空");
            return;
        }
        if(!StringUtil.isPhoneNumber(etConsigneeMobile.getText().toString().trim())){
            MyApplication.showToast("收货人手机号格式不对");
            return;
        }
        if(TextUtils.isEmpty(etAddressDetail.getText().toString().trim())){
            MyApplication.showToast("请输入详细地址");
            return;
        }

        // 支付方式列表适配器
        payTypeListAdapter = new PayTypeListAdapter(mContext, R.layout.item_pay_type, PAY_TYPE_NAMES);
        payTypeListAdapter.notifyDataSetChanged();

        BottomDialogFragment.newInstance(payTypeListAdapter,
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            switch (position) {
                                case 0://微信支付
                                    _doWxpayAlipay(2);
                                    break;
                                case 1://支付宝支付
                                    _doWxpayAlipay(1);
                                    break;
                                default:
                                    _doWxpayAlipay(1);
                                    break;
                            }
                    }
                })
                .show(getSupportFragmentManager(), getClass().getSimpleName());

    }

    private void _doWxpayAlipay(final int pay_method_id) {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("insurance_query_id",queryBaoxianHistory.insurance_query_id);
        params.put("insurance_quote_id",insurance_quote_id);
        params.put("owner_name",etCarUserName.getText().toString().trim());
        params.put("id_no",etCarUserCard.getText().toString().trim());
        params.put("name",etConsigneeName.getText().toString().trim());
        params.put("mobile",etConsigneeMobile.getText().toString().trim());
        params.put("address",tvCategory.getText().toString().trim()+etAddressDetail.getText().toString().trim());
        params.put("pay_method_id",pay_method_id);//支付方式 1-支付宝 2-微信
        params.put("total_price",actual_total_price);//支付金额
        VictorHttpUtil.doPost(mContext, Define.url_insurance_order_add_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {

                    @Override
                    public void callbackSuccess(String url, Element element) {

                        if(pay_method_id==1){
                            //支付宝支付
                            PayPolicyAliData payPolicyAliData = JSON.parseObject(element.data, PayPolicyAliData.class);
                            order_id = payPolicyAliData.insurance_order_id;
                            final String orderInfo = payPolicyAliData.payStr;
                            //  final  String orderInfo="app_id=2017032406383486&method=alipay.trade.app.pay&timestamp=2017-04-24+15%3A20%3A01&charset=utf-8&version=1.0&notify_url=https%3A%2F%2Fxapitest.cheweifang.cn%2Fapp%2Fnotify%2Fali_notify%2Fnotify_url.php&biz_content=%7B%22body%22%3A%22494%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22out_trade_no%22%3A%22201704241520011493018401660%22%2C%22total_amount%22%3A0.01%2C%22subject%22%3A%22201704241520011493018401660%22%7D&sign_type=RSA2&sign=G8CobNDtKNpSeB%2FzDsWvId4bShakSlPZlC%2FIk1yWM6vVuWMZ4wospQrILmoUaUSsoIjwewmUY8v1T%2F4CvtqAQNjC1haOwAwSyiLYU7MBTOmVVxSug0e5v9ZDpJKMg9hg4ampM35FWTSAcqSkoYeFdebdPyh3hexU1lcYMr0Q%2FwNLgozLmu8VSeD7ICW6bVMSQw4INzBFwEFDB6cujVf4cKQOJQpWfyejAVa14RonmnnVceAOlPWZDgxVB5aP02y%2FZQJX%2BEpogTcCtfZVpdqxxoppFr4rXRJ9q9dpRsO%2B72BWbyTPwWGlSoq8LsghGyDXSJQUVgEeodvQm1v5ljTbTg%3D%3D";
                            Runnable payRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(CommitPolicyActivity.this);
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

                        }else if(pay_method_id==2){
                            //微信支付
                            PayPolicyWXData payPolicyWXData = JSON.parseObject(element.data, PayPolicyWXData.class);
                            order_id = payPolicyWXData.insurance_order_id;
                            WXPayParams wxparams = payPolicyWXData.payStr;
                            WXPayUtil.startWXPay(mContext,wxparams,order_id+"");
                        }

                    }
                });
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
                confirmAliPay(1);
            } else {// 判断resultStatus 为非“9000”则代表可能支付失败
                if ("8000".equalsIgnoreCase(resultStatus)) {// 4006：订单支付失败 .“8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    // MyApplication.showToast("支付结果确认中");
                } else if ("6001".equals(resultStatus)) {// 6001：用户中途取消支付操作
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
                    confirmAliPay(-1);
                }
            }

        }
    };

    private void confirmAliPay(int state){
        MyParams params = new MyParams();
        params.put("insurance_order_id",order_id);
        params.put("confirm_status",state);//-1：失败 1-成功
        VictorHttpUtil.doPost(mContext, Define.url_insurance_order_confirm_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {

                    @Override
                    public void callbackSuccess(String url, Element element) {

                        PayConfirmData payConfirmData = JSON.parseObject(element.data, PayConfirmData.class);
                        int is_success = payConfirmData.is_success;//  #支付状态 0-回调失败 1-成功 2-未回调且客户端确认成功 3-客户端确认失败
                        if(is_success==1){
                            //成功
                            MyApplication.openActivity(mContext,PayPolicySuccessActivity.class);
//                            finish();
                        }else{
                            //失败
                            MyApplication.showToast("支付失败,请稍后重试!");
                        }

                    }
                });
    }

    /**
     * 支付方式列表适配器
     */
    private class PayTypeListAdapter extends VictorBaseArrayAdapter<String> {

        public PayTypeListAdapter(Context context, int layoutResId, String[] array) {
            super(context, layoutResId, array);
        }

        @Override
        public void bindView(int position, View view, String entity) {

                //支付方式图标
                ImageView iv_paytype = AbViewHolder.get(view, R.id.iv_paytype);
                iv_paytype.setImageResource(PAY_TYPE_ICONS[position]);
                // 支付方式名称
                TextView tv_name = AbViewHolder.get(view, R.id.tv_name);
                tv_name.setText(PAY_TYPE_NAMES[position]);

        }


    }
}
