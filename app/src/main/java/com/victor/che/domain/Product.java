package com.victor.che.domain;

import com.victor.che.util.MathUtil;

import java.io.Serializable;

/**
 * 产品实体
 */
public class Product implements Serializable {
    //    "goods_id": 1  #商品id,
    //            "name": "商品1"  #商品名称,
    //            "price": "12.00"  #商品原价,\
    //            "sale_price": "10.00"  #商品售价,
    //            "card_num_price": 1  #商品需要支付的次数
    public String goods_id;
    public String name;
    public double price;
    public double sale_price;
    public int card_num_price;

    public double act_price;
    public String act_begin_time;
    public String act_end_time;

    public boolean checked = false;// 本地变量，是否被选中

    public String getSalePrice() {
        return MathUtil.getMoneyText(sale_price);
    }

    public String getPrice() {
        return MathUtil.getMoneyText(price);
    }

}
