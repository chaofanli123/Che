package com.victor.che.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.DailyReport;
import com.victor.che.util.DateUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.FullyLinearLayoutManager;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 营业额报表界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/17 0017 17:27
 */
public class SalesReportActivity extends BaseActivity {

    @BindView(R.id.timepicker)
    TextView timepicker;

    @BindView(R.id.tv_total)
    TextView tv_total;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private ReportsAdapter mAdapter;
    private List<DailyReport> mList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_sales_report;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("营业额报表");

        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mAdapter = new ReportsAdapter(R.layout.item_daily_report, mList);
        mRecyclerView.setAdapter(mAdapter);

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
        // 获取营业额报表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//商家编号
        params.put("date", date);// 报表日期，默认为当天数据
        VictorHttpUtil.doGet(mContext, Define.URL_REPORT_AMOUNT_LIST, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        // 总营业额
                        tv_total.setText(MathUtil.getFinanceValue(element.options.getDoubleValue("amount")));

                        mList = JSON.parseArray(element.data, DailyReport.class);
                        mAdapter.setNewData(mList);
                        mAdapter.notifyDataSetChanged();

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

    /**
     * 日报明细适配器
     */
    private class ReportsAdapter extends QuickAdapter<DailyReport> {

        public ReportsAdapter(int layoutResId, List<DailyReport> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DailyReport item) {
            helper.setText(R.id.tv_left, item.goods_category_name)//服务项目名称
                    .setText(R.id.tv_right, MathUtil.getFinanceValue(item.amount) + "元");//营业额
        }
    }
}
