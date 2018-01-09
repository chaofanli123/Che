package com.victor.che.event;

import com.victor.che.domain.BaoxianStyle;

public class ToubaoxiugaiEvent {
    public boolean ison_off;
    public int positon;
    public BaoxianStyle baoxianStyle;

    public ToubaoxiugaiEvent() {

    }

    public ToubaoxiugaiEvent(BaoxianStyle baoxianStyle, boolean ison_off,int positon) {
        this.baoxianStyle = baoxianStyle;
        this.ison_off = ison_off;
        this.positon=positon;
    }
}
