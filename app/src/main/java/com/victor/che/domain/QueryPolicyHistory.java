package com.victor.che.domain;

import java.io.Serializable;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/11 0011 10:43.
 * 出险记录实体类
 */

public class QueryPolicyHistory implements Serializable{

//     "insurance_quote_id": 20  #报价id,
//    "owner_name": "11"  #车主姓名,
//            "name": "test"  #姓名,
//            "mobile": "13021909909"  #手机号,
//            "address": "ddddd"  #地址,
//            "status": 1  #状态, 1-待支付 2-出保中 3-已出保,
//            "car_plate_no": "豫A12345"  #车牌号,
//            "license_brand_model": "111"  #车辆型号,
//            "total_price": "0.00"  #总保费,
//            "discount": "0.00"  #折扣,
//            "actual_total_price": "0.00"  #实付金额,
//            "insurance_company_name": "人保车险"  #保险公司名称,
//            "insurance_company_icon": "http://image.cheweifang.cn/"  #保险公司图标

    public int insurance_quote_id;
    public String owner_name;
    public String name;
    public String mobile;
    public String address;
    public int status;
    public String car_plate_no;
    public String license_brand_model;
    public String total_price;
    public String discount;
    public String actual_total_price;
    public String insurance_company_name;
    public String insurance_company_icon;

    public int getInsurance_quote_id() {
        return insurance_quote_id;
    }

    public void setInsurance_quote_id(int insurance_quote_id) {
        this.insurance_quote_id = insurance_quote_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCar_plate_no() {
        return car_plate_no;
    }

    public void setCar_plate_no(String car_plate_no) {
        this.car_plate_no = car_plate_no;
    }

    public String getLicense_brand_model() {
        return license_brand_model;
    }

    public void setLicense_brand_model(String license_brand_model) {
        this.license_brand_model = license_brand_model;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getActual_total_price() {
        return actual_total_price;
    }

    public void setActual_total_price(String actual_total_price) {
        this.actual_total_price = actual_total_price;
    }

    public String getInsurance_company_name() {
        return insurance_company_name;
    }

    public void setInsurance_company_name(String insurance_company_name) {
        this.insurance_company_name = insurance_company_name;
    }

    public String getInsurance_company_icon() {
        return insurance_company_icon;
    }

    public void setInsurance_company_icon(String insurance_company_icon) {
        this.insurance_company_icon = insurance_company_icon;
    }

    @Override
    public String toString() {
        return "QueryPolicyHistory{" +
                "insurance_quote_id=" + insurance_quote_id +
                ", owner_name='" + owner_name + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", car_plate_no='" + car_plate_no + '\'' +
                ", license_brand_model='" + license_brand_model + '\'' +
                ", total_price='" + total_price + '\'' +
                ", discount='" + discount + '\'' +
                ", actual_total_price='" + actual_total_price + '\'' +
                ", insurance_company_name='" + insurance_company_name + '\'' +
                ", insurance_company_icon='" + insurance_company_icon + '\'' +
                '}';
    }
}
