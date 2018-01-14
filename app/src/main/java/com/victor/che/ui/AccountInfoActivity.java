package com.victor.che.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人信息界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/3 0003 10:43
 */
public class AccountInfoActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    public int getContentView() {
        return R.layout.activity_account_info;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("个人信息");
        if (MyApplication.isLogined()) {
            tv_name.setText(MyApplication.CURRENT_USER.username);
            if (!StringUtil.isEmpty(MyApplication.CURRENT_USER.login_mobile)) {
                tvPhone.setText(MyApplication.CURRENT_USER.login_mobile);
            }
        }
    }
    @OnClick(R.id.area_change_pwd)
    void gotoChangePwd() {
        MyApplication.openActivity(mContext, ChangePwdActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
