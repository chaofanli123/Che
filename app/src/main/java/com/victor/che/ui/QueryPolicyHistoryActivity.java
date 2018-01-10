package com.victor.che.ui;

import android.graphics.Color;
import android.os.Bundle;
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
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.QueryPolicyHistory;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.MyRecyclerView;
import com.victor.che.widget.TipDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/11 0011 9:34.
 * 出险记录列表界面
 */

public class QueryPolicyHistoryActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    MyRecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    private HistoryQueryListAdapter messageListAdapter;
    private List<QueryPolicyHistory> messageArrayList = new ArrayList<>();
    private PtrHelper<QueryPolicyHistory> mPtrHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_pending_order_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("出险记录");

        messageListAdapter = new HistoryQueryListAdapter(R.layout.item_policy_history_list, messageArrayList);
        mPtrHelper = new PtrHelper<>(mPtrFrame, messageListAdapter, messageArrayList);

        mPtrHelper.enableLoadMore(true, mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(messageListAdapter);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(20)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                loadData(pullToRefresh, curpage, pageSize);
            }
        });

        mRecyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {

            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                Bundle bundle = new Bundle();
                if (position == -1 || position >= messageArrayList.size()) {
                    return;
                }
                if (messageArrayList.get(position).status == 1) {//待支付
                    showDialog(messageArrayList.get(position));
                } else if (messageArrayList.get(position).status == 2) {//出保中
                    showDialog(messageArrayList.get(position));
                } else if(messageArrayList.get(position).status == 3){//已出保
                    bundle.putSerializable("queryPolicyHistory",messageArrayList.get(position));
                    MyApplication.openActivity(mContext,PolicyDetailActivity.class,bundle);
                }

            }
        });
        mPtrHelper.autoRefresh(true);
    }

    /**
     * 显示对话框
     */
    private void showDialog(final QueryPolicyHistory queryPolicyHistory) {
        String msg;
        if(queryPolicyHistory.status == 1){
            //待支付
            msg = "待支付...";
        }else{
            //出保中
            msg = "出保中...\n"+"请耐心等待";
        }

        TipDialogFragment.newInstance("提示",msg).show(getSupportFragmentManager(),getClass().getSimpleName());
    }


    /**
     * 获取出险查询记录列表
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize ) {
        MyParams params=new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        //        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登陆者id
        params.put("pageSize",pageSize);
        params.put("start",curpage);
        VictorHttpUtil.doGet(mContext, Define.url_querl_insurance_order_list, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<QueryPolicyHistory> queryPolicyHistories = JSON.parseArray(element.data, QueryPolicyHistory.class);
                        if (pullToRefresh) {////刷新
                            messageArrayList.clear();//清空数据
                            if (CollectionUtil.isEmpty(queryPolicyHistories)) {  //无数
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            }else {
                                // 有数据
                                messageArrayList.addAll(queryPolicyHistories);
                                messageListAdapter.setNewData(messageArrayList);
                                messageListAdapter.notifyDataSetChanged();

                                if (CollectionUtil.getSize(queryPolicyHistories) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        }else {//加载更多
                            mPtrHelper.processLoadMore(queryPolicyHistories);
                        }

                    }
                });

    }


    /**
     * 订单列表适配器
     */
    private class HistoryQueryListAdapter extends QuickAdapter<QueryPolicyHistory> {

        public HistoryQueryListAdapter(int layoutResId, List<QueryPolicyHistory> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, QueryPolicyHistory queryPolicyHistory) {

            String queryState;
            int stateColor;
            boolean queryIsShow = false;
            if (queryPolicyHistory.getStatus()==1) { //待支付
                queryState = "待支付";
                stateColor = Color.parseColor("#007dc9");
            }else if (queryPolicyHistory.getStatus() == 2) {//出保中
                queryState= "出保中";
                stateColor = Color.parseColor("#666666");
            }else{//已出保
                queryState = "已出保";
                stateColor = Color.parseColor("#45c018");
                queryIsShow = true;
            }

            holder.setText(R.id.tv_car_no,queryPolicyHistory.car_plate_no)
                    .setText(R.id.tv_query_state,queryState)
                    .setTextColor(R.id.tv_query_state,stateColor)
                    .setText(R.id.tv_car_user_name,queryPolicyHistory.name)
                    .setText(R.id.tv_car_brand_name,queryPolicyHistory.license_brand_model)
                    .setText(R.id.tv_insurance_name,queryPolicyHistory.insurance_company_name)
                    .setText(R.id.tv_actual_pay,queryPolicyHistory.actual_total_price+"元")
                    .setVisible(R.id.ll_query_other,queryIsShow);
        }

    }
}
