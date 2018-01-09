package com.victor.che.event;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/13.
 */

public class PayMessage implements Serializable {
    private static final long serialVersionUID = 1L;
//    "appid": "wxff87f02dac93ce58",
//            "partnerid": "1455289802",
//            "prepayid": "wx20170413100830527676b8370455229601",
//            "package": "Sign=WXPay",
//            "noncestr": "zdge1o0lvp50reawalzqotxy1grzxzl2",
//            "timestamp": 1492049540,
//            "sign": "BB1E7125D938BA9C556DEBF910F2A6E8"

    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    private String sign;
    @JSONField(name = "package")
    private String packageValue;

}
