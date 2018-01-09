package com.victor.che.domain;

/**
 * Created by Administrator on 2017/5/11.
 */

public class XianzhongMessage {

//    "insurance_category_id": 3  #险种id,
//            "name": "车船税险"  #险种名称,
//            "is_free": 0  #是否不计免陪 0：否；1：是,
//            "is_coverage": 1  #是否枚举可选保额。0：否；1：是,
//            "coverage":
//            [
//            "20",
//            "50"
//            ]
  //  险种信息，数据格式： 方案序号_车险id_是否免赔_保额，多个用逗号分割 eg: 1_1_1_20,1_2_0_0
    private int position;
    private int  insurance_category_id;
    private int is_free;
    private String coverage;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getInsurance_category_id() {
        return insurance_category_id;
    }

    public void setInsurance_category_id(int insurance_category_id) {
        this.insurance_category_id = insurance_category_id;
    }

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }
}
