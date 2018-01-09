package com.victor.che.domain;

import java.io.Serializable;

/**
 * 车型实体
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月22日 下午9:24:51
 */
public class CarChexing implements Serializable {

    private static final long serialVersionUID = 1L;

    //    "brand_series_id":"20000057",
    //            "name":"奥迪A4"
    public String car_model_id;// 车型id
    public String name;// 车型名

    public String getBrand_series_id() {
        return car_model_id;
    }

    public void setBrand_series_id(String brand_series_id) {
        this.car_model_id = brand_series_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarChexing() {
        super();
    }

    public CarChexing(String brand_series_id, String name) {
        super();
        this.car_model_id = brand_series_id;
        this.name = name;
    }

}
