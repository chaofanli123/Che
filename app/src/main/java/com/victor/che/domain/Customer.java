package com.victor.che.domain;

import com.victor.che.util.StringUtil;

import java.io.Serializable;

/**
 * 客户（会员）实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 10:06
 */
public class Customer implements Serializable {
    //    "provider_user_id": 1  #用户id,
    //            "name": "许继雪"  #用户姓名,
    //            "mobile": "13021960147"  #用户手机号,
    //            "avatar_thumb": "http://image.cheweifang.cn/"  #用户头像
    public String provider_user_id;
    public String name;
    public String mobile;
    public String avatar_thumb;
    public String car_plate_no = "";
    public boolean checked = false;// 本地变量，是否被选中

    public String getName() {
        return StringUtil.isEmpty(name) ? "无姓名" : name;
    }
}
