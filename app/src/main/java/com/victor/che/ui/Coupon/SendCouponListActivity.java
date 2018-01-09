package com.victor.che.ui.Coupon;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.SimpleTextWatcher;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Order;
import com.victor.che.domain.ShopsCoupon;
import com.victor.che.domain.UserCoupon;
import com.victor.che.domain.Vipcard;
import com.victor.che.ui.VipcardMgrActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.BottomDialogFragment;
import com.victor.che.widget.MyRecyclerView;
import com.victor.che.widget.PtrlMeiTuanFrameLayout;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.victor.che.R.id.tv_prov_vipcard_type;

/**
 * 发券记录
 */
public class SendCouponListActivity extends BaseActivity {

    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_coupon_price)
    TextView tvCouponPrice;
    @BindView(R.id.tv_money_off)
    TextView tvMoneyOff;
    @BindView(R.id.rl_parse)
    RelativeLayout rlParse;
    @BindView(R.id.tv_coupon_message)
    TextView tvCouponMessage;
    @BindView(R.id.tv_coupon_time)
    TextView tvCouponTime;
    @BindView(R.id.topbar_search)
    EditText topbarSearch;
    @BindView(R.id.mRecyclerView)
    MyRecyclerView recycler;
    @BindView(R.id.tv_titlename)
    TextView tvTitlename;
    @BindView(R.id.activity_send_coupon_list)
    LinearLayout activitySendCouponList;
    @BindView(R.id.mPtrFrame)
    PtrlMeiTuanFrameLayout mPtrFrame;

    /**
     * adapter
     */
    private CouponUserAdapter messageListAdapter;
    private List<UserCoupon> messageArrayList = new ArrayList<UserCoupon>();
    private PtrHelper<UserCoupon> mPtrHelper;
    private ShopsCoupon shopsCoupon;
    private int couponid=0;
    private String keywords = "";// 搜索关键字

    private CouponListAdapter provVipcardAdapter;// 优惠券适配器
    private int mSelectedPos = 0;//默认选中的优惠券位置（会增加“全部”这个分类）

    private List<ShopsCoupon> list=new ArrayList<>();


    @Override
    public int getContentView() {
        return R.layout.activity_send_coupon_list;
    }

    @Override
    protected void initView() {
        super.initView();
        getCoupon();
        shopsCoupon= (ShopsCoupon) getIntent().getSerializableExtra("shopsCoupon");
        showView(shopsCoupon);
        messageListAdapter = new CouponUserAdapter(R.layout.item_send_coupon_list, messageArrayList);  //
        mPtrHelper = new PtrHelper<>(mPtrFrame, messageListAdapter, messageArrayList);
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
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);

        // 不停的请求搜索接口
        topbarSearch.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                keywords = s.toString().trim();
                mPtrHelper.autoRefresh(true);
            }
        });
        topbarSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keywords = topbarSearch.getText().toString().trim();
                    mPtrHelper.autoRefresh(true);
                }
                return false;
            }
        });


    }

    /**
     * 获取优惠券列表
     */
    private void getCoupon(){
    // 获取所有类别
    MyParams params = new MyParams();
    params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
    VictorHttpUtil.doGet(mContext, Define.url_coupon_all_list_v1, params, false, "加载中...",
            new BaseHttpCallbackListener<Element>() {
                @Override
                public void callbackSuccess(String url, Element element) {
                    list = JSON.parseArray(element.data, ShopsCoupon.class);
                    if (CollectionUtil.isEmpty(list)) {
                        MyApplication.showToast("优惠券列表为空");
                        return;
                    }
                  //
                    provVipcardAdapter = new CouponListAdapter(mContext, R.layout.item_bottom_dialog, list);
                }
            });
}
    private void showView(ShopsCoupon shopsCoupon){
        if (!StringUtil.isEmpty(shopsCoupon.getName())) {
            tvTitlename.setText(shopsCoupon.getName());
        }
        if (!StringUtil.isEmpty(shopsCoupon.getMoney())) {
            tvCouponPrice.setText(shopsCoupon.getMoney());
        }
        if (!StringUtil.isEmpty(shopsCoupon.getName())) {
            tvCouponMessage.setText(shopsCoupon.getName());
        }
        if ("0.00".equals(shopsCoupon.getFull_money())) { //不限制
            tvMoneyOff.setVisibility(View.GONE);
        } else {
            tvMoneyOff.setVisibility(View.VISIBLE);
            tvMoneyOff.setText("满" + shopsCoupon.getFull_money() + "可用");
        }

        if (StringUtil.isEmpty(shopsCoupon.getGrant_start_time())||StringUtil.isEmpty(shopsCoupon.getGrant_end_time())) { //不限制时间
            tvCouponTime.setText("不限期限");
        } else {
            tvCouponTime.setText("限" + shopsCoupon.getGrant_start_time() + "至" + shopsCoupon.getGrant_end_time() + "使用");
        }
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
        params.put("coupon_id", shopsCoupon.getCoupon_id());//否	优惠券id，全部为0
        params.put("user_input", keywords);// string	否	用户输入的手机号或车牌号
        VictorHttpUtil.doGet(mContext, Define.url_coupon_grant_record_grant_list_v1, params, false, null,
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<UserCoupon> temp = JSON.parseArray(element.data, UserCoupon.class);
                        if (pullToRefresh) {// 下拉刷新
                            messageArrayList.clear();//清空数据
                            messageListAdapter.setNewData(messageArrayList);
                            messageListAdapter.notifyDataSetChanged();
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                // 有数据
                                messageArrayList.addAll(temp);
                                messageListAdapter.setNewData(messageArrayList);
                                messageListAdapter.notifyDataSetChanged();
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
     * 禁用优惠券
     */
    private void showdialog(final UserCoupon shopsCoupon) {
        AlertDialogFragment.newInstance(
                "提示",
                "确定是否作废该优惠券？",
                "是",
                "否",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyParams params = new MyParams();
                        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
                        params.put("coupon_grant_record_id", shopsCoupon.getCoupon_grant_record_id());//用户优惠券id
                        VictorHttpUtil.doPost(mContext, Define.url_coupon_grant_record_del_v1, params, false, null,
                                new BaseHttpCallbackListener<Element>() {
                                    @Override
                                    public void callbackSuccess(String url, Element element) {
                                        messageArrayList.remove(shopsCoupon);
                                        messageListAdapter.notifyDataSetChanged();
                                        MyApplication.showToast("作废成功");
                                    }
                                });
                    }
                },
                null)
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }


    @OnClick(R.id.tv_titlename)
    public void onViewClicked() {
        if (CollectionUtil.isEmpty(list)) {
            MyApplication.showToast("优惠券列表为空");
            return;
        }
        BottomDialogFragment.newInstance(provVipcardAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mSelectedPos) {
                    return;
                }
                mSelectedPos = position;
                provVipcardAdapter.notifyDataSetChanged();
                    shopsCoupon = list.get(mSelectedPos);
                    showView(shopsCoupon);
                    // 重新获取优惠券发券记录会员卡列表
                    mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
                        @Override
                        public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                            _reqData(pullToRefresh, curpage, pageSize);
                        }
                    });
                    mPtrHelper.autoRefresh(true);

            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }
    /**
     * 订单列表适配器
     */
    private class CouponUserAdapter extends QuickAdapter<UserCoupon> {

        public CouponUserAdapter(int layoutResId, List<UserCoupon> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final UserCoupon shopsCoupon) {
            if (!StringUtil.isEmpty(shopsCoupon.getCoupon_record_no())) {
                holder.setText(R.id.tv_coupon_number, shopsCoupon.getCoupon_record_no());
            }
            if (!StringUtil.isEmpty(shopsCoupon.getMobile())) {
                holder.setText(R.id.tv_phone_number, shopsCoupon.getMobile());
            }
            if (!StringUtil.isEmpty(shopsCoupon.getCar_plate_no())) {
                holder.setText(R.id.tv_chepai, shopsCoupon.getCar_plate_no());
            }
            TextView tv_coupon_time = holder.getView(R.id.tv_time);//时间截止
            if (StringUtil.isEmpty(shopsCoupon.getAvailable_end_time())) { //不限制时间
                tv_coupon_time.setText("不限期限");
            } else {
                tv_coupon_time.setText(shopsCoupon.getAvailable_start_time() + "至" + shopsCoupon.getAvailable_end_time());
            }
            TextView style = holder.getView(R.id.tv_send_coupon_style);
            if (shopsCoupon.getStatus()==0) { //待使用
                style.setText("作废");
                style.setBackgroundResource(R.drawable.sl_btn_recharge);
                style.setTextColor(getResources().getColor(R.color.textcolor_theme_color_pressed));
                style.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showdialog(shopsCoupon);
                    }
                });
            }else {//已使用
                style.setText("已使用");
                style.setBackgroundResource(R.drawable.sl_btn_goto_index);
                style.setTextColor(getResources().getColor(R.color.dark_gray_text));
            }


        }
    }
    class CouponListAdapter extends VictorBaseListAdapter<ShopsCoupon> {
        public CouponListAdapter(Context context, int layoutResId, List<ShopsCoupon> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, ShopsCoupon entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.getName());
            textView.setTextColor(getResources().getColor(mSelectedPos == position ? R.color.theme_color : R.color.black_text));
        }
    }

}
