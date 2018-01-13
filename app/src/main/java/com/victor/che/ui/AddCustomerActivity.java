package com.victor.che.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Vipcard;
import com.victor.che.event.SelectCarBrandAndSeriesEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.DateUtil;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.BottomDialogFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加客户界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/2 0002 17:39
 */
public class AddCustomerActivity extends BaseActivity {

    @BindView(R.id.et_customer_mobile)
    EditText et_customer_mobile;

    @BindView(R.id.et_pln)
    EditText et_pln;

    @BindView(R.id.iv_car_brand_logo)
    ImageView iv_car_brand_logo;

    @BindView(R.id.tv_car_brand_series)
    TextView tv_car_brand_series;

    @BindView(R.id.et_customer_name)
    EditText et_customer_name;

    @BindView(R.id.rdo_male)
    RadioButton rdo_male;

    @BindView(R.id.rdo_female)
    RadioButton rdo_female;

    @BindView(R.id.tv_prov_vipcard_type)
    TextView tv_prov_vipcard_type;

    @BindView(R.id.label_remain_value)
    TextView label_remain_value;

    @BindView(R.id.et_remain_value)
    EditText et_remain_value;

    @BindView(R.id.area_remain_value)
    View area_remain_value;

    @BindView(R.id.area_end_time)
    View area_end_time;

    @BindView(R.id.tv_end_time)
    TextView tv_end_time;

    private String brand_id = "";
    private String series_id = "";

    private List<Vipcard> provVipcardList;
    private ProvVipcardListAdapter provVipcardAdapter;
    private int selectedVipcardPos = -1;//选中会员卡的位置

    @Override
    public int getContentView() {
        return R.layout.activity_add_customer;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("新增用户");

        String mobile = getIntent().getStringExtra("mobile");
        String car_plate_no = getIntent().getStringExtra("car_plate_no");

        if(!TextUtils.isEmpty(mobile))
        et_customer_mobile.setText(mobile);
        if(!TextUtils.isEmpty(car_plate_no))
        et_pln.setText(car_plate_no);


        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_mobile));//手机号必填

        provVipcardAdapter = new ProvVipcardListAdapter(mContext, R.layout.item_bottom_dialog, provVipcardList);

        // 获取服务商的会员卡列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_CARD_LIST, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        provVipcardList = JSON.parseArray(element.body, Vipcard.class);
                        if (CollectionUtil.isEmpty(provVipcardList)) {
                            MyApplication.showToast("服务商没有添加任何会员卡");
                            return;
                        }
                        provVipcardAdapter.setData(provVipcardList);
                        provVipcardAdapter.notifyDataSetChanged();
                    }
                });
    }

    @OnClick(R.id.area_car_brand_series)
    void gotoSelectCarBrandSeries() {
        MyApplication.openActivity(mContext, SelectCarBrandActivity.class);
    }

    /**
     * 选择了汽车品牌和车系后
     */
    @Subscribe
    public void onSelectedCarBrandSeries(SelectCarBrandAndSeriesEvent event) {
        if (event == null) {
            return;
        }
        iv_car_brand_logo.setVisibility(View.VISIBLE);
        // 车品牌logo
        ImageLoaderUtil.display(mContext, iv_car_brand_logo, event.carBrand.image);
        // 车品牌和车系名称
        tv_car_brand_series.setText(event.carBrand.name + event.carSeries.name);

        // 修改车辆的品牌和车系
        this.brand_id = event.carBrand.car_brand_series_id;
        this.series_id = event.carSeries.car_brand_series_id;
    }

    /**
     * 保存
     */
    @OnClick(R.id.topbar_right)
    void doOperate() {
        String mobile = et_customer_mobile.getText().toString().trim();
        if (StringUtil.isEmpty(mobile)) {
            MyApplication.showToast("手机号不能为空");
            et_customer_mobile.requestFocus();
            return;
        }
        if (!StringUtil.isPhoneNumber(mobile)) {
            MyApplication.showToast("手机号格式不正确");
            et_customer_mobile.requestFocus();
            return;
        }

        String pln = et_pln.getText().toString().trim();
        if (!StringUtil.isEmpty(pln) && !StringUtil.isPln(pln)) {
            MyApplication.showToast("车牌号格式不正确");
            et_pln.requestFocus();
            return;
        }

        String strValue = "";
        int card_category_id = 0;
        if (!CollectionUtil.isEmpty(provVipcardList) && selectedVipcardPos >= 0) {// 会员卡列表不为空
            card_category_id = provVipcardList.get(selectedVipcardPos).card_category_id;
            if (card_category_id == 3) {//年卡
                strValue = tv_end_time.getText().toString().trim();
            } else if (card_category_id == 1) {//次卡
                strValue = et_remain_value.getText().toString().trim();
                if (!StringUtil.isEmpty(strValue)) {
                    int num = Integer.parseInt(strValue);
                    if (num <= 0) {
                        MyApplication.showToast("余次必须为正数");
                        et_remain_value.requestFocus();
                        return;
                    }
                }
            } else if (card_category_id == 2) {//余额卡
                strValue = et_remain_value.getText().toString().trim();
                if (!StringUtil.isEmpty(strValue)) {
                    double eb = Double.parseDouble(strValue);
                    if (eb <= 0) {
                        MyApplication.showToast("余额必须为正数");
                        et_remain_value.requestFocus();
                        return;
                    }
                }
            }
        }

        int gender = rdo_male.isChecked() ? 0 : 1;//用户性别 0-男 1-女

        // 添加商品
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("mobile", mobile);// 用户手机号
        params.put("car_plate_no", pln);// 车牌号
        params.put("car_brand_id", brand_id);// 车品牌id
        params.put("car_brand_series_id", series_id);// 车系id
        params.put("name", et_customer_name.getText().toString().trim());// 用户姓名
        params.put("gender", gender);// 用户性别 0-男 1-女
        if (!StringUtil.isEmpty(strValue)) {
            params.put("provider_card_id", provVipcardList.get(selectedVipcardPos).provider_card_id);// 服务商卡id
            params.put("card_category_id", card_category_id);// 卡类型id
            params.put("card_value", strValue);// 卡值(如果是年卡,值为日期类型)
        }
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_USER_ADD, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = JSON.parseObject(element.body);
                        if (jsonobj == null) {
                            MyApplication.showToast("添加用户成功");

                            finish();
                        } else {
                            int exist_user = jsonobj.getIntValue("exist_user");
                            final String provider_user_id = jsonobj.getString("provider_user_id");
                            if (exist_user == 1 || exist_user == 2) {
                                String msg = (exist_user == 1 ? "手机号" : "车牌号") + "已经存在，是否编辑已存在的用户？";
                                AlertDialogFragment.newInstance(
                                        "提示",
                                        msg,
                                        "取消",
                                        "编辑",
                                        null,
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("provider_user_id", provider_user_id);//用户id
                                                MyApplication.openActivity(mContext, CustomerDetailsActivity.class, bundle);
                                            }
                                        })
                                        .show(getSupportFragmentManager(), getClass().getSimpleName());
                            }
                        }
                    }
                });
    }

    /**
     * 显示时间选择的对话框
     */
    @OnClick(R.id.area_end_time)
    void showDatePickerDialog() {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();

        final TextView curEditText = tv_end_time;
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
     * 展示所有卡类型
     */
    @OnClick(R.id.area_vipcard)
    void showVipcardTypeDialog() {
        BottomDialogFragment.newInstance(provVipcardAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != selectedVipcardPos) {//更换卡类型时清空输入框
                    et_remain_value.setText("");
                }
                selectedVipcardPos = position;
                provVipcardAdapter.notifyDataSetChanged();

                Vipcard vipcard = provVipcardList.get(position);
                _render(vipcard);

            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    private void _render(Vipcard vipcard) {
        if (vipcard == null) {
            return;
        }
        tv_prov_vipcard_type.setText(vipcard.name);

        label_remain_value.setText(vipcard.getCardTypeLabel() + "：");//余额/余次文本

        if (vipcard.card_category_id == 3) {//年卡
            area_remain_value.setVisibility(View.GONE);
            area_end_time.setVisibility(View.VISIBLE);
        } else {
            area_remain_value.setVisibility(View.VISIBLE);
            area_end_time.setVisibility(View.GONE);

            if (vipcard.card_category_id == 1) {//次卡
                label_remain_value.setText("余次");
                et_remain_value.setHint("请输入余次");
                et_remain_value.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (vipcard.card_category_id == 2) {//储值卡
                label_remain_value.setText("余额");
                et_remain_value.setHint("请输入余额");
                et_remain_value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
        }
    }

    public class ProvVipcardListAdapter extends VictorBaseListAdapter<Vipcard> {

        public ProvVipcardListAdapter(Context context, int layoutResId, List<Vipcard> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, Vipcard entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.name);
            textView.setTextColor(getResources().getColor(selectedVipcardPos == position ? R.color.theme_color : R.color.black_text));
        }
    }
}
