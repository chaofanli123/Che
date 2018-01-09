package com.victor.che.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类说明:
 *
 * @author 作者 LUYA, E-mail: 468034043@qq.com
 * @version 创建时间：2015年12月2日 下午4:10:31
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected Activity mActivity;
    protected int curpage = 1;// 当前页码
    private View rootView;

    private Unbinder unbinder;

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = getActivity();
        this.mContext = getActivity();
        // 注册EventBus
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentView(), null);
        AutoUtils.auto(rootView);// 结合AutoLayout

        // 绑定butterknife
        unbinder = ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }

    protected View findViewById(int id) {
        return rootView.findViewById(id);
    }

    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 解绑butterknife
        unbinder.unbind();
    }

    /**
     * 获取内容布局视图
     *
     * @return
     */
    public abstract int getContentView();

    @Subscribe
    public void onEventMainThread(String s) {

    }

    @Override
    public void onDestroy() {
        // 取消注册EventBus
        EventBus.getDefault().unregister(this);
      //  PgyCrashManager.unregister();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
       // PgyCrashManager.unregister();
    }

//    @SuppressLint("NewApi")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE: {
//                for (int i = 0; i < permissions.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//
//
//                    } else {
//                    }
//
//                }
//            }
//            case REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS: {
//                for (int i = 0; i < permissions.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(getActivity(), "允许读写存储！", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(getActivity(), "未允许读写存储！", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//            break;
//            default: {
//                super.onRequestPermissionsResult(requestCode, permissions,
//                        grantResults);
//            }
//        }
//
//    }
}
