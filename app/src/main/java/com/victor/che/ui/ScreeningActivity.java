package com.victor.che.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.victor.che.R;
import com.victor.che.adapter.MarkerManyGridAdapter;
import com.victor.che.adapter.MarkerOrderGridAdapter;
import com.victor.che.adapter.MarkerServerGridAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Channel;
import com.victor.che.domain.OrderCategory;
import com.victor.che.event.ScreenStyleResultEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.DateUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.NoScrollGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 筛选界面
 */
public class ScreeningActivity extends BaseActivity {
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    //支付方式
    @BindView(R.id.gv_pay_style)
    NoScrollGridView gvPayStyle;
    //订单类型
    @BindView(R.id.gv_order_style)
    NoScrollGridView gvOrderStyle;
    //服务类别
    @BindView(R.id.gv_sreve_style)
    NoScrollGridView gvSreveStyle;

    private MarkerManyGridAdapter adapterPay;
   private  ArrayList<String> paystyleList;
    private String[] paystylearray;
    private String paystylename="";


    private List<OrderCategory> orderCategoryList;
    private MarkerOrderGridAdapter adapterOrder;
    private OrderCategory orderCategory;

    private List<Channel> categoryList;
    private MarkerServerGridAdapter adapterServer;
    private  Channel category;

    @Override
    public int getContentView() {
        return R.layout.activity_screening;
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.btn_resume, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time: //开始时间
                showDatePickerDialog(tvStartTime);

                break;
            case R.id.tv_end_time://结束时间
                showDatePickerDialog(tvEndTime);

                break;
            case R.id.btn_submit://完成
               String timestart=tvStartTime.getText().toString().trim();
                String timeend=tvEndTime.getText().toString().trim();
                String time="";
                if (TextUtils.isEmpty(timestart)&&!TextUtils.isEmpty(timeend)) {
                    MyApplication.showToast("请选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(timeend)&&!TextUtils.isEmpty(timestart)) {
                    MyApplication.showToast("请选择结束时间");
                    return;
                }
                if (TextUtils.isEmpty(timestart)||TextUtils.isEmpty(timeend)) {
                    time="";

                }else if (!TextUtils.isEmpty(tvStartTime.getText().toString().trim())&&!TextUtils.isEmpty(tvEndTime.getText().toString().trim())) {

                    if (Integer.parseInt(timestart.replace("-","")) > Integer.parseInt(timeend.replace("-",""))) {
                        MyApplication.showToast("开始时间不能大于结束时间");
                        return;
                    }
                    time=timestart+"至"+timeend;
                }

                ScreenStyleResultEvent event = new ScreenStyleResultEvent(orderCategory, category,time,paystylename);
                EventBus.getDefault().post(event);
                finish();
                break;
            case R.id.btn_resume://重置
                tvStartTime.setText("");
                tvEndTime.setText("");
                getContentView();
               initView();
                break;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("筛选");
        /**
         * 支付方式
         */
        paystyleList = new ArrayList<>();
        paystylearray = getResources().getStringArray(R.array.allpaystylelist);
        paystyleList.addAll(Arrays.asList(paystylearray));
        adapterPay = new MarkerManyGridAdapter(mContext, paystyleList);
        gvPayStyle.setAdapter(adapterPay);
        gvPayStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                paystylename = paystyleList.get(position) + "";
                adapterPay.setSeclection(position);//传值更新
                adapterPay.notifyDataSetChanged();
            }
        });
        /**
         * 获取订单类型
         */
        orderCategoryList = new ArrayList<>();
        getOrederStyle();
        gvOrderStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                 orderCategory = orderCategoryList.get(position);
                adapterOrder.setSeclection(position);//传值更新
                adapterOrder.notifyDataSetChanged();
            }
        });

        /**
         * 获取服务类别
         */
        categoryList = new ArrayList<>();
        getServerlist();
        gvSreveStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                category = categoryList.get(position);
                adapterServer.setSeclection(position);//传值更新
                adapterServer.notifyDataSetChanged();
            }
        });
    }

    /**
     * 获取服务类别
     */
    private void getServerlist() {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        VictorHttpUtil.doGet(mContext, Define.URL_GOODS_CATEGORY_LIST, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        categoryList = JSON.parseArray(element.data, Channel.class);
                        if (CollectionUtil.isEmpty(categoryList)) {
                            MyApplication.showToast("服务类型为空");
                            return;
                        }
                        adapterServer = new MarkerServerGridAdapter(mContext, categoryList);
                        gvSreveStyle.setAdapter(adapterServer);
                    }
                });
    }
    /**
     * 获取订单类型
     */
    private void getOrederStyle() {
        // 获取订单类型
        VictorHttpUtil.doGet(mContext, Define.URL_ORDER_CATEGORY_LIST, null, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        orderCategoryList.addAll(JSON.parseArray(element.data, OrderCategory.class));
                        if (CollectionUtil.isEmpty(orderCategoryList)) {
                            MyApplication.showToast("订单类型列表为空");
                            return;
                        }
                        adapterOrder = new MarkerOrderGridAdapter(mContext, orderCategoryList);
                        gvOrderStyle.setAdapter(adapterOrder);
                    }
                });
    }

    /**
     * 显示时间对话框
     */
    private void showDatePickerDialog(final TextView textview) {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = textview.getText().toString().trim();
        if (!StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }

        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        textview.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YMD));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - _100year)//设置最小时间
                //.setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                .setMaxMillseconds(System.currentTimeMillis())//设置最大时间+100年
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

}
