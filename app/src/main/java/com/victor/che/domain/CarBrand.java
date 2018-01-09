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
public class CarBrand implements Serializable, Comparable<CarBrand>, IndexableEntity {

    private static final long serialVersionUID = 1L;
    //    "car_brand_series_id": 165  #品牌id,
    //            "name": "中欧"  #品牌名称,
    //            "image": "http://image.cheweifang.cn/parentbrand/zhongou.jpg"  #品牌图片,
    //            "first_letter": "Z"  #首字母
    public String car_brand_series_id;
    public String name;// 汽车品牌名称
    public String image;// 在线logo
    public String first_letter;// 首字母


    public CarBrand() {
        super();
    }

    public CarBrand(String car_brand_series_id, String brand_name, String firstLetter) {
        super();
        this.car_brand_series_id = car_brand_series_id;
        this.name = brand_name;
        this.first_letter = firstLetter;
    }

    /**
     * 重写排序规则
     */
    @Override
    public int compareTo(CarBrand another) {
        if ("@".equals(first_letter) || "#".equals(another.first_letter)) {
            return -1;
        } else if ("#".equals(first_letter) || "@".equals(another.first_letter)) {
            return 1;
        } else {
            return first_letter.compareTo(another.first_letter);
        }
    }

    /**
     * 排序的字段
     *
     * @return
     */
    @Override
    public String getFieldIndexBy() {
        return first_letter;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.first_letter = indexField;
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
