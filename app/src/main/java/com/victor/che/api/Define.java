
package com.victor.che.api;

/**
 * 类说明: 定义网络请求的url
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年10月29日 下午9:11:53
 */
public class Define {

    public static final String MWEB_DOMAIN = "http://x.cheweifang.cn/";// 手机webview网页域名
  //  public static final String API_DOMAIN ="https://xapi.cheweifang.cn/index.php?";// API域名
    public static final String API_DOMAIN ="http://222.143.21.157:8090/aims/";//api地址
//    public static final String MWEB_DOMAIN = String.format("http://x%s.cheweifang.cn/", MyApplication.DEBUG ? "test" : "");// 手机webview网页域名
//
//    public static final String API_DOMAIN = String.format("https://xapi%s.cheweifang.cn/index.php?", MyApplication.DEBUG ? "test" : "");// API域名

    /**
     * 登录
     */
    public static final String URL_LOGIN = API_DOMAIN + "a/login";









    /**
     * 获取总店服务商产品类别列表
     */
    public static final String URL_GOODS_CATEGORY_LIST = API_DOMAIN + "c=goods_category&a=list&v=1";

    /**
     * 获取服务商商品列表
     */
    public static final String URL_GOODS_LIST = API_DOMAIN + "c=goods&a=list&v=1";

    /**
     * 下架服务商服务商品
     */
    public static final String URL_GOODS_OFF = API_DOMAIN + "c=goods&a=off&v=1";

    /**
     * 服务商编辑商品
     */
    public static final String URL_GOODS_EDIT = API_DOMAIN + "c=goods&a=edit&v=1";

    /**
     * 消费接口（洗车）收银界面
     */
    public static final String URL_ORDER_ADD = API_DOMAIN + "c=order&a=add&v=2";

    /**
     * 检查token
     */
    public static final String URL_CHECK_TOKEN = API_DOMAIN + "c=auth&a=check_token&v=1";

    /**
     * 提示app更新---app最新版本信息
     */
    public static final String URL_APP_VERSION = API_DOMAIN + "c=app_client_version&a=info&v=1";

    /**
     * 登录获取验证码
     */
    public static final String URL_GET_CAPTCHA = API_DOMAIN + "c=auth&a=sendcode&v=1";

    /**
     * 意见反馈
     */
    public static final String URL_POST_FEEDBACK = API_DOMAIN + "c=feedback&a=add&v=1";

    /**
     * 获取用户列表
     */
    public static final String URL_PROVIDER_USER_LIST = API_DOMAIN + "c=provider_user&a=list&v=1";

    /**
     * 获取用户详情
     */
    public static final String URL_PROVIDER_USER_DETAILS = API_DOMAIN + "c=provider_user&a=detail&v=1";

    /**
     * 修改密码
     */
    public static final String URL_CHANGE_PWD = API_DOMAIN + "c=provider&a=reset_password&v=1";

    /**
     * 获取服务商卡列表
     */
    public static final String URL_PROVIDER_CARD_LIST = API_DOMAIN + "c=provider_card&a=list&v=1";

    /**
     * 获取订单列表
     */
    public static final String URL_ORDER_LIST = API_DOMAIN + "c=order&a=list&v=1";

    /**
     * 订单详情
     */
    public static final String URL_ORDER_DETAILS = API_DOMAIN + "c=order&a=detail&v=1";

    /**
     * 获取商家已卖出商品实时金额统计
     */
    public static final String URL_STATISTIC_AMOUNT = API_DOMAIN + "c=order&a=statistic_amount&v=1";

    /**
     * 服务商开卡
     */
    public static final String URL_PROVIDER_CARD_ALLOCATE = API_DOMAIN + "c=provider_card&a=allocate&v=1";

    /**
     * 服务商添加服务商品
     */
    public static final String URL_GOODS_ADD = API_DOMAIN + "c=goods&a=add&v=1";

    /**
     * 用户卡列表
     */
    public static final String URL_PROVIDER_USER_CARD_LIST = API_DOMAIN + "c=provider_user_card&a=list&v=1";

    /**
     * 收银界面列表
     */
    public static final String URL_PROVIDER_USER_CARD_LIST_receipt = API_DOMAIN + "c=provider_user_card&a=list&v=2";

    /**
     * 车牌号识别
     */
    public static final String URL_QUERY_PLN = "http://v.juhe.cn/certificates/query.php";

    /**
     * 添加用户
     */
    public static final String URL_PROVIDER_USER_ADD = API_DOMAIN + "c=provider_user&a=add&v=1";

    /**
     * 获取车辆品牌或型号
     */
    public static final String URL_BRAND_SERIES_LIST = API_DOMAIN + "c=car_brand_series&a=list&v=1";

    /**
     * 用户车辆列表
     */
    public static final String URL_PROVIDER_USER_CAR_LIST = API_DOMAIN + "c=provider_user_car&a=list&v=1";

    /**
     * 修改用户信息
     */
    public static final String URL_PROVIDER_USER_EDIT = API_DOMAIN + "c=provider_user&a=edit&v=1";

    /**
     * 服务商添加卡
     */
    public static final String URL_PROVIDER_CARD_ADD = API_DOMAIN + "c=provider_card&a=add&v=1";

    /**
     * 服务商开卡统计
     */
    public static final String URL_PROVIDER_CARD_STATISTIC = API_DOMAIN + "c=provider_card&a=statistic&v=1";

    /**
     * 用户卡编辑(会员卡基本信息编辑或充值)【修改手机号暂时先不考虑】
     */
    public static final String URL_PROVIDER_USER_CARD_EDIT = API_DOMAIN + "c=provider_user_card&a=edit&v=1";

    /**
     * 服务商提现接口
     */
    public static final String URL_PROVIDER_WITHDRAW = API_DOMAIN + "c=provider&a=withdraw&v=1";

    /**
     * 获取服务商已提现金额和账户余额
     */
    public static final String URL_PROVIDER_AMOUNT = API_DOMAIN + "c=provider&a=amount&v=1";

    /**
     * 报表数据
     */
    public static final String URL_REPORT_LIST = API_DOMAIN + "c=report&a=list&v=1";

    /**
     * 服务商信息编辑
     */
    public static final String URL_PROVIDER_EDIT = API_DOMAIN + "c=provider&a=edit&v=1";

    /**
     * 获取卡类型
     */
    public static final String URL_CARD_CATEGORY_LIST = API_DOMAIN + "c=card_category&a=list&v=1";

    /**
     * 获取订单类型
     */
    public static final String URL_ORDER_CATEGORY_LIST = API_DOMAIN + "c=order_category&a=list&v=1";

    /**
     * 【日报表】获取用户报表
     */
    public static final String URL_REPORT_USER_LIST = API_DOMAIN + "c=report&a=user_list&v=1";

    /**
     * 【日报表】获取营业额报表
     */
    public static final String URL_REPORT_AMOUNT_LIST = API_DOMAIN + "c=report&a=amount_list&v=1";

    /**
     * 获取商品分类详情(新增卡选择适用的服务时使用)
     */
    public static final String URL_GOODS_CATEGORY_DETAIL = API_DOMAIN + "c=goods_category&a=detail&v=1";

    /**
     * 修改用户车辆信息
     */
    public static final String URL_PROVIDER_USER_CAR_EDIT = API_DOMAIN + "c=provider_user_car&a=edit&v=1";

    /**
     * 添加用户车辆
     */
    public static final String URL_PROVIDER_USER_CAR_ADD = API_DOMAIN + "c=provider_user_car&a=add&v=1";

    /**
     * 获取服务商的员工列表
     */
    public static final String URL_STAFF_USER_LIST = API_DOMAIN + "c=staff_user&a=list&v=1";

    /**
     * 收银第三方支付获取订单状态
     */
    public static final String URL_ORDER_STATUS = API_DOMAIN + "c=order&a=order_status&v=1";

    /**
     * 获取推送给服务商的消息列表
     */
    public static final String URL_PROVIDER_NOTIFY_LIST = API_DOMAIN + "c=provider_notify&a=list&v=1";

    /**
     * 获取APP的banner
     */
    public static final String URL_APP_CONFIG = API_DOMAIN + "c=app_config&a=list&v=1";

    /**
     * 用户搜索接口(支持用户手机号，车牌号，卡号模糊搜索)
     */
    public static final String URL_PROVIDER_USER_SEARCH = API_DOMAIN + "c=provider_user&a=search&v=1";

    /**
     * 合并用户时获取用户信息
     */
    public static final String URL_MERGE_USER_LIST = API_DOMAIN + "c=provider_user&a=merge_user_list&v=1";

    /**
     * 合并的用户详情
     */
    public static final String URL_MERGE_USER_DETAIL = API_DOMAIN + "c=provider_user&a=merge_user_detail&v=1";

    /**
     * 用户合并
     */
    public static final String URL_PROVIDER_USER_MERGE = API_DOMAIN + "c=provider_user&a=merge&v=1";


    /**
     * 删除用户
     */
    public static final String URL_PROVIDER_USER_DEL = API_DOMAIN + "c=provider_user&a=del&v=1";

    /**
     * 服务商上架商品
     */
    public static final String URL_GOODS_ON = API_DOMAIN + "c=goods&a=on&v=1";

    /**
     * 服务商卡编辑
     */
    public static final String URL_PROVIDER_CARD_EDIT = API_DOMAIN + "c=provider_card&a=edit&v=1";

    /**
     * 	服务商自定义开卡接口
     */
    public static final String URL_PROVIDER_CARD_CUSTOM_ALLOCATE = API_DOMAIN + "c=provider_card&a=custom_allocate&v=1";

    /**
     * 	订单核销
     */
    public static final String URL_ORDER_CONSUME = API_DOMAIN + "c=order&a=consume&v=1";

    /**
     * 根据手机号或车牌号获取另一个信息
     */
    public static final String URL_PROVIDER_USER_INFO = API_DOMAIN + "c=provider_user&a=user_info&v=1";

    /**
     * 获取保险查询记录列表2
     */
    public static final String url_querl_list = API_DOMAIN + "c=insurance_query&a=list&v=2";

    /**
     * 获取出险查询记录列表
     */
    public static final String url_querl_insurance_order_list = API_DOMAIN + "c=insurance_order&a=list&v=1";

    /**
     * 新增车险查询
     */
    public static final String url_insurance_query_add_v1 = API_DOMAIN + "c=insurance_query&a=add&v=1";
    /**
     * 用户车辆列表
     */
    public static final String url_provder_user_car_list_v1 = API_DOMAIN + "c=provider_user_car&a=list&v=1";

    /**
     * 获取二手车估值城市列表
     */
    public static final String url_usedcar_area_list_v1 = API_DOMAIN + "c=usedcar_area&a=list&v=1";

    /**
     * 获取品牌车系车型
     */
    public static final String url_usedcar_brand_series_list_v1 = API_DOMAIN + "c=usedcar_brand_series&a=list&v=1";
    /**
     * 获取二手车历史估值记录
     */
    public static final String url_usedcar_history_list_v1 = API_DOMAIN + "c=usedcar_assess&a=list&v=1";

    /**
     * 二手车估值
     */
    public static final String url_usedcar_assess_car_assess_v1 = API_DOMAIN + "c=usedcar_assess&a=car_assess&v=1";

    /**
     * 二手车估值记录详情
     */
    public static final String url_usedcar_history_detail_list_v1 = API_DOMAIN + "c=usedcar_assess&a=detail&v=1";

    /**
     * 选择车辆
     */
    public static final String url_provider_choose_car_list_v2 = API_DOMAIN + "c=provider_user_car&a=list&v=2";

    /**
     * 提交到挂单
     */
    public static final String url_cart_add_pending_order = API_DOMAIN + "c=cart&a=add&v=1";

    /**
     * 获取挂单
     */
    public static final String url_cart_list_pending_order = API_DOMAIN + "c=cart&a=list&v=1";

    /**
     * 删除挂单
     */
    public static final String url_cart_del_pending_order = API_DOMAIN + "c=cart&a=del&v=1";

    /**
     * 编译挂单
     */
    public static final String url_cart_edit_pending_order = API_DOMAIN + "c=cart&a=edit&v=1";
    /**
     * 获取数据区间信息【1.1.0版本】
     */
    public static final String url_provider_user_track_section_v1 = API_DOMAIN + "c=user_track&a=section&v=1";

    /**
     * 删除数据区间【1.1.0版本新增】
     */
    public static final String url_provider_user_track_del_section_v1 = API_DOMAIN + "c=user_track&a=del_section&v=1";
    /**
     * 添加数据区间【1.1.0版本新增】
     */
    public static final String url_provider_user_track_add_section_v1 = API_DOMAIN + "c=user_track&a=add_section&v=1";

    /**
     * 用户跟踪数据【1.1.0版本新增】
     */
    public static final String url_provider_user_track_list_v1 = API_DOMAIN + "c=user_track&a=list&v=1";
    /**
     * 获取险种列表
     */
    public static final String url_insurance_category_list_v1 = API_DOMAIN + "c=insurance_category&a=list&v=1";
    /**
     * 车险保险公司列表
     */
    public static final String url_insurance_company_list_v1 = API_DOMAIN + "c=insurance_company&a=list&v=1";
    /**
     * 车险询价提交【1.1.1版本新增】
     */
    public static final String url_insurance_query_add_v2 = API_DOMAIN + "c=insurance_query&a=add&v=2";

    /**
     * 报价详情数据【1.1.0版本新增】
     */
    public static final String url_insurance_query_detail_v2= API_DOMAIN + "c=insurance_query&a=detail&v=2";

    /**
     * 保险详细报价【1.1.0版本新增】
     */
    public static final String url_insurance_quote_detail_v1= API_DOMAIN + "c=insurance_quote&a=detail&v=1";

    /**
     * 添加保单【1.1.0版本新增】
     */
    public static final String url_insurance_order_add_v1= API_DOMAIN + "c=insurance_order&a=add&v=1";

    /**
     * 保单支付确认【1.1.0版本新增】
     */
    public static final String url_insurance_order_confirm_v1= API_DOMAIN + "c=insurance_order&a=confirm&v=1";

    /**
     * 服务商提现账户列表【1.1.1版本新增】
     */
    public static final String url_provider_bank_account_list_v1= API_DOMAIN + "c=provider_bank_account&a=list&v=1";

    /**
     * 服务商添加银行卡【1.1.1版本】
     */
    public static final String url_provider_bank_account_add_v1= API_DOMAIN + "c=provider_bank_account&a=add&v=1";

    /**
     * 服务商提现账户列表【1.1.1版本新增】
     */
    public static final String url_provider_bank_account_edit_v1= API_DOMAIN + "c=provider_bank_account&a=edit&v=1";

    /**
     * 服务商提现账户列表【1.1.1版本新增】
     */
    public static final String url_provider_store_list_v1= API_DOMAIN + "c=provider&a=list&v=1";
    /**
     *收银获取用户优惠券
     */
    public static final String url_coupon_grant_record_list_v1= API_DOMAIN + "c=coupon_grant_record&a=list&v=1";
    /**
     *收银成功商家优惠券列表
     */
    public static final String url_coupon_list_v1= API_DOMAIN + "c=coupon&a=list&v=1";
    /**
     *收银成功赠送优惠券
     */
    public static final String url_coupon_grant_record_donate_v1= API_DOMAIN + "c=coupon_grant_record&a=donate&v=1";
    /**
     *门店获取服务类别接口
     */
    public static final String url_subbranch_goods_category_list_v1= API_DOMAIN + "c=subbranch_goods_category&a=list&v=1";
    /**
     *提交门店服务类别管理
     */
    public static final String url_subbranch_goods_category_manage_v1= API_DOMAIN + "c=subbranch_goods_category&a=manage&v=1";

    /**
     *直接获取商家优惠券列表
     */
    public static final String url_coupon_all_list_v1= API_DOMAIN + "c=coupon&a=all_list&v=1";
    /**
     *优惠券状态修改（禁用或启用）
     */
    public static final String url_coupon_change_status= API_DOMAIN + "c=coupon&a=change_status&v=1";
    /**
     *优惠券添加
     */
    public static final String url_coupon_add_v1= API_DOMAIN + "c=coupon&a=add&v=1";
    /**
     *优惠券修改
     */
    public static final String url_coupon_edit_v1= API_DOMAIN + "c=coupon&a=edit&v=1";
    /**
     *直接获取优惠券发放记录
     */
    public static final String url_coupon_grant_record_grant_list_v1= API_DOMAIN + "c=coupon_grant_record&a=grant_list&v=1";
    /**
     用户优惠券删除
     */
    public static final String url_coupon_grant_record_del_v1= API_DOMAIN + "c=coupon_grant_record&a=del&v=1";

}
