package com.victor.che.domain;

import java.io.Serializable;

/**
 * 用户车辆实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 16:10
 */
public class UserCar implements Serializable {
    private static final long serialVersionUID = 1L;
//    "provider_user_car_id": 1  #用户车辆id,
//            "car_plate_no": "豫A98309"  #车牌号,
//            "is_default": 1  #是否是默认车辆 1-默认车辆,
//            "car_brand_series": "S5 Coupe"  #车辆品牌型号名,
//            "image": ""  #车品牌图片,
//            "car_brand_name": ""  #车品牌名称,
//            "car_series_name": "S5 Coupe"  #车车系名称,
//            "car_brand_id": ""  #1车品牌id,
//            "car_brand_series_id": "2"  #车车系id
    public int provider_user_car_id;
    public String car_plate_no;
    public int is_default;
    public String car_brand_series;
    public String image;
    public String car_brand_name;
    public String car_series_name;
    public String car_brand_id;
    public String car_brand_series_id;



    public int getProvider_user_car_id() {
        return provider_user_car_id;
    }

    public void setProvider_user_car_id(int provider_user_car_id) {
        this.provider_user_car_id = provider_user_car_id;
    }

    public String getCar_plate_no() {
        return car_plate_no;
    }

    public void setCar_plate_no(String car_plate_no) {
        this.car_plate_no = car_plate_no;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getCar_brand_series() {
        return car_brand_series;
    }

    public void setCar_brand_series(String car_brand_series) {
        this.car_brand_series = car_brand_series;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCar_brand_name() {
        return car_brand_name;
    }

    public void setCar_brand_name(String car_brand_name) {
        this.car_brand_name = car_brand_name;
    }

    public String getCar_series_name() {
        return car_series_name;
    }

    public void setCar_series_name(String car_series_name) {
        this.car_series_name = car_series_name;
    }

    public String getCar_brand_id() {
        return car_brand_id;
    }

    public void setCar_brand_id(String car_brand_id) {
        this.car_brand_id = car_brand_id;
    }

    public String getCar_brand_series_id() {
        return car_brand_series_id;
    }

    public void setCar_brand_series_id(String car_brand_series_id) {
        this.car_brand_series_id = car_brand_series_id;
    }
}
