package com.victor.che.domain;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/11 0011 18:41.
 */

public class PayConfirmData {
//      "is_success": 2  #支付状态 0-回调失败 1-成功 2-未回调且客户端确认成功 3-客户端确认失败
    public int is_success;

    public int getIs_success() {
        return is_success;
    }

    public void setIs_success(int is_success) {
        this.is_success = is_success;
    }
}
