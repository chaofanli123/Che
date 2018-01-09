package com.victor.che.event;

public class ProductEvent {
    public int currentPos = 0;//当前位置
    public int status = 1;//0-下架 1-在售 2-全部（为了支持旧版，新版必须传）

    public ProductEvent() {

    }

    public ProductEvent(int status) {
        this.status = status;
    }
}
