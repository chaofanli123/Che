package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Vipcard;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.PtrHelper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 会员卡列表界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 15:57
 */
public class VipcardListActivity extends BaseActivity {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private List<Vipcard> mList = new ArrayList<>();
    private VipcardListAdapter mAdapter;

    private PtrHelper<Vipcard> mPtrHelper;

    private String provider_user_id;

    @Override
    public int getContentView() {
        return R.layout.common_pull_to_refresh_recyclerview;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("会员卡");

        findViewById(R.id.root).setBackgroundColor(getResources().getColor(R.color.theme_color));

        provider_user_id = getIntent().getStringExtra("provider_user_id");

        //        int padding = ViewUtil.dip2px(mContext, 10);
        //        mRecyclerView.setPadding(padding, padding, padding, padding);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(20)
                .colorResId(R.color.transparent)
                .build());//添加分隔线
        mAdapter = new VipcardListAdapter(R.layout.item_vipcard_list, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });

        mPtrHelper.autoRefresh(true);
    }

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {

        // 获取订单列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("provider_user_id", provider_user_id);// 用户编号,(获取用户消费记录时必传)
        params.put("start", curpage);//列表记录开始位置
        params.put("pageSize", pageSize);//一页显示行数
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_USER_CARD_LIST, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Vipcard> temp = JSON.parseArray(element.data, Vipcard.class);
                        if (pullToRefresh) {// 下拉刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                ImageView iv_no_data = (ImageView) common_no_data.findViewById(R.id.iv_no_data);
                                iv_no_data.setImageResource(R.drawable.ic_no_vipcard);
                                TextView tv_tip = (TextView) common_no_data.findViewById(R.id.tv_tip);
                                tv_tip.setText("无会员卡！");
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
     * 会员卡列表适配器
     */
    private class VipcardListAdapter extends QuickAdapter<Vipcard> {

        public VipcardListAdapter(int layoutResId, List<Vipcard> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final Vipcard item) {
            final boolean isExpire = item.isExpire();
            helper
                    //                    .setImageResource(R.id.iv_vipcard_logo, isExpire ? R.drawable.ic_vipcard_invalid : R.drawable.bg_vipcard_valid)
                    //                    .setTextColor(R.id.tv_card_name, isExpire ? R.color.disabled : R.color.black_text)
                    //                    .setTextColor(R.id.tv_cardno, isExpire ? R.color.disabled : R.color.black_text)
                    //                    .setTextColor(R.id.label_available_service, isExpire ? R.color.disabled : R.color.c_9a9a9a)
                    //                    .setTextColor(R.id.tv_service_content, isExpire ? R.color.disabled : R.color.disabled)
                    //                    .setTextColor(R.id.label_remain_value, isExpire ? R.color.disabled : R.color.c_9a9a9a)
                    //                    .setTextColor(R.id.tv_remain_value, isExpire ? R.color.disabled : R.color.black_text)
                    //                    .setTextColor(R.id.label_end_time, isExpire ? R.color.disabled : R.color.c_9a9a9a)
                    //                    .setTextColor(R.id.tv_end_time, isExpire ? R.color.disabled : R.color.black_text)
                    //                    .setBackgroundRes(R.id.root, isExpire ? R.drawable.bg_vipcard_invalid : R.drawable.bg_vipcard_valid)
                    //                    .setImageResource(R.id.iv_show_hide, isExpire ? R.drawable.ic_hide_content_invalid : R.drawable.ic_hide_content)
                    .setText(R.id.tv_card_name, item.name)// 卡的名称
                    .setText(R.id.tv_remain_value, item.card_category_id == 1 ? item.num + "次" : MathUtil.getMoneyText(item.money))// 卡的余次/余额
                    .setText(R.id.tv_cardno, item.card_no)// 卡的描述
                    .setText(R.id.tv_service_content, item.getUsedGoodsText())// 可用服务
                    .setText(R.id.label_remain_value, item.getCardTypeLabel())//卡类型
                    .setText(R.id.tv_remain_value, item.getRemainValue())//余额
                    .setText(R.id.tv_end_time, item.getEndTime());//有效期

            final TextView tv_service_content = helper.getView(R.id.tv_service_content);
            final ImageView iv_show_hide = helper.getView(R.id.iv_show_hide);

            // 显示或隐藏内容
            helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isExpire) {
                        iv_show_hide.setImageResource(tv_service_content.getMaxLines() == 1 ? R.drawable.ic_show_content_invalid : R.drawable.ic_hide_content_invalid);
                    } else {
                        iv_show_hide.setImageResource(tv_service_content.getMaxLines() == 1 ? R.drawable.ic_show_content : R.drawable.ic_hide_content);
                    }
                    tv_service_content.setMaxLines(tv_service_content.getMaxLines() == 1 ? Integer.MAX_VALUE : 1);
                }
            });

            // 编辑
            helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("mPosition", helper.getLayoutPosition());
                    bundle.putSerializable("mVipcard", item);
                    MyApplication.openActivity(mContext, EditVipcardActivity.class, bundle);
                }
            });

            // 充值
            TextView tv_recharge = helper.getView(R.id.tv_recharge);
            if (item.card_category_id == 1 || item.card_category_id == 2) {//次卡或储值卡
                // 充值
                tv_recharge.setEnabled(true);
                tv_recharge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("mPosition", helper.getLayoutPosition());
                        bundle.putSerializable("mVipcard", item);
                        MyApplication.openActivity(mContext, RechargeVipcardActivity.class, bundle);
                    }
                });
            } else {
                tv_recharge.setEnabled(false);
            }
        }
    }
}
