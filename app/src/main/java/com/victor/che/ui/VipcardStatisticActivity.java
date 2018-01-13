package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.GlobalParams;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Vipcard;
import com.victor.che.event.RecyclerViewItemChangedEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 会员卡统计界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/2 0002 13:42
 */
public class VipcardStatisticActivity extends BaseActivity {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private ProVipcardListAdapter mAdapter;
    private List<Vipcard> mList = new ArrayList<>();

    private PtrHelper<Vipcard> mPtrHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_vipcard_statistic;
    }
    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("会员卡");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(20)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线
        mAdapter = new ProVipcardListAdapter(R.layout.item_vipcard_statistic, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {

        // 获取用户会员卡列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_CARD_LIST, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Vipcard> temp = JSON.parseArray(element.body, Vipcard.class);

                        GlobalParams.gProvVipcardList = temp;//全局的服务商会员卡列表

                        if (pullToRefresh) {// 下拉刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                ImageView nodata_img = (ImageView) common_no_data.findViewById(R.id.iv_no_data);
                                nodata_img.setBackgroundResource(R.drawable.ic_empty_huiyuancard);
                                TextView tv_tip = (TextView) common_no_data.findViewById(R.id.tv_tip);
                                tv_tip.setText("暂无会员卡");
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
     * 新增卡
     */
    @OnClick(R.id.btn_add_vipcard)
    void gotoAddVipcard() {
        MyApplication.openActivity(mContext, AddVipcardActivity.class);
    }

    /**
     * 自定义开卡
     */
    @OnClick(R.id.btn_custom_active_vipcard)
    void gotoActiveVipcard() {
        MyApplication.openActivity(mContext, CustomActiveVipcardActivity.class);
    }

    /**
     * 用户会员卡发生修改后
     *
     * @param event
     */
    @Subscribe
    public void onItemChanged(RecyclerViewItemChangedEvent event) {
        if (event != null && mAdapter != null) {
            if (event.obj instanceof Vipcard) {
                Vipcard vipcard = (Vipcard) event.obj;
                mList.set(event.position, vipcard);
                mAdapter.notifyItemChanged(event.position);
            }
        }
    }

    /**
     * 商户会员卡列表适配器
     */
    private class ProVipcardListAdapter extends QuickAdapter<Vipcard> {

        public ProVipcardListAdapter(int layoutResId, List<Vipcard> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final Vipcard item) {
            helper.setText(R.id.tv_vipcard_type_no, item.code)//会员卡编号
                    .setText(R.id.tv_vipcard_name, item.name)//会员卡名称
                    .setText(R.id.tv_vipcard_type, item.getCardTypeName())// 卡类型名称
                    .setText(R.id.tv_service_content, "可用服务包含：" + item.getServiceContent())// 可用服务
            ;

            // 用户会员卡列表
            helper.getView(R.id.tv_customer_vipcard).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("mSelectedPos", helper.getLayoutPosition());
                    MyApplication.openActivity(mContext, VipcardMgrActivity.class, bundle);
                }
            });

            // 开卡
            helper.getView(R.id.tv_active_vipcard).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("mPosition", helper.getLayoutPosition());
                    bundle.putSerializable("mVipcard", item);
                    MyApplication.openActivity(mContext, ActiveVipcardActivity.class, bundle);
                }
            });

            // 进入卡详情界面
            helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("mPosition", helper.getLayoutPosition());
                    bundle.putSerializable("mVipcard", mList.get(helper.getLayoutPosition()));
                    MyApplication.openActivity(mContext, VipcardDetailsActivity.class, bundle);
                }
            });
        }
    }
}
