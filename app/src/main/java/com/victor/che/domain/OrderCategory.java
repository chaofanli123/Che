package com.victor.che.domain;

import java.io.Serializable;

/**
 * 订单类型实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/16 0016 16:51
 */
public class OrderCategory implements Serializable {

    //    "order_category_id": 1  #订单分类id,
    //            "name": "活动"  #订单分类名称
    public int order_category_id;
    public String name;
}
