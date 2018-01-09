package com.victor.che.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

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
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.ChooseCar;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/4/25 0025 15:09.
 * 选择车辆界面（从收银界面跳转）
 */

public class ChooseCarActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    /*
   list初始化
    */
    private List<ChooseCar> mList = new ArrayList<>();
    private CarMyListAdapter mAdapter;

    private PtrHelper<ChooseCar> mPtrHelper;
    private String mobile;

    @Override
    public int getContentView() {
        return R.layout.activity_choose_car_list;
    }

    @Override
    protected void initView() {
        super.initView();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mAdapter = new CarMyListAdapter(R.layout.item_choose_car_list, mList);
        mRecyclerView.setAdapter(mAdapter);

        // 设置标题
        setTitle("选择车辆");
        mobile = getIntent().getStringExtra("mobile");
        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(mList.get(position));
                finish();
            }
        });

        mPtrHelper.autoRefresh(true);

    }

    /**
     * 获取用户车辆信息
     *
     * @param pullToRefresh
     * @param curpage
     * @param pageSize
     */
    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        // 获取车辆列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        if(!TextUtils.isEmpty(mobile))
            params.put("mobile",mobile);
        params.put("pageSize", pageSize);
        params.put("start", curpage);
        VictorHttpUtil.doGet(mContext, Define.url_provider_choose_car_list_v2, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<ChooseCar> temp = JSON.parseArray(element.data, ChooseCar.class);
                        if (pullToRefresh) {//刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                //有数据
                                mList.addAll(temp);
                                mAdapter.setNewData(mList);
                                mAdapter.notifyDataSetChanged();
                                if (CollectionUtil.getSize(temp) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {//加载更多
                            mPtrHelper.processLoadMore(temp);
                        }

                    }

                });

    }


    /**
     * 车辆列表适配器
     */
    class CarMyListAdapter extends QuickAdapter<ChooseCar> {

        public CarMyListAdapter(int layoutResId, List<ChooseCar> list) {
            super(layoutResId, list);
        }

        @Override
        protected void convert(BaseViewHolder holder, final ChooseCar userCar) {

            holder.setText(R.id.tv_car_no,userCar.car_plate_no)
                    .setText(R.id.tv_car_mileage, TextUtils.isEmpty(userCar.mileage)?"未记录":userCar.mileage+"万公里")
                    .setText(R.id.tv_car_brand,userCar.car_brand_series)
                    .setText(R.id.tv_car_next_time,TextUtils.isEmpty(userCar.next_maintain)?"未记录":userCar.next_maintain);

        }
    }

}
