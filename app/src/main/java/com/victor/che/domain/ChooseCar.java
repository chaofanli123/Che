package com.victor.che.domain;

import java.io.Serializable;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/4/27 0027 16:05.
 * 选择车辆实体类
 */

public class ChooseCar implements Serializable {
      /*"car_plate_no": "豫A12345"  #车牌号,
            "car_brand_series": "安驰奥迪"  #车品牌车系名称,
            "mileage": "未记录"  #行驶里程,
            "next_maintain": "未记录"  #下次保养时间*/

      public String car_plate_no;
    public String car_brand_series;
    public String mileage;
    public String next_maintain;

    public String getCar_plate_no() {
        return car_plate_no;
    }

    public void setCar_plate_no(String car_plate_no) {
        this.car_plate_no = car_plate_no;
    }

    public String getCar_brand_series() {
        return car_brand_series;
    }

    public void setCar_brand_series(String car_brand_series) {
        this.car_brand_series = car_brand_series;
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

    @Override
    public String toString() {
        return "ChooseCar{" +
                "car_plate_no='" + car_plate_no + '\'' +
                ", car_brand_series='" + car_brand_series + '\'' +
                ", mileage='" + mileage + '\'' +
                ", next_maintain='" + next_maintain + '\'' +
                '}';
    }
}
