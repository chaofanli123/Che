package com.victor.che.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.victor.che.base.BaseApplication;
import com.victor.che.domain.User;
import com.victor.che.event.Region;
import com.victor.che.util.AbActivityManager;
import com.victor.che.util.AppUtil;
import com.victor.che.util.SharedPreferencesUtil;
import com.videogo.openapi.EZOpenSDK;

import java.util.List;
/**
 * 全局应用类
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年4月19日 下午5:01:46
 */
public class MyApplication extends BaseApplication {
    public static SharedPreferencesUtil spUtil;
    public static User CURRENT_USER;// 当前用户
    //  public static final boolean DEBUG = BuildConfig.DEBUG;// 是否debug， 开发和测试阶段使用
      public static final boolean DEBUG = true;// 是否debug， 开发和测试阶段使用
    //   public static final boolean DEBUG = false;// 生产环境使用
    public static String versionCode = "1";// 版本号
    public static String versionName = "1.0.0";// 版本名称

    private  static boolean isLogin = false; // 登录状态
    public static boolean isAppOnBackground = false;// App是否被切换到后台

    public static List<Region> RegionList;// 全局保存的 服务城市列表，首页请求一次
    public static Region CURRENT_REGION = null;// 当前的服务区域（// id<=0，服务器要定位到默认城市）

    public static  Context context;

//    static {
//        System.loadLibrary("appconfig");
//    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * sdk日志开关，正式发布需要去掉
         */
 EZOpenSDK.showSDKLog(DEBUG);
/**
 * 设置是否支持P2P取流,详见api
 */
        EZOpenSDK.enableP2P(false);

/**
 * APP_KEY请替换成自己申请的
 */
        EZOpenSDK.initLib(this, "566fb0a1d274443f8d32d74212c570e7", "");

//初始化shareSdk
        //   ShareSDK.initSDK(this);
        // 获取版本信息
        context=getApplicationContext();
        versionName = AppUtil.getVersionName(this);
        versionCode = String.valueOf(AppUtil.getVersionCode(this));
        //   PgyCrashManager.register(context);
        // 开启极光推送
//        JPushInterface.setDebugMode(DEBUG);// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);

        // 开启腾讯bugly
     //   BuglyUtil.initCrashReport(getApplicationContext(), "63a1268c2e", DEBUG);

        //        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
        //            @Override
        //            public void onCoreInitFinished() {
        //                Logger.e("onCoreInitFinished");
        //            }
        //
        //            @Override
        //            public void onViewInitFinished(boolean b) {
        //                Logger.e("onViewInitFinished");
        //            }
        //        });

        // 设置sp的名称
        spUtil = new SharedPreferencesUtil(getApplicationContext(), "cbw_config");
        Logger.init("cbw")
                .logLevel(DEBUG ? LogLevel.FULL : LogLevel.NONE);//初始化日志
        /*初始化OkGo*/
//        HttpHeaders commonHttpHeaders = new HttpHeaders();//公共的http请求头
//        commonHttpHeaders.put("version", versionName);   // 版本名称
//        commonHttpHeaders.put("build", versionCode);// 版本号
//        commonHttpHeaders.put("appId", "5");// app标识 1-车维坊商户APP 2-车维坊用户APP 3-车维坊微站APP 4-车维坊后台 5-车工匠APP 6-车工匠微信APP
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    //                    .debug("OkGo", Level.INFO, true)
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(10000L)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)
                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
                    // .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    // .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效
                    //可以设置https的证书,以下几种方案根据需要自己设置
                    //                    .setCertificates()                                  //方法一：信任所有证书,不安全有风险
                    //              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
                    //              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
                    //              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                    //               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//
                    //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
                    //               .setHostnameVerifier(new SafeHostnameVerifier())
                    //                    .addInterceptor(new LoggingInterceptor())//添加日志监听器
                    //这两行同上，不需要就不要加入
                   ;   //设置全局公共参数 .addCommonHeaders(commonHttpHeaders)
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取已经登录的用户
    //    CURRENT_USER = getUser();
        // checkToken();
        /* 界面唤醒的时候checktoken */
        RegionList = getRegionList();// 获取服务城市列表
        CURRENT_REGION = getCurrentRegion();// 获取当前服务区域
    }

    /**
     * 获取服务城市列表
     *
     * @return
     */
    public  static List<Region> getRegionList() {
        return spUtil.getObject("REGION_LIST", List.class);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 获取当前服务城市
     *
     * @return
     */
    private static Region getCurrentRegion() {
        return spUtil.getObject("CURRENT_REGION", Region.class);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        AbActivityManager.getInstance().clearAllActivity();

        // 杀死进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogined() {
        if (MyApplication.CURRENT_USER == null || MyApplication.CURRENT_USER.mobileLogin==false) {
            return false;
        }
        return true;
    }

    /**
     * 保存用户
     *
     * @param user
     */
    public static void saveUser(User user) {
        MyApplication.CURRENT_USER = user;
        spUtil.setObject("CURRENT_USER", CURRENT_USER);
    }
    /**
     * 读取用户
     *
     * @return
     */
    public static User getUser() {
        return (User) spUtil.getObject("CURRENT_USER", User.class);
    }
    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }
}
