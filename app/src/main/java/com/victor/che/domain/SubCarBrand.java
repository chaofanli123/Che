package com.victor.che.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 汽车子品牌实体
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月22日 下午9:04:37
 */
public class SubCarBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    //    "car_brand_series_id":248,
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
    public String car_brand_series_id; // 子品牌ID
    public String name;// 子品牌名称
    public List<CarSeries> series_version = new ArrayList<CarSeries>();// 子品牌车系列表

    /**
     * 获取该品牌所有的车系
     *
     * @return
     */
    public List<CarSeries> getCarSeries() {
        return series_version;
    }

}
