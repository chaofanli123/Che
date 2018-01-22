package com.victor.che.ui;

import android.Manifest;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.victor.che.R;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.util.AppUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 引导界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 18:48
 */
public class SplashActivity extends BaseActivity {

//    @BindView(R.id.iv_splash)
//    ImageView iv_splash;
    @BindView(R.id.btn_skip)
    Button btn_skip;
    Handler handler = new Handler();
    private int seconds = 3;
    private boolean firstStartedApp;// 是否是第一次启动app

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            btn_skip.setText(Html.fromHtml("跳过( <font color='#2067ce'>" + seconds + "</font> )"));
            seconds--;
            if (seconds <= 0) {
                doSkip();

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

        btn_skip.setText(Html.fromHtml("跳过( <font color='#2067ce'>3</font> )"));

        if (!AppUtil.isNetworkAvailable(mContext)) { // 未联网
            // 显示默认启动图
            //            iv_splash.setImageResource(R.drawable.def_splash_image);
            // 倒计时进入主页
            enterApp();
            return;
        }
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
                        //                        iv_splash.setImageResource(R.drawable.def_splash_image);
                        // 倒计时进入主页
                        enterApp();
                    }

                }).check();
    }

    /**
     * 获取存储权限后，展示启动图
     */
    private void displaySplashImage() {
        firstStartedApp = MyApplication.spUtil.getBoolean(ConstantValue.SP.FIRST_STARTED_APP, true);
        // 进入app
        enterApp();
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
     * 跳过
     */
    @OnClick(R.id.btn_skip)
    void doSkip() {
        if (!MyApplication.isLogined()) {//未登录进入登录界面
            MyApplication.openActivity(mContext, LoginActivity.class);
        } else {// 第二次进入
            //进入首页
                MyApplication.openActivity(mContext, TabBottomActivity.class);
        }
        finish();
    }

    /**
     * 进入App界面
     */
    private void enterApp() {
        handler.postDelayed(runnable, 1000);
    }

}
