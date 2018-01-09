package com.victor.che.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/2 0002 17:43.
 */

public class Pending implements Serializable {
   /* "cart_id": 1  #购物车id,
            "sale_user_id": 1  #服务师傅id,
            "mobile": "13021960148"  #手机号,
            "car_plate_no": "豫A90909"  #车牌号,
            "total_price": "220.00"  #总价,
            "mileage": "10.00"  #行驶里程,
            "next_maintain": "2017-04-27 00:00:00"  #下次保养时间,
            "create_time": "2017-04-28 09:16:04"  #添加时间,
            "detail":
            [
    {
        "sale_price": "12.00"  #售价,
            "buy_num": 5  #购买数量,
            "goods_category_name": "洗车"  #服务类别名称,
            "goods_name": "商品2"  #商品名称
    }*/
    public int cart_id;
    public int sale_user_id;
    public String mobile;
    public String car_plate_no;
    public String total_price;
    public String mileage;
    public String next_maintain;
    public String create_time;

    public List<Shoppingcar> detail;

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getSale_user_id() {
        return sale_user_id;
    }

    public void setSale_user_id(int sale_user_id) {
        this.sale_user_id = sale_user_id;
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

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getNext_maintain() {
        return next_maintain;
    }

    public void setNext_maintain(String next_maintain) {
        this.next_maintain = next_maintain;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<Shoppingcar> getDetail() {
        return detail;
    }

    public void setDetail(List<Shoppingcar> detail) {
        this.detail = detail;
    }


}
