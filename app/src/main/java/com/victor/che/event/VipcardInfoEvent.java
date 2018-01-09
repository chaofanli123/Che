package com.victor.che.event;

import com.victor.che.domain.Vipcard;

import java.io.Serializable;

/**
 * 编辑会员卡信息事件
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/16 0016 10:32
 */
public class VipcardInfoEvent implements Serializable {
    public static final int EDIT_VIPCARD_NAME = 1;
    public static final int EDIT_VIPCARD_TYPE = 2;
    public static final int EDIT_REMAIN_VALUE = 3;
    public static final int EDIT_SALE_PRICE = 4;
    public static final int EDIT_ORIGINAL_PRICE = 5;
    public static final int EDIT_SERVICE_CONTENT = 6;

    public int action;//动作类型，1-名称；2-类型；3-可用次数；4-售价；5-原价；6-可用服务；
    public Vipcard data;//数据

    public VipcardInfoEvent() {
    }

    public VipcardInfoEvent(int action, Vipcard data) {
        this.action = action;
        this.data = data;
    }
}
