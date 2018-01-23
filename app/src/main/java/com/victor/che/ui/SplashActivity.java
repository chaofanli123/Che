package com.victor.che.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.victor.che.R;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 引导界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 18:48
 */
public class SplashActivity extends BaseActivity {
    private  SharedPreferencesUtil spUtil;
    Handler handler = new Handler();
    @BindView(R.id.lin_splsh)
    LinearLayout linSplsh;
    private int seconds = 3;
    private boolean firstStartedApp;// 是否是第一次启动app

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seconds--;
            if (seconds <= 0) {
                if (!MyApplication.isLogined()) {//未登录进入登录界面
                    MyApplication.openActivity(mContext, LoginActivity.class);
                } else {// 第二次进入
                    //进入首页
                    MyApplication.openActivity(mContext, TabBottomActivity.class);
                }
                return;
            }
            handler.postDelayed(this, 1000);
        }
    };
    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        super.initView();
        spUtil=new SharedPreferencesUtil(getApplicationContext(), "cbw_config");
        setViewData();
//        if (!AppUtil.isNetworkAvailable(mContext)) { // 未联网
//            // 显示默认启动图
//            linSplsh.setBackgroundResource(R.drawable.ic_welcom);
//            // 倒计时进入主页
//            enterApp();
//            return;
//        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // 存储权限检查
        new TedPermission(mActivity)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)//存储权限
                .setDeniedMessage("您必须有存储权限正常使用app，请到\"设置->应用->权限\"中配置权限")
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        // 已获得授权--显示启动图
                        displaySplashImage();
                    }
                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        // 设置本地默认启动图
                                          linSplsh.setBackgroundResource(R.drawable.ic_welcom);
                        // 倒计时进入主页
                        setViewData();
                    }

                }).check();
    }

    /**
     * 获取存储权限后，展示启动图
     */
    private void displaySplashImage() {
        firstStartedApp = spUtil.getBoolean(ConstantValue.SP.FIRST_STARTED_APP, true);
        // 进入app
       // enterApp();
        setViewData();
    }

    /**
     * 点击图片，跳转链接
     *
     * @param view
     */
    public void gotoWebView(View view) {

    }

    @Override
    public void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    /**
     * 进入App界面
     */
//    private void enterApp() {
//
//        handler.postDelayed(runnable, 1000);
//    }

    /**
     * 三秒启动
     * */
    protected void setViewData() {
        final Intent intent;
        //判断是否是第一次登陆
        if (!MyApplication.isLogined()) {//未登录进入登录界面
            intent=new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {// 第二次进入
            //进入首页
            intent = new Intent(this, TabBottomActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
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
