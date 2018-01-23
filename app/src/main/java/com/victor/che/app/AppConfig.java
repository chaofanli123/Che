package com.victor.che.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.victor.che.util.PreferencesUtils;

import java.io.File;


@SuppressLint("NewApi")
public class AppConfig {

    public final static String CONF_AUTO_LOGIN = "auto_login";//是否自动登录
    public static boolean debug = false;
    public final static String APP_FIRST_LAUNCHER = "first_launcher_v3";//是否第一次运行
    public final static File ROOT_FILE = new File(Environment.getExternalStorageDirectory(), "/Che");/*文件存储根目录*/

    /**
     * 第一次运行程序
     */
    public static boolean isFirstLauncher(Context context) {
        return PreferencesUtils.getBoolean(context, APP_FIRST_LAUNCHER, true);
    }

    /**
     * 设置是否第一次运行程序
     */
    public static void setiSFirstLauncher(Context context, boolean value) {
        PreferencesUtils.putBoolean(context, APP_FIRST_LAUNCHER, value);
    }


    /**
     * 是否自动登录
     *
     *
     * @param context
     * @return
     */
    public static boolean isAutoLogin(Context context) {
        return PreferencesUtils.getBoolean(context, CONF_AUTO_LOGIN, true);
    }

    /**
     * 设置是否自动登录
     *
     * @param context
     * @param value
     */
    public static void setIsAutoLogin(Context context, boolean value) {
        PreferencesUtils.putBoolean(context, CONF_AUTO_LOGIN, true);
    }


}
