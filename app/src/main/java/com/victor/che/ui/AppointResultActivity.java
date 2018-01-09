package com.victor.che.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 预约成功界面
 */
public class AppointResultActivity extends BaseActivity {
    @BindView(R.id.tv_appoint_msg)
    TextView tvAppointMsg;
    private boolean mSuccess = true;// 预约成功或失败
    @Override
    public int getContentView() {
        return R.layout.activity_appoint_result;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        mSuccess = getIntent().getBooleanExtra("mSuccess", true); // 获取预约结果
        setTitle(mSuccess ? "预约成功" : "预约失败");
        tvAppointMsg.setText(mSuccess ? "恭喜您，预约成功！" : "预约失败！");
    }
}
