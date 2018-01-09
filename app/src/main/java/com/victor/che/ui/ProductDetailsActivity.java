package com.victor.che.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Product;
import com.victor.che.util.DateUtil;
import com.victor.che.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品详情界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/2 0002 17:23
 */
public class ProductDetailsActivity extends BaseActivity {

    @BindView(R.id.et_original_price)
    EditText et_original_price;

    @BindView(R.id.et_sale_price)
    EditText et_sale_price;

    @BindView(R.id.et_act_price)
    EditText et_act_price;

    @BindView(R.id.et_product_name)
    EditText et_product_name;

    @BindView(R.id.tv_start_time)
    TextView tv_start_time;

    @BindView(R.id.tv_end_time)
    TextView tv_end_time;

    private Product mProduct;

    @Override
    public int getContentView() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("编辑服务");

        mProduct = (Product) getIntent().getSerializableExtra("mProduct");

        if (mProduct == null) {
            MyApplication.showToast("商品为空，请稍后重试");
            return;
        }

        /***数据回显***/
        et_product_name.setText(mProduct.name);//商品名称
        et_original_price.setText(String.valueOf(mProduct.price));//原价
        et_sale_price.setText(String.valueOf(mProduct.sale_price));//销售价
        et_act_price.setText(String.valueOf(mProduct.act_price));//活动价
        tv_start_time.setText(mProduct.act_begin_time);//活动开始时间
        tv_end_time.setText(mProduct.act_end_time);//活动结束时间
    }

    /**
     * 显示时间选择的对话框
     */
    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    void showDatePickerDialog(View v) {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        final TextView curEditText = (v.getId() == R.id.tv_start_time) ? tv_start_time : tv_end_time;
        String text = curEditText.getText().toString().trim();
        if (!StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }

        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new com.jzxiang.pickerview.listener.OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        curEditText.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YMD));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - _100year)//设置最小时间-100年
                .setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 保存
     */
    @OnClick(R.id.topbar_right)
    void doOperate() {

        String productName = et_product_name.getText().toString().trim();
        if (StringUtil.isEmpty(productName)) {
            MyApplication.showToast("商品名称不能为空");
            et_product_name.requestFocus();
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
                MyApplication.showToast("原价不能低于售价");
                et_original_price.requestFocus();
                return;
            }
        }

        if (originPrice <= 0) {//没填原价， 默认"原价=销售价+5"
            originPrice = salePrice + ConstantValue.DEFAULT_PRICE_OFFSET;
        }

        if (productName.equalsIgnoreCase(mProduct.name)
                && originPrice == mProduct.price
                && salePrice == mProduct.sale_price) {//未发生修改
            // 关闭本页
            finish();
            return;
        }

        // 修改商品
        MyParams params = new MyParams();
        params.put("goods_id", mProduct.goods_id);// 商品id
        params.put("goods_name", productName);// 	商品名称
        params.put("price", originPrice);// 原价
        params.put("sale_price", salePrice);// 售价
        VictorHttpUtil.doPost(mContext, Define.URL_GOODS_EDIT, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast("修改成功");

                        EventBus.getDefault().post(ConstantValue.Event.REFRESH_PRODUCT_LIST);

                        finish();
                    }
                });
    }
}
