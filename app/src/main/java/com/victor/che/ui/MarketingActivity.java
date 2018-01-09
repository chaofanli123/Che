package com.victor.che.ui;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 营销页面
 */
public class MarketingActivity extends BaseActivity {

    @BindView(R.id.Rel_baoxian_select)
    RelativeLayout RelBaoxianSelect;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;

    @Override
    public int getContentView() {
        return R.layout.activity_marketing;
    }
    @Override
    protected void initView() {
        super.initView();
        tvTopbarTitle.setText("营销");
    }
    @OnClick({R.id.Rel_baoxian_select, R.id.Rel_usercar_value,R.id.Rel_follow_user,R.id.tv_end_time,R.id.tv_endmoney,R.id.tv_nocome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /**
             * 进入车险询价页面
             */
            case R.id.Rel_baoxian_select:
                MyApplication.openActivity(mContext, QueryBaoxianActivity.class);
                break;
            /**
             * 进入二手车估值页面
             */
            case R.id.Rel_usercar_value:
                MyApplication.openActivity(mContext, UsedCarValuesActivity.class);
                break;
            /**
             * 会员卡到期
             */
            case R.id.tv_end_time:
                MyApplication.openActivity(mContext, EndtimeUsercardrActivity.class);
                break;
            /**
             * 会员卡余额不足
             */
            case R.id.tv_endmoney:
                MyApplication.openActivity(mContext, EndMoneyUsercardActivity.class);
                break;
            /**
             * 长期未到店
             */
            case R.id.tv_nocome:
                MyApplication.openActivity(mContext, NocommingUsercardActivity.class);
                break;
        }
    }
}
