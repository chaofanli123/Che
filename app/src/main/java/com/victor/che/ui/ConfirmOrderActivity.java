package com.victor.che.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.SimpleTextWatcher;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.domain.Pending;
import com.victor.che.domain.Shoppingcar;
import com.victor.che.domain.Vipcard;
import com.victor.che.util.AbViewHolder;
import com.victor.che.util.MathUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.BottomDialogFragment;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/3 0003 15:33.
 * 订单确认界面
 */

public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.tv_car_no)
    TextView tvCarNo;
    @BindView(R.id.tv_car_user_mobile)
    TextView tvCarUserMobile;
    @BindView(R.id.tv_creat_time)
    TextView tvCreatTime;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.et_sale_price)
    EditText etSalePrice;
    private boolean userIsExist;

    private double actual_pay = 0;//实际支付金额

    private final int[] PAY_TYPE_ICONS = {R.drawable.ic_paytype_vipcard, R.drawable.ic_paytype_unionpay, R.drawable.ic_paytype_wxpay, R.drawable.ic_paytype_alipay};
    private final String[] PAY_TYPE_NAMES = {"会员卡支付", "银联/现金支付", "微信支付", "支付宝"};
    private final int[] PAY_TYPE_ICONS2 = {R.drawable.ic_paytype_unionpay, R.drawable.ic_paytype_wxpay, R.drawable.ic_paytype_alipay};
    private final String[] PAY_TYPE_NAMES2 = {"银联/现金支付", "微信支付", "支付宝"};
    private PayTypeListAdapter payTypeListAdapter;
    private boolean isCompleted;
    private String provider_user_id;
    private ArrayList<Vipcard> vipcardList;
    private Pending pending;
    private Shoppingcar shoppingcar;


    @Override
    public int getContentView() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initView() {
        super.initView();

        setTitle("确认收银");

        //        userIsExist = getIntent().getBooleanExtra("userIsExist", false);
        //        isCompleted = getIntent().getBooleanExtra("isCompleted", false);
        //        provider_user_id = getIntent().getStringExtra("provider_user_id");
        //
        //        sale_user_id = getIntent().getStringExtra("sale_user_id");
        //        mobile = getIntent().getStringExtra("mobile");
        //        car_plate_no = getIntent().getStringExtra("car_plate_no");
        //        goods_data = getIntent().getStringExtra("goods_data");
        //        total_price = getIntent().getFloatExtra("total_price", 0f);
        //        mileage = getIntent().getFloatExtra("mileage", 0f);
        //        next_maintains = getIntent().getStringExtra("next_maintain");
        //        vipcardList = (ArrayList<Vipcard>) getIntent().getSerializableExtra("vipcardList");
        //        shoppingCars = (ArrayList<Shoppingcar>) getIntent().getSerializableExtra("shoppingCars");

        pending = (Pending) getIntent().getSerializableExtra("pending");

        shoppingcar = pending.detail.get(0);

        tvCarNo.setText(pending.car_plate_no);
        tvCarUserMobile.setText(pending.mobile);

        tvCreatTime.setText(pending.create_time);

        tvAllPrice.setText(pending.total_price + "元");
        etSalePrice.setText(pending.total_price + "");
        actual_pay = Double.parseDouble(pending.total_price);

        recycler.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        ConfirmOrderAdapter mAdapter = new ConfirmOrderAdapter(R.layout.item_confirm_order, pending.detail);
        recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(1)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线
        recycler.setAdapter(mAdapter);

        etSalePrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {//点击完成
                    if (!StringUtil.isMoney(etSalePrice.getText().toString().trim())) {
//                        MyApplication.showToast("格式不正确");
                        etSalePrice.requestFocus();
                        return false;
                    }
                    actual_pay = Double.parseDouble(etSalePrice.getText().toString().trim());
                }
                return false;
            }
        });

        //销售价改变
        etSalePrice.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (!StringUtil.isMoney(text)){
//                    MyApplication.showToast("格式不正确");
                    etSalePrice.requestFocus();
                    return;
                }
                actual_pay = Double.parseDouble(etSalePrice.getText().toString().trim());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }
        });

        doSearch(pending.mobile, pending.car_plate_no);
    }

    public void doSearch(String mobile, String car_plate_no) {


        // 获取用户和卡信息
        MyParams params = new MyParams();

        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("mobile", mobile);// 手机号
        params.put("car_plate_no", car_plate_no);//车牌号
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_USER_CARD_LIST_receipt, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = parseObject(element.data);
                            JSONObject user_info = jsonobj.getJSONObject("user_info");

                            if (user_info != null) {
                                userIsExist = true;

                                if (user_info.getIntValue("is_completed") == 1) {//信息完善
                                    isCompleted = true;

                                }
                                // 用户编号
                                provider_user_id = user_info.getString("provider_user_id");
                            }

                            vipcardList = (ArrayList<Vipcard>) JSON.parseArray(jsonobj.getString("cards"), Vipcard.class);// 会员卡列表

                            if (jsonobj.getIntValue("exist_user") == -1) {
                                //用户不存在
//                                MyApplication.showToast(element.getMsg());
                                userIsExist = false;
                            } else {
                                userIsExist = true;
                            }

                            if (jsonobj.getIntValue("conflict") == 1) {
                                //有冲突
                                final String user_id = jsonobj.getString("provider_user_id");

                                AlertDialogFragment.newInstance(
                                        "提示",
                                        "您输入的车牌号和手机号是两个用户，无法开卡是否编辑用户信息？",
                                        "取消",
                                        "编辑",
                                        null,
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("provider_user_id", user_id);
                                                MyApplication.openActivity(mContext, ConflictActivity.class, bundle);
                                            }
                                        })
                                        .show(getSupportFragmentManager(), getClass().getSimpleName());
                            }
                    }
                });

    }

    @OnClick(R.id.btn_operate)
    void operate() {

        if (TextUtils.isEmpty(etSalePrice.getText().toString().trim())) {
            MyApplication.showToast("请输入成交价");
            return;
        }

        if (!StringUtil.isMoney(etSalePrice.getText().toString().trim())) {
            MyApplication.showToast("成交价格式不正确");
            etSalePrice.requestFocus();
            return;
        }

        if (!userIsExist) {
            // 不显示会员卡支付
            payTypeListAdapter = new PayTypeListAdapter(mContext, R.layout.item_pay_type, PAY_TYPE_NAMES2);
            payTypeListAdapter.notifyDataSetChanged();
        } else {
            // 支付方式列表适配器
            payTypeListAdapter = new PayTypeListAdapter(mContext, R.layout.item_pay_type, PAY_TYPE_NAMES);
            payTypeListAdapter.notifyDataSetChanged();
        }

        BottomDialogFragment.newInstance(payTypeListAdapter,
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (userIsExist) {
                            //用户存在
                            switch (position) {
                                case 0:// 会员卡支付
                                    _gotoSelectVipcard();
                                    break;
                                case 1:// 银联/现金支付
                                    AlertDialogFragment.newInstance(
                                            "友情提示",
                                            "请确认您已收到用户的现金/刷卡支付",
                                            "取消",
                                            "确定",
                                            null,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    _doCashPay();
                                                }
                                            })
                                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                                    break;
                                case 2://微信支付
                                    _doWxpayAlipay("2");
                                    break;
                                default://支付宝支付
                                    _doWxpayAlipay("1");
                                    break;
                            }
                        } else {
                            //用户不存在
                            switch (position) {
                                case 0:// 银联/现金支付
                                    AlertDialogFragment.newInstance(
                                            "友情提示",
                                            "请确认您已收到用户的现金/刷卡支付",
                                            "取消",
                                            "确定",
                                            null,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    _doCashPay();
                                                }
                                            })
                                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                                    break;
                                case 1://微信支付
                                    _doWxpayAlipay("2");
                                    break;
                                default://支付宝支付
                                    _doWxpayAlipay("1");
                                    break;
                            }
                        }
                    }
                })
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 实际现金支付
     */
    private void _doCashPay() {

        /**
         * 格式--支付方式_支付金额_卡号，(无卡的卡号为0)
         * 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡
         */
        String pay_amount = String.format("6_%s_0", actual_pay);

        String goods_data = shoppingcar.goods_category.goods_category_id+"_"+shoppingcar.goods.goods_id+"_"+shoppingcar.goods.buy_num+"_"+shoppingcar.goods.actual_sale_price;
        // 获取用户和卡信息
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登陆者id， APP 必传,微信不传
        params.put("sale_user_id", pending.sale_user_id);// 销售者id， APP 必传,微信不传

        params.put("mobile", pending.mobile);
        params.put("car_plate_no", pending.car_plate_no);
        params.put("goods_data", goods_data);//商品数据,格式： 服务类别id_商品id_购买数量， 多个商品用逗号分割

        params.put("pay_amount", pay_amount);//支付方式:格式--支付方式_支付金额_卡号，(无卡的卡号为000000) 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡
        //        params.put("save_amount", save_amount);// 优惠金额

        if (TextUtils.isEmpty(pending.mileage))
            params.put("mileage", pending.mileage);//行驶里程
        if (!TextUtils.isEmpty(pending.next_maintain))
            params.put("next_maintain", pending.next_maintain);//下次保养时间

        params.put("cart_id",pending.cart_id);
        VictorHttpUtil.doPost(mContext, Define.URL_ORDER_ADD, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        // 跳转到支付结果界面
                        _gotoReceiptResult();

                    }
                });
    }

    /**
     * 跳转到支付结果界面
     */
    private void _gotoReceiptResult() {
        // 跳转到支付结果界面
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCompleted", isCompleted);//信息是否完善
        bundle.putString("provider_user_id", provider_user_id);//用户id
        MyApplication.openActivity(mContext, ReceiptResultActivity.class, bundle);

        // 关闭当前界面
        finish();
    }

    /**
     * 去选择会员卡界面
     */
    private void _gotoSelectVipcard() {

        Bundle bundle = new Bundle();
        bundle.putString("mGoodsId", shoppingcar.goods.goods_id);
        bundle.putDouble("mTotalEb", actual_pay);//所需总eb

        bundle.putInt("mTotalNum", shoppingcar.goods.card_num_price*shoppingcar.goods.buy_num);// 所需总次数
        bundle.putSerializable("mVipcardList", vipcardList);//会员卡列表
        MyApplication.openActivity(mContext, ChooseReceiptVipcardActivity.class, bundle);
    }

    /**
     * 选择会员卡后
     *
     * @param vipcard
     */
    @Subscribe
    public void onSelectedVipcard(final Vipcard vipcard) {
        if (vipcard == null) {
            return;
        }
        //        final Product product = productList.get(selectedProductPos);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * 格式--支付方式_支付金额_卡号，(无卡的卡号为0)
                 * 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡 7.年卡
                 */
                StringBuilder pay_amount = new StringBuilder();
                pay_amount.append(vipcard.card_category_id == 1 ? "3" : (vipcard.card_category_id == 2 ? "4" : "7"))
                        .append("_");
                if (vipcard.card_category_id == 1) {
                    pay_amount.append(shoppingcar.goods.card_num_price * shoppingcar.goods.buy_num);
                } else {
                    pay_amount.append(actual_pay);
                }
                pay_amount.append("_")
                        .append(vipcard.card_no);

                String goods_data = shoppingcar.goods_category.goods_category_id+"_"+shoppingcar.goods.goods_id+"_"+shoppingcar.goods.buy_num+"_"+shoppingcar.goods.actual_sale_price;

                // 获取用户和卡信息
                MyParams params = new MyParams();
                params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
                params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登陆者id， APP 必传,微信不传
                params.put("sale_user_id", pending.sale_user_id);// 销售者id， APP 必传,微信不传
                params.put("pay_amount", pay_amount.toString());//支付方式:格式--支付方式_支付金额_卡id，(无卡的卡id为0) 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡
                params.put("mobile", pending.mobile);
                params.put("car_plate_no", pending.car_plate_no);
                params.put("goods_data", goods_data);//商品数据,格式： 服务类别id_商品id_购买数量， 多个商品用逗号分割

                if (TextUtils.isEmpty(pending.mileage))
                    params.put("mileage", pending.mileage);//行驶里程
                if (!TextUtils.isEmpty(pending.next_maintain))
                    params.put("next_maintain", pending.next_maintain);//下次保养时间

                params.put("cart_id",pending.cart_id);
                VictorHttpUtil.doPost(mContext, Define.URL_ORDER_ADD, params, true, "加载中...",
                        new BaseHttpCallbackListener<Element>() {
                            @Override
                            public void callbackSuccess(String url, Element element) {
                                // 跳转到支付结果界面
                                _gotoReceiptResult();
                            }
                        });
            }
        }, 100);
    }

    /**
     * 微信支付，支付宝支付
     */
    private void _doWxpayAlipay(final String pay_type) {

        /**
         * 格式--支付方式_支付金额_卡号，(无卡的卡号为000000)
         * 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡
         */
        String pay_amount = String.format("%s_%s_0", pay_type, actual_pay);

        String goods_data = shoppingcar.goods_category.goods_category_id+"_"+shoppingcar.goods.goods_id+"_"+shoppingcar.goods.buy_num+"_"+shoppingcar.goods.actual_sale_price;
        // 获取用户和卡信息
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登陆者id， APP 必传,微信不传
        params.put("sale_user_id", pending.sale_user_id);// 销售者id， APP 必传,微信不传
        params.put("pay_amount", pay_amount);//支付方式:格式--支付方式_支付金额_卡id，(无卡的卡id为0) 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡
        params.put("mobile", pending.mobile);
        params.put("car_plate_no", pending.car_plate_no);
        params.put("goods_data", goods_data);//商品数据,格式： 服务类别id_商品id_购买数量， 多个商品用逗号分割
        if (TextUtils.isEmpty(pending.mileage))
            params.put("mileage", pending.mileage);//行驶里程
        if (!TextUtils.isEmpty(pending.next_maintain))
            params.put("next_maintain", pending.next_maintain);//下次保养时间

        params.put("cart_id",pending.cart_id);

        VictorHttpUtil.doPost(mContext, Define.URL_ORDER_ADD, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {

                        JSONObject jsonobj = parseObject(element.data);
                        if (jsonobj == null) {
                            MyApplication.showToast("订单返回数据异常");
                            return;
                        }
                        String url0 = jsonobj.getString("url");//收款二维码图片地址
                        String order_id = jsonobj.getString("order_id");//订单id

                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isCompleted", isCompleted);//信息是否完善
                        bundle.putString("provider_user_id", provider_user_id);//用户id
                        bundle.putString("pay_type", pay_type);//支付方式
                        bundle.putString("actual_pay", MathUtil.getFinanceValue(actual_pay));//支付金额
                        bundle.putString("url", url0);//收款二维码图片地址
                        bundle.putString("order_id", order_id);//订单id
                        MyApplication.openActivity(mContext, ReceiptQrcodeActivity.class, bundle);

                        // 关闭当前界面
                        finish();
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
            if (array.length == PAY_TYPE_ICONS.length) {
                //支付方式图标
                ImageView iv_paytype = AbViewHolder.get(view, R.id.iv_paytype);
                iv_paytype.setImageResource(PAY_TYPE_ICONS[position]);
                // 支付方式名称
                TextView tv_name = AbViewHolder.get(view, R.id.tv_name);
                tv_name.setText(PAY_TYPE_NAMES[position]);
            } else {
                //支付方式图标
                ImageView iv_paytype = AbViewHolder.get(view, R.id.iv_paytype);
                iv_paytype.setImageResource(PAY_TYPE_ICONS2[position]);
                // 支付方式名称
                TextView tv_name = AbViewHolder.get(view, R.id.tv_name);
                tv_name.setText(PAY_TYPE_NAMES2[position]);
            }


        }


    }

    private class ConfirmOrderAdapter extends QuickAdapter<Shoppingcar> {
        public ConfirmOrderAdapter(int layoutResId, List<Shoppingcar> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Shoppingcar item) {

            helper
                    .setText(R.id.tv_goods_category_name, item.goods.goods_name)
                    .setText(R.id.tv_buy_num, "x" + item.goods.buy_num)
                    .setText(R.id.tv_product_price, item.goods.actual_sale_price + "元")
                    .setText(R.id.tv_all_price, item.goods.buy_num * Double.parseDouble(item.goods.actual_sale_price) + "元");
        }
    }
}
