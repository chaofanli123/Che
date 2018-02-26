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
import com.victor.che.bean.WeiGuiJiLu;
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

public class WeiGuiJiLuctivity extends BaseActivity {

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
    private List<WeiGuiJiLu.PageBean.ListBean> messageArrayList;
    private PtrHelper<WeiGuiJiLu.PageBean.ListBean> mPtrHelper;
    private int index;/*点击的愿望下标*/
    public static int currentPos = 0;//当前位置
    @Override
    public int getContentView() {
        return R.layout.activity_wei_gui_ji_luctivity;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("违规记录");
        init();
    }


    private void init() {
        messageArrayList = new ArrayList<>();
        messageListAdapter = new CouponAdapter(R.layout.item_weiguijilu, messageArrayList);
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
                    startActivity(new Intent(mContext, WeiGuiJiLuChaKanActivity.class).putExtra("id", messageArrayList.get(position).getId()));
                }
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("pageNo", curpage / pageSize + 1);
        params.put("pageSize", pageSize);
        VictorHttpUtil.doPost(mContext, Define.URL_WeiGuiJiLu + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        WeiGuiJiLu Policy = JSON.parseObject(element.body, WeiGuiJiLu.class);
                        //                        List<QueryUserCarHistory> queryUserCarHistories = new ArrayList<QueryUserCarHistory>();
                        List<WeiGuiJiLu.PageBean.ListBean> shopsCouponList = new ArrayList<>();
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
    private class CouponAdapter extends QuickAdapter<WeiGuiJiLu.PageBean.ListBean> {

        public CouponAdapter(int layoutResId, List<WeiGuiJiLu.PageBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final WeiGuiJiLu.PageBean.ListBean shopsCoupon) {
            holder.setText(R.id.tv_yangzhichangmingcheng, "养殖场名称："+shopsCoupon.getAquFarm().getFarmName());
            holder.setText(R.id.tv_cunzaiwenti, "存在问题: "+shopsCoupon.getOthers());
            holder.setText(R.id.tv_zhenggaijianyi, "整改建议: "+shopsCoupon.getDispose());
            holder.setText(R.id.tv_jiancharen, "检查人: "+shopsCoupon.getUser().getName());
            holder.setText(R.id.tv_beijiancharen, "被检查人: "+shopsCoupon.getCheckedName());
            holder.setText(R.id.tv_jianchashijian, "检查时间: "+shopsCoupon.getUpdateDate());
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
