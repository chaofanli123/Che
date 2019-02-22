package com.victor.che.ui;

import android.content.Intent;

import com.victor.che.R;
import com.victor.che.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 引导界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 18:48
 */
public class SplashActivity extends BaseActivity {
//    @BindView(R.id.lin_splsh)
//    ImageView linSplsh;
    private int seconds = 3;
    private boolean firstStartedApp;// 是否是第一次启动app
    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }
    @Override
    protected void initView() {
        super.initView();
        setViewData();
    }

    /**
     * 三秒启动
     * */
    protected void setViewData() {
        final Intent intent;
        intent=new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task,3*1000);//此处的Delay可以是3*1000，代表三秒
    }
}
