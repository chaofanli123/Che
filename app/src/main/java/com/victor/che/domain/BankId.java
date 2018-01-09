package com.victor.che.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12.
 */

public class BankId implements Serializable {

    /**
     * provider_bank_account_id : 1  #银行卡id
     */

    private int provider_bank_account_id;

    public int getProvider_bank_account_id() {
        return provider_bank_account_id;
    }

    public void setProvider_bank_account_id(int provider_bank_account_id) {
        this.provider_bank_account_id = provider_bank_account_id;
    }
}
