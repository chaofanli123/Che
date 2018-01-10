package com.victor.che.domain;

import java.io.Serializable;

/**
 * 优惠券实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 16:10
 */
public class Coupon implements Serializable{
//// {
//    "coupons":
//            [
//    {
//        "coupon_grant_record_id": 3  #用户优惠券id,
//            "name": "test"  #优惠券名称,
//            "money": "10.00"  #优惠券面值,
//            "description": "2222"  #优惠券描述,
//            "full_money": "1.00"  #满多少可用，为0.00表示不限制,
//            "available_start_time": "2017-06-06"  #可使用开始日期,
//            "available_end_time": ""  #可使用结束日期，为空表示不限制
//    }
//    ],
//            "useful_count": 3  #可用优惠券总数,
//            "unuseful_count": 0  #不可用总数
//}
        /**
         * coupon_grant_record_id : 3
         * name : test
         * money : 10.00
         * description : 2222
         * full_money : 1.00
         * available_start_time : 2017-06-06
         * available_end_time :
         */
        private int coupon_grant_record_id;
    public  int coupon_id;  //赠送优惠券时候的id
        private String name;
        private String money;
        private String description;
        private String full_money;
        private String available_start_time;
        private String available_end_time;

    public boolean checked = false;// 本地变量，是否被选中
    public int select; //1,是赠送，0是已赠送
        public int getCoupon_grant_record_id() {
            return coupon_grant_record_id;
        }

        public void setCoupon_grant_record_id(int coupon_grant_record_id) {
            this.coupon_grant_record_id = coupon_grant_record_id;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFull_money() {
            return full_money;
        }

        public void setFull_money(String full_money) {
            this.full_money = full_money;
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
}
