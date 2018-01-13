package com.victor.che.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.BankId;
import com.victor.che.widget.ClearEditText;
import com.victor.che.widget.PostiDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBankcardActivity extends BaseActivity {

    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.iv_reload)
    ImageView ivReload;
    @BindView(R.id.tv_label_user_name)
    TextView tvLabelUserName;
    @BindView(R.id.et_card_user_name)
    ClearEditText etCardUserName;
    @BindView(R.id.iv_choose_customer)
    ImageView ivChooseCustomer;
    @BindView(R.id.tv_label_bank_name)
    TextView tvLabelBankName;
    @BindView(R.id.et_bank_name)
    ClearEditText etBankName;
    @BindView(R.id.tv_label_card_id)
    TextView tvLabelCardId;
    @BindView(R.id.et_bank_card_id)
    ClearEditText etBankCardId;
    @BindView(R.id.et_bank_branch)
    ClearEditText etBankBranch;
    @BindView(R.id.btn_operate)
    Button btnOperate;

    private String money;
    private String bandBanksActivity;

    @Override
    public int getContentView() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("添加银行卡");
        money = getIntent().getStringExtra("money");
        bandBanksActivity = getIntent().getStringExtra("BandBanksActivity");
    }

    @OnClick({R.id.iv_choose_customer, R.id.btn_operate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_customer:
                showDialog();
                break;
            case R.id.btn_operate:
                /**
                 * 添加银行卡
                 */
                addBandcard();
                break;
        }
    }

    private void addBandcard() {
        //        name	string	是	开户人姓名
        //        card_no	string	是	银行卡号
        //        bank	string	是	银行名称
        //        account_bank	string	否	开户行名称
        // 获取账户余额
        String name = etCardUserName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            MyApplication.showToast("持卡人姓名不能为空");
            etCardUserName.requestFocus();
            return;
        }

        String bank_name = etBankName.getText().toString().trim();
        if (TextUtils.isEmpty(bank_name)) {
            MyApplication.showToast("银行名称不能为空");
            etBankName.requestFocus();
            return;
        }
        String car_num = etBankCardId.getText().toString().trim();
        if (TextUtils.isEmpty(car_num)) {
            MyApplication.showToast("银行卡号不能为空");
            etBankCardId.requestFocus();
            return;
        }
        String BankBranch = etBankBranch.getText().toString().trim();
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//服务商编号
        params.put("name", name);//
        params.put("card_no", car_num);//
        params.put("bank", bank_name);//
        params.put("account_bank", BankBranch);//服务商编号
        VictorHttpUtil.doPost(mContext, Define.url_provider_bank_account_add_v1, params, true, "添加中...", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                if("BandBanksActivity".equals(bandBanksActivity)){
                    //说明是从绑定银行卡界面过来的
                    MyApplication.openActivity(mContext,BandBanksActivity.class);
                    finish();
                    return;
                }
                Gson gson = new Gson();
                BankId bankId = gson.fromJson(element.body, BankId.class);
                int provider_bank_account_id = bankId.getProvider_bank_account_id();
                Bundle bundle = new Bundle();
                bundle.putString("money", money);
                bundle.putString("bank_id", provider_bank_account_id + "");
                bundle.putString("type", "2");
                MyApplication.openActivity(mContext, ApplytixianActivity.class, bundle);
                finish();
            }
        });
    }

    /**
     * 显示对话框
     */
    private void showDialog() {
        String msg = "为保证账户资金安全，请绑定认证\n用户本人的银行卡";
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
}
