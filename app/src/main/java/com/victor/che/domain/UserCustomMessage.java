package com.victor.che.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/2.
 */

public class UserCustomMessage  implements Serializable{

/*
    "user_track_id": 2  #列表id,
            "max_value": 60  #区间最大值,
            "min_value": 20  #区间最小值,
            "unit": "天"  #区间单位*/
    private static final long serialVersionUID = 1L;
    private int user_track_id;
    private int max_value;
    private int min_value;
    private String unit;

    public int getUser_track_id() {
        return user_track_id;
    }

    public void setUser_track_id(int user_track_id) {
        this.user_track_id = user_track_id;
    }

    public int getMax_value() {
        return max_value;
    }

    public void setMax_value(int max_value) {
        this.max_value = max_value;
    }

    public int getMin_value() {
        return min_value;
    }

    public void setMin_value(int min_value) {
        this.min_value = min_value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
