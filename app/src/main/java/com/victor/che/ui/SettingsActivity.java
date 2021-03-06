package com.victor.che.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.AppVersion;
import com.victor.che.domain.User;
import com.victor.che.util.AbFileUtil;
import com.victor.che.util.AppUtil;
import com.victor.che.util.DataCleanManager;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.victor.che.app.MyApplication.spUtil;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.tv_version_name)
    TextView tv_version_name;

    @BindView(R.id.tv_cache_size)
    TextView tv_cache_size;

    @Override
    public int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        // 设置标题
        setTitle("设置");
        // 显示缓存大小
        _calcCacheSize();
        // 显示版本号
        tv_version_name.setText(
                "v" + AppUtil.getVersionName(mContext) + (MyApplication.DEBUG ? "-Beta" : ""));

    }

    /**
     * 清除缓存
     */
    @OnClick(R.id.area_clear_cache)
    void clearCache() {
        AlertDialogFragment.newInstance(
                "清除缓存",
                "确认要清除您手机的缓存吗？",
                "取消",
                "确定",
                null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _doClearAppCache();
                    }
                })
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 检查更新
     */
    @OnClick(R.id.area_check_update)
    void checkUpdate() {
        MyParams params = new MyParams();
        params.put("ver", AppUtil.getVersionCode(mContext));//当前版本的版本号
        VictorHttpUtil.doPost(mContext, Define.URL_APP_VERSION+";JSESSIONID="+MyApplication.getUser().JSESSIONID, params, true, "查询中……",
                new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                if (StringUtil.isEmpty(element.body)) {
                    MyApplication.showToast("已经是最新版本");
                    return;
                }
                final AppVersion version = JSON.parseObject(element.body, AppVersion.class);
                final AppVersion.DataBean data = version.getData();
                if (data == null
                        || data.getVer()+"" == null
                        || data.getVer()+"".compareTo(MyApplication.versionCode) <= 0) {
                    MyApplication.showToast("已经是最新版本");
                    return;
                }
                // 有新版本
                _doUpdate(data);
//                AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(
//                        "发现新版本",
//                        data.getRemarks(),
//                        "以后再说",
//                        "确定",
//                        null,
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        });
//                alertDialogFragment.setCancelable(false);// 不可取消
//                alertDialogFragment.show(getSupportFragmentManager(), getClass().getSimpleName());
            }
        });
    }

    private void _doUpdate(final AppVersion.DataBean version) {
        // 存储权限检查
        new TedPermission(mActivity)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)//存储权限
                .setDeniedMessage(getString(R.string.rationale_storage))
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        // 下载APK，并且替换安装
                        if (!AbFileUtil.isCanUseSD()) {// sd卡不存在
                            MyApplication.showToast("没有sdcard，请安装上再试");
                            return;
                        }
                        String path = Define.API_DOMAIN + version.getDownPath();
                        String s = path.replaceAll("\\\\", "//");
                        showUpdataDialog(version.getRemarks(),s);
                      //  VictorHttpUtil.downloadApk(mContext,s);
                    }
                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }

                }).check();
    }
    //版本更新弹框
    protected void showUpdataDialog(String msg, final String loadurl) {
        AlertDialog.Builder builer = new AlertDialog.Builder(this,R.style.Dialog_Alert_Self) ;
        builer.setTitle("版本升级");
        builer.setMessage(msg);
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(loadurl);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        //当点取消按钮时进行登录
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builer.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00317e"));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#666666"));
    }
    /**
     * 去意见反馈界面
     */
    @OnClick(R.id.tv_feedback)
    void gotoFeedback() {
        MyApplication.openActivity(mContext, FeedbackActivity.class, true);
    }

    /**
     * 去关于我们界面(WebView)
     */
    @OnClick(R.id.tv_about_us)
    void gotoAboutUs() {
//        Bundle bundle = new Bundle();
//        bundle.putString("mUrl", Define.MWEB_DOMAIN + "appweb/app_about.php");
//        MyApplication.openActivity(mContext, WebViewActivity.class, bundle);
        Bundle bundle=new Bundle();
        bundle.putString("mUrl", "");
        MyApplication.openActivity(mContext, AboutusActivity.class);
    }
    /**
     * 修改密码
     */
    @OnClick(R.id.area_change_pwd)
    void gotoChangePwd() {
        MyApplication.openActivity(mContext, ChangePwdActivity.class);
    }

    /**
     * 联系我们
     */
    @OnClick(R.id.area_contact_us)
    void doContactUs() {
        AppUtil.dial(mContext, getString(R.string.service_phone));
    }


    /**
     * 退出登录
     */
    @OnClick(R.id.btn_operate)
    void doLogout() {
        AlertDialogFragment.newInstance(
                "退出登录",
                "退出登录后，您的帐号信息会被清空，确认退出登录吗？",
                "取消",
                "确定",
                null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 清空用户信息
                        User user = new User();
                        MyApplication.saveUser(user);
                        spUtil.setBoolean(ConstantValue.SP.FIRST_STARTED_APP,false);
                        // 关闭本页面
                        finish();
                        // 停止推送
                      //  JPushInterface.stopPush(getApplicationContext());
                        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));

                  EventBus.getDefault().post(TabBottomActivity.class);
                    }
                })
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }


    /**
     * 计算缓存大小
     */
    private void _calcCacheSize() {
        // 计算缓存大小并显示
        long cacheSize = 0L;
        try {
            cacheSize = DataCleanManager.getFolderSize(getCacheDir());// 内部缓存大小
            cacheSize += DataCleanManager.getFolderSize(new File(AbFileUtil// 外部andbase缓存大小
                    .getDownloadRootDir(mContext)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String formatFileSize = android.text.format.Formatter.formatFileSize(mContext, cacheSize);
        tv_cache_size.setText(formatFileSize);
    }

    /**
     * 清除缓存
     */
    private void _doClearAppCache() {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    MyApplication.showToast("缓存清除成功");
                    tv_cache_size.setText("0B");
                } else {
                    MyApplication.showToast("缓存清除失败");
                }
            }
        };
        new Thread() {
            public void run() {
                Message msg = new Message();
                try {

                    // 清除本地缓存
                    DataCleanManager.cleanInternalCache(mContext);
                    DataCleanManager.cleanDatabases(mContext);

                    // 清除webview缓存
                    deleteDatabase("webview.db");
                    deleteDatabase("webview.db-shm");
                    deleteDatabase("webview.db-wal");
                    deleteDatabase("webviewCache.db");
                    deleteDatabase("webviewCache.db-shm");
                    deleteDatabase("webviewCache.db-wal");

                    // 清除数据缓存
                    DataCleanManager.clearCacheFolder(getFilesDir(), System.currentTimeMillis());
                    DataCleanManager.clearCacheFolder(getCacheDir(), System.currentTimeMillis());
                    // 2.2版本才有将应用缓存转移到sd卡的功能
                    //                    DataCleanManager.clearCacheFolder(MethodsCompat.getExternalCacheDir(mContext),
                    //                            System.currentTimeMillis());

                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

}
