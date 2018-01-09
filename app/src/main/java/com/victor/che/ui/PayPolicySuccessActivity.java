package com.victor.che.ui;

import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;

import butterknife.OnClick;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/11 0011 18:55.
 */

public class PayPolicySuccessActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_pay_policy_success;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("支付结果");
    }

    /**
     * 继续询价
     */
    @OnClick(R.id.btn_goto_receipt)
    void gotoQueryInsurance(){
        MyApplication.openActivity(mContext,QueryBaoxianActivity.class);
        finish();
    }

    /**
     * 回到首页
     */
    @OnClick(R.id.btn_goto_index)
    void gotoIndex(){
        MyApplication.openActivity(mContext, TabBottomActivity.class);
        finish();
    }
}
