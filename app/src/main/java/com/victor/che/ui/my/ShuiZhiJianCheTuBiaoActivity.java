package com.victor.che.ui.my;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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
import com.victor.che.bean.ShuiZhiJianCheTuBiao;
import com.victor.che.ui.my.util.StringUtil;
import com.victor.che.ui.my.util.SuitLines;
import com.victor.che.ui.my.util.Unit;
import com.victor.che.util.DateUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShuiZhiJianCheTuBiaoActivity extends BaseActivity {

    @BindView(R.id.ed_tiem_start)
    TextView edTiemStart;
    @BindView(R.id.et_time_end)
    TextView etTimeEnd;
    @BindView(R.id.img_search)
    ImageView imgSearch;

    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.xinglv)
    SuitLines xinglv;
    @BindView(R.id.tv_shuju)
    TextView tvShuju;
    @BindView(R.id.tv_danwei)
    TextView tvDanwei;
    @BindView(R.id.chart1)
    LineChart mChart;

    private String[] mVals = new String[]
            {"溶解氧", "温度", "PH", "浊度", "氨氮"};

    private String begin = "";//开始时间
    private String end = "";//结束时间

    private float textSize = 10;
    private String channel = 5 + "";

    @Override
    public int getContentView() {
        return R.layout.activity_shui_zhi_jian_che_tu_biao;
    }

    @Override
    protected void initView() {
        super.initView();

        init();
    }

    private void sz(SuitLines sl) {
        sl.setXySize(textSize);
        sl.setDefaultOneLineColor(Color.WHITE);
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    private void init() {
        begin = getOldDate(-3);
        end = getOldDate(0);
        edTiemStart.setText(begin);
        etTimeEnd.setText(end);
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        TagAdapter<String> stringTagAdapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        stringTagAdapter.setSelectedList(0);
        mFlowLayout.setAdapter(stringTagAdapter);

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (mVals[position].equals("溶解氧")) {
                    tvDanwei.setText("单位:mg/l");
                    channel = 5 + "";
                } else if (mVals[position].equals("温度")) {
                    tvDanwei.setText("单位:°C");
                    channel = 6 + "";
                } else if (mVals[position].equals("PH")) {
                    tvDanwei.setText("单位:ph");
                    channel = 8 + "";
                } else if (mVals[position].equals("浊度")) {
                    tvDanwei.setText("单位:NTU");
                    channel = 9 + "";
                } else if (mVals[position].equals("氨氮")) {
                    tvDanwei.setText("单位:mg/l");
                    channel = 11 + "";
                }
                loadData();
                return true;
            }
        });
        sz(xinglv);
        loadData();


        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setViewPortOffsets(0, 0, 0, 0);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);
        mChart.setNoDataText("暂无数据");
        XAxis x = mChart.getXAxis();
        x.setEnabled(false);

        YAxis y = mChart.getAxisLeft();
        y.setTypeface(mTfLight);
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        mChart.getAxisRight().setEnabled(false);

        // add data
//        setData(2, 2);


    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTypeface(mTfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
        }
    }

    /**
     * 获取商家优惠券
     *
     * @param
     */
    private void loadData() {
        MyParams params = new MyParams();
        if (!StringUtil.isEmpty(begin)) {
            params.put("beginTime", begin);
        }
        if (!StringUtil.isEmpty(end)) {
            params.put("endTime", end);
        }
        params.put("poolId", getIntent().getStringExtra("id"));
        params.put("channel", channel);
        VictorHttpUtil.doPost(mContext, Define.URL_CaiJiXingXi + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        final ShuiZhiJianCheTuBiao notify = JSON.parseObject(element.body, ShuiZhiJianCheTuBiao.class);
                        List<Unit> xinglvlines = new ArrayList<>();
                        if (notify.getIotList().size() > 0) {
//                            if (channel.equals("11")){
//                                xinglv.maxValueOfY=0.1f;
//                            }
                            xinglv.setVisibility(View.GONE);
//                            mChart.setVisibility(View.VISIBLE);
                            for (ShuiZhiJianCheTuBiao.IotListBean data : notify.getIotList()) {
                                String[] split = data.getTime().split(" ");
                                String[] split1 = split[0].split("-");
                                String yr = split1[1] + "-" + split1[2];
                                String[] split2 = split[1].split(":");
                                String xf = split2[0] + ":" + split2[1];
                                String time = yr + " " + xf;
                                xinglvlines.add(new Unit(Float.valueOf(String.valueOf(data.getVal())), time));
                            }
                            xinglv.feedWithAnim(xinglvlines);
                            tvShuju.setVisibility(View.GONE);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayList<Entry> yVals = new ArrayList<Entry>();

                                    for (int i = 0; i < notify.getIotList().size(); i++) {
                                        yVals.add(new Entry(i, Float.valueOf(String.valueOf(notify.getIotList().get(i).getVal()))));
                                    }
                                    LineDataSet set1;

                                    if (mChart.getData() != null &&
                                            mChart.getData().getDataSetCount() > 0) {
                                        set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
                                        set1.setValues(yVals);
                                        mChart.getData().notifyDataChanged();
                                        mChart.notifyDataSetChanged();
                                    } else {
                                        // create a dataset and give it a type
                                        set1 = new LineDataSet(yVals, "DataSet 1");

                                        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                                        set1.setCubicIntensity(0.2f);
                                        //set1.setDrawFilled(true);
                                        set1.setDrawCircles(false);
                                        set1.setLineWidth(1.8f);
                                        set1.setCircleRadius(4f);
                                        set1.setCircleColor(Color.WHITE);
                                        set1.setHighLightColor(Color.rgb(244, 117, 117));
                                        set1.setColor(Color.WHITE);
                                        set1.setFillColor(Color.WHITE);
                                        set1.setFillAlpha(100);
                                        set1.setDrawHorizontalHighlightIndicator(false);
                                        set1.setFillFormatter(new IFillFormatter() {
                                            @Override
                                            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                                                return -10;
                                            }
                                        });

                                        // create a data object with the datasets
                                        LineData data = new LineData(set1);
                                        data.setValueTypeface(mTfLight);
                                        data.setValueTextSize(9f);
                                        data.setDrawValues(false);

                                        // set data
                                        mChart.setData(data);
                                    }

                                    mChart.getLegend().setEnabled(false);

                                    mChart.animateXY(2000, 2000);

                                    // dont forget to refresh the drawing
                                    mChart.invalidate();
                                }
                            });

                        } else {
//                            tvShuju.setVisibility(View.VISIBLE);
                            xinglv.setVisibility(View.GONE);
//                            mChart.setVisibility(View.GONE);
                        }

                    }
                });
    }

    @OnClick({R.id.ed_tiem_start, R.id.et_time_end, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ed_tiem_start: //开始时间
                showDatePickerDialog(edTiemStart);
                break;
            case R.id.et_time_end://结束时间
                showDatePickerDialog(etTimeEnd);
                break;
            case R.id.img_search://搜索
                begin = edTiemStart.getText().toString().trim();
                end = etTimeEnd.getText().toString().trim();
                loadData();
                break;

        }
    }

    /**
     * 显示时间对话框
     */
    private void showDatePickerDialog(final TextView tv) {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = tv.getText().toString().trim();
        if (!com.victor.che.util.StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }
        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        tv.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YMD));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
