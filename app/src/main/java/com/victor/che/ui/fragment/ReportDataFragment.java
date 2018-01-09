package com.victor.che.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.Reports;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 报表数据界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class ReportDataFragment extends BaseFragment {

    private static final String FLAG = "mFlag";

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_last_count)
    TextView tv_last_count;

    @BindView(R.id.tv_last_amount)
    TextView tv_last_amount;

    @BindView(R.id.tv_current_count)
    TextView tv_current_count;

    @BindView(R.id.tv_current_amount)
    TextView tv_current_amount;

    private PtrHelper<Reports> mPtrHelper;
    private List<Reports> mList = new ArrayList<>();
    private ReportsListAdapter mAdapter;

    /**
     * 创建实例
     *
     * @param flag
     * @return
     */
    public static ReportDataFragment newInstance(int flag) {

        Bundle args = new Bundle();
        args.putInt(FLAG, flag);

        ReportDataFragment fragment = new ReportDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_report_data;
    }


    private int flag = 1;// 报表类型 1-日报 2-周报 3-月报
    private String lastText = "昨天";
    private String currentText = "今天";


    @Override
    protected void initView() {
        flag = getArguments().getInt(FLAG, 1);

        switch (flag) {
            case 2://周报
                lastText = "上周";
                currentText = "本周";
                break;
            case 3://月报
                lastText = "上月";
                currentText = "本月";
                break;
            default://日报
                lastText = "昨天";
                currentText = "今天";
                break;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mAdapter = new ReportsListAdapter(R.layout.item_report_data, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });

        // 自动刷新
        mPtrHelper.autoRefresh(true);
    }

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {

        // 获取报表数据
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("type", flag);//报表类型 1-日报 2-周报 3-月报
        params.put("start", curpage);//列表记录开始位置
        params.put("pageSize", pageSize);//一页显示行数
        VictorHttpUtil.doGet(mContext, Define.URL_REPORT_LIST, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {

                        JSONObject jsonobj = JSON.parseObject(element.data);


                        JSONObject last = jsonobj.getJSONObject("last");
                        if (last != null) {
                            // 昨天(上周，上月)用户数
                            tv_last_count.setText(lastText + "新增：" + last.getString("count"));
                            // 昨天(上周，上月)交易额
                            tv_last_amount.setText(lastText + "新增：" + MathUtil.getFinanceValue(last.getDoubleValue("amount")) + "元");
                        }

                        JSONObject current = jsonobj.getJSONObject("current");
                        if (current != null) {
                            // 今天（本周，本月）用户数
                            tv_current_count.setText(currentText + "新增：" + current.getString("count"));
                            // 今天（本周，本月）交易额
                            tv_current_amount.setText(currentText + "新增：" + MathUtil.getFinanceValue(current.getDoubleValue("amount")) + "元");
                        }

                        List<Reports> temp = JSON.parseArray(jsonobj.getString("history"), Reports.class);
                        if (pullToRefresh) {// 下拉刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                // 有数据
                                mList.addAll(temp);
                                mAdapter.setNewData(mList);
                                mAdapter.notifyDataSetChanged();

                                if (CollectionUtil.getSize(temp) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {// 加载更多
                            mPtrHelper.processLoadMore(temp);
                        }
                    }

                });
    }

    /**
     * 客户会员卡列表适配器
     */
    private class ReportsListAdapter extends QuickAdapter<Reports> {

        public ReportsListAdapter(int layoutResId, List<Reports> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final Reports item) {
            helper.setText(R.id.tv_time, item.date)//日期
                    .setText(R.id.tv_customer_count, item.count)//客户数量
                    .setText(R.id.tv_sales_amount, MathUtil.getFinanceValue(item.amount));//交易额
        }
    }
}
