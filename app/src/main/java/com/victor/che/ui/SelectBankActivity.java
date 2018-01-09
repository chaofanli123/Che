package com.victor.che.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Bank;
import com.victor.che.widget.LinearLayoutManagerWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectBankActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    //    @BindView(R.id.tv_bank_name)
    //    TextView tvBankName;
    //    @BindView(R.id.tv_bank_num)
    //    TextView tvBankNum;
    private Bank bank;
    private BanklistAdapter adapter;
    private Bank.CommonBean defaultX;//默认银行卡
    private List<Bank.CommonBean> common;//非默认银行卡
    private List<Bank.CommonBean> mList = new ArrayList<>();//银行卡集合

    @Override
    public int getContentView() {
        return R.layout.activity_select_bank;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("选择银行卡");
        bank = (Bank) getIntent().getSerializableExtra("bank");

        defaultX = bank.getDefaultX();
        common = bank.getCommon();
        mList.add(defaultX);
        mList.addAll(common);
        adapter = new BanklistAdapter(R.layout.item_bank, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        //        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
        //                .sizeResId(R.dimen.common_divider_dp)
        //                .colorResId(R.color.divider)
        //                .build());//添加分隔线
        mRecyclerView.setAdapter(adapter);
        //        tvBankName.setText(defaultX.getName());
        //        tvBankNum.setText(defaultX.getCard_no());
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Bank.CommonBean commonBean = mList.get(i);
                EventBus.getDefault().post(commonBean);
                finish();
            }
        });
    }

//    /**
//     * 点击默认银行
//     */
//    @OnClick(R.id.rl_bank)
//    public void onViewClicked() {
//        EventBus.getDefault().post(defaultX);
//        finish();
//    }

    /**
     * 银行卡列表
     */
    private class BanklistAdapter extends QuickAdapter<Bank.CommonBean> {

        public BanklistAdapter(int layoutResId, List<Bank.CommonBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Bank.CommonBean item) {
            helper
                    .setText(R.id.tv_bank_name, item.getName())
                    .setText(R.id.tv_bank_num, item.getCard_no())
                    .setVisible(R.id.tv_is_current_card, item.equals(defaultX));

        }
    }

}
