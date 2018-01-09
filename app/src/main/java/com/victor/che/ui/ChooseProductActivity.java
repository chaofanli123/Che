package com.victor.che.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Channel;
import com.victor.che.domain.Product;
import com.victor.che.domain.Vipcard;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 选择可用服务
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/2/7 0007 17:23
 */
public class ChooseProductActivity extends BaseActivity {

    @BindView(R.id.rv_category)
    RecyclerView rv_category;

    @BindView(R.id.rv_product)
    RecyclerView rv_product;

    @BindView(R.id.chk_select_all)
    CheckBox chk_select_all;

    private ArrayList<Channel> allCategoryList = new ArrayList<>();//所有分类和产品
    private CategoryListAdapter categoryListAdapter;
    private ProductListAdapter productListAdapter;

    private int categoryPos = 0;//分类选中的下标
    private int productPos = 0;//选中产品的下标

    private Vipcard mVipcard;//卡详情界面传过来的已选服务
    private boolean mEdit;//是否是编辑的动作

    @Override
    public int getContentView() {
        return R.layout.activity_choose_product;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("可用服务");

        mVipcard = (Vipcard) getIntent().getSerializableExtra("mVipcard");//
        allCategoryList = (ArrayList<Channel>) getIntent().getSerializableExtra("mAllCategoryList");//所有分类和产品
        if (CollectionUtil.isEmpty(allCategoryList)) {
            MyApplication.showToast("服务为空，请稍后重试");
            return;
        }
        mEdit = getIntent().getBooleanExtra("mEdit", false);

        rv_category.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        rv_category.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        categoryListAdapter = new CategoryListAdapter(R.layout.item_product_category, allCategoryList);
        rv_category.setAdapter(categoryListAdapter);

        rv_product.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        rv_product.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        productListAdapter = new ProductListAdapter(R.layout.item_product, null);
        rv_product.setAdapter(productListAdapter);

        // 渲染分类列表
        categoryListAdapter.setNewData(allCategoryList);
        categoryListAdapter.notifyDataSetChanged();

        // 设置是否全选
        chk_select_all.setText("全部" + allCategoryList.get(0).name + "服务");
        chk_select_all.setChecked(allCategoryList.get(0).checked);

        // 渲染产品列表
        productListAdapter.setNewData(allCategoryList.get(0).goods);
        productListAdapter.notifyDataSetChanged();

        // 点击分类
        rv_category.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                categoryPos = position;
                categoryListAdapter.notifyDataSetChanged();

                // 设置是否全选
                chk_select_all.setText("全部" + allCategoryList.get(categoryPos).name + "服务");
                chk_select_all.setChecked(allCategoryList.get(categoryPos).checked);

                // 更新产品列表
                productListAdapter.setNewData(allCategoryList.get(categoryPos).goods);
                productListAdapter.notifyDataSetChanged();
            }
        });


        // 点击产品
        rv_product.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                productPos = position;

                Product current = allCategoryList.get(categoryPos).goods.get(position);

                current.checked = !current.checked;
                if (!current.checked) {// 某一个未被选中，全选取消
                    chk_select_all.setChecked(false);
                    allCategoryList.get(categoryPos).checked = false;
                }

                productListAdapter.notifyDataSetChanged();
            }
        });

    }

    // 点击全选
    @OnClick(R.id.chk_select_all)
    void toggleSelectAll() {
        if (CollectionUtil.isEmpty(allCategoryList)) {
            return;
        }

        // 记录分类是否全选
        allCategoryList.get(categoryPos).checked = chk_select_all.isChecked();

        // 所有产品是否被选中
        ArrayList<Product> list = allCategoryList.get(categoryPos).goods;
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).checked = chk_select_all.isChecked();
        }

        // 更新产品列表
        productListAdapter.notifyDataSetChanged();
    }

    /**
     * 确认范围
     */
    @OnClick(R.id.topbar_right)
    void doOperate() {

        if (mEdit) {//编辑卡操作
            MyParams params = new MyParams();
            params.put("provider_card_id", mVipcard.provider_card_id);// 服务商卡id
            String rules = _getRules();
            if (!StringUtil.isEmpty(rules)) {
                params.put("rules", _getRules());// 适用的服务, 数据格式: 参数名=值,参数名1=值1 (参数名对应值见备注)
            }
            VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_CARD_EDIT, params, true, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {
                            MyApplication.showToast("修改成功");

                            // 传送数据
                            EventBus.getDefault().post(allCategoryList);
                            finish();
                        }
                    });
        } else {// 添加卡操作
            // 传送数据
            EventBus.getDefault().post(allCategoryList);

            finish();
        }
    }

    /**
     * 获取适用的服务范围
     *
     * @return
     */
    private String _getRules() {
        StringBuilder builder = new StringBuilder();
        if (!CollectionUtil.isEmpty(allCategoryList)) {
            for (Channel category : allCategoryList) {
                if (category.checked) {//分类全选
                    builder.append(",").append("goods_category_id=").append(category.goods_category_id);
                } else {// 分类未全选
                    if (!CollectionUtil.isEmpty(category.goods)) {
                        for (Product product : category.goods) {
                            if (product.checked) {
                                builder.append(",").append("goods_id=").append(product.goods_id);
                            }
                        }
                    }
                }
            }
            if (builder.length() > 0) {//移除首位的逗号“,”
                builder.deleteCharAt(0);
            }
        }
        return builder.toString();
    }

    /**
     * 分类列表适配器
     */
    private class CategoryListAdapter extends QuickAdapter<Channel> {

        public CategoryListAdapter(int layoutResId, List<Channel> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Channel item) {
            helper.setText(R.id.text, item.name)// 分类
                    .setBackgroundRes(R.id.text, helper.getLayoutPosition() == categoryPos ? R.color.category_checked : R.color.white)
                    .setTextColor(R.id.text, getResources().getColor(helper.getLayoutPosition() == categoryPos ? R.color.theme_color : R.color.dark_gray_text));
        }
    }

    /**
     * 产品列表适配器
     */
    private class ProductListAdapter extends QuickAdapter<Product> {

        public ProductListAdapter(int layoutResId, List<Product> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Product item) {
            helper.setText(R.id.text, item.name)// 产品
                    .setImageResource(R.id.imageView, item.checked ? R.drawable.ic_checked : R.drawable.ic_unchecked);// 是否被选中
        }
    }
}
