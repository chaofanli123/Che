package com.victor.che.domain;

/**
 * @autor Victor
 * @email 468034043@qq.com
 * @time 2017/4/17 0017 10:37.
 */

public class UserCarDetail {
    /*"used_date": "2017-01"  #上牌时间,
            "mileage": "0.00"  #行驶里程,
            "purchase_price": "15.00"  #购买价格,
            "deal_price": "18.00"  #出售价格,
            "city_name": "郑州市"  #城市名称,
            "car_model_name": "奥迪SQ5(进口) 2014款 SQ5 3.0TFSI quattro"  #车型名称,
            "purpose_text": "自用"  #用途,
            "car_status_text": "一般"  #车况*/
    public String used_date;
    public String mileage;
    public String purchase_price;
    public String buy_price;
    public String deal_price;
    public String city_name;
    public String car_model_name;
    public String purpose_text;
    public String car_status_text;

    public String getUsed_date() {
        return used_date;
    }

    public void setUsed_date(String used_date) {
        this.used_date = used_date;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
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

    public String getPurpose_text() {
        return purpose_text;
    }

    public void setPurpose_text(String purpose_text) {
        this.purpose_text = purpose_text;
    }

    public String getCar_status_text() {
        return car_status_text;
    }

    public void setCar_status_text(String car_status_text) {
        this.car_status_text = car_status_text;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    @Override
    public String toString() {
        return "UserCarDetail{" +
                "used_date='" + used_date + '\'' +
                ", mileage='" + mileage + '\'' +
                ", purchase_price='" + purchase_price + '\'' +
                ", buy_price='" + buy_price + '\'' +
                ", deal_price='" + deal_price + '\'' +
                ", city_name='" + city_name + '\'' +
                ", car_model_name='" + car_model_name + '\'' +
                ", purpose_text='" + purpose_text + '\'' +
                ", car_status_text='" + car_status_text + '\'' +
                '}';
    }
}
