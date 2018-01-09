package com.victor.che.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.WithdrawRecord;

import butterknife.BindView;

public class TixianDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_tixian_time)
    TextView tvTixianTime;
    @BindView(R.id.ll_deal_state)
    LinearLayout llDealState;
    @BindView(R.id.ll_receipt)
    LinearLayout llReceipt;
    @BindView(R.id.tv_deal_time)
    TextView tvDealTime;
    @BindView(R.id.tv_deal_name)
    TextView tvDealName;
    @BindView(R.id.iv_tixian_receipt)
    ImageView ivTixianReceipt;
    private WithdrawRecord withdrawRecord;

    @Override
    public int getContentView() {
        return R.layout.activity_tixian_details;
    }

    @Override
    protected void initView() {
        super.initView();
        withdrawRecord = (WithdrawRecord) getIntent().getSerializableExtra("tixian");
        setTitle("提现详情");
        tvTime.setText(withdrawRecord.create_time);
        tvMoney.setText(withdrawRecord.withdraw_money + "元");
        tvTixianTime.setText(withdrawRecord.withdraw_start_time + " 至 " + withdrawRecord.withdraw_end_time);
        if (withdrawRecord.withdraw_status == 1) {// 已结算
            tvState.setText("已结算");
            tvState.setTextColor(getResources().getColor(R.color.theme_color));
            llDealState.setVisibility(View.VISIBLE);
            llReceipt.setVisibility(View.VISIBLE);
            tvDealTime.setText(withdrawRecord.withdraw_time);
            tvDealName.setText(withdrawRecord.operator);
            Glide.with(mContext).load(withdrawRecord.receipt_url).error(R.drawable.def_banner).dontAnimate().into(ivTixianReceipt);
        } else {
            tvState.setText("未处理");
            tvState.setTextColor(getResources().getColor(R.color.bule));
            llDealState.setVisibility(View.GONE);
            llReceipt.setVisibility(View.GONE);
        }
        tvTixianTime.setText(withdrawRecord.withdraw_start_time + "至" + withdrawRecord.withdraw_end_time);
    }

}
