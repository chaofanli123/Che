package com.victor.che.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/2.
 */

public class UsercardEnd implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     *
     "card_category_id": 1  #卡类别id， 1-次卡 2-余额卡,
     "num": 11  #可用次数,
     "money": "0"  #可用余额,
     "end_time": ""  #过期时间,
     "mobile": "13021960147"  #用户手机号,
     "user_name": ""  #用户名称,
     "car_plate_no": "豫A98300"  #默认车牌号,
     "card_name": "测试"  #卡名称
     */
    private int card_category_id;
    private int num;
    private String money;
    private String end_time;
    private String mobile;
    private String user_name;
    private String car_plate_no;
    private String card_name;

    public int getCard_category_id() {
        return card_category_id;
    }

    public void setCard_category_id(int card_category_id) {
        this.card_category_id = card_category_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

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

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }
}
