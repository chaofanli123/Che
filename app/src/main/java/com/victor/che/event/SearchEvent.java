package com.victor.che.event;

public class SearchEvent {
    public int currentPos = 0;//当前位置
    public String keywords = "";//搜索标题
    public String type = "";//类型
    public String status = "";//状态

    public SearchEvent() {

    }

    public SearchEvent(String keywords,String type,String status, int currentPos) {
        this.keywords = keywords;
        this.currentPos = currentPos;
        this.type = type;
        this.status = status;
    }
}
