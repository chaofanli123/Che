package com.victor.che.ui;

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
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Car;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.util.PtrHelper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 车辆列表界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 15:57
 */
public class CarListActivity extends BaseActivity {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private List<Car> mList = new ArrayList<>();
    private CarListAdapter mAdapter;

    private PtrHelper<Car> mPtrHelper;

    private String provider_user_id;

    @Override
    public int getContentView() {
        return R.layout.activity_car_list;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("用户车辆");

        provider_user_id = getIntent().getStringExtra("provider_user_id");

        /****************** 设置XRecyclerView属性 **************************/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mAdapter = new CarListAdapter(R.layout.item_car, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        // 单击进入车辆详情界面
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("mCar", mList.get(position));
                bundle.putString("provider_user_id", provider_user_id);
                MyApplication.openActivity(mContext, CarDetailsActivity.class, bundle);
            }
        });
        mPtrHelper.autoRefresh(true);
    }
    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        // 获取订单列表
        MyParams params = new MyParams();
        params.put("provider_user_id", provider_user_id);// 	用户id
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_USER_CAR_LIST, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Car> temp = JSON.parseArray(element.body, Car.class);
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
                    }

                });
    }

    /**
     * 去添加车辆
     */
    @OnClick(R.id.topbar_right)
    void gotoAddCar() {
        Bundle bundle = new Bundle();
        bundle.putString("provider_user_id", provider_user_id);
        MyApplication.openActivity(mContext, CarDetailsActivity.class, bundle);
    }

    /**
     * 修改完毕后，刷新列表
     *
     * @param event
     */
    @Subscribe
    public void onRefreshCarList(String event) {
        if (ConstantValue.Event.REFRESH_CAR_LIST.equalsIgnoreCase(event)) {
            mPtrHelper.autoRefresh(true);
        }
    }

    /**
     * 订单列表适配器
     */
    private class CarListAdapter extends QuickAdapter<Car> {

        public CarListAdapter(int layoutResId, List<Car> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Car item) {
            helper.setText(R.id.tv_car_brand_series, item.car_brand_series)// 车型车系
                    .setText(R.id.tv_pln, item.car_plate_no);// 车牌号

            // 车品牌logo
            ImageView iv_avatar = helper.getView(R.id.iv_avatar);
            ImageLoaderUtil.display(mContext, iv_avatar, item.image, R.drawable.ic_car_pre, R.drawable.ic_car_pre);
        }
    }
}
