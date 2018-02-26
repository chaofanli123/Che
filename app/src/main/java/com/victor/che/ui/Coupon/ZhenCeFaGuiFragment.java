package com.victor.che.ui.Coupon;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.victor.che.base.BaseFragment;
import com.victor.che.base.SimpleTextWatcher;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.bean.Policy;
import com.victor.che.ui.my.ZhengCheFaGuiActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.DateUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.ClearEditText;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.victor.che.widget.ListDialogFragment;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 通知政策法规
 */
public class ZhenCeFaGuiFragment extends BaseFragment {
    @BindView(R.id.recycler_usr_car)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl_user_car)
    PtrFrameLayout pcflUserCar;
    @BindView(R.id.tv_time_gongshi)
    ClearEditText tvTimeGongshi;
    Unbinder unbinder;


    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.btn_order_type)
    Button btn_order_type;
    @BindView(R.id.lin_search)
    LinearLayout linSearch;
    /**
     * adapter
     */
    private CouponAdapter messageListAdapter;
    private List<Policy.PageBean.ListBean> messageArrayList;
    private PtrHelper<Policy.PageBean.ListBean> mPtrHelper;
    private int index;/*点击的愿望下标*/
    public static int currentPos = 0;//当前位置

    private String keywords = "";// 搜索title
    private String type = "";// 搜索类型
    private String sendtime = "";// 公示时间
    private int selectedOrderTypePos = 0; //类型
    private OrderTypeListAdapter ordertypeListAdapter; //类型适配器
    private int selectedOrderStatePos = 0; //状态
    //类型 1会议通告 2奖惩通告 3活动通告
    private String[] ORDER_TYPE = {"全部", "部门文件","法规规章","规范性文件","政策解读"};

    @Override
    public int getContentView() {
        return R.layout.fragment_zhuangchefagui;
    }

    @Override
    protected void initView() {
        /**
         * 订单状态
         */
        ordertypeListAdapter = new OrderTypeListAdapter(mContext, R.layout.item_list_dialog, ORDER_TYPE);
        linSearch.setFocusable(true);
        linSearch.setFocusableInTouchMode(true);
        et_search.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                keywords = s.toString().trim();
                _doSearch();
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keywords = et_search.getText().toString().trim();
                    _doSearch();
                }
                return false;
            }
        });
        messageArrayList = new ArrayList<>();
        messageListAdapter = new CouponAdapter(R.layout.item_coupon_zhengchefagui, messageArrayList);
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
//                ShopsCoupon shopsCoupon = messageArrayList.get(position);
                if (!CollectionUtil.isEmpty(messageArrayList)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", messageArrayList.get(position).getId());
                    MyApplication.openActivity(mContext, ZhengCheFaGuiActivity.class, bundle);
                }
            }
        });
        mPtrHelper.autoRefresh(false);
    }

//    @Subscribe
//    public void onSearch(SearchEvent event) {
//        if (event == null) {
//            return;
//        }
//        this.keywords = event.keywords;
//        this.type = event.type;
//        this.status = event.status;
//        if (currentPos == event.currentPos) {//只处理当前页事件
//            mPtrHelper.autoRefresh(true);
//        }
//    }
    /**
     * 开始搜索
     */
    private void _doSearch() {
        mPtrHelper.autoRefresh(true);
        //  EventBus.getDefault().post(new SearchEvent(keywords, type, status, currentPos));
    }

    /**
     * 显示类型
     */
    @OnClick(R.id.btn_order_type)
    void showOrdertypeDialog() {
        ListDialogFragment.newInstance(ordertypeListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderTypePos = position;
                ordertypeListAdapter.notifyDataSetChanged();
                btn_order_type.setText(ORDER_TYPE[position]);
                btn_order_type.requestLayout();// 防止文字和图片覆盖
                //类型  1"部门文件",2 "法规规章", 3"规范性文件",4"政策解读"
                if (selectedOrderTypePos == 0) {
                    type = "";
                } else {
                    type = selectedOrderTypePos + "";
                }
                _doSearch();
            }
        }).show(getFragmentManager(), getClass().getSimpleName());
    }
    /**
     * 获取商家优惠券
     *
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);//
        params.put("pageNo", curpage / pageSize + 1);
        params.put("pageSize", pageSize);
        if (!StringUtil.isEmpty(keywords)) {
            params.put("title", keywords);
        }
        if (!StringUtil.isEmpty(type)) {
            params.put("type", type);
        }
        //公示时间
        if (!StringUtil.isEmpty(sendtime)) {
            params.put("sendtime", sendtime);
        }
        VictorHttpUtil.doPost(mContext, Define.URL_ZHENGCHEFAGUI + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        Policy Policy = JSON.parseObject(element.body, Policy.class);
                        //                        List<QueryUserCarHistory> queryUserCarHistories = new ArrayList<QueryUserCarHistory>();
                        List<Policy.PageBean.ListBean> shopsCouponList = new ArrayList<>();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 选择时间显示
     */
    @OnClick(R.id.tv_time_gongshi)
    public void ontimeClicked() {
        showDatePickerDialog(tvTimeGongshi);
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
                        sendtime=tv.getText().toString().trim();
                        _doSearch();
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
        mDialogYearMonthDay.show(getFragmentManager(), getClass().getSimpleName());
    }
    /**
     * 订单列表适配器
     */
    private class CouponAdapter extends QuickAdapter<Policy.PageBean.ListBean> {

        public CouponAdapter(int layoutResId, List<Policy.PageBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final Policy.PageBean.ListBean shopsCoupon) {

            holder.setText(R.id.tv_time, shopsCoupon.getCreateDate());
            holder.setText(R.id.tv_title, "标题: " + shopsCoupon.getTitle());
            if (shopsCoupon.getType().equals("1")) {
                holder.setText(R.id.tv_leixing, "类型: " + "部门文件");
            } else if (shopsCoupon.getType().equals("2")) {
                holder.setText(R.id.tv_leixing, "类型: " + "法规规章");
            } else if (shopsCoupon.getType().equals("3")) {
                holder.setText(R.id.tv_leixing, "类型: " + "规范性文件");
            } else if (shopsCoupon.getType().equals("4")) {
                holder.setText(R.id.tv_leixing, "类型: " + "政策解读");
            }
            holder.setText(R.id.tv_beizhuxingxi, "备注信息: " + shopsCoupon.getRemarks());
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
//    @Subscribe
//    public void onMessageEvent(MessageEvent event) {
//        switch (event.code) {
//            case MessageEvent.ALL_WISH_RELOAD:///*刷新全部愿望列表*/
//            //    Map<String, String> paramMap = (Map<String, String>) event.object;
//                mPtrHelper.autoRefresh(true);
//                break;
//            case MessageEvent.ALL_WISH_REFRESH:///*刷新局部愿望*/
//                if (event.position!=-1){
//                    index=event.position;
//                    //  messageListAdapter.PolicyDataSetChanged();
//                }
//                if (index >= 0) {
//                    if (event.object != null) {
//                        Policy shopsCoupon= (Policy) event.object;
//                        messageArrayList.set(index, shopsCoupon);
//                    } else {
//                        messageArrayList.remove(index);
//                    }
//                    index = -1;
//                    messageListAdapter.PolicyDataSetChanged();
//                }
//                break;
//        }
//    }

    /**
     * 订单类型适配器
     */
    private class OrderTypeListAdapter extends VictorBaseArrayAdapter<String> {

        public OrderTypeListAdapter(Context context, int layoutResId, String[] array) {
            super(context, layoutResId, array);
        }

        @Override
        public void bindView(int position, View view, String entity) {
            TextView textView = (TextView) view;
            textView.setText(entity);
            ViewUtil.setDrawableRight(mContext, textView, selectedOrderTypePos == position ? R.drawable.ic_checked : R.drawable.ic_unchecked);
        }
    }
}
