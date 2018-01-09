package com.victor.che.domain;

import java.io.Serializable;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/4 0004 12:24.
 */

public class Goods implements Serializable{
    //        "price": "15.00"  #商品原价单价,
    //                "sale_price": "12.00"  #商品售价,
    //                "actual_sale_price": "12.00"  #商品实际售价(修改后的价格),
    //        "card_num_price": 1  #商品需要支付的次数,
    //                "buy_num": 5  #购买数量,
    //                "goods_id": 1  #商品id,
    //                "goods_name": "商品2"  #商品名称
    public String price;
    public String sale_price;
    public String actual_sale_price;
    public int buy_num;
    public String  goods_id;
    public String goods_name;
    public int card_num_price;

    public int getCard_num_price() {
        return card_num_price;
    }

    public void setCard_num_price(int card_num_price) {
        this.card_num_price = card_num_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public int getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(int buy_num) {
        this.buy_num = buy_num;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getActual_sale_price() {
        return actual_sale_price;
    }

    public void setActual_sale_price(String actual_sale_price) {
        this.actual_sale_price = actual_sale_price;
    }
}