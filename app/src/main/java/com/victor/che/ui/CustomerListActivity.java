package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Customer;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.LinearLayoutManagerWrapper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 搜索界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 17:33
 */
public class CustomerListActivity extends BaseActivity {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private List<Customer> mList = new ArrayList<>();
    private CustomerListAdapter mAdapter;

    private PtrHelper<Customer> mPtrHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_customer_list;
    }
    @Override
    protected void initView() {
        super.initView();

        mAdapter = new CustomerListAdapter(R.layout.item_customer_list, mList);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);

        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.setAdapter(mAdapter);
        // 单击进入会员详情界面
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("provider_user_id", mList.get(position).provider_user_id);
                MyApplication.openActivity(mContext, CustomerDetailsActivity.class, bundle);
            }
        });

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        // 获取客户列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("start", curpage);// 列表开始位置,默认为0
        params.put("pageSize", pageSize);// 每页显示条数,不传则显示所有
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_USER_LIST, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Customer> temp = JSON.parseArray(element.body, Customer.class);
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
     * 去搜索界面
     */
    @OnClick(R.id.topbar_search)
    void gotoSearch() {
        MyApplication.openActivity(mContext, SearchActivity.class);
    }

    /**
     * 添加客户界面
     */
    @OnClick(R.id.iv_add_customer)
    void gotoAddCustomer() {
        MyApplication.openActivity(mContext, AddCustomerActivity.class);
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
        }
    }
}
