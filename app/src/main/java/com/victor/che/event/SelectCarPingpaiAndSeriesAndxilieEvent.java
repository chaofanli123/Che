package com.victor.che.event;


        import com.victor.che.domain.CarChexi;
        import com.victor.che.domain.CarChexing;
        import com.victor.che.domain.CarPingpai;

/**
 * 选择车品牌和车系事件
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016/11/18 0018 9:53
 */
public class SelectCarPingpaiAndSeriesAndxilieEvent {
    public CarPingpai carBrand;//车品牌
    public CarChexi carSeries;//车系
    public CarChexing chexing;//车型

    public SelectCarPingpaiAndSeriesAndxilieEvent(CarPingpai carBrand, CarChexi carSeries,CarChexing chexing) {
        this.carBrand = carBrand;
        this.carSeries = carSeries;
        this.chexing=chexing;
    }

    public SelectCarPingpaiAndSeriesAndxilieEvent() {
    }
}
