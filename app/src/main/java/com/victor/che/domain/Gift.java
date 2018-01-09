package com.victor.che.domain;

import java.io.Serializable;

/**
 * 赠品
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/17 0017 12:28
 */
public class Gift implements Serializable {
    public String name;
    public int count = 1;// 初始值为1

    public int selectCategoryPos = 0;//选中的分类
    public int selectProductPos = 0;//选中的产品

    public String categoryName = "";//选择的分类名称
    public String productName = "";//选择的产品名称
}
