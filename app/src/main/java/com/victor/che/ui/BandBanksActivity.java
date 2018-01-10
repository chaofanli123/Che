package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Bank;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class BandBanksActivity extends BaseActivity {

    @BindView(R.id.topbar_right)
    ImageView topbarRight;
    @BindView(R.id.mRecyclerView)
    MyRecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;
    private Bank bank;
    private Bank.CommonBean defaultX;
    private BankCardAdapter mAdapter;
    private List<Bank.CommonBean> mList = new ArrayList<>();
    private PtrHelper<Bank.CommonBean> mPtrHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_band_banks;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("银行卡绑定");
        topbarRight.setImageResource(R.drawable.icon_add);
        //        new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false)

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        mAdapter = new BankCardAdapter(R.layout.item_band_banks, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
//        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData();
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPtrHelper.autoRefresh(true);
    }

    /**
     * 请求数据
     */
    private void _reqData() {
        /*
        获取银行卡
         */
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
        VictorHttpUtil.doGet(mContext, Define.url_provider_bank_account_list_v1, params, false, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                mList.clear();//清空数据
                bank = JSON.parseObject(element.data, Bank.class);
                defaultX = bank.getDefaultX();
                List<Bank.CommonBean> common = bank.getCommon();
                if (defaultX == null) {
                    //无默认
                    //                    rlBank.setVisibility(View.GONE);
                    if (CollectionUtil.isEmpty(common)) {
                        //空
                        View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                        mAdapter.setEmptyView(common_no_data);
                    } else {
                        //有数据
                        mList.addAll(common);
                        mAdapter.setNewData(mList);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    mList.add(defaultX);
                    if (!CollectionUtil.isEmpty(common)) {
                        //有数据
                        mList.addAll(common);
                        mAdapter.setNewData(mList);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                mPtrHelper.refreshComplete();
            }
        });
    }

    /**
     * 添加银行卡
     */
    @OnClick(R.id.topbar_right)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("BandBanksActivity", "BandBanksActivity");
        MyApplication.openActivity(mContext, AddBankcardActivity.class, bundle);
    }



    /**
     * 银行卡列表适配器
     */
    private class BankCardAdapter extends QuickAdapter<Bank.CommonBean> {


        public BankCardAdapter(int layoutResId, List<Bank.CommonBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final Bank.CommonBean item) {

            helper
                    .setText(R.id.tv_bank_name, item.getBank())
                    .setText(R.id.tv_bank_num, item.getCard_no())
                    .setOnClickListener(R.id.tv_bank_unband, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialogFragment.newInstance(
                                    "提示",
                                    "是否解除绑定该银行卡",
                                    "否",
                                    "是",
                                    null,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //解绑
                                            unbandCard(item.getProvider_bank_account_id(), "del");
                                        }
                                    })
                                    .show(getSupportFragmentManager(), getClass().getSimpleName());
                        }
                    });

            TextView view = helper.getView(R.id.tv_tixian_user);
            if (item.equals(mData.get(0))) {
                view.setText("当前提现账户");
                helper.setBackgroundRes(R.id.tv_tixian_user, R.color.white);
                view.setGravity(Gravity.RIGHT);
            } else {
                view.setText("设为默认提现账户");
                helper.setBackgroundRes(R.id.tv_tixian_user, R.drawable.shp_receipt);
                view.setPadding(20, 0, 20, 0);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unbandCard(item.getProvider_bank_account_id(), "set");
                    }
                });
            }

        }
    }

    /**
     * 解绑,设为默认银行卡
     */
    private void unbandCard(int provider_bank_account_id, String type) {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
        params.put("provider_bank_account_id", provider_bank_account_id);
        params.put("type", type);
        VictorHttpUtil.doPost(mContext, Define.url_provider_bank_account_edit_v1, params, false, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                //重新获取银行卡
//                _reqData();
                mPtrHelper.autoRefresh(true);
            }
        });

    }
}
