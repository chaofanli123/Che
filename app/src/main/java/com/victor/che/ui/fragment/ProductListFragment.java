package com.victor.che.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.Product;
import com.victor.che.event.ProductEvent;
import com.victor.che.ui.ProductDetailsActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 产品列表界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/8 0008 16:14
 */
public class ProductListFragment extends BaseFragment {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private List<Product> mList = new ArrayList<>();
    private ProductListAdapter mAdapter;
    private PtrHelper<Product> mPtrHelper;
    private View common_no_data;

    @Override
    public int getContentView() {
        return R.layout.common_pull_to_refresh_recyclerview;
    }

    public static ProductListFragment newInstance(String goods_category_id) {
        Bundle args = new Bundle();
        args.putString("goods_category_id", goods_category_id);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String goods_category_id;
    private int status = 1;

    @Override
    protected void initView() {
        super.initView();

        findViewById(R.id.topbar).setVisibility(View.GONE);

        goods_category_id = getArguments().getString("goods_category_id");
        if (StringUtil.isEmpty(goods_category_id)) {
            MyApplication.showToast("服务类别不存在");
            return;
        }

        common_no_data = View.inflate(mContext, R.layout.common_no_data, null);


        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mAdapter = new ProductListAdapter(R.layout.item_product_mgr, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrFrame.disableWhenHorizontalMove(true);//解决与横向移动的手势冲突
        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(false);
    }

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        // 获取商品列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("goods_category_id", goods_category_id);//商品分类id，(获取某类商品中必传)
        params.put("status", status);//0-下架 1-在售 2-全部（为了支持旧版，新版必须传）
        params.put("start", curpage);// 列表开始位置,默认为0
        params.put("pageSize", pageSize);// 每页显示条数,不传则显示所有
        VictorHttpUtil.doGet(mContext, Define.URL_GOODS_LIST, params, false, null,
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Product> temp = JSON.parseArray(element.body, Product.class);
                        if (pullToRefresh) {// 下拉刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
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
     * 添加完毕后，刷新列表
     *
     * @param event
     */
    @Subscribe
    public void onRefreshProductList(String event) {
        if (ConstantValue.Event.REFRESH_PRODUCT_LIST.equalsIgnoreCase(event)) {
            mPtrHelper.autoRefresh(true);
        }
    }

    /**
     * 添加完毕后，刷新列表
     *
     * @param event
     */
    @Subscribe
    public void onProductStatusChanged(ProductEvent event) {
        if (event != null) {
            this.status = event.status;
            initView();
            //mPtrHelper.autoRefresh(true);
        }
    }

    /**
     * 产品列表适配器
     */
    private class ProductListAdapter extends QuickAdapter<Product> {

        public ProductListAdapter(int layoutResId, List<Product> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, Product item) {

            helper.setText(R.id.tv_name, item.name)//商品名称
                    .setText(R.id.tv_original_price, item.getPrice())//商品原价
                    .setText(R.id.tv_sale_price, item.getSalePrice())//商品销售价
                    .setTextColor(R.id.tv_sale_price, getResources().getColor(status == 1 ? R.color.red : R.color.dark_gray_text));//售价颜色

            TextView tv_operate = helper.getView(R.id.tv_operate);
            ViewUtil.setDrawableTop(mContext, tv_operate, status == 1 ? R.drawable.ic_off_sale : R.drawable.ic_on_sale);
            tv_operate.setText(status == 1 ? "下架" : "上架");
            tv_operate.setTextColor(getResources().getColor(status == 1 ? R.color.theme_color : R.color.on_sale));

            /**
             * 点击下架
             */
            helper.getView(R.id.area_operate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = status == 1 ? "下架后本项目将不在用户端显示!是否确定下架？" : "上架后本项目将在用户端显示!是否确定上架？";
                    AlertDialogFragment.newInstance(
                            "友情提示",
                            msg,
                            "确定",
                            "取消",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    _doOnOffProduct(helper.getLayoutPosition(), status);
                                }
                            },
                            null)
                            .show(getFragmentManager(), getClass().getSimpleName());
                }
            });
            if (MyApplication.CURRENT_USER.is_initial_provider==1) {//总店身份 可以进入详情编辑
                // 点击进入详情界面
                helper.getView(R.id.area_details).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("mProduct", mList.get(helper.getLayoutPosition()));
                        MyApplication.openActivity(mContext, ProductDetailsActivity.class, bundle);
                    }
                });
            }

        }
    }

    /**
     * 下架某个产品
     *
     * @param position
     */
    private void _doOnOffProduct(final int position, int status) {
        // 下架服务商服务商品
        MyParams params = new MyParams();
        params.put("goods_id", mList.get(position).goods_id);//商品id
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登录者id
        VictorHttpUtil.doPost(mContext, status == 1 ? Define.URL_GOODS_OFF : Define.URL_GOODS_ON, params, true, "处理中...", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                // 刷新界面
                MyApplication.showToast(element.msg);
                mList.remove(position);
                mAdapter.notifyItemRemoved(position);
                if (CollectionUtil.isEmpty(mList)) {
                    // 无数据
                    mPtrHelper.setEmptyView(common_no_data);
                }
            }
        });
    }
}
