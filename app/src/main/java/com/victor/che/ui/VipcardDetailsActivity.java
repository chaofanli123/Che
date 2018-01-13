package com.victor.che.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
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
import com.victor.che.event.RecyclerViewItemChangedEvent;
import com.victor.che.event.VipcardInfoEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员卡详情界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 13:57
 */
public class VipcardDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_vipcard_name)
    TextView tv_vipcard_name;

    @BindView(R.id.tv_vipcard_type)
    TextView tv_vipcard_type;

    @BindView(R.id.label_face_value)
    TextView label_face_value;

    @BindView(R.id.tv_face_value)
    TextView tv_face_value;

    @BindView(R.id.tv_sale_price)
    TextView tv_sale_price;

    @BindView(R.id.tv_original_price)
    TextView tv_original_price;

    @BindView(R.id.tv_service_content)
    TextView tv_service_content;

    private Vipcard mVipcard;
    private int mPosition;

    private ArrayList<Channel> allCategoryList;// 选中的产品适用范围

    @Override
    public int getContentView() {
        return R.layout.activity_vipcard_details;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("卡详情");

        mPosition = getIntent().getIntExtra("mPosition", 0);
        mVipcard = (Vipcard) getIntent().getSerializableExtra("mVipcard");

        if (mVipcard == null) {
            MyApplication.showToast("会员卡为空");
            return;
        }
        tv_vipcard_name.setText(mVipcard.name);
        tv_vipcard_type.setText(mVipcard.getCardTypeName());
        label_face_value.setText(mVipcard.getFaceValueLabel());
        tv_face_value.setText(mVipcard.getFaceValue());
        tv_sale_price.setText(MathUtil.getMoneyText(mVipcard.sale_price));
        tv_original_price.setText(MathUtil.getMoneyText(mVipcard.price));
        tv_service_content.setText(mVipcard.getNewServiceContent());


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

                        if (CollectionUtil.isEmpty(allCategoryList)) {
                            return;
                        }
                        // 组装可用服务的rules
                        if (mVipcard.all_service != null) {
                            // 标记分类和产品是否被选中
                            for (Channel category : allCategoryList) {
                                if (!CollectionUtil.isEmpty(mVipcard.all_service.goods_category_id)) {// 分类是否被选中
                                    category.checked = mVipcard.all_service.goods_category_id.contains(category.goods_category_id);
                                }
                                if (!CollectionUtil.isEmpty(mVipcard.all_service.goods_id)
                                        && !CollectionUtil.isEmpty(category.goods)) {//具体的产品是否被选中
                                    for (Product p : category.goods) {
                                        p.checked = mVipcard.all_service.goods_id.contains(p.goods_id);
                                    }
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 修改名称
     */
    @OnClick(R.id.area_vipcard_name)
    void gotoEditVipcardName() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mVipcardInfo", new VipcardInfoEvent(VipcardInfoEvent.EDIT_VIPCARD_NAME, mVipcard));
        MyApplication.openActivity(mContext, EditVipcardInfoActivity.class, bundle);
    }

    /**
     * 修改会员卡类型
     */
    //    @OnClick(R.id.area_vipcard_type)
    //    void gotoEditVipcardType() {
    //        Bundle bundle = new Bundle();
    //        bundle.putSerializable("mVipcardInfo", new VipcardInfoEvent(VipcardInfoEvent.EDIT_VIPCARD_NAME, mVipcard));
    //        MyApplication.openActivity(mContext, EditCustomerActivity.class, bundle);
    //    }

    /**
     * 修改可用金额/可用次数
     */
    @OnClick(R.id.area_remain_value)
    void gotoEditRemainValue() {
        if (mVipcard.card_category_id == 3) {
            MyApplication.showToast("年卡的可用次数禁止修改");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("mVipcardInfo", new VipcardInfoEvent(VipcardInfoEvent.EDIT_REMAIN_VALUE, mVipcard));
        MyApplication.openActivity(mContext, EditVipcardInfoActivity.class, bundle);
    }


    /**
     * 修改售价
     */
    @OnClick(R.id.area_sale_price)
    void gotoEditSalePrice() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mVipcardInfo", new VipcardInfoEvent(VipcardInfoEvent.EDIT_SALE_PRICE, mVipcard));
        MyApplication.openActivity(mContext, EditVipcardInfoActivity.class, bundle);
    }

    /**
     * 修改原价
     */
    @OnClick(R.id.area_original_price)
    void gotoEditOriginalPrice() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mVipcardInfo", new VipcardInfoEvent(VipcardInfoEvent.EDIT_ORIGINAL_PRICE, mVipcard));
        MyApplication.openActivity(mContext, EditVipcardInfoActivity.class, bundle);
    }

    /**
     * 修改可用服务
     */
    @OnClick(R.id.area_service_content)
    void gotoEditServiceContent() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("mEdit", true);// 编辑状态
        bundle.putSerializable("mVipcard", mVipcard);
        bundle.putSerializable("mAllCategoryList", allCategoryList);
        MyApplication.openActivity(mContext, ChooseProductActivity.class, bundle);
    }

    /**
     * 会员卡信息发生修改
     *
     * @param event
     */
    @Subscribe
    public void onDataChanged(VipcardInfoEvent event) {
        if (event == null) {
            return;
        }
        mVipcard = event.data;
        switch (event.action) {
            case VipcardInfoEvent.EDIT_VIPCARD_NAME://修改会员卡姓名
                tv_vipcard_name.setText(mVipcard.name);
                break;
            case VipcardInfoEvent.EDIT_REMAIN_VALUE://修改可用次数/可用余额
                tv_face_value.setText(mVipcard.getFaceValue());
                break;
            case VipcardInfoEvent.EDIT_SALE_PRICE://修改售价
                tv_sale_price.setText(MathUtil.getMoneyText(mVipcard.sale_price));
                break;
            case VipcardInfoEvent.EDIT_ORIGINAL_PRICE://修改原价
                tv_original_price.setText(MathUtil.getMoneyText(mVipcard.price));
                break;
            case VipcardInfoEvent.EDIT_VIPCARD_TYPE://修改会员卡类型（暂不考虑）
                break;
            default:
                break;
        }

        // 通知列表更改
        RecyclerViewItemChangedEvent evt = new RecyclerViewItemChangedEvent();
        evt.position = mPosition;
        evt.obj = mVipcard;
        EventBus.getDefault().post(evt);
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
            mVipcard.used_goods_text = getString(R.string.choose_product_range);
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
            mVipcard.used_goods_text = builder.toString();
        }

        // 通知列表更改
        RecyclerViewItemChangedEvent evt = new RecyclerViewItemChangedEvent();
        evt.position = mPosition;
        evt.obj = mVipcard;
        EventBus.getDefault().post(evt);
    }
}
