package com.victor.che.ui;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Channel;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.BottomDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加产品界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/2 0002 17:27
 */
public class AddProductActivity extends BaseActivity {

    @BindView(R.id.tv_category)
    TextView tv_category;

    @BindView(R.id.et_product_name)
    EditText et_product_name;

    @BindView(R.id.et_original_price)
    EditText et_original_price;

    @BindView(R.id.et_sale_price)
    EditText et_sale_price;

    private List<Channel> categoryList = new ArrayList<>();
    private CategoryListAdapter categoryListAdapter;

    private int selectedCategoryPos = 0;//选中的类别位置

    @Override
    public int getContentView() {
        return R.layout.activity_add_product;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("新增服务");

        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_product_name));//产品名称必填
        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_sale_price));//原价必填

        ((TextView) findViewById(R.id.topbar_right)).setText("提交");

        // 获取所有产品类型
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        VictorHttpUtil.doGet(mContext, Define.URL_GOODS_CATEGORY_LIST, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        categoryList = JSON.parseArray(element.body, Channel.class);
                        if (CollectionUtil.isEmpty(categoryList)) {
                            MyApplication.showToast("服务类型为空");
                            return;
                        }
                        tv_category.setText(categoryList.get(0).name);
                        categoryListAdapter = new CategoryListAdapter(mContext, R.layout.item_bottom_dialog, categoryList);
                    }
                });
    }

    /**
     * 展示所有类型弹框
     */
    @OnClick(R.id.area_category)
    void showDialog() {
        if (CollectionUtil.isEmpty(categoryList)) {
            MyApplication.showToast("服务类型为空");
            return;
        }
        BottomDialogFragment.newInstance(categoryListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryPos = position;
                categoryListAdapter.notifyDataSetChanged();
                tv_category.setText(categoryList.get(position).name);

            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 提交
     */
    @OnClick(R.id.topbar_right)
    void doOperate() {

        if (CollectionUtil.isEmpty(categoryList)) {
            MyApplication.showToast("服务类型为空");
            return;
        }

        String productName = et_product_name.getText().toString().trim();
        if (StringUtil.isEmpty(productName)) {
            MyApplication.showToast("名称不能为空");
            return;
        }

        String strOriginPrice = et_original_price.getText().toString().trim();
        double originPrice = 0;
        if (!StringUtil.isEmpty(strOriginPrice)) {

            if (!StringUtil.isMoney(strOriginPrice)) {//格式不正确
                MyApplication.showToast("原价格式不正确");
                et_original_price.requestFocus();
                return;
            }

            originPrice = Double.parseDouble(strOriginPrice);
            if (originPrice <= 0) {
                MyApplication.showToast("原价必须大于0");
                et_original_price.requestFocus();
                return;
            }
        }

        String strSalePrice = et_sale_price.getText().toString().trim();
        if (StringUtil.isEmpty(strSalePrice)) {
            MyApplication.showToast("请输入售价");
            et_sale_price.requestFocus();
            return;
        }
        if (!StringUtil.isMoney(strSalePrice)) {
            MyApplication.showToast("售价格式不正确");
            et_sale_price.requestFocus();
            return;
        }
        double salePrice = Double.parseDouble(strSalePrice);
        if (salePrice <= 0) {
            MyApplication.showToast("售价必须大于0");
            et_sale_price.requestFocus();
            return;
        }

        if (StringUtil.isNotEmpty(strOriginPrice)) {//原价不为空，判断与销售价的大小关系
            if (originPrice < salePrice) {
                MyApplication.showToast("原价不能小于售价");
                et_original_price.requestFocus();
                return;
            }
        }

        if (originPrice <= 0) {//没填原价， 默认"原价=销售价+5"
            originPrice = salePrice + ConstantValue.DEFAULT_PRICE_OFFSET;
        }

        // 添加商品
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("goods_category_id", categoryList.get(selectedCategoryPos).goods_category_id);//服务项目编号
        params.put("goods_name", productName);// 商品名称
        params.put("price", originPrice);// 原价
        params.put("sale_price", salePrice);// 售价
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登录者id(登录后返回的user_id)
        VictorHttpUtil.doPost(mContext, Define.URL_GOODS_ADD, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast("添加服务成功");

                        EventBus.getDefault().post(ConstantValue.Event.REFRESH_PRODUCT_LIST);

                        finish();
                    }
                });
    }

    /**
     * 产品列表适配器
     */
    private class CategoryListAdapter extends VictorBaseListAdapter<Channel> {

        private Context mContext;

        public CategoryListAdapter(Context context, int layoutResId, List<Channel> mList) {
            super(context, layoutResId, mList);
            this.mContext = context;
        }

        @Override
        public void bindView(int position, View view, Channel entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.name);
            textView.setTextColor(getResources().getColor(selectedCategoryPos == position ? R.color.theme_color : R.color.black_text));
        }
    }
}
