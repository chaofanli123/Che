package com.victor.che.app;

/**
 * 项目常量值
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月22日 上午9:22:32
 */
public class ConstantValue {

    /**
     * HTTP请求失败的状态码
     */
    public static final int DEFAULT_PRICE_OFFSET = 0;//默认的差价（原价与销售价之间的差价）

    /**
     * HTTP请求服务错误的状态码
     */
    public static final int HTTP_SERVER_ERROR_CODE = 500;

    /**
     * 编码格式
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * SharedPreferences中的key----设置JPush别名
     */
    public static final String SP_KEY_SET_JPUSH_ALIAS = "set_jpush_alias";

    /**
     * 手机客户端的类型
     */
    public static final int OS = 2;// 登录设备 0：未知，1：ios，2：Android

    /**
     * token默认有效期30天
     */
    public static final int TOKEN_EXPIRE_DAY = 30;



    /**
     * SharedPreferences相关的key
     */
    public static class SP {
        /**
         * 首次启动的key
         */
        public static final String FIRST_STARTED_APP = "first_start_app1";
    }
    /**
     * 默认服务城市
     */
    public static final String DEFAULT_SERVICE_CITY = "新乡市";

    /**
     * EventBus相关事件
     */
    public static class Event {
        /**
         * 刷新产品列表
         */
        public static final String REFRESH_PRODUCT_LIST = "refresh_product_list";

        /**
         * 刷新汽车列表
         */
        public static final String REFRESH_CAR_LIST = "refresh_car_list";

        /**
         * 刷新用户列表
         */
        public static final String REFRESH_CUSTOMER_LIST = "refresh_customer_list";

        /**
         * 开卡成功
         */
        public static final String ACTIVE_VIPCARD = "active_vipcard";

        /**
         * 刷新会员卡列表
         */
        public static final String REFRESH_VIPCARD_LIST = "refresh_vipcard_list";
        /**
         * 删除购物车
         */
        public static final String DELETE_SHOPPING_CAR = "delete_shopping_car";
    }
    /**
     * 表示预约（预约支付结果界面不同）
     */

    public static final int APPOINT =-10001 ;
    /**
     * 支付方式---微信支付
     */
    public static final String PAY_TYPE_WXPAY = "wxpay";
    /**
     * 支付方式---支付宝支付
     */
    public static final String PAY_TYPE_ALIPAY = "alipay";

    /**
     * 获取区间的类型 1-会员卡到期区间 2-会员卡余额不足区间 3-会员卡余次不足区间 4-会员长期未到店区间 5-用户消费区间
     */
    public static final int USER_CARD_END_TIME = 1;
    public static final int USER_CARD_END_MONGEY = 2;
    public static final int USER_CARD_END_TIMES = 3;
    public static final int USER_CARD_ON_COMMING = 4;
    public static final int USER_CARD_CONSUME = 5;

}
