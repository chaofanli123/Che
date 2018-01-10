package com.victor.che.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/17.
 */

public class PayData implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * payDic : {"appid":"wxff87f02dac93ce58","partnerid":"1455289802","prepayid":"wx201704211831537e774e6df70250588258","package":"Sign=WXPay","noncestr":"bkp6x21al0jbv4tty95t6zmc4k4kxsa5","timestamp":1492770713,"sign":"253B79AA81412BC12AE43047FCB312AD"}
     * shopping_order_id : 395
     */

    private PayDicBean payDic;
    private String shopping_order_id;

    public PayDicBean getPayDic() {
        return payDic;
    }

    public void setPayDic(PayDicBean payDic) {
        this.payDic = payDic;
    }

    public String getShopping_order_id() {
        return shopping_order_id;
    }

    public void setShopping_order_id(String shopping_order_id) {
        this.shopping_order_id = shopping_order_id;
    }

    public static class PayDicBean {
        /**
         * appid : wxff87f02dac93ce58
         * partnerid : 1455289802
         * prepayid : wx201704211831537e774e6df70250588258
         * package : Sign=WXPay
         * noncestr : bkp6x21al0jbv4tty95t6zmc4k4kxsa5
         * timestamp : 1492770713
         * sign : 253B79AA81412BC12AE43047FCB312AD
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @JSONField(name = "package")
        public String packageValue;
        private String noncestr;
        private int timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageValue;
        }

        public void setPackageX(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
