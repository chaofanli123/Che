package com.victor.che.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 * 商家优惠券
 */
public class ShopsCoupon implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * coupon_id : 20
     * name : 美容选用
     * money : 30.00
     * full_money : 100.00
     * description : 用于美容车辆
     * grant_start_time : 2017-06-11 10:15:00
     * grant_end_time : 2017-06-30 10:15:00
     * num : 21
     * expire_day : 30
     * limit_num_type : 0
     * status : 1
     * all_service : {"used_service_text":"全部洗车,","goods_id":[],"goods_category_id":[1]}
     * is_grant : 1
     *
     *  "num": 0,
       "available_num": 0,
     */

    private int coupon_id;
    private String name;
    private String money;
    private String full_money;
    private String description;
    private String grant_start_time;
    private String grant_end_time;
    private int num;
    private int expire_day;
    private int limit_num_type;
    private int status;
    private AllServiceBean all_service;
    private int is_grant;
    public int available_num;


    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
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

    public String getFull_money() {
        return full_money;
    }

    public void setFull_money(String full_money) {
        this.full_money = full_money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGrant_start_time() {
        return grant_start_time;
    }

    public void setGrant_start_time(String grant_start_time) {
        this.grant_start_time = grant_start_time;
    }

    public String getGrant_end_time() {
        return grant_end_time;
    }

    public void setGrant_end_time(String grant_end_time) {
        this.grant_end_time = grant_end_time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getExpire_day() {
        return expire_day;
    }

    public void setExpire_day(int expire_day) {
        this.expire_day = expire_day;
    }

    public int getLimit_num_type() {
        return limit_num_type;
    }

    public void setLimit_num_type(int limit_num_type) {
        this.limit_num_type = limit_num_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AllServiceBean getAll_service() {
        return all_service;
    }

    public void setAll_service(AllServiceBean all_service) {
        this.all_service = all_service;
    }

    public int getIs_grant() {
        return is_grant;
    }

    public void setIs_grant(int is_grant) {
        this.is_grant = is_grant;
    }

    public static class AllServiceBean implements Serializable{
        /**
         * used_service_text : 全部洗车,
         * goods_id : []
         * goods_category_id : [1]
         */
        private String used_service_text;
        private List<Integer> goods_id;
        private List<Integer> goods_category_id;

        public String getUsed_service_text() {
            return used_service_text;
        }

        public void setUsed_service_text(String used_service_text) {
            this.used_service_text = used_service_text;
        }

        public List<Integer> getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(List<Integer> goods_id) {
            this.goods_id = goods_id;
        }

        public List<Integer> getGoods_category_id() {
            return goods_category_id;
        }

        public void setGoods_category_id(List<Integer> goods_category_id) {
            this.goods_category_id = goods_category_id;
        }
    }
}
