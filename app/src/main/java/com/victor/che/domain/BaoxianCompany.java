package com.victor.che.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/10.
 */

public class BaoxianCompany  implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * insurance_company_id : 1
     * name : 阳光保险
     * icon_thumb :
     */

    private int insurance_company_id;
    private String name;
    private String icon_thumb;
    public boolean checked = false;// 本地变量，是否被选中


    public int getInsurance_company_id() {
        return insurance_company_id;
    }

    public void setInsurance_company_id(int insurance_company_id) {
        this.insurance_company_id = insurance_company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_thumb() {
        return icon_thumb;
    }

    public void setIcon_thumb(String icon_thumb) {
        this.icon_thumb = icon_thumb;
    }
}
