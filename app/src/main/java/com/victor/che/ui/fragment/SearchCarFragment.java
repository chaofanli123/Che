package com.victor.che.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.Car;
import com.victor.che.event.SearchEvent;
import com.victor.che.ui.CustomerDetailsActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * 搜索用户界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/1 0001 9:35
 */
public class SearchCarFragment extends BaseFragment {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    public static int currentPos = 1;//当前位置
    public static String keywords = "";

    /**********
     * 搜索用户
     ************/
    private List<Car> mList = new ArrayList<>();
    private CarListAdapter mAdapter;
    private PtrHelper<Car> mPtrHelper;

    @Override
    public void initView() {

        findViewById(R.id.topbar).setVisibility(View.GONE);

        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mAdapter = new CarListAdapter(R.layout.item_search_car_customer, mList);
        mRecyclerView.setAdapter(mAdapter);

        int dp10 = ViewUtil.dip2px(mContext, 10);
        mRecyclerView.setPadding(0, dp10, 0, 0);
        mRecyclerView.setBackgroundColor(Color.parseColor("#efeff4"));

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        if (!StringUtil.isEmpty(keywords)) {
            mPtrHelper.autoRefresh(false);
        }

        // 单击进入会员详情界面
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("provider_user_id", mList.get(position).provider_user_id);
                MyApplication.openActivity(mContext, CustomerDetailsActivity.class, bundle);
            }
        });
    }


    @Override
    public int getContentView() {
        return R.layout.common_pull_to_refresh_recyclerview;
    }

    @Subscribe
    public void onSearch(SearchEvent event) {
        if (event == null) {
            return;
        }
        this.keywords = event.keywords;
        if (currentPos == event.currentPos
                && !StringUtil.isEmpty(keywords)) {//只处理当前页事件
                     mPtrHelper.autoRefresh(false);
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
        if (StringUtil.isEmpty(keywords)) {
            mPtrHelper.refreshComplete();
            return;
        }
        // 获取车辆列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("type", currentPos + 1);// 查询类型 1-用户信息 2-车辆信息 3-卡信息 ，默认为1
        params.put("search_value", keywords);// 查询值
        params.put("start", curpage);//列表记录开始位置
        params.put("pageSize", pageSize);//一页显示行数
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_USER_SEARCH, params, false, null,
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Car> temp = JSON.parseArray(element.data, Car.class);
                        if (pullToRefresh) {// 下拉刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data2, null);
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
     * 车辆列表适配器
     */
    private class CarListAdapter extends QuickAdapter<Car> {

        public CarListAdapter(int layoutResId, List<Car> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Car item) {
            helper.setText(R.id.tv_title, item.car_brand_series)// 品牌车系
                    .setText(R.id.tv_content, item.car_plate_no)// 车牌号
                    .setText(R.id.tv_desc, item.mobile)// 手机号
                    .setVisible(R.id.iv_car_avatar, true)//显示汽车头像
                    .setVisible(R.id.iv_customer_avatar, false);//隐藏用户头像

            // 汽车头像
            ImageView iv_car_avatar = helper.getView(R.id.iv_car_avatar);
            ImageLoaderUtil.display(mContext, iv_car_avatar, item.car_brand_image, R.drawable.def_car_avatar, R.drawable.def_car_avatar);
        }
    }

}
