package com.victor.che.domain;

import java.io.Serializable;

/**
 * 支付字符串实体
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年8月10日 上午11:47:59
 */
public class PayStr implements Serializable {

    private static final long serialVersionUID = 1L;
    //	{
    //		"out_trade_no": "2016120215285266666635394",
    //			"pay_type": "wxpay",
    //			"wxpaystr": {
    //		"appid": "wx602db28caf75356c",
    //				"partnerid": "1383995202",
    //				"prepayid": "wx2016120215285642d24a7d850800910499",
    //				"package": "Sign=WXPay",
    //				"noncestr": "jssf0sewd36du9iz5g5lgk93i6ngrljv",
    //				"timestamp": 1480663736,
    //				"sign": "AE94DD9C515A8D9DD6A6A6A257ACE7B0"
    //	},
    //		"subject": "0.01元玻璃水活动",
    //			"body": "0.01元玻璃水活动",
    //			"total_fee": 1,
    //			"save_amount": "0.00",
    //			"result_url": "http://mtest.cheweifang.cn/appweb/glass_water/glass_water_authcode.php?mobile=13253685958&area_code=370100",
    //			"confirm_url": "http://mtest.cheweifang.cn/appweb/glass_water/pay_confirm.php?1=1"
    //	}


    public String out_trade_no;// 商户订单号
    public String pay_type;// 支付方式， wxpay 微信支付 alipay 支付宝支付
    public String wxpaystr;// 微信支付字符串(微信专用)
    public String subject;// 支付宝支付相关的参数
    public String body;// 支付宝支付相关的参数
    public double total_fee;// 支付宝支付相关的参数
    public double save_amount;// 节省多少钱
    public String result_url;// 支付结果界面的url
    public String confirm_url;// 支付返回结果 确认url

}
