package com.victor.che.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员卡可用服务实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/21 0021 10:51
 */
public class AllService implements Serializable {
    //                "used_service_text": "商品测试,商品2,美容,",
    //                        "goods_id":
    //                        [
    //                        1,
    //                        2
    //                        ],
    //                        "goods_category_id":
    //                        [
    //                        2
    //                        ]
    public String used_service_text;
    public List<String> goods_id = new ArrayList<>();
    public List<String> goods_category_id = new ArrayList<>();
}
