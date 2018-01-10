package com.victor.che.ui;

import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.base.BaseActivity;

import butterknife.BindView;

/**
 * 支付结果界面
 */
public class PayResultActivity extends BaseActivity {


    @BindView(R.id.tv_pay_msg)
    TextView tvPayMsg;
    private boolean mPaySuccess;//支付是否成功 1:支付成功;0：支付失败;-1：支付取消
    @Override
    public int getContentView() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("支付完成");
        mPaySuccess = getIntent().getBooleanExtra("mPaySuccess", false);
    }
}
