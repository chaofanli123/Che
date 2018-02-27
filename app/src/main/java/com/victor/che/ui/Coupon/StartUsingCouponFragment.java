package com.victor.che.ui.Coupon;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phillipcalvin.iconbutton.IconButton;
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
import com.victor.che.bean.Notify;
import com.victor.che.ui.my.TongZhiXiaDaActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.victor.che.widget.ListDialogFragment;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 *通知下达列表
 */
public class StartUsingCouponFragment extends BaseFragment {
    @BindView(R.id.recycler_usr_car)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl_user_car)
    PtrFrameLayout pcflUserCar;

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.btn_order_type)
    Button btn_order_type;
    @BindView(R.id.btn_order_state)
    IconButton btnOrderstate;
    @BindView(R.id.lin_search)
    LinearLayout linSearch;
    /**
     * adapter
     */
    private CouponAdapter messageListAdapter;
    private List<Notify.PageBean.ListBean> messageArrayList;
    private PtrHelper<Notify.PageBean.ListBean> mPtrHelper;
    private int index;/*点击的愿望下标*/
    public static int currentPos = 0;//当前位置

    private String keywords = "";// 搜索title
    private String type = "";// 搜索类型
    private String status = "";// 搜索状态
    private int selectedOrderTypePos = 0; //类型
    private OrderTypeListAdapter ordertypeListAdapter; //类型适配器
    private OrderStateListAdapter orderStateListAdapter; //状态适配器
    private int selectedOrderStatePos = 0; //状态
    //类型 1会议通告 2奖惩通告 3活动通告
    private String[] ORDER_TYPE = {"全部", "会议通告", "奖惩通告", "活动通告"};
    //状态 1草稿2发布
    private String[] ORDER_STATES = {"全部", "草稿", "发布"};

    @Override
    public int getContentView() {
        return R.layout.fragment_usercar_history_values;
    }
    @Override
    protected void initView() {
        /**
         * 订单状态
         */
        ordertypeListAdapter = new OrderTypeListAdapter(mContext, R.layout.item_list_dialog, ORDER_TYPE);
        orderStateListAdapter = new OrderStateListAdapter(mContext, R.layout.item_list_dialog, ORDER_STATES);
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
        messageListAdapter = new CouponAdapter(R.layout.item_coupon_startusing, messageArrayList);
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
                if (!CollectionUtil.isEmpty(messageArrayList)) {
                    startActivity(new Intent(mContext, TongZhiXiaDaActivity.class).putExtra("id",messageArrayList.get(position).getId()));
                }

            }
        });
            mPtrHelper.autoRefresh(false);
    }
    /**
     * 获取商家优惠券
     *
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);//
        params.put("pageNo",curpage/pageSize+1);
        params.put("pageSize", pageSize);
        if (!StringUtil.isEmpty(keywords)) {
            params.put("title", keywords);
        }
        if (!StringUtil.isEmpty(type)) {
            params.put("type", type);
        }
        if (!StringUtil.isEmpty(status)) {
            params.put("status", status);
        }
        VictorHttpUtil.doPost(mContext, Define.URL_TONGZHIXIADALIST+";JSESSIONID="+MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        Notify notify = JSON.parseObject(element.body, Notify.class);
                        List<Notify.PageBean.ListBean> shopsCouponList;
                        shopsCouponList=notify.getPage().getList();
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

    /**
     * 显示状态
     */
    @OnClick(R.id.btn_order_state)
    void showOrderStateDialog() {
        ListDialogFragment.newInstance(orderStateListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderStatePos = position;
                orderStateListAdapter.notifyDataSetChanged();
                btnOrderstate.setText(ORDER_STATES[position]);
                btnOrderstate.requestLayout();// 防止文字和图片覆盖
                //类型 1会议通告 2奖惩通告 3活动通告
                if (selectedOrderStatePos == 0) {
                    status = "";
                } else {
                    status = selectedOrderStatePos + "";
                }
                _doSearch();
            }
        }).show(getFragmentManager(), getClass().getSimpleName());
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
                //类型 1会议通告 2奖惩通告 3活动通告
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
     * 开始搜索
     */
    private void _doSearch() {
        mPtrHelper.autoRefresh(true);
      //  EventBus.getDefault().post(new SearchEvent(keywords, type, status, currentPos));
    }
//    @Subscribe
//    public void onSearch(SearchEvent event) {
//        if (event == null) {
//            return;
//        }
//        this.keywords = event.keywords;
//        this.type=event.type;
//        this.status=event.status;
//        if (currentPos == event.currentPos) {//只处理当前页事件
//            mPtrHelper.autoRefresh(true);
//        }
//    }

    /**
     * 订单列表适配器
     */
    private class CouponAdapter extends QuickAdapter<Notify.PageBean.ListBean> {

        public CouponAdapter(int layoutResId, List<Notify.PageBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final Notify.PageBean.ListBean shopsCoupon) {

            holder.setText(R.id.tv_time, shopsCoupon.getCreateDate());
            holder.setText(R.id.tv_title, "标题: "+shopsCoupon.getTitle());
            if (shopsCoupon.getType().equals("1")){
                holder.setText(R.id.tv_leixing,"类型: "+"会议通告" );
            }else if (shopsCoupon.getType().equals("2")){
                holder.setText(R.id.tv_leixing,"类型: "+"奖惩通告" );
            }else if (shopsCoupon.getType().equals("3")){
                holder.setText(R.id.tv_leixing,"类型: "+"活动通告" );
            }
            if (shopsCoupon.getStatus().equals("0")){
                holder.setText(R.id.tv_zhuangtai, "状态: "+"草稿");
            }else if (shopsCoupon.getStatus().equals("1")) {
                holder.setText(R.id.tv_zhuangtai, "状态: "+"发布");
            }
            int total = Integer.valueOf(shopsCoupon.getReadNum()) + Integer.valueOf(shopsCoupon.getUnReadNum());
            holder.setText(R.id.tv_caozuozhuangtai, "查阅状态: "+shopsCoupon.getReadNum()+"/"+total);
        }
    }
    /**
     * 释放内存
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(messageListAdapter!=null)
            messageListAdapter=null;
        if(mPtrHelper!=null)
            mPtrHelper=null;
    }


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

    /**
     * 订单状态适配器
     */
    private class OrderStateListAdapter extends VictorBaseArrayAdapter<String> {

        public OrderStateListAdapter(Context context, int layoutResId, String[] array) {
            super(context, layoutResId, array);
        }

        @Override
        public void bindView(int position, View view, String entity) {
            TextView textView = (TextView) view;
            textView.setText(entity);
            ViewUtil.setDrawableRight(mContext, textView, selectedOrderStatePos == position ? R.drawable.ic_checked : R.drawable.ic_unchecked);
        }
    }

}
