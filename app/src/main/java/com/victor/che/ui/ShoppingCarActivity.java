package com.victor.che.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Shoppingcar;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.AmountView;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.victor.che.R.id.amount_view;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/4/26 0026 10:39.
 * 购物车界面
 */

public class ShoppingCarActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_shopping_car_num)
    TextView tvShoppingCarNum;

    private ShoppingCarAdapter mAdapter;
    private ArrayList<Shoppingcar> mList;
    private int shoppingCarNum;

    @Override
    public int getContentView() {
        return R.layout.activity_shopping_car_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("购物车");

        mList = (ArrayList<Shoppingcar>) getIntent().getSerializableExtra("shoppingcars");
        shoppingCarNum = getIntent().getIntExtra("shoppingCarNum", 0);

        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(20)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线
        mAdapter = new ShoppingCarAdapter(R.layout.item_shoppingcar_list, mList);
        mRecyclerView.setAdapter(mAdapter);

        if (shoppingCarNum != 0) {
            tvShoppingCarNum.setVisibility(View.VISIBLE);
            tvShoppingCarNum.setText(shoppingCarNum + "");
        }

        if (mList == null || shoppingCarNum == 0) {
            //说明无数据
            // 无数据
            View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
            if (mAdapter != null) {
                mAdapter.setEmptyView(common_no_data);
                mAdapter.notifyDataSetChanged();
            }
        }

        // 计算实际支付价格
        _setActualPay(_calcActualPay());
    }

    /**
     * 订单列表适配器
     */
    private class ShoppingCarAdapter extends QuickAdapter<Shoppingcar> {

        public ShoppingCarAdapter(int layoutResId, List<Shoppingcar> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final Shoppingcar item) {

            helper
                    .setText(R.id.tv_product_name, item.goods.goods_name)//商品名称
                    .setText(R.id.tv_product_price, item.goods.sale_price + "")//商品单价
                    .setText(R.id.et_sale_price, item.goods.actual_sale_price + "")//商品售价
                    .setText(R.id.tv_order_sum, Double.parseDouble(item.goods.actual_sale_price)*item.goods.buy_num + "");//商品总价

            final EditText etSalePrice = helper.getView(R.id.et_sale_price);
            final AmountView view = helper.getView(amount_view);
            view.setInputable(false);
            view.setAmount(item.goods.buy_num);

            // 数量发生变化
            view.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                @Override
                public void onAmountChange(View view, int amount) {
                    String sale = etSalePrice.getText().toString().trim();
                    double sale_price = Double.parseDouble(sale);

                    Double total_price = MathUtil.mul(sale_price, amount);
                    helper.setText(R.id.tv_order_sum, MathUtil.getFinanceValue(total_price) + "元");//商品总价改变

                    item.goods.buy_num = amount;

                    setShoppingCarNum();
//                    item.total_price = total_price;
                    // 计算实际支付价格
                    _setActualPay(_calcActualPay());
                }
            });


            etSalePrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {//点击完成

                        String sale = etSalePrice.getText().toString().trim();

                        if (TextUtils.isEmpty(sale)) {
                            MyApplication.showToast("请输入销售价");
                            etSalePrice.requestFocus();
                            return false;
                        }

                        if (!StringUtil.isMoney(sale)) {
                            MyApplication.showToast("销售价格式不正确");
                            etSalePrice.requestFocus();
                            return false;
                        }
                        double sale_price = Double.parseDouble(sale);

                        int count = view.getAmount();

                        Double total_price = MathUtil.mul(sale_price, count);
                        helper.setText(R.id.tv_order_sum, MathUtil.getFinanceValue(total_price) + "元");//商品总价改变

                        item.goods.actual_sale_price = sale_price+"";
//                        item.total_price = total_price;
                        // 计算实际支付价格
                        _setActualPay(_calcActualPay());

                    }
                    return false;
                }
            });

        }
    }

    private void setShoppingCarNum() {
        shoppingCarNum = 0;
        for(Shoppingcar s:mList){
            shoppingCarNum+=s.goods.buy_num;
        }

        tvShoppingCarNum.setText(shoppingCarNum +"");

    }

    /**
     * 设置实际支付价格
     *
     * @param amount
     */
    private void _setActualPay(double amount) {

        //说明数量或价格变化,通知收银界面的购物车
        EventBus.getDefault().post(mList);
        tvAllPrice.setText(MathUtil.getFinanceValue(amount) + "元");
    }

    /**
     * 计算实际支付价格
     */
    private double _calcActualPay() {
        if (CollectionUtil.isEmpty(mList)) {
            return 0;
        }
        Double allPrice = 0.0;
        for (Shoppingcar s : mList) {
            allPrice += s.goods.buy_num*Double.parseDouble(s.goods.actual_sale_price);
        }

        return allPrice;
    }

    /**
     * 删除购物车
     */
    @OnClick(R.id.ll_delete)
    public void deleteShoppingCar() {
        AlertDialogFragment.newInstance(
                "提示",
                "您确定清空购物车吗？",
                "取消",
                "确定",
                null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.clear();
                        View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                        if (mAdapter != null) {

                            mAdapter.setEmptyView(common_no_data);
                            mAdapter.notifyDataSetChanged();
                        }
                        shoppingCarNum = 0;
                        tvShoppingCarNum.setVisibility(View.GONE);
                        tvAllPrice.setText(0.00 + "元");
                        EventBus.getDefault().post(ConstantValue.Event.DELETE_SHOPPING_CAR);
                    }
                })
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }


}
