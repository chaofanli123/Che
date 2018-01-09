package com.victor.che.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 产品分类实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/4 0004 10:39
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;


    //    "goods_category_id": 1  #商品分类id,
    //            "name": "洗车"  #商品分类名称
    public String goods_category_id;
    public String name;

    public ArrayList<Product> goods;// 包含的产品

    public boolean checked = false;// 本地变量，是否被全选
}
