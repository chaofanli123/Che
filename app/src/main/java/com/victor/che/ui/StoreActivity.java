package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
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
import com.victor.che.domain.Store;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
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
 * @time 2017/5/17 0017 9:59.
 */

public class StoreActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    MyRecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;
    private List<Store> mList = new ArrayList<>();
    private PtrHelper<Store> mPtrHelper;
    private StoreAdapter mAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_pending_order_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("门店");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new StoreAdapter(R.layout.item_store_list, mList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(1)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线

        mRecyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                if (position == -1 || position >= mList.size()) {
                    return;
                }
                //点击条目跳转
                Bundle bundle = new Bundle();
                bundle.putSerializable("store",mList.get(position));
                MyApplication.openActivity(mContext,StoresdetailsActivity.class,bundle);
            }
        });
        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    private void reqData(final boolean pullToRefresh, final int curpage, final int pageSize) {


        // 获取订单列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("start", curpage);//列表记录开始位置
        params.put("pageSize", pageSize);//一页显示行数

        VictorHttpUtil.doGet(mContext, Define.url_provider_store_list_v1, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Store> temp = JSON.parseArray(element.data, Store.class);

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


    private class StoreAdapter extends QuickAdapter<Store> {

        public StoreAdapter(int layoutResId, List<Store> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Store item) {

            helper
                    .setText(R.id.tv_store_name,item.getName())
                    .setText(R.id.tv_store_address,item.getAddress());
            Glide.with(mContext).load(item.getThumb_image_url()).error(R.drawable.def_customer_avatar).dontAnimate().into((ImageView) helper.getView(R.id.iv_store_img));
        }
    }

}
