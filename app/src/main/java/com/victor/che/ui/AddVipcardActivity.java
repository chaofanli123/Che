package com.victor.che.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
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
import com.victor.che.domain.Product;
import com.victor.che.domain.VipcardCategory;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.BottomDialogFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.text.InputType.TYPE_CLASS_NUMBER;

/**
 * 新增会员卡界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/2 0002 14:06
 */
public class AddVipcardActivity extends BaseActivity {

    @BindView(R.id.et_vipcard_name)
    EditText et_vipcard_name;

    @BindView(R.id.et_face_value)
    EditText et_facevalue;

    @BindView(R.id.et_original_price)
    EditText et_original_price;

    @BindView(R.id.et_sale_price)
    EditText et_sale_price;

    @BindView(R.id.tv_category)
    TextView tv_category;

    @BindView(R.id.label_face_value)
    TextView label_face_value;

    @BindView(R.id.tv_service_content)
    TextView tv_service_content;

    private List<VipcardCategory> categoryList;
    private int selectedCategoryPos = 0;//选中的类别位置
    private VipcardCategoryListAdapter vipcardCategoryListAdapter;

    private ArrayList<Channel> allCategoryList;// 选中的产品适用范围

    @Override
    public int getContentView() {
        return R.layout.activity_add_vipcard;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("新增卡");

        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_vipcard_name));//卡名称必填
        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_sale_price));//售价必填

        //        获取卡类型
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        VictorHttpUtil.doGet(mContext, Define.URL_CARD_CATEGORY_LIST, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        categoryList = JSON.parseArray(element.body, VipcardCategory.class);
                        if (CollectionUtil.isEmpty(categoryList)) {
                            MyApplication.showToast("会员卡类型列表为空");
                            return;
                        }
                        vipcardCategoryListAdapter = new VipcardCategoryListAdapter(mContext, R.layout.item_bottom_dialog, categoryList);
                        _render();
                    }
                });

        // 获取所有分类和产品
        _doGetAllCategoryAndProduct();
    }

    /**
     * 获取所有分类和产品
     */
    private void _doGetAllCategoryAndProduct() {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
        VictorHttpUtil.doGet(mContext, Define.URL_GOODS_CATEGORY_DETAIL, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        allCategoryList = (ArrayList<Channel>) JSON.parseArray(element.body, Channel.class);

                        if (CollectionUtil.isEmpty(allCategoryList)) {
                            MyApplication.showToast("服务类型为空");
                            return;
                        }
                        // 过滤掉无产品的分类
                        List<Channel> temp = new ArrayList<>();
                        for (Channel item : allCategoryList) {
                            if (!CollectionUtil.isEmpty(item.goods)) {
                                temp.add(item);
                            }
                        }
                        allCategoryList.clear();
                        allCategoryList.addAll(temp);
                    }
                });
    }

    /**
     * 展示所有卡类型弹框
     */
    @OnClick(R.id.area_category)
    void showCategoryDialog() {
        if (CollectionUtil.isEmpty(categoryList)) {
            MyApplication.showToast("卡类型列表为空");
            return;
        }
        BottomDialogFragment.newInstance(vipcardCategoryListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedCategoryPos == position) {
                    return;
                }
                selectedCategoryPos = position;

                _render();
            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 渲染界面
     */
    private void _render() {
        vipcardCategoryListAdapter.notifyDataSetChanged();
        tv_category.setText(categoryList.get(selectedCategoryPos).name);

        et_facevalue.setText("");

        // 切换label_face_value和输入框限制
        if (1 == categoryList.get(selectedCategoryPos).card_category_id) {// 选中的是次卡
            label_face_value.setText("次数");
            et_facevalue.setHint("请输入次数");
            et_facevalue.setInputType(TYPE_CLASS_NUMBER);
        } else if (2 == categoryList.get(selectedCategoryPos).card_category_id) {//选中的是储值卡
            label_face_value.setText("余额");
            et_facevalue.setHint("请输入余额");
            et_facevalue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (3 == categoryList.get(selectedCategoryPos).card_category_id) {//选中的是储值卡
            label_face_value.setText("年数");
            et_facevalue.setHint("请输入年数，如1、2");
            et_facevalue.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        ViewUtil.setLabelRequired(label_face_value);//售价必填
    }

    /**
     * 选择服务范围
     */
    @OnClick(R.id.area_choose_product)
    void gotoChooseProduct() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mAllCategoryList", allCategoryList);
        MyApplication.openActivity(mContext, ChooseProductActivity.class, bundle);
    }

    /**
     * 选择完适用服务范围后
     *
     * @param allCategoryList
     */
    @Subscribe
    public void onChooseProducts(ArrayList<Channel> allCategoryList) {
        this.allCategoryList = allCategoryList;

        if (CollectionUtil.isEmpty(allCategoryList)) {
            tv_service_content.setText(getString(R.string.choose_product_range));
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < allCategoryList.size(); i++) {
                Channel category = allCategoryList.get(i);
                if (category.checked) {//分类全选
                    builder.append("全部" + category.name + "服务").append(",");
                } else {//分类未全选
                    if (!CollectionUtil.isEmpty(category.goods)) {
                        for (int j = 0; j < category.goods.size(); j++) {
                            Product product = category.goods.get(j);
                            if (product.checked) {
                                builder.append(product.name).append(",");
                            }
                        }
                    }
                }
            }
            if (builder.length() >= 1) {//移除最后的逗号“,”
                builder.deleteCharAt(builder.length() - 1);
            } else {// 未选中任何一个产品
                builder.append(getString(R.string.choose_product_range));
            }
            tv_service_content.setText(builder.toString());
        }
    }

    /**
     * 保存
     */
    @OnClick(R.id.topbar_right)
    void doOperate() {
        if (CollectionUtil.isEmpty(categoryList)) {
            MyApplication.showToast("会员卡类型列表为空");
            return;
        }

        String cardname = et_vipcard_name.getText().toString().trim();
        if (StringUtil.isEmpty(cardname)) {// 卡名称为空
            MyApplication.showToast("卡名称不能为空");
            et_vipcard_name.requestFocus();
            return;
        }
        String strValue = et_facevalue.getText().toString().trim();
        int num = 0;
        double facevalue = 0;
        if (1 == categoryList.get(selectedCategoryPos).card_category_id) {//次卡
            if (StringUtil.isEmpty(strValue)) {
                MyApplication.showToast("请输入次数");
                et_facevalue.requestFocus();
                return;
            }
            num = Integer.parseInt(strValue);
            if (num <= 0) {
                MyApplication.showToast("次数必须为正数");
                et_facevalue.requestFocus();
                return;
            }
        } else if (2 == categoryList.get(selectedCategoryPos).card_category_id) {//储值卡
            if (StringUtil.isEmpty(strValue)) {
                MyApplication.showToast("请输入余额");
                et_facevalue.requestFocus();
                return;
            }
            facevalue = Double.parseDouble(strValue);
            if (facevalue <= 0) {
                MyApplication.showToast("次数必须为正数");
                et_facevalue.requestFocus();
                return;
            }
        } else if (3 == categoryList.get(selectedCategoryPos).card_category_id) {//储值卡
            if (StringUtil.isEmpty(strValue)) {
                MyApplication.showToast("请输入年数");
                et_facevalue.requestFocus();
                return;
            }
            facevalue = Double.parseDouble(strValue);
            if (facevalue <= 0) {
                MyApplication.showToast("年数必须为正数");
                et_facevalue.requestFocus();
                return;
            }
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
                MyApplication.showToast("原价不能低于售价");
                et_original_price.requestFocus();
                return;
            }
        }

        if (originPrice <= 0) {//没填原价， 默认"原价=销售价+5"
            originPrice = salePrice + ConstantValue.DEFAULT_PRICE_OFFSET;
        }

        // 服务商添加卡
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登录者id
        params.put("name", cardname);// 商品名称
        params.put("card_category_id", categoryList.get(selectedCategoryPos).card_category_id);//	卡类型id(card_type接口返回的id值)
        params.put("card_value", strValue);// 卡面值（可以为次数、储值数或年数）
        params.put("price", originPrice);// 原价
        params.put("sale_price", salePrice);// 售价
        params.put("rules", _getRules());// 适用的服务, 数据格式: 参数名=值,参数名1=值1 (参数名对应值见备注)
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_CARD_ADD, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast("添加会员卡成功");

                        //                        EventBus.getDefault().post(ConstantValue.Event.REFRESH_PRODUCT_LIST);

                        finish();
                    }
                });
    }

    /**
     * 获取适用的服务范围
     *
     * @return
     */
    private String _getRules() {
        StringBuilder builder = new StringBuilder();
        if (!CollectionUtil.isEmpty(allCategoryList)) {
            for (int i = 0; i < allCategoryList.size(); i++) {
                Channel category = allCategoryList.get(i);
                if (category.checked) {//分类全选
                    builder.append("goods_category_id=").append(category.goods_category_id).append(",");
                } else {// 分类未全选
                    if (!CollectionUtil.isEmpty(category.goods)) {
                        for (int j = 0; j < category.goods.size(); j++) {
                            Product product = category.goods.get(j);
                            if (product.checked) {
                                builder.append("goods_id=").append(product.goods_id).append(",");
                            }
                        }
                    }
                }
            }
            if (builder.length() >= 1) {//移除最后的逗号“,”
                builder.deleteCharAt(builder.length() - 1);
            }
        }
        return builder.toString();
    }

    /**
     * 卡类型列表适配器
     */
    private class VipcardCategoryListAdapter extends VictorBaseListAdapter<VipcardCategory> {

        public VipcardCategoryListAdapter(Context context, int layoutResId, List<VipcardCategory> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, VipcardCategory entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.name);
            textView.setTextColor(getResources().getColor(selectedCategoryPos == position ? R.color.theme_color : R.color.black_text));
        }
    }
}
