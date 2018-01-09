package com.victor.che.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */

public class Bank implements Serializable{

    private static final long serialVersionUID = 1L;


    /**
     * default : {"provider_bank_account_id":6,"card_no":"6222100992993","name":"李江","bank":"中国银行"}
     * common : [{"provider_bank_account_id":7,"card_no":"1029039393","name":"许","bank":"中国银行"}]
     */

  //  @SerializedName(name="default")
    @JSONField(name="default")
    private CommonBean defaultX;  //默认银行卡
    private List<CommonBean> common;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public CommonBean getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(CommonBean defaultX) {
        this.defaultX = defaultX;
    }

    public List<CommonBean> getCommon() {
        return common;
    }

    public void setCommon(List<CommonBean> common) {
        this.common = common;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "defaultX=" + defaultX +
                ", common=" + common +
                '}';
    }

    public static class CommonBean implements Serializable{
        /**
         * provider_bank_account_id : 7
         * card_no : 1029039393
         * name : 许
         * bank : 中国银行
         */

        private int provider_bank_account_id;
        private String card_no;

        private String bank;private String name;

        public int getProvider_bank_account_id() {
            return provider_bank_account_id;
        }

        public void setProvider_bank_account_id(int provider_bank_account_id) {
            this.provider_bank_account_id = provider_bank_account_id;
        }

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        @Override
        public String toString() {
            return "CommonBean{" +
                    "provider_bank_account_id=" + provider_bank_account_id +
                    ", card_no='" + card_no + '\'' +
                    ", name='" + name + '\'' +
                    ", bank='" + bank + '\'' +
                    '}';
        }
    }
}
