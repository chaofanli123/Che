package com.victor.che.domain;

import java.io.Serializable;

/**
 * 消费实体
 *
 * @author Administrator
 */
public class Consume implements Serializable {

    private static final long serialVersionUID = 1L;
    // "buy_num":"1",
    // "total_price":"0.00",
    // "total_num":-1,
    // "total_eb":0
    public int buy_num;// 消费次数
    public double buy_eb;// 消费e点数
    public String total_num;// 剩余次数
    public String total_eb;// 剩余e点数
}
