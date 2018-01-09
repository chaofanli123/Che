package com.victor.che.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Order;
import com.victor.che.domain.OrderItem;
import com.victor.che.util.MathUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.FullyLinearLayoutManager;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单详情界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 16:32
 */
public class OrderDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_order_state)
    TextView tv_order_state;

    @BindView(R.id.tv_order_no)
    TextView tv_order_no;

    @BindView(R.id.tv_customer_name)
    TextView tv_customer_name;

    @BindView(R.id.tv_customer_mobile)
    TextView tv_customer_mobile;

    @BindView(R.id.tv_pln)
    TextView tv_pln;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_order_sum)
    TextView tv_order_sum;//总价

    @BindView(R.id.tv_order_coupon)
    TextView tv_order_coupon;//优惠

    @BindView(R.id.tv_actual_pay)
    TextView tv_actual_pay;//实际支付

    @BindView(R.id.tv_pay_type)
    TextView tv_pay_type;//支付方式

    @BindView(R.id.tv_trade_no)
    TextView tv_trade_no;//交易号

    @BindView(R.id.tv_worker)
    TextView tv_worker;//服务师傅

    @BindView(R.id.tv_staff_user)
    TextView tv_staff_user;//收银员

    @BindView(R.id.tv_order_time)
    TextView tv_order_time;//下单时间

    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;//付款时间

    @BindView(R.id.tv_rating)
    TextView tv_rating;

    @BindView(R.id.ratingbar)
    SimpleRatingBar ratingbar;

    @BindView(R.id.tv_comment_content)
    TextView tv_comment_content;

    @BindView(R.id.area_pay_type)
    View area_pay_type;

    @BindView(R.id.area_trade_no)
    View area_trade_no;

    @BindView(R.id.area_staff_user)
    View area_staff_user;

    @BindView(R.id.area_pay_time)
    View area_pay_time;

    @BindView(R.id.area_comment)
    View area_comment;
    @BindView(R.id.tv_order_coupon_price)
    TextView tvOrderCouponPrice; //优惠券面值

    private Order mOrder;

    private String provider_user_id;

    @Override
    public int getContentView() {
        return R.layout.activity_order_details;
    }

    private int status = 0;//是否删除

    @Override
    protected void initView() {
        // 设置标题
        setTitle("订单详情");

        mOrder = (Order) getIntent().getSerializableExtra("mOrder");

        if (mOrder == null) {
            MyApplication.showToast("订单编号为空，请稍后重试");
            return;
        }

        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线

        /****订单信息****/
        tv_order_no.setText("订单号：" + mOrder.order_no);// 订单号
        tv_order_state.setText(mOrder.getOrderState());//订单状态
        tv_order_sum.setText(MathUtil.getMoneyText(mOrder.total_actual_price));//订单总价
        tvOrderCouponPrice.setText(MathUtil.getMoneyText(mOrder.coupon_amount));//优惠券价值

        // 获取订单详情
        MyParams params = new MyParams();
        params.put("order_id", mOrder.order_id);//订单id
        VictorHttpUtil.doGet(mContext, Define.URL_ORDER_DETAILS, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = JSON.parseObject(element.data);

                        /****用户信息****/
                        JSONObject user_info = jsonobj.getJSONObject("user_info");
                        if (user_info != null) {
                            status = user_info.getIntValue("status");//-1删除，
                            String name = user_info.getString("name");
                            if (status == -1) {
                                tv_customer_name.setText(Html.fromHtml(name + "<font color='#e53935'>(该用户已被删除)</font>"));//用户名
                            } else {
                                tv_customer_name.setText(name);//用户名
                            }
                            tv_customer_mobile.setText(user_info.getString("mobile"));//用户手机号
                            String pln = user_info.getString("car_plate_no");
                            if (StringUtil.isEmpty(pln)) {
                                tv_pln.setVisibility(View.GONE);
                            } else {
                                tv_pln.setVisibility(View.VISIBLE);
                                tv_pln.setText(pln);//车牌号
                            }
                        }

                        /****服务详情****/
                        String order_detail = jsonobj.getString("order_detail");
                        if (!StringUtil.isEmpty(order_detail)) {
                            List<OrderItem> orderItems = JSON.parseArray(order_detail, OrderItem.class);
                            OrderItemAdapter mAdapter = new OrderItemAdapter(R.layout.item_order_product, orderItems);
                            //                            mAdapter.setNewData(orderItems);
                            mRecyclerView.setAdapter(mAdapter);
                        }

                        /****订单信息****/
                        JSONObject order_info = jsonobj.getJSONObject("order_info");
                        if (order_info != null) {
                            tv_order_sum.setText(MathUtil.getMoneyText(order_info.getDoubleValue("total_price")));//总价
                            tv_order_coupon.setText(MathUtil.getMoneyText(order_info.getDoubleValue("save_amount")));//优惠
                            tv_actual_pay.setText(MathUtil.getMoneyText(order_info.getDoubleValue("total_actual_price")));//实际支付
                            tv_worker.setText(order_info.getString("sale_user"));//服务师傅
                            tv_staff_user.setText(order_info.getString("staff_user"));//收银员
                            tv_order_time.setText(order_info.getString("create_time"));//下单时间
                            tv_pay_time.setText(order_info.getString("pay_time"));//付款时间
                            provider_user_id = order_info.getString("provider_user_id");
                            tvOrderCouponPrice.setText(MathUtil.getMoneyText(order_info.getDoubleValue("coupon_amount")));//优惠券价钱
                        }

                        // #订单状态(1未支付，2已支付，3待评价，4已评价 6退款)
                        int visiblity = mOrder.order_status == 1 ? View.GONE : View.VISIBLE;
                        area_pay_type.setVisibility(visiblity);//支付方式
                        area_trade_no.setVisibility(visiblity);//交易号
                        area_staff_user.setVisibility(visiblity);//收银员
                        area_pay_time.setVisibility(visiblity);//付款时间
                        // 是否显示评价区域
                        area_comment.setVisibility(mOrder.order_status == 4 ? View.VISIBLE : View.GONE);

                        /****支付信息****/
                        if (mOrder.order_status != 1) {// 已支付过
                            JSONObject order_pay_info = jsonobj.getJSONObject("order_pay_info");
                            if (order_pay_info != null) {
                                tv_pay_type.setText(order_pay_info.getString("pay_method_text"));//支付方式
                                tv_trade_no.setText(order_pay_info.getString("pay_no"));//交易号
                            }
                        }

                        /****评价信息****/
                        if (area_comment.getVisibility() == View.VISIBLE) {
                            JSONObject order_comment = jsonobj.getJSONObject("order_comment");
                            if (order_comment != null) {
                                area_comment.setVisibility(View.VISIBLE);
                                float rating_score = order_comment.getFloat("score");
                                tv_rating.setText(rating_score + "分");//评分
                                ratingbar.setRating(rating_score);//评分控件
                                tv_comment_content.setText(order_comment.getString("content"));//评论内容
                            } else {
                                area_comment.setVisibility(View.GONE);
                            }
                        }
                    }
                });

    }

    /**
     * 复制订单号
     */
    @OnClick(R.id.tv_copy)
    void doCopy() {
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, mOrder.order_no));
        MyApplication.showToast("订单号复制成功");
    }

    /**
     * 复制订单号
     */
    @OnClick(R.id.area_customer_info)
    void gotoCustomerEdit() {
        if (status != -1) {
            Bundle bundle = new Bundle();
            bundle.putString("provider_user_id", provider_user_id);
            MyApplication.openActivity(mContext, CustomerDetailsActivity.class, bundle);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    /**
     * 订单子项适配器
     */
    private class OrderItemAdapter extends QuickAdapter<OrderItem> {

        public OrderItemAdapter(int layoutResId, List<OrderItem> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderItem item) {
            helper.setText(R.id.tv_product_name, item.goods_name)//商品名称
                    .setText(R.id.tv_product_price, MathUtil.getMoneyText(item.sale_price))//商品销售价
                    .setText(R.id.tv_available_num, "可使用数量：" + item.available_num)//产品名称
                    .setText(R.id.tv_product_count, "x " + item.buy_num)//总数量
            ;
        }
    }
}