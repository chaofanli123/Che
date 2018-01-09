package com.victor.che.domain;

import com.victor.che.pay.wxpay.wxpay.WXPayParams;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/11 0011 16:56.
 */

public class PayPolicyWXData {
//     "order_id": 1  #订单id,
//            "payStr": "a
    public int insurance_order_id;
    public WXPayParams payStr;

    public int getInsurance_order_id() {
        return insurance_order_id;
    }

    public void setInsurance_order_id(int insurance_order_id) {
        this.insurance_order_id = insurance_order_id;
    }

    @Override
    public String toString() {
        return "PayPolicyAliData{" +
                "insurance_order_id=" + insurance_order_id +
                ", payStr='" + payStr + '\'' +
                '}';
    }

}
