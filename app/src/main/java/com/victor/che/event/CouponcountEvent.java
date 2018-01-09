package com.victor.che.event;

public class CouponcountEvent {
    public String useful_count ;//当前位置
    public String unuseful_count;//搜索关键字

    public CouponcountEvent() {

    }

    public CouponcountEvent(String useful_count, String unuseful_count) {
        this.useful_count = useful_count;
        this.unuseful_count = unuseful_count;
    }
}
