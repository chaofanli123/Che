package com.victor.che.ui;

import android.view.View;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Consume;

import butterknife.BindView;

/**
 * 洗车或商品购买结果界面
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年6月17日 下午4:39:30
 */
public class PurchaseResultActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView tv_result;

    @BindView(R.id.tv_error_tip)
    TextView tv_error_tip;

    @BindView(R.id.area_order_info)
    View area_order_info;

    @BindView(R.id.tv_consume_amount)
    TextView tv_consume_amount;// 消费e点数

    @BindView(R.id.tv_balance)
    TextView tv_balance;// 卡内余额

    private boolean mSuccess;// 是否验证成功
    private Consume mConsume;// 消费实体

    @Override
    public int getContentView() {
        return R.layout.activity_purchase_result;
    }

    @Override
    protected void initView() {
        // 设置标题
        setTitle("消费结果");

        mSuccess = getIntent().getBooleanExtra("mSuccess", false);
        mConsume = (Consume) getIntent().getSerializableExtra("mConsume");

        tv_result.setText(mSuccess ? "消费成功！" : "消费失败！");

        tv_error_tip.setVisibility(mSuccess ? View.GONE : View.VISIBLE);
        area_order_info.setVisibility(mSuccess ? View.VISIBLE : View.GONE);

        if (mSuccess && mConsume != null) {
            tv_consume_amount.setText(mConsume.buy_eb <= 0 ? mConsume.buy_num + "次" : mConsume.buy_eb + "e点");
            tv_balance.setText(mConsume.buy_eb <= 0 ? mConsume.total_num + "次" : mConsume.total_eb + "e点");
        }
    }

}
