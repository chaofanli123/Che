package com.victor.che.domain;

import java.io.Serializable;

/**
 * 报表数据实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 10:52
 */
public class Reports implements Serializable {
    //    "date": "2017-01-06",
    //            "count": 0,
    //            "amount": 0
    public String date;//日期
    public double amount;//交易额
    public String count;//客户数量
}
