package com.victor.che.domain;

import java.io.Serializable;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * 汽车品牌实体
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016/11/16 0016
 */
public class CarPingpai implements Serializable,IndexableEntity {

    private static final long serialVersionUID = 1L;
//    "brand_series_id": "2000386"  #品牌车系车型d,
//            "name": "AC",
//            "image_src": "http://image.cheweifang.cn"  #品牌图标
    public String brand_series_id;
    public String name;// 汽车品牌名称
    public String image_src;// 在线logo


    public CarPingpai() {
        super();
    }

    public CarPingpai(String car_brand_series_id, String brand_name, String image_src) {
        super();
        this.brand_series_id = car_brand_series_id;
        this.name = brand_name;
        this.image_src = image_src;
    }

    /**
     * 排序的字段
     *
     * @return
     */
    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField;
    }

    /**
     * 保存排序field的拼音,在执行比如搜索等功能时有用 （若不需要，空实现该方法即可）
     *
     * @param pinyin
     */
    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
    }
}
