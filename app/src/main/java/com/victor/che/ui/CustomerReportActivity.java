package com.victor.che.ui;

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
import com.victor.che.util.DateUtil;
import com.victor.che.util.StringUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户报表界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/17 0017 17:12
 */
public class CustomerReportActivity extends BaseActivity {

    @BindView(R.id.timepicker)
    TextView timepicker;

    @BindView(R.id.tv_total)
    TextView tv_total;

    @BindView(R.id.tv_male_count)
    TextView tv_male_count;

    @BindView(R.id.tv_female_count)
    TextView tv_female_count;

    @BindView(R.id.tv_unknown_gender_count)
    TextView tv_unknown_gender_count;

    @Override
    public int getContentView() {
        return R.layout.activity_customer_report;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("用户报表");

        // 日期默认显示今天
        timepicker.setText(DateUtil.getStringByFormat(new Date(), DateUtil.YMD));

        // 获取当天报表数据
        _reqData(System.currentTimeMillis());
    }

    /**
     * 请求某一天的报表数据
     *
     * @param millseconds
     */
    private void _reqData(long millseconds) {
        String date = DateUtil.getStringByFormat(millseconds, DateUtil.YMD);
        // 获取用户报表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//商家编号
        params.put("date", date);// 报表日期，默认为当天数据
        VictorHttpUtil.doGet(mContext, Define.URL_REPORT_USER_LIST, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = JSON.parseObject(element.data);

                        if (jsonobj == null) {
                            MyApplication.showToast("用户报表数据为空，请稍后重试");
                            return;
                        }

                        tv_total.setText(jsonobj.getString("count"));//总人数
                        tv_male_count.setText(jsonobj.getString("man_count"));//男用户数
                        tv_female_count.setText(jsonobj.getString("woman_count"));//总人数
                        tv_unknown_gender_count.setText(jsonobj.getString("unknown"));//男用户数
                    }

                });
    }

    /**
     * 显示时间选择的对话框
     */
    @OnClick(R.id.timepicker)
    void showDatePickerDialog() {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();

        final TextView curEditText = timepicker;
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

                        _reqData(millseconds);
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
                .setMaxMillseconds(System.currentTimeMillis())//设置最大时间为当天
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
