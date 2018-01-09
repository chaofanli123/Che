package com.victor.che.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 车辆实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 16:10
 */
public class Car implements Serializable {
    //    "provider_user_car_id": 1  #用户车辆id,
    //            "car_plate_no": "豫A98309"  #车牌号,
    //            "is_default": 1  #是否是默认车辆 1-默认车辆,
    //            "car_brand_series": "S5 Coupe"  #车辆品牌型号名,
    //            "image": ""  #车品牌图片,
    //            "car_brand_name": ""  #车品牌名称,
    //            "car_series_name": "S5 Coupe"  #车车系名称
    //    "car_brand_id": ""  #1车品牌id,
    //            "car_brand_series_id": "2"  #车车系id
    //      "mobile": "13021960147"  #用户手机号

/*    {
        "car_plate_no": "豫A98309"  #车牌号,
            "image": ""  #车品牌图标,
            "car_brand_series": "S5 Coupe"  #车型号,
            "license_img": ""  #行驶证原图
    }*/
    public String provider_user_car_id;
    public String car_plate_no;
    public String is_default;
    public String car_brand_series;
    public String image;
    public String car_brand_name;
    public String car_series_name;
    public String car_brand_id;
    public String car_brand_series_id;
    public String provider_user_id;
    public String car_brand_image;
    public String mobile;
    public String license_img;

}
