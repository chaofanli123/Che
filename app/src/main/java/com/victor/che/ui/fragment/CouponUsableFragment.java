package com.victor.che.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.victor.che.domain.Coupon;
import com.victor.che.event.CouponEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.MyRecyclerView;
import com.victor.che.widget.TipDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * A simple {@link Fragment} subclass.
 * 可用优惠券
 */
public class CouponUsableFragment extends BaseFragment {

    @BindView(R.id.recycler_usr_car)
    MyRecyclerView recycler;
    @BindView(R.id.pcfl_user_car)
    PtrFrameLayout pcflUserCar;
    Unbinder unbinder;
    /**
     * 历史估值adapter
     */
    private CouponUsableListAdapter messageListAdapter;
    private List<Coupon> messageArrayList = new ArrayList<Coupon>();
    private PtrHelper<Coupon> mPtrHelper;

    private int select;
    private List<String> selectID = new ArrayList<>();
    private List<String> baoxian_id = new ArrayList<>();
    private List<Coupon> list;
    private Double total_price=0.00;
    /**
     * 从上一页传过来的数据，需要上传后台
     */
    private String mobile,car_plate_no,goods_id,price;
    private String useful_count; //可用优惠券总数
    private String unuseful_count; //不可用优惠券总数
    @Override
    public int getContentView() {
        return R.layout.fragment_coupon_usable;
    }
    @Override
    protected void initView() {
        super.initView();
        mobile=getArguments().getString("mobile");
        car_plate_no=getArguments().getString("car_plate_no");
        goods_id=getArguments().getString("goods_id");
        price=getArguments().getString("price");

        messageListAdapter = new CouponUsableListAdapter(R.layout.item_coupon_usable, messageArrayList);
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
                select = position;
                Coupon coupon = messageArrayList.get(position);
                coupon.checked = !coupon.checked;
                if (coupon.checked) {
                    baoxian_id.add(coupon.getCoupon_grant_record_id() + "");
                    total_price= MathUtil.add(total_price,Double.parseDouble(coupon.getMoney()));
                } else {
                    baoxian_id.remove(coupon.getCoupon_grant_record_id() + "");
                    total_price= MathUtil.sub(total_price,Double.parseDouble(coupon.getMoney()));
                }
                list = new ArrayList<Coupon>();
                list.add(coupon);
                messageListAdapter.notifyDataSetChanged();
            }
        });
        mPtrHelper.autoRefresh(false);
    }

    /**
     * 获取优惠券
     *
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("pageSize", pageSize);
        params.put("start", curpage);
        params.put("mobile", mobile);
        params.put("car_plate_no", car_plate_no);
        params.put("goods_id", goods_id);
        params.put("price", price);
        params.put("is_useful", 1);
        VictorHttpUtil.doGet(mContext, Define.url_coupon_grant_record_list_v1, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = parseObject(element.data);
                        if (jsonobj != null) {
                            List<Coupon> coupons = JSON.parseArray(jsonobj.getString("coupons"), Coupon.class);// 会员卡列表
                            if (pullToRefresh) {////刷新
                                messageArrayList.clear();//清空数据
                                if (CollectionUtil.isEmpty(coupons)) {
                                    // 无数据
                                    View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                    mPtrHelper.setEmptyView(common_no_data);
                                } else {
                                    // 有数据
                                    messageArrayList.addAll(coupons);
                                    messageListAdapter.setNewData(messageArrayList);
                                    messageListAdapter.notifyDataSetChanged();

                                    if (CollectionUtil.getSize(coupons) < pageSize) {
                                        // 上拉加载无更多数据
                                        mPtrHelper.loadMoreEnd();
                                    }
                                }
                                mPtrHelper.refreshComplete();
                            } else {//加载更多
                                mPtrHelper.processLoadMore(coupons);
                            }
                        }

                    }
                });

    }

    /**
     * 确定按钮 发送面值给收银页面并计算叠加面值的值
     */
    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        selectID.addAll(baoxian_id);
        CouponEvent event=new CouponEvent(total_price,selectID);
        EventBus.getDefault().post(event);
        getActivity().finish();
    }

    /**
     * 优惠券列表适配器
     */
    private class CouponUsableListAdapter extends QuickAdapter<Coupon> {

        public CouponUsableListAdapter(int layoutResId, List<Coupon> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final Coupon coupon) {
            holder.setText(R.id.tv_coupon_price, coupon.getMoney());
            holder.setText(R.id.tv_coupon_message, coupon.getName());
            TextView money_off = holder.getView(R.id.tv_money_off);
            if ("0.00".equals(coupon.getFull_money())) { //不限制
                money_off.setVisibility(View.GONE);
            } else {
                money_off.setVisibility(View.VISIBLE);
                money_off.setText("满" + coupon.getFull_money() + "可用");
            }

            TextView tv_coupon_time = holder.getView(R.id.tv_coupon_time);//时间截止
            if (StringUtil.isEmpty(coupon.getAvailable_end_time())) { //不限制时间
                tv_coupon_time.setText("不限期限");
            } else {
                tv_coupon_time.setText("限" + coupon.getAvailable_start_time() + "至" + coupon.getAvailable_end_time() + "使用");
            }
            RelativeLayout coupon_xinagxi = holder.getView(R.id.rl_coupon_xiangxi);
            coupon_xinagxi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show(coupon.getDescription());
                }
            });
            final ImageView select = holder.getView(R.id.img_select);
            if (coupon.checked) {//选择状态
                select.setBackgroundResource(R.drawable.ic_select_compan_click);
            } else {
                select.setBackgroundResource(R.drawable.ic_select_company_normal);
            }
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


    private void show(String msg) {
        TipDialogFragment.newInstance(
                "详细信息",
                msg
        )
                .show(getFragmentManager(), getClass().getSimpleName());
    }

}
