package com.victor.che.event;

import com.victor.che.domain.Xingshizheng;

public class XingshizhengEvent {
   public Xingshizheng xingshizheng ;//需要提交的信息

    public XingshizhengEvent() {

    }
    public XingshizhengEvent(Xingshizheng xingshizheng) {
        this.xingshizheng = xingshizheng;
    }
}
