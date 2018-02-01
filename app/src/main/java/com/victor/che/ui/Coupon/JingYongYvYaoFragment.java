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
import com.victor.che.bean.fishDrug;
import com.victor.che.domain.ShopsCoupon;
import com.victor.che.event.SearchEvent;
import com.victor.che.ui.my.JingYongYvYaoActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 *通知下达列表
 */
public class JingYongYvYaoFragment extends BaseFragment {
    @BindView(R.id.recycler_usr_car)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl_user_car)
    PtrFrameLayout pcflUserCar;
    /**
     * adapter
     */
    private CouponAdapter messageListAdapter;
    private List<fishDrug.PageBean.ListBean> messageArrayList;
    private PtrHelper<fishDrug.PageBean.ListBean> mPtrHelper;
    private int index;/*点击的愿望下标*/
    public static String keywords = "";
    private String type = "";// 搜索类型
    private String status = "";// 搜索状态
    public static int currentPos = 0;//当前位置

    @Override
    public int getContentView() {
        return R.layout.fragment_usercar_history_values;
    }
    @Override
    protected void initView() {
        messageArrayList = new ArrayList<>();
        messageListAdapter = new CouponAdapter(R.layout.item_coupon_jingyongyvyao, messageArrayList);
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
//                ShopsCoupon shopsCoupon = messageArrayList.get(position);
//                Bundle bundle=new Bundle();
//                bundle.putString("type","couponlist");
//                bundle.putString("position",position+"");
//                bundle.putSerializable("shopsCoupon",shopsCoupon);
                startActivity(new Intent(mContext, JingYongYvYaoActivity.class).putExtra("id",messageArrayList.get(position).getId()));
            }
        });
        mPtrHelper.autoRefresh(false);
    }


    @Subscribe
    public void onSearch(SearchEvent event) {
        if (event == null) {
            return;
        }
        this.keywords = event.keywords;
        this.type=event.type;
        this.status=event.status;
        if (currentPos == event.currentPos) {//只处理当前页事件
            mPtrHelper.autoRefresh(true);
        }
    }


    /**
     * 获取商家优惠券
     *
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
       params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);//
        params.put("pageNo",curpage/pageSize+1);
        params.put("pageSize", pageSize);
        if (!StringUtil.isEmpty(keywords)) {
            params.put("title", keywords);
        }
        if (!StringUtil.isEmpty(type)) {
            params.put("type", type);
        }
        if (!StringUtil.isEmpty(status)) {
            params.put("status", status);
        }
        VictorHttpUtil.doPost(mContext, Define.URL_JINGYONGYVYAO+";JSESSIONID="+MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        fishDrug fishDrug = JSON.parseObject(element.body, fishDrug.class);
                        //                        List<QueryUserCarHistory> queryUserCarHistories = new ArrayList<QueryUserCarHistory>();
                       List<fishDrug.PageBean.ListBean> shopsCouponList=new ArrayList<>();
                        shopsCouponList=fishDrug.getPage().getList();

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
    private class CouponAdapter extends QuickAdapter<fishDrug.PageBean.ListBean> {

        public CouponAdapter(int layoutResId, List<fishDrug.PageBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final fishDrug.PageBean.ListBean shopsCoupon) {

            holder.setText(R.id.tv_yaowumingcheng, "药物名称: "+shopsCoupon.getMedicineName());
            holder.setText(R.id.tv_yingyuming, "英文名: "+shopsCoupon.getEnglishName());
            holder.setText(R.id.tv_bieming, "别名: "+shopsCoupon.getAnotherName());
            holder.setText(R.id.tv_yinyongyiju, "引用依据: "+shopsCoupon.getReferenceBasis());
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
//                    //  messageListAdapter.fishDrugDataSetChanged();
//                }
//                if (index >= 0) {
//                    if (event.object != null) {
//                        fishDrug shopsCoupon= (fishDrug) event.object;
//                        messageArrayList.set(index, shopsCoupon);
//                    } else {
//                        messageArrayList.remove(index);
//                    }
//                    index = -1;
//                    messageListAdapter.fishDrugDataSetChanged();
//                }
//                break;
//        }
//    }
}
