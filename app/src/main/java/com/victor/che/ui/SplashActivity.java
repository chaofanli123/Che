package com.victor.che.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.victor.che.R;
import com.victor.che.app.ConstantValue;
import com.victor.che.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.victor.che.app.MyApplication.spUtil;

/**
 * 引导界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 18:48
 */
public class SplashActivity extends BaseActivity {
    Handler handler = new Handler();
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
       // Picasso.with(mContext).load(R.drawable.ic_welcom).into(linSplsh);
        setViewData();
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // 存储权限检查
//        new TedPermission(mActivity)
//                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)//存储权限
//                .setDeniedMessage("您必须有存储权限正常使用app，请到\"设置->应用->权限\"中配置权限")
//                .setDeniedCloseButtonText("取消")
//                .setGotoSettingButtonText("设置")
//                .setPermissionListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted() {
//                        // 已获得授权--显示启动图
//                        displaySplashImage();
//                    }
//                    @Override
//                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                        // 设置本地默认启动图
//                                          linSplsh.setBackgroundResource(R.drawable.ic_welcom);
//                        // 倒计时进入主页
//                        setViewData();
//                    }
//
//                }).check();
//    }
    /**
     * 获取存储权限后，展示启动图
     */
    private void displaySplashImage() {
        firstStartedApp = spUtil.getBoolean(ConstantValue.SP.FIRST_STARTED_APP, true);
        // 进入app
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
//        if (spUtil.getBoolean(ConstantValue.SP.FIRST_STARTED_APP)==true) { //已经登录过了
//            //进入首页
//            intent = new Intent(this, TabBottomActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }else {
//            intent=new Intent(this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }
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
