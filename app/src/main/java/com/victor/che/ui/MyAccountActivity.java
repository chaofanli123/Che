package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
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
import com.victor.che.domain.WithdrawRecord;
import com.victor.che.util.MathUtil;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.victor.che.widget.PostiDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的账户界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 15:32
 */
public class MyAccountActivity extends BaseActivity {

    @BindView(R.id.tv_withdraw)
    TextView tv_withdraw;

    @BindView(R.id.tv_available_balance)
    TextView tv_available_balance;

    @BindView(R.id.btn_operate)
    TextView btn_operate;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_maxmongey)
    TextView tvMaxmongey;
    @BindView(R.id.topbar_right)
    ImageView topbarRight;

    private WithdrawRecordAdapter mAdapter;
    private List<WithdrawRecord> mList = new ArrayList<>();
    private String money_ketixian;

    private Bank bank;

    @Override
    public int getContentView() {
        return R.layout.activity_my_account;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("账户");
        Glide.with(mContext).load(R.drawable.ic_bank_logo).error(R.drawable.def_banner).dontAnimate().into(topbarRight);
        Glide.with(mContext).load(R.drawable.ic_yinhang_card).dontAnimate().into(topbarRight);
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mAdapter = new WithdrawRecordAdapter(R.layout.item_withdraw_record, mList);
        mRecyclerView.setAdapter(mAdapter);

        // 获取账户余额
//        doGetAccoutMoney();

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                WithdrawRecord withdrawRecord = mList.get(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tixian", withdrawRecord);
                MyApplication.openActivity(mContext, TixianDetailsActivity.class, bundle);
            }
        });

        //获取银行卡
//        doGetBankCards();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在界面回显时重新获取,解决添加完银行卡或解绑银行卡返回到该界面数据没刷新出现的bug
        // 获取账户余额
        doGetAccoutMoney();
        //获取银行卡
        doGetBankCards();
    }


    /**
     * 获取银行卡
     */
    private void doGetBankCards() {
        MyParams params1 = new MyParams();
        params1.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
        VictorHttpUtil.doGet(mContext, Define.url_provider_bank_account_list_v1, params1, false, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                bank = JSON.parseObject(element.data, Bank.class);
            }
        });
    }

    /**
     * 获取账户余额
     */
    private void doGetAccoutMoney() {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_AMOUNT, params, true, "申请中...", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                JSONObject jsonobj = JSON.parseObject(element.data);
                if (jsonobj == null) {
                    MyApplication.showToast("返回数据为空，请稍后重试");
                    return;
                }
                //历史提现金额
                tv_withdraw.setText(MathUtil.getMoneyText(jsonobj.getDoubleValue("withdraw")));
                // 账户余额
                tv_available_balance.setText(jsonobj.getString("all_amount") + "元");
                //amount 最多可提现余额
                money_ketixian = jsonobj.getString("amount");
                tvMaxmongey.setText(jsonobj.getString("amount") + "元");
                // 提现记录
                mList = JSON.parseArray(jsonobj.getString("withdraw_record"), WithdrawRecord.class);
                mAdapter.setNewData(mList);
                mAdapter.notifyDataSetChanged();
                btn_operate.setEnabled(true);
            }
        });
    }


    /**
     * 申请提现
     */
    @OnClick(R.id.btn_operate)
    void doOperate() {
        /**
         * 判断是否可提现
         */
        if ("0.00".equals(money_ketixian)) {
            MyApplication.showToast("您的余额不足，不能提现");
            return;
        }
        if (bank == null || (bank.getDefaultX() == null && bank.getCommon().size() == 0)) {
            MyApplication.showToast("您还没有银行卡，请先添加银行卡");
            Bundle bundle = new Bundle();
            bundle.putString("money", money_ketixian);
            MyApplication.openActivity(mContext, AddBankcardActivity.class, bundle);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("money", money_ketixian);
        bundle.putString("type", "1");
        MyApplication.openActivity(mContext, ApplytixianActivity.class, bundle);
    }

    /**
     * 进入添加银行卡
     */
    @OnClick({R.id.tv_tishi, R.id.topbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topbar_right://进入绑定银行卡界面
                MyApplication.openActivity(mContext, BandBanksActivity.class);
                break;
            case R.id.tv_tishi://提示框
                showDialog();
                break;
        }
    }

    /**
     * 显示对话框
     */
    private void showDialog() {
        String msg = "当天的入账资金需到后一天才能结算，可提现的余额实际是上一天的尚未提现的资金总额";
        PostiDialogFragment.newInstance(
                "提现金额说明",
                msg,
                "知道了",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        )
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 订单列表适配器
     */
    private class WithdrawRecordAdapter extends QuickAdapter<WithdrawRecord> {

        public WithdrawRecordAdapter(int layoutResId, List<WithdrawRecord> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WithdrawRecord item) {
            helper.setText(R.id.tv_apply_time, item.create_time)// 提现时间
                    .setText(R.id.tv_withdraw_money, MathUtil.getMoneyText(item.withdraw_money))// 提现金额
                    //.setText(R.id.tv_deal_time, item.withdraw_time)// 处理时间
                    .setText(R.id.tv_withdraw_status, item.getWithdrawStatus())// 处理状态
                    .setTextColor(R.id.tv_withdraw_status, getResources().getColor(item.getWithdrawStatusColor()));
            TextView status = helper.getView(R.id.tv_withdraw_status);
            if ("1".equals(item.getWithdrawStatus())) { //
                status.setTextColor(getResources().getColor(R.color.theme_color));
            } else {
                status.setTextColor(getResources().getColor(R.color.bule));
            }
            // .setVisible(R.id.label_deal_time, item.withdraw_status == 1)
            //.setVisible(R.id.tv_deal_time, item.withdraw_status == 1);
        }
    }
}
