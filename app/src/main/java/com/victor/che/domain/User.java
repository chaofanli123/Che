package com.victor.che.domain;

import com.victor.che.util.DateUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 用户实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 1729
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
//        "provider_id" 1  #服务商id,
//                "name" "测试商家"  #服务商名称,
//                "address" "郑州蓝宝万"  #服务商地址,
//                "thumb_image_url" "http//image.cheweifang.cn/"  #门店缩略图,
//                "service_tel" "13021960147"  #服务商联系方式,
//                "wxmp_qrcode" ""  #微信公众号图片,
//                "wxpay_collection_qrcode" ""  #服务商微信支付二维码,
//                "alipay_collection_qrcode" ""  #服务商支付宝支付二维码,
//                "login_token" "fd986f645eaac22ba512a69e4695c30d"  #login_token,
//                "staff_user_id" 1  #登陆者id
//        "user_name" "张三"  #登录者的名称,
//                "create_time" "2017-02-21 11:13:45"  #服务商注册时间
//   login_mobile  登录者手机号

    /**
     * 		"username": "admin",
     "name": "",
     "mobileLogin": true,
     "JSESSIONID": ""
     */

    public String username;
    public boolean mobileLogin;
    public String JSESSIONID;
    public String name;


    public String provider_id;

    public String address;
    public String thumb_image_url;
    public String service_tel;
    public String wxmp_qrcode;//公众号二维码
    public String wxpay_collection_qrcode;//服务商微信支付二维码,
    public String alipay_collection_qrcode;//服务商支付宝支付二维码,
    public String login_token;
    public String create_time;

    public String staff_user_id;
    public Date tokenExpireTime = new Date();// token过期时间(不在json范围内)
    public String login_mobile;
    public int is_initial_provider;//1，总店，0分店

    /**
     * 获取注册天数
     *
     * @return
     */
    public int getRegDays() {
        if (create_time == null || "".equalsIgnoreCase(create_time.trim())) {
            return 1;
        }
        Date createDate = DateUtil.getDateByFormat(create_time, DateUtil.YMDHMS);
        if (createDate == null) {
            return 1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createDate.getTime());

        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(System.currentTimeMillis());
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1 + 1;
    }
}
