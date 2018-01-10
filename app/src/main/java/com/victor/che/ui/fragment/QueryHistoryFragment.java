package com.victor.che.ui.fragment;


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
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.QueryBaoxianHistory;
import com.victor.che.ui.QueryBaoxianActivity;
import com.victor.che.ui.QueryjieguoActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.MyRecyclerView;
import com.victor.che.widget.PostiDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 查询历史界面
 */
public class QueryHistoryFragment extends BaseFragment {

    @BindView(R.id.recycler)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl)
    PtrFrameLayout pcfl;
    private int index;

    /**
     * 愿望adapter
     */
    private HistoryQueryListAdapter messageListAdapter;
    private List<QueryBaoxianHistory> messageArrayList = new ArrayList<QueryBaoxianHistory>();
    private PtrHelper<QueryBaoxianHistory> mPtrHelper;
    @Override
    public int getContentView() {
        return R.layout.fragment_query_history;
    }
    @Override
    protected void initView() {
        super.initView();
        messageListAdapter = new HistoryQueryListAdapter(R.layout.item_query_history, messageArrayList);
        mPtrHelper = new PtrHelper<>(pcfl, messageListAdapter, messageArrayList);
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
                index = position;
                Bundle bundle = new Bundle();
                if (position == -1 || position>=messageArrayList.size()) {
                    return;
                }
                if (messageArrayList.get(position).status==3) {//失败
                         //显示对话框
                    showDialog(messageArrayList.get(position));
                }else if(messageArrayList.get(position).status==2){//完成
                    String car_plate_no = messageArrayList.get(position).getCar_plate_no();//车牌号
//                    String mobile = messageArrayList.get(position).getMobile();
                    bundle.putString("mUrl",  Define.MWEB_DOMAIN+"web/insurance/insuranceResult.html?car_plate_no=");
                    bundle.putBoolean("mShowShare", true);// 显示分享
                    bundle.putString("car_plate_no",car_plate_no);
//                    bundle.putString("mobile",mobile);
                    MyApplication.openActivity(mContext, QueryjieguoActivity.class, bundle);

                }else if (messageArrayList.get(position).status==1) {//查询中
                    String car_plate_no = messageArrayList.get(position).getCar_plate_no();//车牌号
                    bundle.putString("mUrl",  Define.MWEB_DOMAIN+"web/insurance/insuranceWriting.html");
                    bundle.putBoolean("mShowShare", false);// 显示分享
                   // bundle.putString("car_plate_no",car_plate_no);
                    MyApplication.openActivity(mContext, QueryjieguoActivity.class, bundle);
                }

            }
        });
        mPtrHelper.autoRefresh(true);
    }
    /**
     * 显示对话框
     */
    private void showDialog(final QueryBaoxianHistory queryBaoxianHistory) {
        String msg = "  查询失败是您的信息填写不正确 \n或照片拍摄不清楚，您要重新编辑吗？";
        PostiDialogFragment.newInstance(
                "提示",
                msg,
                "确定",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("querybaoxianCar",queryBaoxianHistory);
                        MyApplication.openActivity(getActivity(),QueryBaoxianActivity.class,bundle);
                        getActivity().finish();
                    }
                }
                )
                .show(getFragmentManager(), getClass().getSimpleName());

//        DialogTool.showDialog(mContext,"提示",msg,true);
    }
    /**
     * 获取获取保险查询记录列表
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize ) {
        MyParams params=new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登陆者id
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
            if (queryBaoxianHistory.getStatus()==1) { //查询中
               holder.setBackgroundRes(R.id.root,R.drawable.icon_query_loading);
                holder.setText(R.id.label_end_time,"到期时间：");
                holder.setText(R.id.tv_card_num,queryBaoxianHistory.getCar_plate_no());
                holder.setText(R.id.tv_state,"查询中");
                holder.setTextColor(R.id.tv_state,getResources().getColor(R.color.on_sale));

            }else if (queryBaoxianHistory.getStatus() == 2) {//完成
//                holder.setText(R.id.label_end_time,"到期时间："+queryBaoxianHistory.getEnd_time());
                holder.setText(R.id.tv_card_num,queryBaoxianHistory.getCar_plate_no());
                holder.setText(R.id.tv_state,"完成");
                holder.setTextColor(R.id.tv_state,getResources().getColor(R.color.theme_color));
              holder.setBackgroundRes(R.id.root,R.drawable.icon_query_finish);

            }else if (queryBaoxianHistory.getStatus() == 3) {//失败
                holder.setText(R.id.label_end_time,"到期时间：");
                holder.setText(R.id.tv_card_num,queryBaoxianHistory.getCar_plate_no());
                holder.setText(R.id.tv_state,"查询失败");
                holder.setTextColor(R.id.tv_state,getResources().getColor(R.color.red));
              holder.setBackgroundRes(R.id.root,R.drawable.icon_query_false);
            }

        }

    }
}
