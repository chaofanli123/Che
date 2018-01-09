package com.victor.che.domain;

import java.io.Serializable;

/**
 * @autor Victor
 * @email 468034043@qq.com
 * @time 2017/4/13 0013 13:53.
 * 二手车估值记录实体类
 */

public class QueryUserCarHistory implements Serializable {

     /*"usedcar_assess_record_id": 1  #估值记录id,
            "car_plate_no": ""  #车牌号,
            "purchase_price": "15.00"  #购买价格,
            "deal_price": "18.00"  #售价,
            "city_name": "郑州市"  #城市名,
            "car_model_name": "奥迪SQ5(进口)"  #品牌车系名
            "create_time": "2017-04-14"  #创建时间*/
    public int usedcar_assess_record_id;
    public String car_plate_no;
    public String purchase_price;
    public String deal_price;
    public String city_name;
    public String car_model_name;
    public String create_time;

    public int getUsedcar_assess_record_id() {
        return usedcar_assess_record_id;
    }

    public void setUsedcar_assess_record_id(int usedcar_assess_record_id) {
        this.usedcar_assess_record_id = usedcar_assess_record_id;
    }

    public String getCar_plate_no() {
        return car_plate_no;
    }

    public void setCar_plate_no(String car_plate_no) {
        this.car_plate_no = car_plate_no;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getDeal_price() {
        return deal_price;
    }

    public void setDeal_price(String deal_price) {
        this.deal_price = deal_price;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCar_model_name() {
        return car_model_name;
    }

    public void setCar_model_name(String car_model_name) {
        this.car_model_name = car_model_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "QueryUserCarHistory{" +
                "usedcar_assess_record_id=" + usedcar_assess_record_id +
                ", car_plate_no='" + car_plate_no + '\'' +
                ", purchase_price='" + purchase_price + '\'' +
                ", deal_price='" + deal_price + '\'' +
                ", city_name='" + city_name + '\'' +
                ", car_model_name='" + car_model_name + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
