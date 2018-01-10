package com.victor.che.event;


/**
 * 选择车品牌和车系事件
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016/11/18 0018 9:53
 */
public class SelectCityEvent {
    public Region area_id;//车id
    public Region name;//车名

    public SelectCityEvent(Region area_id, Region name) {
        this.area_id = area_id;
        this.name = name;
    }

    public SelectCityEvent() {

    }
}
