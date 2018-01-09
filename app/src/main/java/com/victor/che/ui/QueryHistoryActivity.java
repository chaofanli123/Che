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
import com.victor.che.domain.QueryBaoxianHistory;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.MyRecyclerView;
import com.victor.che.widget.TipDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/8 0008 16:38.
 * 询价记录列表界面
 */

public class QueryHistoryActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    MyRecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    private HistoryQueryListAdapter messageListAdapter;
    private List<QueryBaoxianHistory> messageArrayList = new ArrayList<QueryBaoxianHistory>();
    private PtrHelper<QueryBaoxianHistory> mPtrHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_pending_order_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("询价记录");
        messageListAdapter = new HistoryQueryListAdapter(R.layout.item_query_history_list,messageArrayList);
        mPtrHelper = new PtrHelper<>(mPtrFrame,messageListAdapter,messageArrayList);

        mPtrHelper.enableLoadMore(true,mRecyclerView);
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
                if (position == -1 || position>=messageArrayList.size()) {
                    return;
                }
                if (messageArrayList.get(position).status==1) {//查询中
                    showDialog(messageArrayList.get(position));
                }else if(messageArrayList.get(position).status==2){//完成
                    bundle.putSerializable("queryBaoxianHistory",messageArrayList.get(position));
                    MyApplication.openActivity(mContext, QueryQuoteActivity.class, bundle);
                }else{//失败   status = 3,4,5
                    showDialog(messageArrayList.get(position));
                }

            }
        });
        mPtrHelper.autoRefresh(true);
    }


    /**
     * 显示对话框
     */
    private void showDialog(final QueryBaoxianHistory queryBaoxianHistory) {
        String msg;
        if(queryBaoxianHistory.status == 1){
            //报价中
            msg = "报价中...\n"+"请耐心等待";
        }else if(queryBaoxianHistory.status == 7){
            msg = "已出保";
        }else{
            //失败
            msg = "核保失败:"+queryBaoxianHistory.fail_reason + queryBaoxianHistory.remark;
        }

        TipDialogFragment.newInstance("提示",msg).show(getSupportFragmentManager(),getClass().getSimpleName());
    }


    /**
     * 获取获取保险查询记录列表
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize ) {
        MyParams params=new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
//        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登陆者id
        params.put("pageSize",pageSize);
        params.put("start",curpage);
        VictorHttpUtil.doGet(mContext, Define.url_querl_list, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<QueryBaoxianHistory> queryBaoxianHistories = JSON.parseArray(element.data, QueryBaoxianHistory.class);
                        if (pullToRefresh) {////刷新
                            messageArrayList.clear();//清空数据
                            if (CollectionUtil.isEmpty(queryBaoxianHistories)) {  //无数
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            }else {
                                // 有数据
                                messageArrayList.addAll(queryBaoxianHistories);
                                messageListAdapter.setNewData(messageArrayList);
                                messageListAdapter.notifyDataSetChanged();

                                if (CollectionUtil.getSize(queryBaoxianHistories) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        }else {//加载更多
                            mPtrHelper.processLoadMore(queryBaoxianHistories);
                        }

                    }
                });

    }


    /**
     * 订单列表适配器
     */
    private class HistoryQueryListAdapter extends QuickAdapter<QueryBaoxianHistory> {

        public HistoryQueryListAdapter(int layoutResId, List<QueryBaoxianHistory> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, QueryBaoxianHistory queryBaoxianHistory) {

            String queryState;
            int stateColor;
            boolean queryIsShow = false;
            if (queryBaoxianHistory.getStatus()==1) { //查询中
                queryState = "报价中";
                stateColor = Color.parseColor("#666666");
            }else if (queryBaoxianHistory.getStatus() == 2) {//完成
                queryState= "已报价";
                stateColor = Color.parseColor("#45c018");
            }else if (queryBaoxianHistory.getStatus() == 7){//已出保
                queryState= "已出保";
                stateColor = Color.parseColor("#666666");
            }else {//失败
                queryState = "核保失败";
                stateColor = Color.parseColor("#ef5350");
                queryIsShow = true;
            }


            holder.setText(R.id.tv_car_no,queryBaoxianHistory.car_plate_no)
                    .setText(R.id.tv_query_state,queryState)
                    .setTextColor(R.id.tv_query_state, stateColor)
                    .setText(R.id.tv_car_user_name,queryBaoxianHistory.name)
                    .setText(R.id.tv_car_brand_name,queryBaoxianHistory.license_brand_model)
                    .setText(R.id.tv_query_time,queryBaoxianHistory.create_time)
                    .setVisible(R.id.ll_query_other,queryIsShow)
                    .setVisible(R.id.ll_query_other2,queryBaoxianHistory.getStatus()==2);

        }

    }

}
