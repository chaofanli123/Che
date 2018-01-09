package com.victor.che.domain;

import com.victor.che.R;

import java.io.Serializable;

/**
 * 提现记录
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/2/7 0007 16:06
 */
public class WithdrawRecord implements Serializable {

    //    "withdraw_status": 2  #结算状态 1-已结算 2 -未结算,
    //            "withdraw_money": "0.00"  #提现金额,
    //            "create_time": "2017-02-06 11:11:17"  #提现时间
    //    "withdraw_time": "2017-02-07 10:20:20"  #提现处理时间
//    "withdraw_start_time": "2017-05-01 00:00:00"  #提现开始时间,
//            "withdraw_end_time": "2017-05-07 23:59:59"  #提现结束时间,
    //"receipt_url": ""  #提现回执单地址

    public int withdraw_status;
    public double withdraw_money;
    public String create_time;
    public String withdraw_time;
    public String withdraw_start_time;

    public String withdraw_end_time;
    public String receipt_url;
    public String operator;//    #处理人姓名





    /**
     * 获取处理状态
     *
     * @return
     */
    public String getWithdrawStatus() {
        return withdraw_status == 1 ? "已结算" : "未结算";
    }

    /**
     * 获取提现处理状态文本颜色
     *
     * @return
     */
    public int getWithdrawStatusColor() {
        return withdraw_status == 1 ? R.color.theme_color : R.color.withdraw_dealing;
    }
}
