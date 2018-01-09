package com.victor.che.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 二手车估值汽车子品牌实体
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月22日 下午9:04:37
 */
public class SubCardata implements Serializable {

    private static final long serialVersionUID = 1L;

    //     "name": "宝龙汽车",
    //            "name":"阿尔法-罗密欧",
    //            "series_version":[
    //    {
    //        "car_brand_series_id":251,
    //            "pid":248,
    //            "name":"ALFA GT"
    //    },
    //    {
    //        "car_brand_series_id":250,
    //            "pid":248,
    //            "name":"ALFA 156"
    //    },
    //    {
    //        "car_brand_series_id":249,
    //            "pid":248,
    //            "name":"ALFA 147"
    //    }
    //    ]
    public String sale_year;// 子品牌名称
    public List<CarChexing> car_models = new ArrayList<CarChexing>();// 子品牌车系列表

    /**
     * 获取该品牌所有的车系
     *
     * @return
     */
    public List<CarChexing> getCarSeries() {
        return car_models;
    }

}
