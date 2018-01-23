package com.victor.che.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.AppVersion;
import com.victor.che.ui.fragment.AccountFragment;
import com.victor.che.ui.fragment.IndexFragment;
import com.victor.che.ui.fragment.IndexFragment1;
import com.victor.che.ui.fragment.MessageFragment;
import com.victor.che.util.AbFileUtil;
import com.victor.che.util.AppUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 底部导航菜单
 * 主Activity
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:36
 */
public class TabBottomActivity extends BaseActivity {

    private Fragment fragment0, fragment1, fragment2, fragment3;

    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;

    @BindView(R.id.tab_bottom_0)
    RadioButton tab_bottom_0;

    @BindView(R.id.tab_bottom_1)
    RadioButton tab_bottom_1;

    @BindView(R.id.tab_bottom_3)
    RadioButton tab_bottom_3;
    @BindView(R.id.tab_bottom_2)
    RadioButton tabBottom2;

    private int currentTabIndex = 0;// 被选中的tab的下标

    @Override
    public int getContentView() {
        return R.layout.activity_tab_bottom;
    }

    @Override
    protected void initView() {
        super.initView();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_bottom_0:// 首页
                        currentTabIndex = 0;
                        setTabSelection(0);
                        break;
                    case R.id.tab_bottom_1:// 执法
                        setTabSelection(1);
                        currentTabIndex = 1;
                        break;
                    case R.id.tab_bottom_2:// 执法记录
                        setTabSelection(2);
                        currentTabIndex = 2;
                        break;
                    case R.id.tab_bottom_3:// 账户
                        setTabSelection(3);
                        currentTabIndex = 3;
                        break;
                }
            }
        });
        // 默认选中首页
        setTabSelection(0);
        // 检查更新
        checkUpdate();
    }

    private void checkUpdate() {
        MyParams params = new MyParams();
        params.put("ver", AppUtil.getVersionCode(mContext));//当前版本的版本号
        VictorHttpUtil.doPost(mContext, Define.URL_APP_VERSION+";JSESSIONID="+MyApplication.getUser().JSESSIONID, params, true, "查询中……", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                if (StringUtil.isEmpty(element.body)) {
                    return;
                }
                final AppVersion version = JSON.parseObject(element.body, AppVersion.class);
                if (version == null
                        || version.ver+"" == null
                        || version.ver+"".compareTo(MyApplication.versionName) <= 0) {
                    return;
                }
                // 有新版本
                AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(
                        "发现新版本",
                        version.remarks,
                        "以后再说",
                        "确定",
                        null,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                _doUpdate(version);
                            }
                        });
                alertDialogFragment.setCancelable(false);// 不可取消
                alertDialogFragment.show(getSupportFragmentManager(), getClass().getSimpleName());
            }
        });
    }
    private void _doUpdate(final AppVersion version) {
        // 存储权限检查
        new TedPermission(MyApplication.CONTEXT)
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
                        VictorHttpUtil.downloadApk(mContext, version.downPath);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    }

                }).check();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentTabIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentTabIndex = savedInstanceState.getInt("position");
        setTabSelection(currentTabIndex);
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 选中哪个界面
     *
     * @param position
     */
    public void setTabSelection(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                tab_bottom_0.setChecked(true);
                if (fragment0 == null) {
                    fragment0 = new IndexFragment1();
                    transaction.add(R.id.frame_layout, fragment0, "fragment0");
                } else {
                    transaction.show(fragment0);
                }
                break;
            case 1:
                tab_bottom_1.setChecked(true);
                if (fragment1 == null) {
                    fragment1 = new MessageFragment();
                    transaction.add(R.id.frame_layout, fragment1, "fragment1");
                } else {
                    transaction.show(fragment1);
                }
                break;
            case 2:
               tabBottom2.setChecked(true);
                if (fragment2 == null) {
                    fragment2 = new IndexFragment();
                    transaction.add(R.id.frame_layout, fragment2, "fragment2");
                } else {
                    transaction.show(fragment2);
                }
                break;
            case 3:
                tab_bottom_3.setChecked(true);
                if (fragment3 == null) {
                    fragment3 = new AccountFragment();
                    transaction.add(R.id.frame_layout, fragment3, "fragment3");
                } else {
                    transaction.show(fragment3);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (fragment0 != null) {
            transaction.hide(fragment0);
        }
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
        if (fragment3 != null) {
            transaction.hide(fragment3);
        }
        transaction.commit();
    }

    /**
     * 实现再按一次返回桌面
     */
    private boolean isGoToTheDesktop = false;// 返回桌面
    boolean mCanGoback;

    @Override
    public void onPause() {
        super.onPause();
        isGoToTheDesktop = false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (!mCanGoback) {
            if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)
                    && (!this.isGoToTheDesktop) && (event.getAction() == 0)) {
                MyApplication.showToast("再按一次返回键回到桌面");
                this.isGoToTheDesktop = true;
                return true;
            }
            if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)
                    && (this.isGoToTheDesktop) && (event.getAction() == 0)) {
                // 返回手机桌面
                Intent localIntent = new Intent(Intent.ACTION_MAIN);
                localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                localIntent.addCategory(Intent.CATEGORY_HOME);
                startActivity(localIntent);
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 接收来自退出登录的消息
     *
     * @param event
     */
    @Subscribe
    private void onlout(Activity event) {
        if (event == null) {
            return;
        }
        event.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
