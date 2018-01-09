package com.victor.che.ui.Coupon;


import android.os.Bundle;
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
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.ShopsCoupon;
import com.victor.che.event.MessageEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 *启用优惠券列表
 */
public class StartUsingCouponFragment extends BaseFragment {

    @BindView(R.id.recycler_usr_car)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl_user_car)
    PtrFrameLayout pcflUserCar;
    /**
     * adapter
     */
    private CouponAdapter messageListAdapter;
    private List<ShopsCoupon> messageArrayList = new ArrayList<ShopsCoupon>();
    private PtrHelper<ShopsCoupon> mPtrHelper;
    private int index;/*点击的愿望下标*/

    @Override
    public int getContentView() {
        return R.layout.fragment_usercar_history_values;
    }
    @Override
    protected void initView() {
        super.initView();
        messageListAdapter = new CouponAdapter(R.layout.item_coupon_startusing, messageArrayList);  //
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
                index = position;
                if (position == -1) {
                    return;
                }
                ShopsCoupon shopsCoupon = messageArrayList.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("type","couponlist");
                bundle.putString("position",position+"");
                bundle.putSerializable("shopsCoupon",shopsCoupon);
                MyApplication.openActivity(mContext,NewAddCouponActivity.class,bundle);
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    /**
     * 获取商家优惠券
     *
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("pageSize", pageSize);
        params.put("start", curpage);
        params.put("status", 1);
        VictorHttpUtil.doGet(mContext, Define.url_coupon_all_list_v1, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<ShopsCoupon> shopsCouponList = JSON.parseArray(element.data, ShopsCoupon.class);
                        //                        List<QueryUserCarHistory> queryUserCarHistories = new ArrayList<QueryUserCarHistory>();
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
    private class CouponAdapter extends QuickAdapter<ShopsCoupon> {

        public CouponAdapter(int layoutResId, List<ShopsCoupon> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final ShopsCoupon shopsCoupon) {
            holder.setText(R.id.tv_coupon_price, shopsCoupon.getMoney());
            holder.setText(R.id.tv_coupon_message, shopsCoupon.getName());
            TextView money_off = holder.getView(R.id.tv_money_off);
            if ("0.00".equals(shopsCoupon.getFull_money())) { //不限制
                money_off.setVisibility(View.GONE);
            } else {
                money_off.setVisibility(View.VISIBLE);
                money_off.setText("满" + shopsCoupon.getFull_money() + "可用");
            }
            TextView tv_coupon_time = holder.getView(R.id.tv_coupon_time);//时间截止
            if (StringUtil.isEmpty(shopsCoupon.getGrant_start_time())||StringUtil.isEmpty(shopsCoupon.getGrant_end_time())) { //不限制时间
                tv_coupon_time.setText("不限期限");
            } else {
                tv_coupon_time.setText("限" + shopsCoupon.getGrant_start_time() + "至" + shopsCoupon.getGrant_end_time() + "发放");
            }
            /**
             * 发券记录
             */
            holder.setOnClickListener(R.id.tv_send_coupon_history, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("shopsCoupon",shopsCoupon);
                    MyApplication.openActivity(mContext,SendCouponListActivity.class,bundle);
                }
            });
            /**
             * 禁用
             */
            holder.setOnClickListener(R.id.tv_forbidden, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showdialog(shopsCoupon,shopsCoupon.getCoupon_id());
                }
            });
            /**
             * 发券 去发券activty
             */
            holder.setOnClickListener(R.id.tv_send_coupon, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopsCoupon.getIs_grant() == 1) {
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("shopsCoupon",shopsCoupon);
                        bundle.putString("couponId", String.valueOf(shopsCoupon.getCoupon_id()));
                        MyApplication.openActivity(mContext,SelectUserActivity.class,bundle);
                    }else {
                        MyApplication.showToast("该优惠券不可以发券");
                    }

                }
            });
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

    /**
     *禁用优惠券 禁用成功以后去掉该item刷新列表 重新获取数据；
     */
   private void  showdialog(final ShopsCoupon shopsCoupon, final int coupon_id){
       AlertDialogFragment.newInstance(
               "提示",
               "禁用之后不可以再发放该优惠券，是否确定？",
               "是",
               "否",
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       MyParams params = new MyParams();
                       params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
                       params.put("coupon_id", coupon_id);//优惠券id
                       params.put("status", 0);//要修改的状态值： 0-禁用 1-启用
                       VictorHttpUtil.doPost(mContext, Define.url_coupon_change_status, params, false, null,
                               new BaseHttpCallbackListener<Element>() {
                                   @Override
                                   public void callbackSuccess(String url, Element element) {
                                       messageArrayList.remove(shopsCoupon);
                                         messageListAdapter.notifyDataSetChanged();
                                       MyApplication.showToast("禁用成功");
                                   }
                               });
                   }
               },
               null)
               .show(getFragmentManager(), getClass().getSimpleName());
    }
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        switch (event.code) {
            case MessageEvent.ALL_WISH_RELOAD:///*刷新全部愿望列表*/
            //    Map<String, String> paramMap = (Map<String, String>) event.object;
                mPtrHelper.autoRefresh(true);
                break;
            case MessageEvent.ALL_WISH_REFRESH:///*刷新局部愿望*/
                if (event.position!=-1){
                    index=event.position;
                    //  messageListAdapter.notifyDataSetChanged();
                }
                if (index >= 0) {
                    if (event.object != null) {
                        ShopsCoupon shopsCoupon= (ShopsCoupon) event.object;
                        messageArrayList.set(index, shopsCoupon);
                    } else {
                        messageArrayList.remove(index);
                    }
                    index = -1;
                    messageListAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
