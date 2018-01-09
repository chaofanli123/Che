package com.victor.che.domain;

import java.io.Serializable;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/3 0003 9:12.
 */

public class Shoppingcar implements Serializable{

    public Goods goods;
    public GoodsCategory goods_category;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsCategory getGoods_category() {
        return goods_category;
    }

    public void setGoods_category(GoodsCategory goods_category) {
        this.goods_category = goods_category;
    }
}
