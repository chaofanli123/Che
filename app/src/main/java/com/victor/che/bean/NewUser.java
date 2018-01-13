package com.victor.che.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员卡可用服务实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/21 0021 10:51
 */
public class NewUser implements Serializable {

    /**
     * username : admin
     * name :
     * mobileLogin : true
     * JSESSIONID :
     */

    private String username;
    private String name;
    private boolean mobileLogin;
    private String JSESSIONID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

    public void setMobileLogin(boolean mobileLogin) {
        this.mobileLogin = mobileLogin;
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }
}
