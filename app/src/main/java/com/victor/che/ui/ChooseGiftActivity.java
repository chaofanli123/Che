package com.victor.che.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Channel;
import com.victor.che.domain.Gift;
import com.victor.che.domain.Product;
import com.victor.che.event.GiftEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AmountView;
import com.victor.che.widget.BottomDialogFragment;
import com.victor.che.widget.LinearLayoutManagerWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择会员卡支付界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 15:55
 */
public class ChooseGiftActivity extends BaseActivity {

    @BindView(R.id.label_gift_value)
    TextView label_gift_value;

    @BindView(R.id.et_gift_value)
    EditText et_gift_value;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.area_gift)
    View area_gift;

    private ArrayList<Gift> giftList;
    private GiftListAdapter mAdapter;

    private List<Channel> categoryList = new ArrayList<>();// 服务类别
    private CategoryListAdapter categoryListAdapter;

    private List<Product> productList = new ArrayList<>();// 产品列表
    private ProductListAdapter productListAdapter;

    private int card_category_id;//卡类型id

    @Override
    public int getContentView() {
        return R.layout.activity_choose_gift;
    }

    @Override
    public void initView() {
        super.initView();

        // 设置标题
        setTitle("选择赠品");
        ((TextView) findViewById(R.id.topbar_right)).setText("确定");

        giftList = (ArrayList<Gift>) getIntent().getSerializableExtra("mChoosedGift");//获取已选择的赠品
        if (giftList == null) {
            giftList = new ArrayList<>();
        }

        // 赠送次数或余额
        String giftValue = getIntent().getStringExtra("mGiftValue");
        et_gift_value.setText(giftValue);

        card_category_id = getIntent().getIntExtra("card_category_id", 0);
        switch (card_category_id) { //  #卡类型 1-次卡 2-储值卡, 3-年卡
            case 1:// 1-次卡
                area_gift.setVisibility(View.VISIBLE);
                label_gift_value.setText("赠送次数");
                et_gift_value.setHint("请输入次数");
                et_gift_value.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 2:// 2-储值卡
                area_gift.setVisibility(View.VISIBLE);
                label_gift_value.setText("赠送金额");
                et_gift_value.setHint("请输入金额");
                et_gift_value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case 3:// 3-年卡
                area_gift.setVisibility(View.GONE);
                break;
            default:
                MyApplication.showToast("卡类型异常，请稍后重试");
                return;
        }

        categoryList = (List<Channel>) getIntent().getSerializableExtra("mCategoryList");
        if (CollectionUtil.isEmpty(categoryList)) {
            MyApplication.showToast("服务类型为空");
            return;
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mAdapter = new GiftListAdapter(R.layout.item_gift, giftList);
        mRecyclerView.setAdapter(mAdapter);

        categoryListAdapter = new CategoryListAdapter(mContext, R.layout.item_bottom_dialog, categoryList);
        productListAdapter = new ProductListAdapter(mContext, R.layout.item_bottom_dialog, productList);

    }

    /**
     * 添加赠品
     */
    @OnClick(R.id.btn_operate)
    void doAddGift() {
        Gift gift = new Gift();
        gift.name = "赠品" + (giftList.size() + 1);
        giftList.add(gift);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 确定
     */
    @OnClick(R.id.topbar_right)
    void doConfirm() {
        StringBuilder gift_text = new StringBuilder();// 赠送的文本
        String strValue = "";
        if (card_category_id == 1 || card_category_id == 2) {//次卡或储值卡
            strValue = et_gift_value.getText().toString().trim();
            String msg = card_category_id == 1 ? "次数" : "金额";
            if (!StringUtil.isEmpty(strValue)) {
                double value = Double.parseDouble(strValue);
                if (value <= 0) {
                    MyApplication.showToast("赠送\" + msg + \"必须为正数");
                    et_gift_value.requestFocus();
                    return;
                }

                // 此处实现的是“赠送2次”或“赠送15.88元”
                gift_text.append("赠送");
                if (card_category_id == 1) {
                    gift_text.append((int) value).append("次");
                } else if (card_category_id == 2) {
                    gift_text.append(MathUtil.getMoneyText(value));
                }
            }
        }

        /*******赠品组装成要提交的格式********/
        StringBuilder gift_service = new StringBuilder();//赠送的服务
        // 商品id=赠送次数 eg:1=10(1.0.4新增)
        if (!CollectionUtil.isEmpty(giftList)) {
            for (Gift item : giftList) {
                ArrayList<Product> goods = categoryList.get(item.selectCategoryPos).goods;
                if (!CollectionUtil.isEmpty(goods)) {
                    Product p = categoryList.get(item.selectCategoryPos).goods.get(item.selectProductPos);
                    gift_service.append(",")
                            .append(p.goods_id)
                            .append("=")
                            .append(item.count);

                    gift_text.append(",")
                            .append("赠送")
                            .append(p.name)
                            .append("（服务名称）")
                            .append(item.count)
                            .append("次（数量）");
                }
            }
            if (gift_service.length() > 0) {
                gift_service.deleteCharAt(0);
            }
        }
        // 回传数据
        GiftEvent event = new GiftEvent(strValue, gift_service.toString(), gift_text.toString());
        event.giftList = giftList;
        Logger.e(event.toString());
        EventBus.getDefault().post(event);
        finish();
    }

    /**
     * 展示所有产品弹框
     */
    private void _showCategoryDialog(final BaseViewHolder helper) {
        this.helper = helper;
        if (CollectionUtil.isEmpty(categoryList)) {
            MyApplication.showToast("服务列表为空");
            return;
        }
        categoryListAdapter.setData(categoryList);
        BottomDialogFragment.newInstance(categoryListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                giftList.get(helper.getLayoutPosition()).selectCategoryPos = position;
                //                productListAdapter.notifyDataSetChanged();
                helper.setText(R.id.tv_category, categoryList.get(position).name);

                if (CollectionUtil.isEmpty(categoryList.get(position).goods)) {
                    helper.setText(R.id.tv_product, "还未有上架的服务");
                } else {
                    helper.setText(R.id.tv_product, categoryList.get(position).goods.get(0).name);// 默认选中第一项产品
                    giftList.get(helper.getLayoutPosition()).selectProductPos = 0;
                }

            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 展示所有产品弹框
     */
    void _showProductDialog(final BaseViewHolder helper) {
        this.helper = helper;
        int selectCategoryPos = giftList.get(helper.getLayoutPosition()).selectCategoryPos;
        productList = categoryList.get(selectCategoryPos).goods;
        if (CollectionUtil.isEmpty(productList)) {//某分类下的商品列表为空
            MyApplication.showToast("服务列表为空");
            return;
        }
        productListAdapter.setData(productList);
        productListAdapter.notifyDataSetChanged();
        BottomDialogFragment.newInstance(productListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                giftList.get(helper.getLayoutPosition()).selectProductPos = position;//记录选中产品的位置
                helper.setText(R.id.tv_product, productList.get(position).name);
            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 订单列表适配器
     */
    private class GiftListAdapter extends QuickAdapter<Gift> {

        public GiftListAdapter(int layoutResId, List<Gift> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final Gift item) {
            helper.setText(R.id.tv_gift_name, item.name)//订单类型
                    .setText(R.id.tv_category, categoryList.get(item.selectCategoryPos).name)//服务类别
                    .setText(R.id.tv_product, categoryList.get(item.selectCategoryPos).goods.get(item.selectProductPos).name)//产品名称
            ;
            TextView tv_product = helper.getView(R.id.tv_product);
            ArrayList<Product> productList = categoryList.get(item.selectCategoryPos).goods;
            if (CollectionUtil.isEmpty(productList)) {
                MyApplication.showToast("服务列表为空");
                tv_product.setText("还未有上架的服务");
                return;
            }
            // 数量发生变化
            AmountView amount_view = helper.getView(R.id.amount_view);
            amount_view.setAmount(item.count);
            amount_view.setInputable(false);// 禁止输入
            amount_view.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                @Override
                public void onAmountChange(View view, int amount) {
                    giftList.get(helper.getLayoutPosition()).count = amount;
                }
            });

            // 取消
            helper.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = helper.getLayoutPosition();
                    giftList.remove(pos);
                    for (int i = pos; i < giftList.size(); i++) {
                        giftList.get(i).name = "赠品" + (i + 1);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });

            // 选择服务类别
            helper.getView(R.id.area_choose_category)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            _showCategoryDialog(helper);
                        }
                    });

            // 选择产品
            helper.getView(R.id.area_choose_product)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            _showProductDialog(helper);
                        }
                    });
        }
    }

    private BaseViewHolder helper;


    /**
     * 产品列表适配器
     */
    private class CategoryListAdapter extends VictorBaseListAdapter<Channel> {

        public CategoryListAdapter(Context context, int layoutResId, List<Channel> giftList) {
            super(context, layoutResId, giftList);
        }

        @Override
        public void bindView(int position, View view, Channel entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.name);

            boolean selected = giftList.get(helper.getLayoutPosition()).selectCategoryPos == position;
            textView.setTextColor(getResources().getColor(selected ? R.color.theme_color : R.color.black_text));
        }
    }

    /**
     * 产品列表适配器
     */
    private class ProductListAdapter extends VictorBaseListAdapter<Product> {

        public ProductListAdapter(Context context, int layoutResId, List<Product> giftList) {
            super(context, layoutResId, giftList);
        }

        @Override
        public void bindView(int position, View view, Product entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.name);

            boolean selected = giftList.get(helper.getLayoutPosition()).selectProductPos == position;
            textView.setTextColor(getResources().getColor(selected ? R.color.theme_color : R.color.black_text));
        }
    }
}
