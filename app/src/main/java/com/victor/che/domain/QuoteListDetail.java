package com.victor.che.domain;

import java.io.Serializable;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/10 0010 10:57.
 * 报价方案详情
 */

public class QuoteListDetail implements Serializable{
//     "insurance_quote_id": 20  #报价id,
//            "total_price": "0.00"  #合计保费,
//            "discount": "0.00"  #折扣,
//            "actual_total_price": "0.00"  #实付保费,
//            "insurance_company_name": "人保车险"  #保险公司名称,
//            "insurance_company_icon": ""  #保险公司图标
    public int insurance_quote_id;
    public String total_price;
    public String discount;
    public String actual_total_price;
    public String insurance_company_name;
    public String insurance_company_icon;

    public int getInsurance_quote_id() {
        return insurance_quote_id;
    }

    public void setInsurance_quote_id(int insurance_quote_id) {
        this.insurance_quote_id = insurance_quote_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getActual_total_price() {
        return actual_total_price;
    }

    public void setActual_total_price(String actual_total_price) {
        this.actual_total_price = actual_total_price;
    }

    public String getInsurance_company_name() {
        return insurance_company_name;
    }

    public void setInsurance_company_name(String insurance_company_name) {
        this.insurance_company_name = insurance_company_name;
    }

    public String getInsurance_company_icon() {
        return insurance_company_icon;
    }

    public void setInsurance_company_icon(String insurance_company_icon) {
        this.insurance_company_icon = insurance_company_icon;
    }


    @Override
    public String toString() {
        return "QuoteListDetail{" +
                "insurance_quote_id=" + insurance_quote_id +
                ", total_price='" + total_price + '\'' +
                ", discount='" + discount + '\'' +
                ", actual_total_price='" + actual_total_price + '\'' +
                ", insurance_company_name='" + insurance_company_name + '\'' +
                ", insurance_company_icon='" + insurance_company_icon + '\'' +
                '}';
    }
}
