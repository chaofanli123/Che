package com.victor.che.event;


import com.victor.che.domain.Channel;
import com.victor.che.domain.OrderCategory;

/**
 * 筛选支付方式，日期，订单类型，服务类别
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016/11/18 0018 9:53
 */
public class ScreenStyleResultEvent {
    public OrderCategory order;//订单
    public Channel server;//服务
    public String time;
    public String Paystyle;

    public ScreenStyleResultEvent(OrderCategory order, Channel server, String time, String paystyle) {
        this.order = order;
        this.server = server;
        this.time = time;
        Paystyle = paystyle;
    }
    public ScreenStyleResultEvent() {

    }
}
