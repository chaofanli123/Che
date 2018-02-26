package com.victor.che.ui.my;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.victor.che.bean.BuTieJiLu;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class BuTieJiLvctivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar)
    RelativeLayout topbar;
    @BindView(R.id.recycler_usr_car)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl_user_car)
    PtrClassicFrameLayout pcflUserCar;

    /**
     * adapter
     */
    private CouponAdapter messageListAdapter;
    private List<BuTieJiLu.PageBean.ListBean> messageArrayList;
    private PtrHelper<BuTieJiLu.PageBean.ListBean> mPtrHelper;
    private int index;/*点击的愿望下标*/
    public static int currentPos = 0;//当前位置


    @Override
    public int getContentView() {
        return R.layout.activity_bu_tie_ji_lvctivity;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("补贴记录");
        init();
    }


    private void init() {
        messageArrayList = new ArrayList<>();
        messageListAdapter = new CouponAdapter(R.layout.item_butiejilu, messageArrayList);
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
                if (!CollectionUtil.isEmpty(messageArrayList)){
                    startActivity(new Intent(mContext, BuTieJiLuXiangQingActivity.class).putExtra("id", messageArrayList.get(position).getId()));

                }
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("pageNo", curpage / pageSize + 1);
        params.put("pageSize", pageSize);
        VictorHttpUtil.doPost(mContext, Define.URL_BuTieJiLu + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        BuTieJiLu Policy = JSON.parseObject(element.body, BuTieJiLu.class);
                        //                        List<QueryUserCarHistory> queryUserCarHistories = new ArrayList<QueryUserCarHistory>();
                        List<BuTieJiLu.PageBean.ListBean> shopsCouponList = new ArrayList<>();
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

    /**
     * 订单列表适配器
     */
    private class CouponAdapter extends QuickAdapter<BuTieJiLu.PageBean.ListBean> {

        public CouponAdapter(int layoutResId, List<BuTieJiLu.PageBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final BuTieJiLu.PageBean.ListBean shopsCoupon) {
            holder.setText(R.id.tv_qiye, "企业："+shopsCoupon.getFirm().getFarmName());
            holder.setText(R.id.tv_niandu, "年度: "+shopsCoupon.getYear());
            holder.setText(R.id.tv_butiedanwei, "补贴项目: "+shopsCoupon.getSubsidyItem());
            holder.setText(R.id.tv_butiejine, "补贴金额: "+shopsCoupon.getSubsidyMoney());
            holder.setText(R.id.tv_fafangdanwei, "发放单位: "+shopsCoupon.getGrantFirm());
            holder.setText(R.id.tv_gengxingshijian, "更新时间: "+shopsCoupon.getUpdateDate());
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
}
