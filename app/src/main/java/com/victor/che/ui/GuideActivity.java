package com.victor.che.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.victor.che.R;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 欢迎页
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 18:51
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.img_guide)
    ImageView imgGuide;

    @Override
    public int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        super.initView();
        if (MyApplication.spUtil.getBoolean(ConstantValue.SP.FIRST_STARTED_APP)) {
            startActivity(new Intent(GuideActivity.this, SplashActivity.class));
            finish();
        } else {
            //循环播放
//            customVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    MyApplication.spUtil.setBoolean(ConstantValue.SP.FIRST_STARTED_APP, true);
//                    startActivity(new Intent(LauncherActivity.this, GuideActivity.class));
//                    finish();
//                }
//            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
