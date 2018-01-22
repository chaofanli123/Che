package com.victor.che.ui.Coupon;


import android.content.Intent;
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
import com.victor.che.bean.Policy;
import com.victor.che.domain.ShopsCoupon;
import com.victor.che.ui.my.ZhengCheFaGuiActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 *通知下达列表
 */
public class ZhenCeFaGuiFragment extends BaseFragment {
    @BindView(R.id.recycler_usr_car)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl_user_car)
    PtrFrameLayout pcflUserCar;
    /**
     * adapter
     */
    private CouponAdapter messageListAdapter;
    private List<Policy.PageBean.ListBean> messageArrayList;
    private PtrHelper<Policy.PageBean.ListBean> mPtrHelper;
    private int index;/*点击的愿望下标*/

    @Override
    public int getContentView() {
        return R.layout.fragment_usercar_history_values;
    }
    @Override
    protected void initView() {
        messageArrayList = new ArrayList<>();
        messageListAdapter = new CouponAdapter(R.layout.item_coupon_zhengchefagui, messageArrayList);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器//
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
//                ShopsCoupon shopsCoupon = messageArrayList.get(position);
//                Bundle bundle=new Bundle();
//                bundle.putString("type","couponlist");
//                bundle.putString("position",position+"");
//                bundle.putSerializable("shopsCoupon",shopsCoupon);
                startActivity(new Intent(mContext, ZhengCheFaGuiActivity.class).putExtra("id",messageArrayList.get(position).getId()));
            }
        });
        mPtrHelper.autoRefresh(false);
    }

    /**
     * 获取商家优惠券
     *
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
       params.put("JSESSIONID", MyApplication.CURRENT_USER.JSESSIONID);//
        params.put("pageNo",curpage/pageSize+1);
        params.put("pageSize", pageSize);
//        params.put("title", "");
//        params.put("type", "");
//        params.put("status", "");
        VictorHttpUtil.doPost(mContext, Define.URL_ZHENGCHEFAGUI+";JSESSIONID="+MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        Policy Policy = JSON.parseObject(element.body, Policy.class);
                        //                        List<QueryUserCarHistory> queryUserCarHistories = new ArrayList<QueryUserCarHistory>();
                       List<Policy.PageBean.ListBean> shopsCouponList=new ArrayList<>();
                        shopsCouponList=Policy.getPage().getList();

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
    private class CouponAdapter extends QuickAdapter<Policy.PageBean.ListBean> {

        public CouponAdapter(int layoutResId, List<Policy.PageBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final Policy.PageBean.ListBean shopsCoupon) {

            holder.setText(R.id.tv_time, shopsCoupon.getCreateDate());
            holder.setText(R.id.tv_title, "标题: "+shopsCoupon.getTitle());
            if (shopsCoupon.getType().equals("1")){
                holder.setText(R.id.tv_leixing,"类型: "+"部门文件" );
            }else if (shopsCoupon.getType().equals("2")){
                holder.setText(R.id.tv_leixing,"类型: "+"法规规章" );
            }else if (shopsCoupon.getType().equals("3")){
                holder.setText(R.id.tv_leixing,"类型: "+"规范性文件" );
            }else if (shopsCoupon.getType().equals("4")){
                holder.setText(R.id.tv_leixing,"类型: "+"政策解读" );
            }
            holder.setText(R.id.tv_beizhuxingxi, "备注信息: "+shopsCoupon.getRemarks());
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
                   //    params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
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
//    @Subscribe
//    public void onMessageEvent(MessageEvent event) {
//        switch (event.code) {
//            case MessageEvent.ALL_WISH_RELOAD:///*刷新全部愿望列表*/
//            //    Map<String, String> paramMap = (Map<String, String>) event.object;
//                mPtrHelper.autoRefresh(true);
//                break;
//            case MessageEvent.ALL_WISH_REFRESH:///*刷新局部愿望*/
//                if (event.position!=-1){
//                    index=event.position;
//                    //  messageListAdapter.PolicyDataSetChanged();
//                }
//                if (index >= 0) {
//                    if (event.object != null) {
//                        Policy shopsCoupon= (Policy) event.object;
//                        messageArrayList.set(index, shopsCoupon);
//                    } else {
//                        messageArrayList.remove(index);
//                    }
//                    index = -1;
//                    messageListAdapter.PolicyDataSetChanged();
//                }
//                break;
//        }
//    }
}