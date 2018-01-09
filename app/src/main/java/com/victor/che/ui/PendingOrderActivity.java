package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Pending;
import com.victor.che.domain.Shoppingcar;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.victor.che.widget.MyRecyclerView;
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
 * @time 2017/4/25 0025 14:05.
 * 挂单界面
 */

public class PendingOrderActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    MyRecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    private PendingOrderAdapter mAdapter;
    private PtrHelper<Pending> mPtrHelper;
    private ArrayList<Pending> mList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_pending_order_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("挂单");

        //        provider_user_id = getIntent().getStringExtra("provider_user_id");
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(20)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线
        mAdapter = new PendingOrderAdapter(R.layout.item_pendingorder_list, mList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                if (position == -1 || position >= mList.size()) {
                    return;
                }
                //进入收银界面
                Bundle bundle = new Bundle();
                bundle.putSerializable("pending", mList.get(position));
                MyApplication.openActivity(mContext, ReceiptActivity.class, bundle);
            }
        });


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

    /**
     * 请求数据
     *
     * @param pullToRefresh
     * @param curpage
     * @param pageSize
     */
    private void _reqData(final boolean pullToRefresh, final int curpage, final int pageSize) {
        // 获取订单列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("start", curpage);//列表记录开始位置
        params.put("pageSize", pageSize);//一页显示行数

        VictorHttpUtil.doGet(mContext, Define.url_cart_list_pending_order, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Pending> temp = JSON.parseArray(element.data, Pending.class);

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
     * 订单列表适配器
     */
    private class PendingOrderAdapter extends QuickAdapter<Pending> {

        private RecyclerView recyclerView;
        private PendingOrderItemAdapter adapter;

        public PendingOrderAdapter(int layoutResId, List<Pending> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final Pending item) {
            helper
                    .setText(R.id.tv_car_brand_name, item.car_plate_no)//车牌号
                    .setText(R.id.tv_car_user_mobile, item.mobile)//手机号
                    .setText(R.id.tv_order_time, item.create_time)//时间
                    .setText(R.id.tv_order_sum, "￥" + item.total_price)//总价
                    .setOnClickListener(R.id.tv_receipt, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //确认收银
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("pending", item);
                            MyApplication.openActivity(mContext, ConfirmOrderActivity.class, bundle);

                        }
                    })
                    .setOnClickListener(R.id.iv_delete_pending, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    AlertDialogFragment.newInstance(
                                            "提示",
                                            "您确定删除该挂单吗？",
                                            "取消",
                                            "确定",
                                            null,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    //删除挂单
                                                    deletePendingOrder(item.cart_id);
                                                }
                                            })
                                            .show(getSupportFragmentManager(), getClass().getSimpleName());

                                }
                            }

                    );

            List<Shoppingcar> pendingItems = item.detail;

            recyclerView = helper.getView(R.id.listview);
            LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false);
            linearLayoutManagerWrapper.setAutoMeasureEnabled(true);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);

            recyclerView.setLayoutManager(linearLayoutManagerWrapper);//设置布局管理器


            adapter = new PendingOrderItemAdapter(R.layout.item_order_item, pendingItems);
            recyclerView.setAdapter(adapter);

        }
    }

    /**
     * 删除挂单
     *
     * @param cart_id
     */
    private void deletePendingOrder(int cart_id) {

        MyParams params = new MyParams();
        params.put("cart_id", cart_id);
        VictorHttpUtil.doPost(mContext, Define.url_cart_del_pending_order, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {

                        if (element.code == 0 && element.msg.equals("删除成功")) {
                            //刷新界面
                            mPtrHelper.autoRefresh(true);
                        }
                    }
                });
    }


    private class PendingOrderItemAdapter extends QuickAdapter<Shoppingcar> {

        public PendingOrderItemAdapter(int layoutResId, List<Shoppingcar> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Shoppingcar item) {
            helper
                    .setVisible(R.id.tv_goods_category_name, false)//不显示
                    .setText(R.id.tv_product_name, item.goods.goods_name)//商品名称
                    .setText(R.id.tv_product_count, "x" + item.goods.buy_num)//数量
                    .setText(R.id.tv_product_price, item.goods.actual_sale_price + "元");//售价
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPtrHelper.autoRefresh(true);
    }
}
