package com.victor.che.event;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改服务商信息事件
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 16:38
 */
public class CouponEvent {
    public List<String> selectID = new ArrayList<>();
    public Double total_price;

    public CouponEvent() {
    }

    public CouponEvent(Double total_price,List<String> selectID) {
        this.total_price = total_price;
        this.selectID = selectID;
    }
}
