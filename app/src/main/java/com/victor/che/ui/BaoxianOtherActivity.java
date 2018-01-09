package com.victor.che.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.che.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaoxianOtherActivity extends Activity {

    @BindView(R.id.activity_san_ze_xian)
    LinearLayout activitySanZeXian;
    @BindView(R.id.img_no)
    ImageView imgNo;
    @BindView(R.id.rl_notoubao)
    RelativeLayout rlNotoubao;
    @BindView(R.id.img_toubao)
    ImageView imgToubao;
    @BindView(R.id.rl_toubao)
    RelativeLayout rlToubao;
    @BindView(R.id.bt_qx)
    Button btQx;
    @BindView(R.id.bt_qd)
    Button btQd;
    @BindView(R.id.tv_notoubao)
    TextView tvNotoubao;
    @BindView(R.id.tv_toubao)
    TextView tvToubao;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoxian_other);
        ButterKnife.bind(this);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //////设置为true点击区域外消失
        setFinishOnTouchOutside(true);
    }

    @OnClick({R.id.rl_notoubao, R.id.rl_toubao, R.id.bt_qx, R.id.bt_qd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_notoubao:
                imgNo.setVisibility(View.VISIBLE);
                imgToubao.setVisibility(View.GONE);
                state = 0 + "";
                tvNotoubao.setTextColor(getResources().getColor(R.color.theme_color));
                tvToubao.setTextColor(getResources().getColor(R.color.black_text));
                break;
            case R.id.rl_toubao:
                imgNo.setVisibility(View.GONE);
                imgToubao.setVisibility(View.VISIBLE);
                state = 1 + "";
                tvNotoubao.setTextColor(getResources().getColor(R.color.black_text));
                tvToubao.setTextColor(getResources().getColor(R.color.theme_color));
                break;
            case R.id.bt_qx:
                finish();
                break;
            case R.id.bt_qd:
                state = state + "-" + getIntent().getStringExtra("name");
                Intent intent = new Intent();
                intent.putExtra("NeiRong", state);
                setResult(22, intent);
                finish();
                break;
        }
    }

//    @Override
//    public int getContentView() {
//        return R.layout.activity_baoxian_other;
//    }
}
