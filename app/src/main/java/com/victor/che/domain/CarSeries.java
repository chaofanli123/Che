package com.victor.che.domain;

import java.io.Serializable;

/**
 * 车系实体
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月22日 下午9:24:51
 */
public class CarSeries implements Serializable {

    private static final long serialVersionUID = 1L;

    //    "car_brand_series_id":250,
    //            "pid":248,
    //            "name":"ALFA 156"
    public String car_brand_series_id;// 车系id
    public String pid;
    public String name;// 车系名

    public CarSeries() {
        super();
    }

    public CarSeries(String series_id, String series_name) {
        super();
        this.car_brand_series_id = series_id;
        this.name = series_name;
    }

}
