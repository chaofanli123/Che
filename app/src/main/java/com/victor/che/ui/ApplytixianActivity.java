package com.victor.che.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Bank;
import com.victor.che.widget.PostiDialogFragment;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplytixianActivity extends BaseActivity {

    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_num)
    TextView tvBankNum;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    private Bank.CommonBean defaultX;//默认银行卡
    private Bank bank;
    private String bank_id;
    private String type;
    private String id;

    @Override
    public int getContentView() {
        return R.layout.activity_applytixian;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("申请提现");
        tvMoney.setText(getIntent().getStringExtra("money")+"元");
        bank_id = getIntent().getStringExtra("bank_id");
        type=getIntent().getStringExtra("type");
  /*
        获取银行卡
         */
            MyParams params=new MyParams();
            params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
            VictorHttpUtil.doGet(mContext, Define.url_provider_bank_account_list_v1, params, false, null, new BaseHttpCallbackListener<Element>() {
                @Override
                public void callbackSuccess(String url, Element element) {
                    bank = JSON.parseObject(element.body, Bank.class);
                    defaultX = bank.getDefaultX();
                    if (defaultX != null) {
                        tvBankName.setText(defaultX.getBank());
                        tvBankNum.setText(defaultX.getCard_no());
                    }
                }
            });
    }
    @OnClick({R.id.rl_bank,R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.rl_bank:
                Bundle bundle=new Bundle();
                bundle.putSerializable("bank",bank);
                MyApplication.openActivity(mContext,SelectBankActivity.class,bundle);
                break;
            case R.id.btn_submit:
                if ("1".equals(type)) {//来自账号首页
                    id=defaultX.getProvider_bank_account_id()+"";
                }else {
                    id=bank_id;
                }
                MyParams params=new MyParams();
                params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
                params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登录用户id
                params.put("provider_bank_account_id", id);//银行卡id
                VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_WITHDRAW, params, true, "申请中...", new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
//                        if (element.code==1) {
//                            showDialog();
//                        }
                        MyApplication.openActivity(mContext,MyAccountActivity.class);
                        // 关闭本页
                        finish();
                    }
                });
                break;
        }

    }

    /**
     * 显示对话框
     */
    private void showDialog() {
        String msg = "您的提现申请已提交，请耐心等待";
        PostiDialogFragment.newInstance(
                "提示",
                msg,
                "确定",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        )
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 接收来自非默认银行卡的改变
     * private  List<Bank.CommonBean> common;//非默认银行卡
     */
    @Subscribe
    public void receiveBank( Bank.CommonBean event){
        if (event == null) {
            return;
        }
        tvBankName.setText(event.getBank());
        tvBankNum.setText(event.getCard_no());
    }
}
