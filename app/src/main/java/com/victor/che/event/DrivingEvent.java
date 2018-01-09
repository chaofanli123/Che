package com.victor.che.event;

import com.victor.che.domain.Driving;

import java.io.File;

/**
 * 扫描车牌号事件
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/16 0016 16:08
 */
public class DrivingEvent {

    public Driving driving;

    public File file;

    public DrivingEvent() {
    }

    public DrivingEvent(Driving driving,File file) {
        this.driving=driving;
        this.file = file;
    }
}
