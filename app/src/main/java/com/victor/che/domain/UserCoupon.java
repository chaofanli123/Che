package com.victor.che.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/22.
 */

public class UserCoupon implements Serializable {

    /**
     *   [
     {
     "coupon_grant_record_id": 3  #用户优惠券id,
     "coupon_record_no": "00166593"  #用户优惠券编码,
     "name": "test"  #优惠券名称,
     "money": "10.00"  #优惠券面值,
     "available_start_time": "2017-06-06"  #可使用开始日期,
     "available_end_time": ""  #可使用结束日期，为空表示不限制,
     "mobile": "18790522222"  #用户手机号,
     "car_plate_no": "豫GME000"  #用户默认车牌号,
     "status": 0  #使用状态 0-待使用 1-已使用
     }
     ]
     */

    private int coupon_grant_record_id;
    private String coupon_record_no;
    private String name;
    private String money;
    private String available_start_time;
    private String available_end_time;
    private String mobile;
    private String car_plate_no;
    private int status;

    public int getCoupon_grant_record_id() {
        return coupon_grant_record_id;
    }

    public void setCoupon_grant_record_id(int coupon_grant_record_id) {
        this.coupon_grant_record_id = coupon_grant_record_id;
    }

    public String getCoupon_record_no() {
        return coupon_record_no;
    }

    public void setCoupon_record_no(String coupon_record_no) {
        this.coupon_record_no = coupon_record_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAvailable_start_time() {
        return available_start_time;
    }

    public void setAvailable_start_time(String available_start_time) {
        this.available_start_time = available_start_time;
    }

    public String getAvailable_end_time() {
        return available_end_time;
    }

    public void setAvailable_end_time(String available_end_time) {
        this.available_end_time = available_end_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCar_plate_no() {
        return car_plate_no;
    }

    public void setCar_plate_no(String car_plate_no) {
        this.car_plate_no = car_plate_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
