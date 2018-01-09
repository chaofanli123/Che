package com.victor.che.domain;

import java.io.Serializable;

/**
 * 保险查询历史实体类
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 1729
 */
public class QueryBaoxianHistory implements Serializable {


//                    "insurance_query_id": 3  #记录列表id,
//            "name": "许"  #姓名,
//    "mobile": "13098909090"  #手机号,
//            "car_plate_no": "豫A12345"  #车牌号,
//            "license_brand_model": "22222"  #品牌车型,
//            "create_time": "2016-04-30 00:00:00"  #创建时间,
//            "status": 3  #报价状态 1-报价中 2-已报价 3,4,5-报价失败,
//            "fail_reason": "强制险还在保期"  #报价失败原因,
//            "remark": ""  #备注

    public int insurance_query_id;
    public String name;
    public String mobile;
    public String car_plate_no;
    public String license_brand_model;
    public String create_time;
    public int status;
    public String fail_reason;
    public String remark;

    public int getInsurance_query_id() {
        return insurance_query_id;
    }

    public void setInsurance_query_id(int insurance_query_id) {
        this.insurance_query_id = insurance_query_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCar_plate_no() {
        return car_plate_no;
    }

    public void setCar_plate_no(String car_plate_no) {
        this.car_plate_no = car_plate_no;
    }

    public String getLicense_brand_model() {
        return license_brand_model;
    }

    public void setLicense_brand_model(String license_brand_model) {
        this.license_brand_model = license_brand_model;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "QueryBaoxianHistory{" +
                "insurance_query_id=" + insurance_query_id +
                ", name='" + name + '\'' +
                ", car_plate_no='" + car_plate_no + '\'' +
                ", license_brand_model='" + license_brand_model + '\'' +
                ", create_time='" + create_time + '\'' +
                ", status=" + status +
                ", fail_reason='" + fail_reason + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
