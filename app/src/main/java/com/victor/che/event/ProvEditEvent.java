package com.victor.che.event;

/**
 * 修改服务商信息事件
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 16:38
 */
public class ProvEditEvent {
    public String action;
    public String data;

    public ProvEditEvent() {
    }

    public ProvEditEvent(String action, String data) {
        this.action = action;
        this.data = data;
    }
}
