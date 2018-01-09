package com.victor.che.event;

public class SearchEvent {
    public int currentPos = 0;//当前位置
    public String keywords = "";//搜索关键字

    public SearchEvent() {

    }

    public SearchEvent(String keywords, int currentPos) {
        this.keywords = keywords;
        this.currentPos = currentPos;
    }
}
