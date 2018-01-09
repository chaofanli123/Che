package com.victor.che.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/3.
 */

public class UsercardNocome implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * mobile : 13021960148###手机号
     * user_name : ###用户名
     * car_plate_no : ###车牌号
     * inactived_day : 6$###未到店天数
     */

    private String mobile;
    private String user_name;
    private String car_plate_no;
    private String inactived_day;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCar_plate_no() {
        return car_plate_no;
    }

    public void setCar_plate_no(String car_plate_no) {
        this.car_plate_no = car_plate_no;
    }

    public String getInactived_day() {
        return inactived_day;
    }

    public void setInactived_day(String inactived_day) {
        this.inactived_day = inactived_day;
    }
}
