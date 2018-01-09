package com.victor.che.event;

import java.io.Serializable;

/**
 * RecyclerView某一项改变
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/6 0006 10:01
 */
public class RecyclerViewItemChangedEvent {

    public int position;// 修改的位置
    public Serializable obj;//修改的实体

    public RecyclerViewItemChangedEvent(int mPosition, Serializable obj) {
        this.position = mPosition;
        this.obj = obj;
    }

    public RecyclerViewItemChangedEvent() {
    }
}
