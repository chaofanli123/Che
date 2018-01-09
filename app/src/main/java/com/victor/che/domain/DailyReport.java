package com.victor.che.domain;

import java.io.Serializable;

/**
 * 日报数据实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/17 0017 17:40
 */
public class DailyReport implements Serializable {
    //    "goods_category_name": "洗车"  #服务项目名称,
    //            "amount": "60.00"  #营业额
    public String goods_category_name;
    public double amount;
}
