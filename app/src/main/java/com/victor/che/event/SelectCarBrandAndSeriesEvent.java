package com.victor.che.event;


import com.victor.che.domain.CarBrand;
import com.victor.che.domain.CarSeries;

/**
 * 选择车品牌和车系事件
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016/11/18 0018 9:53
 */
public class SelectCarBrandAndSeriesEvent {
    public CarBrand carBrand;//车品牌
    public CarSeries carSeries;//车系

    public SelectCarBrandAndSeriesEvent(CarBrand carBrand, CarSeries carSeries) {
        this.carBrand = carBrand;
        this.carSeries = carSeries;
    }

    public SelectCarBrandAndSeriesEvent() {

    }
}
