package com.victor.che.event;

import com.victor.che.domain.Gift;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择赠品事件
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/16 0016 16:08
 */
public class GiftEvent {
    public String gift_value;//赠送次数或余额
    public String gift_service;//赠送的服务（赠品）
    public String gift_text;//赠送的文本显示

    public ArrayList<Gift> giftList;//选择的赠品集合

    public GiftEvent() {
    }

    public GiftEvent(String gift_value, String gift_service, String gift_text) {
        this.gift_value = gift_value;
        this.gift_service = gift_service;
        this.gift_text = gift_text;
    }

    @Override
    public String toString() {
        return "GiftEvent{" +
                "gift_value='" + gift_value + '\'' +
                ", gift_service='" + gift_service + '\'' +
                ", gift_text='" + gift_text + '\'' +
                '}';
    }
}
