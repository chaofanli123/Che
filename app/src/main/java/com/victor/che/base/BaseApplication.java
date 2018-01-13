package com.victor.che.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.victor.che.app.MyApplication;
import com.victor.che.domain.User;
import com.victor.che.ui.LoginActivity;


/**
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月23日 下午7:38:03
 */
public class BaseApplication extends Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();
    }



    /**
     * 短土司
     *
     * @param msg
     */
    public static void showToast(String msg) {
        Toast.makeText(CONTEXT, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长土司
     *
     * @param msg
     */
    public static void showToastLong(String msg) {
        Toast.makeText(CONTEXT, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 通过类名启动Activity
     *
     * @param targetClass
     */
    public static void openActivity(Context context, Class<?> targetClass) {
        openActivity(context, targetClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param targetClass
     * @param extras
     */
    public static void openActivity(Context context, Class<?> targetClass, Bundle extras) {
        Intent intent = new Intent(context, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void openActivityForResult(Activity activity, Class<?> targetClass, Bundle extras, int requestCode) {
        Intent intent = new Intent(activity, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void openActivityForResult(Activity activity, Class<?> targetClass, int requestCode) {
        openActivityForResult(activity, targetClass, null, requestCode);
    }

    public static void openActivity(Class<?> targetClass) {
        openActivity(CONTEXT, targetClass);
    }

    /**
     * 通过Action启动Activity
     *
     * @param action
     */
    public static void openActivity(Context context, String action) {
        openActivity(context, action, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param action
     * @param extras
     */
    public static void openActivity(Context context, String action, Bundle extras) {
        Intent intent = new Intent(action);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    /**
     * 需要先登录
     *
     * @param mContext            上下文
     * @param redirectTargetClass 登陆后 重定向的界面
     */
    public static void needLogin(Context mContext, Class<?> redirectTargetClass, Bundle extras) {
        User user = new User();
        MyApplication.CURRENT_USER = user;
        MyApplication.spUtil.setUser(user);
        if (redirectTargetClass != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("mRedirectTargetClass", redirectTargetClass);
            bundle.putBundle("mBundle", extras);
            openActivity(mContext, LoginActivity.class, bundle);
        } else {
            openActivity(mContext, LoginActivity.class);
        }
    }

    /**
     * 需要先登录
     *
     * @param context 上下文
     */
    public static void needLogin(Context context) {
        needLogin(context);
    }

    /**
     * @param context
     * @param targetClass
     * @param needLogin   是否需要登录
     */
    public static void openActivity(Context context, Class<?> targetClass, boolean needLogin) {
        openActivity(context, targetClass, null, needLogin);
    }

    /**
     * @param context
     * @param targetClass
     * @param extras
     * @param needLogin   是否需要登录
     */
    public static void openActivity(Context context, Class<?> targetClass, Bundle extras, boolean needLogin) {
        if (needLogin && !MyApplication.isLogined()) {
            // 先需要登录
            needLogin(context, targetClass, extras);
        } else {
            Intent intent = new Intent(context, targetClass);
            if (extras != null) {
                intent.putExtras(extras);
            }
            context.startActivity(intent);
        }
    }
}
