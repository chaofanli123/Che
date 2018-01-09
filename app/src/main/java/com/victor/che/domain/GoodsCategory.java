package com.victor.che.domain;

import java.io.Serializable;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/4 0004 12:24.
 */

public class GoodsCategory implements Serializable{
    //        "goods_category_name": "洗车"  #服务类别名称,
    //                "goods_category_id": 1  #服务类别id

    public String goods_category_name;
    public String goods_category_id;

    public String getGoods_category_name() {
        return goods_category_name;
    }

    public void setGoods_category_name(String goods_category_name) {
        this.goods_category_name = goods_category_name;
    }

    public String getGoods_category_id() {
        return goods_category_id;
    }

    public void setGoods_category_id(String goods_category_id) {
        this.goods_category_id = goods_category_id;
    }
}
