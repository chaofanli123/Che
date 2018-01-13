package com.victor.che.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.victor.che.widget.TipDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 开卡时选择会员卡界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 17:33
 */
public class ChooseActiveVipcardActivity extends BaseActivity {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private ProVipcardListAdapter mAdapter;
    private List<Vipcard> mList = new ArrayList<>();

    private PtrHelper<Vipcard> mPtrHelper;

    private Vipcard mVipcard;

    @Override
    public int getContentView() {
        return R.layout.common_pull_to_refresh_recyclerview;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("选择卡");

        mVipcard = (Vipcard) getIntent().getSerializableExtra("mVipcard");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);//设置布局管理器
        mAdapter = new ProVipcardListAdapter(R.layout.item_choose_active_vipcard, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);

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
                    .setText(R.id.tv_service_content, item.getServiceContent())// 可用服务
                    .setText(R.id.label_face_value, item.getFaceValueLabel() + ":")// 面额标签
                    .setText(R.id.tv_face_value, item.getFaceValue())// 面额
                    .setText(R.id.tv_original_price, item.getEndTime())// 有效期
            ;
            ImageView iv_checked = helper.getView(R.id.iv_checked);
            if (mVipcard != null
                    && mVipcard.provider_card_id.equalsIgnoreCase(item.provider_card_id)) {
                iv_checked.setImageResource(R.drawable.ic_checked3);
            } else {
                iv_checked.setImageResource(R.drawable.ic_unchecked3);
            }

            helper.getView(R.id.tv_show_more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TipDialogFragment.newInstance("可用服务", mList.get(helper.getLayoutPosition()).getServiceContent())
                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                }
            });

            // 选中
            helper.getView(R.id.iv_checked).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 变换界面
                    mVipcard = mList.get(helper.getLayoutPosition());
                    mAdapter.notifyDataSetChanged();

                    EventBus.getDefault().post(mVipcard); // 单击回传
                    finish();
                }
            });
        }
    }
}
