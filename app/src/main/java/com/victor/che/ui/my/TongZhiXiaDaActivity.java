package com.victor.che.ui.my;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.base.BaseActivity;

import butterknife.BindView;

public class TongZhiXiaDaActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar)
    RelativeLayout topbar;
    @BindView(R.id.tv_leixing)
    TextView tvLeixing;
    @BindView(R.id.tv_biaoti)
    TextView tvBiaoti;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_jiesouren)
    TextView tvJiesouren;
    @BindView(R.id.tv_yueduzhuangtai)
    TextView tvYueduzhuangtai;
    @BindView(R.id.tv_yuedushijian)
    TextView tvYuedushijian;

    @Override
    public int getContentView() {
        return R.layout.activity_tong_zhi_xia_da;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("查看通知");
        init();
    }

    private void init() {
    }
}
