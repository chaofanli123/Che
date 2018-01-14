package com.victor.che.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.QueryUserCarHistory;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.ToastUtils;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 * 二手车历史估值
 */
public class UsercarHistoryValuesFragment extends BaseFragment {

    @BindView(R.id.recycler_usr_car)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl_user_car)
    PtrFrameLayout pcflUserCar;
    /**
     * 历史估值adapter
     */
    private HistoryQueryListAdapter messageListAdapter;
    private List<QueryUserCarHistory> messageArrayList = new ArrayList<QueryUserCarHistory>();
    private PtrHelper<QueryUserCarHistory> mPtrHelper;

    @Override
    public int getContentView() {
        return R.layout.fragment_usercar_history_values;
    }

    @Override
    protected void initView() {
        super.initView();
        messageListAdapter = new HistoryQueryListAdapter(R.layout.item_query_user_car_history, messageArrayList);
        mPtrHelper = new PtrHelper<>(pcflUserCar, messageListAdapter, messageArrayList);
        mPtrHelper.enableLoadMore(true, recycler);//允许加载更多
        recycler.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        recycler.setAdapter(messageListAdapter);
        recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                loadData(pullToRefresh, curpage, pageSize);
            }
        });
        recycler.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {

            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                Bundle bundle = new Bundle();
                if (position < 0 || position >= messageArrayList.size()) {
                    return;
                }

                if ("0".equals(messageArrayList.get(position).purchase_price) ||
                        "0".equals(messageArrayList.get(position).deal_price)) {
                    //估值失败
                    ToastUtils.show(mContext, "估值失败");
                    return;
                }
                int usedcar_assess_record_id = messageArrayList.get(position).usedcar_assess_record_id;
                String car_plate_no = messageArrayList.get(position).car_plate_no;
                bundle.putInt("usedcar_assess_record_id", usedcar_assess_record_id);
                bundle.putString("car_plate_no", car_plate_no);

            }
        });
        //        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
        //            @Override
        //            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //                super.onScrollStateChanged(recyclerView, newState);
        //            }
        //
        //            @Override
        //            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //                mPtrHelper.enablePullToRefresh(new LinearLayoutManager(mContext)
        //                        .findFirstCompletelyVisibleItemPosition() == 0);
        //            }
        //        });
        mPtrHelper.autoRefresh(true);
    }

    /**
     * 获取二手车历史估值记录列表
     *
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("pageSize", pageSize);
        params.put("start", curpage);
        VictorHttpUtil.doGet(mContext, Define.url_usedcar_history_list_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<QueryUserCarHistory> queryUserCarHistories = JSON.parseArray(element.body, QueryUserCarHistory.class);
                        //                        List<QueryUserCarHistory> queryUserCarHistories = new ArrayList<QueryUserCarHistory>();
                        if (pullToRefresh) {////刷新
                            messageArrayList.clear();//清空数据
                            if (CollectionUtil.isEmpty(queryUserCarHistories)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                // 有数据
                                messageArrayList.addAll(queryUserCarHistories);
                                messageListAdapter.setNewData(messageArrayList);
                                messageListAdapter.notifyDataSetChanged();

                                if (CollectionUtil.getSize(queryUserCarHistories) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {//加载更多
                            mPtrHelper.processLoadMore(queryUserCarHistories);
                        }

                    }
                });

    }

    /**
     * 订单列表适配器
     */
    private class HistoryQueryListAdapter extends QuickAdapter<QueryUserCarHistory> {

        public HistoryQueryListAdapter(int layoutResId, List<QueryUserCarHistory> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, QueryUserCarHistory queryUserCarHistory) {

            //根据返回的价格是否为空来判断是否估值成功
            boolean success = !("0".equals(queryUserCarHistory.purchase_price) ||
                    "0".equals(queryUserCarHistory.deal_price));
            holder.setText(R.id.car_plate_no, queryUserCarHistory.car_plate_no)//车牌号
                    .setText(R.id.car_model, queryUserCarHistory.car_model_name)//车型
                    .setText(R.id.tv_city, queryUserCarHistory.city_name)//城市
                    .setText(R.id.tv_time, queryUserCarHistory.create_time)//时间
                    .setVisible(R.id.ll_user_car_success, success)
                    .setVisible(R.id.ll_user_car_error, !success);
            if (success) { //查询成功
                holder.setText(R.id.tv_car_purchase_price, queryUserCarHistory.purchase_price + "万");//收购价格
                holder.setText(R.id.tv_deal_price, queryUserCarHistory.deal_price + "万");//交易价格
            }
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

}
