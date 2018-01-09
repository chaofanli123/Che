package com.victor.che.domain;

import java.io.Serializable;

/**
 * 订单赠品
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/20 0020 11:13
 */
public class OrderGift implements Serializable {
    //            "order_detail_id":57,
    //                    "goods_name":"测试",
    //                    "available_num":10,
    //                    "create_time":"2017-03-13 16:45:30###赠送时间"
    public String order_detail_id;
    public String goods_name;
    public String available_num;
    public String create_time;
}
