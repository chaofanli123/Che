package com.victor.che.ui.my;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.bean.YangZhiChangDanAn;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.DateUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class YangZhiChangDangAnActivity extends BaseActivity {

    @BindView(R.id.recycler_usr_car)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl_user_car)
    PtrClassicFrameLayout pcflUserCar;
    @BindView(R.id.ed_tiem_start)
    TextView edTiemStart;
    @BindView(R.id.et_time_end)
    TextView etTimeEnd;
    @BindView(R.id.img_search)
    ImageView imgSearch;

    /**
     * adapter
     */
    private CouponAdapter messageListAdapter;
    private List<YangZhiChangDanAn.PageBean.ListBean> messageArrayList;
    private PtrHelper<YangZhiChangDanAn.PageBean.ListBean> mPtrHelper;
    private int index;/*点击的愿望下标*/
    public static int currentPos = 0;//当前位置
    private String begin = "";//开始时间
    private String end = "";//结束时间
    @Override
    public int getContentView() {
        return R.layout.activity_yang_zhi_chang_dang_an;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("养殖场档案");
        init();
    }

    private void init() {
        messageArrayList = new ArrayList<>();
        messageListAdapter = new CouponAdapter(R.layout.item_yangzhichangdangan, messageArrayList);
        recycler.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器//
        recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        recycler.setAdapter(messageListAdapter);
        mPtrHelper = new PtrHelper<>(pcflUserCar, messageListAdapter, messageArrayList);
        mPtrHelper.enableLoadMore(true, recycler);//允许加载更多
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                loadData(pullToRefresh, curpage, pageSize);
            }
        });
        recycler.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                index = position;
                if (position == -1) {
                    return;
                }
                startActivity(new Intent(mContext, YangZhiChangDangAnXiangQingActivity.class).putExtra("id", messageArrayList.get(position).getAquFarm().getId()));
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);//
        params.put("pageNo", curpage / pageSize + 1);
        params.put("pageSize", pageSize);
        if (!com.victor.che.ui.my.util.StringUtil.isEmpty(begin)) {
            params.put("begin", begin);
        }
        if (!com.victor.che.ui.my.util.StringUtil.isEmpty(end)) {
            params.put("end", end);
        }
        VictorHttpUtil.doPost(mContext, Define.URL_YangZhiChangXingXi + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        YangZhiChangDanAn Policy = JSON.parseObject(element.body, YangZhiChangDanAn.class);
                        //                        List<QueryUserCarHistory> queryUserCarHistories = new ArrayList<QueryUserCarHistory>();
                        List<YangZhiChangDanAn.PageBean.ListBean> shopsCouponList = new ArrayList<>();
                        shopsCouponList = Policy.getPage().getList();

                        if (pullToRefresh) {////刷新
                            messageArrayList.clear();//清空数据
                            if (CollectionUtil.isEmpty(shopsCouponList)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                // 有数据
                                messageArrayList.addAll(shopsCouponList);
                                messageListAdapter.setNewData(messageArrayList);
                                messageListAdapter.notifyDataSetChanged();
                                if (CollectionUtil.getSize(shopsCouponList) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {//加载更多
                            mPtrHelper.processLoadMore(shopsCouponList);
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
                mPtrHelper.autoRefresh(true);
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
    /**
     * 订单列表适配器
     */
    private class CouponAdapter extends QuickAdapter<YangZhiChangDanAn.PageBean.ListBean> {

        public CouponAdapter(int layoutResId, List<YangZhiChangDanAn.PageBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final YangZhiChangDanAn.PageBean.ListBean shopsCoupon) {
            holder.setText(R.id.tv_title_name, "养殖场名称："+shopsCoupon.getAquFarm().getFarmName());
            holder.setText(R.id.tv_xiangxidizhi, "详细地址: "+shopsCoupon.getAquFarm().getAddress());
            if (shopsCoupon.getAquFarm().getFarmMethod()!=null){
                if (shopsCoupon.getAquFarm().getFarmMethod().equals("1")){
                    holder.setText(R.id.tv_yangzhimoshi, "养殖模式: "+"池塘");
                }else if (shopsCoupon.getAquFarm().getFarmMethod().equals("2")){
                    holder.setText(R.id.tv_yangzhimoshi, "养殖模式: "+"大水面放养");
                }else if (shopsCoupon.getAquFarm().getFarmMethod().equals("3")){
                    holder.setText(R.id.tv_yangzhimoshi, "养殖模式: "+"围栏");
                }else if (shopsCoupon.getAquFarm().getFarmMethod().equals("4")){
                    holder.setText(R.id.tv_yangzhimoshi, "养殖模式: "+"工厂化");
                }else if (shopsCoupon.getAquFarm().getFarmMethod().equals("5")){
                    holder.setText(R.id.tv_yangzhimoshi, "养殖模式: "+"筏吊式");
                }else if (shopsCoupon.getAquFarm().getFarmMethod().equals("6")){
                    holder.setText(R.id.tv_yangzhimoshi, "养殖模式: "+"滩涂底播");
                }else if (shopsCoupon.getAquFarm().getFarmMethod().equals("7")){
                    holder.setText(R.id.tv_yangzhimoshi, "养殖模式: "+"网箱");
                }
            }
            if (shopsCoupon.getAquFarm().getCompanyType()!=null){
                if (shopsCoupon.getAquFarm().getCompanyType().equals("1")){
                    holder.setText(R.id.tv_qiyeleixing, "企业类型: "+"私营独资企业");
                }else if (shopsCoupon.getAquFarm().getCompanyType().equals("2")){
                    holder.setText(R.id.tv_qiyeleixing, "企业类型: "+"私营有限责任公司");
                }else if (shopsCoupon.getAquFarm().getCompanyType().equals("3")){
                    holder.setText(R.id.tv_qiyeleixing, "企业类型: "+" 其他有限责任公司");
                }else if (shopsCoupon.getAquFarm().getCompanyType().equals("4")){
                    holder.setText(R.id.tv_qiyeleixing, "企业类型: "+"其他内资企业");
                }else if (shopsCoupon.getAquFarm().getCompanyType().equals("5")){
                    holder.setText(R.id.tv_qiyeleixing, "企业类型: "+"中外合资经营企业");
                }
            }
            holder.setText(R.id.tv_nianchangliang, "年产量: "+shopsCoupon.getAquFarm().getAnnualOutput());
        }
    }

    /**
     * 释放内存
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (messageListAdapter != null)
            messageListAdapter = null;
        if (mPtrHelper != null)
            mPtrHelper = null;
    }
}
