package com.victor.che.ui.Coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.SimpleTextWatcher;
import com.victor.che.domain.Customer;
import com.victor.che.domain.ShopsCoupon;
import com.victor.che.event.MessageEvent;
import com.victor.che.ui.CustomerDetailsActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.LinearLayoutManagerWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 选择用户
 */
public class SelectUserActivity extends BaseActivity {
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.topbar_search)
    EditText topbarSearch;

    private List<Customer> mList = new ArrayList<>();
    private CustomerListAdapter mAdapter;

    private PtrHelper<Customer> mPtrHelper;
    private String couponId;
    private ShopsCoupon shopsCoupon;
    private String keywords = "";// 搜索关键字

    private List<String> user_id=new ArrayList<>();

    private List<String> selectuserID;
    private int select;
    private List<Customer> list;
    private String provider_user_id="";//用户id

    @Override
    public int getContentView() {
        return R.layout.activity_select_user;
    }
    @Override
    protected void initView() {
        super.initView();
        setTitle("选择用户");
        shopsCoupon= (ShopsCoupon) getIntent().getSerializableExtra("shopsCoupon");
        couponId = getIntent().getStringExtra("couponId");
        mAdapter = new CustomerListAdapter(R.layout.item_customer_select, mList);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);

        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.setAdapter(mAdapter);
        // 单击进入会员详情界面
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                select = position;
                Customer customer = mList.get(position);
                customer.checked = !customer.checked;
                if (customer.checked){
                    user_id.add(customer.provider_user_id+"");

                }else {
                    user_id.remove(customer.provider_user_id+"");
                }
                list = new ArrayList<Customer>();
                list.add(customer);
                adapter.notifyDataSetChanged();
            }
        });

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
    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        // 获取客户列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("start", curpage);// 列表开始位置,默认为0
        params.put("pageSize", pageSize);// 每页显示条数,不传则显示所有
        params.put("user_input",keywords);//否	用户手机号或车牌号(支持模糊输入)
        params.put("coupon_id", couponId);// 是	优惠券发放时搜索用户必传

        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_USER_LIST, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Customer> temp = JSON.parseArray(element.data, Customer.class);
                        if (pullToRefresh) {// 下拉刷新
                            mList.clear();//清空数据
                            mAdapter.setNewData(mList);
                            mAdapter.notifyDataSetChanged();
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
     * 确定发优惠券
     */
    @OnClick(R.id.btn_newadd_conpon)
    void confumsendCoupon() {
        selectuserID=new ArrayList<>();
        selectuserID.addAll(user_id);
        provider_user_id="";
        for (String s : selectuserID) {
            provider_user_id += s + ",";
        }
        provider_user_id=provider_user_id.substring(0,provider_user_id.length()-1);
        if (shopsCoupon.getNum() == 0) {//不限制
            MyParams params = new MyParams();
            params.put("coupon_id",couponId);//优惠券id
            params.put("provider_user_id", provider_user_id);
            params.put("channel_id", 2);
            VictorHttpUtil.doPost(mContext, Define.url_coupon_grant_record_donate_v1, params, true, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {
                            MyApplication.showToast(element.msg);
                            finish();
                        }
                    });  
        }else { //限制人数
            if (shopsCoupon.available_num!=0) {
                if (shopsCoupon.available_num>=selectuserID.size()) {
                    MyParams params = new MyParams();
                    params.put("coupon_id",couponId);//优惠券id
                    params.put("provider_user_id", provider_user_id);
                    params.put("channel_id", 2);
                    VictorHttpUtil.doPost(mContext, Define.url_coupon_grant_record_donate_v1, params, true, "加载中...",
                            new BaseHttpCallbackListener<Element>() {
                                @Override
                                public void callbackSuccess(String url, Element element) {
                                    MyApplication.showToast(element.msg);
                                    MessageEvent event = new MessageEvent(MessageEvent.ALL_WISH_RELOAD, null);
                                    EventBus.getDefault().post(event);
                                    finish();
                                }
                            });
                }else {
                  MyApplication.showToast("该优惠券最多发放给"+shopsCoupon.available_num+"个用户");
                }

            }else {
                MyApplication.showToast("该优惠券不可发放");
            }

        }
       
    }

    /**
     * 修改完毕后，刷新列表
     *
     * @param event
     */
    @Subscribe
    public void onRefreshCustomerList(String event) {
        if (ConstantValue.Event.REFRESH_CUSTOMER_LIST.equalsIgnoreCase(event)) {
            mPtrHelper.autoRefresh(true);
        }
    }

    /**
     * 用户列表适配器
     */
    private class CustomerListAdapter extends QuickAdapter<Customer> {

        public CustomerListAdapter(int layoutResId, List<Customer> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Customer item) {
            ImageLoaderUtil.display(mContext, (ImageView) helper.getView(R.id.iv_avatar), item.avatar_thumb, R.drawable.def_customer_avatar, R.drawable.def_customer_avatar);// 头像
            helper.setText(R.id.tv_car_brand_series, item.getName())//姓名
                    .setText(R.id.tv_pln, item.car_plate_no)//车牌号
                    .setText(R.id.tv_customer_mobile, item.mobile);// 手机号

            TextView tv_pln = helper.getView(R.id.tv_pln);
            tv_pln.setVisibility(StringUtil.isEmpty(item.car_plate_no) ? View.INVISIBLE : View.VISIBLE);

            ImageView img_select = helper.getView(R.id.img_select);
            if (item.checked) {
                img_select.setBackgroundResource(R.drawable.ic_select_compan_click);
            } else {
                img_select.setBackgroundResource(R.drawable.ic_select_company_normal);
            }
        }
    }
}
