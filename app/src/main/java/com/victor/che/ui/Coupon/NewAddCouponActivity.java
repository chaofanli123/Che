package com.victor.che.ui.Coupon;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
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
import com.victor.che.domain.ShopsCoupon;
import com.victor.che.event.MessageEvent;
import com.victor.che.ui.ChooseProductActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.DateUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增优惠券
 */
public class NewAddCouponActivity extends BaseActivity {

    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.et_name)
    ClearEditText etName;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.area_price)
    LinearLayout areaPrice;
    @BindView(R.id.et_full_price)
    EditText etFullPrice;
    @BindView(R.id.area_full_price)
    LinearLayout areaFullPrice;
    /**
     * 开关
     */
    @BindView(R.id.img_on_off)
    ImageView imgOnOff;
    @BindView(R.id.tv_first_time)
    TextView tvFirstTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.et_total_quantity)
    EditText etTotalQuantity;
    @BindView(R.id.et_number_days)
    EditText etNumberDays;
    @BindView(R.id.tv_usable_server)
    TextView tvUsableServer;
    @BindView(R.id.et_describe)
    EditText etDescribe;
    @BindView(R.id.tv_name2)
    TextView tvName2;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_mianz)
    TextView tvMianz;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    private boolean off_on;
    private ArrayList<Channel> allCategoryList;//产品分类列表（内部包含所有产品）
    private String rules = "";//选择的可用服务（组装成请求接口的格式）
    private int limit_num_type;//是否发放
    private String type;
    private ShopsCoupon shopsCoupon;
    private String name, price, fullprice, entTime, fistTime, totalQuantity, numDays, describy;


    @Override
    public int getContentView() {
        return R.layout.activity_new_add_coupon;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTopbarTitle.setText("新增优惠券");
        off_on = false;
        imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_normal);
        limit_num_type = 0;
        // 获取所有分类和产品
        _doGetAllCategoryAndProduct();
        type = getIntent().getStringExtra("type");
        if (type.equals("couponlist")) {//来自列表 编辑优惠券
            tvTopbarTitle.setText("编辑优惠券");
            areaFullPrice.setVisibility(View.GONE);
            tvName2.setText("名称");
            etName.setVisibility(View.INVISIBLE);
            etPrice.setVisibility(View.INVISIBLE);
            tvPrice.setTextColor(getResources().getColor(R.color.c_ef5350));

            shopsCoupon = (ShopsCoupon) getIntent().getSerializableExtra("shopsCoupon");
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(shopsCoupon.getName());
            tvPrice.setText("¥"+shopsCoupon.getMoney());

            if (shopsCoupon.getLimit_num_type() == 1) { //限制一人只能领一张
                off_on = true;
                imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_select);
                limit_num_type = 1;

            } else {//不限制
                off_on = false;
                imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_normal);
                limit_num_type = 0;
            }
            if (!StringUtil.isEmpty(shopsCoupon.getGrant_start_time())) {
                tvFirstTime.setText(shopsCoupon.getGrant_start_time());
            }
            if (!StringUtil.isEmpty(shopsCoupon.getGrant_end_time())) {
                tvEndTime.setText(shopsCoupon.getGrant_end_time());
            }
            if (!StringUtil.isEmpty(shopsCoupon.getNum() + "")) {
                etTotalQuantity.setText(shopsCoupon.getNum() + "");
            }
            if (!StringUtil.isEmpty(shopsCoupon.getExpire_day() + "")) {
                etNumberDays.setText(shopsCoupon.getExpire_day() + "");
            }
            if (!StringUtil.isEmpty(shopsCoupon.getDescription())) {
                etDescribe.setText(shopsCoupon.getDescription());
            }
            ShopsCoupon.AllServiceBean all_service = shopsCoupon.getAll_service();
            if (all_service == null) {
                tvUsableServer.setText(getString(R.string.choose_product_range));
            } else {
                List<Integer> goods_id = all_service.getGoods_id();
                List<Integer> goods_category_id = all_service.getGoods_category_id();
                tvUsableServer.setText(all_service.getUsed_service_text());
                StringBuilder rulesBuilder = new StringBuilder();//服务内容rules
                if (CollectionUtil.isEmpty(goods_category_id)) {
                    for (int i = 0; i < goods_id.size(); i++) {
                        rulesBuilder.append(",").append("goods_id=").append(goods_id.get(i));
                    }
                } else {
                    for (int i = 0; i < goods_category_id.size(); i++) {
                        rulesBuilder.append(",").append("goods_category_id=").append(goods_category_id.get(i));
                    }

                }
                if (rulesBuilder.length() > 0) {
                    rulesBuilder.deleteCharAt(0);
                }
                rules = rulesBuilder.toString();
            }

        }
    }

    @OnClick({R.id.topbar_left, R.id.topbar_right, R.id.img_on_off, R.id.tv_first_time, R.id.tv_end_time, R.id.tv_usable_server})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_on_off: //每人限领一张开关
                if (off_on) {
                    imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_select);
                    off_on = false;
                    limit_num_type = 1;
                } else {
                    imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_normal);
                    off_on = true;
                    limit_num_type = 0;
                }
                break;
            case R.id.tv_first_time://开始发放时间
                //显示时间对话框
                showDatePickerDialog(tvFirstTime);
                break;
            case R.id.tv_end_time://结束发放时间
                //显示时间对话框
                showDatePickerDialog(tvEndTime);
                break;
            case R.id.tv_usable_server://选择可用的服务
                Bundle bundle = new Bundle();
                bundle.putSerializable("mAllCategoryList", allCategoryList);
                MyApplication.openActivity(mContext, ChooseProductActivity.class, bundle);
                break;
            case R.id.topbar_left://取消按钮
                finish();
                break;
            case R.id.topbar_right://确定按钮
                MyParams params = new MyParams();
                if (type.equals("couponlist")) {//来自列表 修改
                    entTime = tvEndTime.getText().toString().trim();
                    if (StringUtil.isEmpty(entTime)) {
                        entTime = "";
                    }
                    fistTime = tvFirstTime.getText().toString().trim();
                    if (StringUtil.isEmpty(fistTime)) {
                        MyApplication.showToast("开始发放时间不能为空");
                        tvFirstTime.requestFocus();
                        return;
                    }
                    totalQuantity = etTotalQuantity.getText().toString().trim();
                    if (StringUtil.isEmpty(totalQuantity)) {
                        totalQuantity = "0";
                    }
                    numDays = etNumberDays.getText().toString().trim();
                    if (StringUtil.isEmpty(numDays)) {
                        numDays = "0";
                    }
                    describy = etDescribe.getText().toString().trim();
                    if (StringUtil.isEmpty(describy)) {
                        describy = "";
                    }
                    params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
                    params.put("coupon_id", shopsCoupon.getCoupon_id());//服务商id
                    params.put("description", describy);//string	否	描述
                    params.put("grant_start_time", fistTime);//是	发放开始时间
                    params.put("grant_end_time", entTime);//	否 发放结束时间，默认为0000-00-00 00:00
                    params.put("num", totalQuantity);//否	发放总数量，默认为0，不限制
                    params.put("limit_num_type", limit_num_type);//否	发放限制：0-不限制，一人可领多张 1-限制一人只能领一张 ,默认为0
                    params.put("expire_day", numDays);//否	领取之后可以可使用天数,默认为0，不限制
                    params.put("rules", rules);//是	适用服务，默认为‘’，格式： goods_id=1,goods_id=2,goods_category_id=1..
                    VictorHttpUtil.doPost(mContext, Define.url_coupon_edit_v1, params, false, null,
                            new BaseHttpCallbackListener<Element>() {
                                @Override
                                public void callbackSuccess(String url, Element element) {
                                    MyApplication.showToast("修改成功");
                                     /*
                    刷新局部列表
                     */
                                    shopsCoupon.setDescription(describy);
                                    shopsCoupon.setGrant_start_time(fistTime);
                                    shopsCoupon.setGrant_end_time(entTime);
                                    shopsCoupon.setNum(Integer.valueOf(totalQuantity));
                                    shopsCoupon.setLimit_num_type(limit_num_type);
                                    shopsCoupon.setExpire_day(Integer.valueOf(numDays));

                                    int location;
                                    if (getIntent().getStringExtra("position") != null) {
                                        location = Integer.valueOf(getIntent().getStringExtra("position"));
                                    } else {
                                        location = -1;
                                    }
                                    MessageEvent event = new MessageEvent(MessageEvent.ALL_WISH_RELOAD, location, shopsCoupon);
                                    EventBus.getDefault().post(event);
                                    finish();
                                }
                            });

                } else {
                    name = etName.getText().toString().trim();
                    if (StringUtil.isEmpty(name)) {
                        MyApplication.showToast("名称不能为空");
                        etName.requestFocus();
                        return;
                    }
                    price = etPrice.getText().toString().trim();
                    if (StringUtil.isEmpty(price)) {
                        MyApplication.showToast("面值不能为空");
                        etPrice.requestFocus();
                        return;
                    }
                    fullprice = etFullPrice.getText().toString().trim();
                    if (StringUtil.isEmpty(fullprice)) {
                        fullprice = "";
                    }

                    entTime = tvEndTime.getText().toString().trim();
                    if (StringUtil.isEmpty(entTime)) {
                        entTime = "";
                    }

                    fistTime = tvFirstTime.getText().toString().trim();
                    if (StringUtil.isEmpty(fistTime)) {
                        MyApplication.showToast("开始发放时间不能为空");
                        tvFirstTime.requestFocus();
                        return;
                    }
                    totalQuantity = etTotalQuantity.getText().toString().trim();
                    if (StringUtil.isEmpty(totalQuantity)) {
                        totalQuantity = "0";
                    }
                    numDays = etNumberDays.getText().toString().trim();
                    if (StringUtil.isEmpty(numDays)) {
                        numDays = "0";
                    }
                    describy = etDescribe.getText().toString().trim();
                    if (StringUtil.isEmpty(describy)) {
                        describy = "";
                    }
                    params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
                    params.put("name", name);//优惠券名称
                    params.put("description", describy);//string	否	描述
                    params.put("money", price);//是	面值
                    params.put("full_money", fullprice);//否	满多少钱可用
                    params.put("grant_start_time", fistTime);//是	发放开始时间
                    params.put("grant_end_time", entTime);//	否 发放结束时间，默认为0000-00-00 00:00
                    params.put("num", totalQuantity);//否	发放总数量，默认为0，不限制
                    params.put("limit_num_type", limit_num_type);//否	发放限制：0-不限制，一人可领多张 1-限制一人只能领一张 ,默认为0
                    params.put("expire_day", numDays);//否	领取之后可以可使用天数,默认为0，不限制
                    params.put("rules", rules);//是	适用服务，默认为‘’，格式： goods_id=1,goods_id=2,goods_category_id=1..
                    VictorHttpUtil.doPost(mContext, Define.url_coupon_add_v1, params, false, null,
                            new BaseHttpCallbackListener<Element>() {
                                @Override
                                public void callbackSuccess(String url, Element element) {
                                    MyApplication.showToast("增加成功");
                                    MyApplication.openActivity(mContext, CouponListActivity.class);
                                    finish();
                                }
                            });
                }

                break;
        }
    }


    /**
     * 显示时间对话框
     */
    private void showDatePickerDialog(final TextView textview) {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = textview.getText().toString().trim();
        if (!StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMDHM);
            calendar.setTime(date == null ? new Date() : date);
        }
        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        textview.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YMDHM));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
//                .setMinMillseconds(System.currentTimeMillis() - _100year)//设置最小时间
//                .setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getSupportFragmentManager(), getClass().getSimpleName());
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
                        allCategoryList = (ArrayList<Channel>) JSON.parseArray(element.data, Channel.class);

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
     * 选择完适用服务范围后
     *(threadMode = ThreadMode.MAIN)
     * @param allCategoryList
     */
    @Subscribe
    public void onChooseProducts(ArrayList<Channel> allCategoryList) {
        this.allCategoryList = allCategoryList;
        if (CollectionUtil.isEmpty(allCategoryList)) {
            tvUsableServer.setText(getString(R.string.choose_product_range));
        } else {
            StringBuilder serviceTextBuidler = new StringBuilder();//服务内容
            StringBuilder rulesBuilder = new StringBuilder();//服务内容rules
            for (Channel category : allCategoryList) {
                if (category.checked) {//分类全选
                    serviceTextBuidler.append(",").append("全部" + category.name + "服务");

                    rulesBuilder.append(",").append("goods_category_id=").append(category.goods_category_id);
                } else {//分类未全选
                    if (!CollectionUtil.isEmpty(category.goods)) {
                        for (Product product : category.goods) {
                            if (product.checked) {
                                serviceTextBuidler.append(",").append(product.name);

                                rulesBuilder.append(",").append("goods_id=").append(product.goods_id);
                            }
                        }
                    }
                }
            }
            if (serviceTextBuidler.length() > 0) {//移除首位的“,”
                serviceTextBuidler.deleteCharAt(0);

            } else {// 未选中任何一个产品
                serviceTextBuidler.append(getString(R.string.choose_product_range));
            }
            tvUsableServer.setText(serviceTextBuidler.toString());

            if (rulesBuilder.length() > 0) {
                rulesBuilder.deleteCharAt(0);
            }
            rules = rulesBuilder.toString();
        }

    }

}
