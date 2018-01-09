package com.victor.che.event;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/13.
 */

public class MessageEvent {
    public static final int ALL_WISH_RELOAD = 0x0002;/*刷新全部愿望列表*/
    public static final int ALL_WISH_REFRESH = 0x0003;/*刷新局部愿望*/

    public int code;
    public Object object;
    public int position;

    public MessageEvent() {

    }

    public MessageEvent(int code) {
        this.code = code;
    }

    public MessageEvent(int code, Object object) {
        this.code = code;
        this.object = object;
    }

    public MessageEvent(int code,int position, Object object) {
        this.code = code;
        this.object = object;
        this.position=position;
    }
}
