package com.victor.che.domain;

import java.io.Serializable;

/**
 * Banner实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/2/22 0022 15:19
 */
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    //    "image_url": "http://image.cheweifang.cn/banner/1.jpg"  #展示的图片地址,
    //            "need_login": 1  #是否需要登录 1-需要 0-不需要,
    //            "redirect_type": 1  #转跳类型：1：web 2：native,
    //            "redirect_data": "banner/2.jpg"  #若redirect_type=web，则data=url值；若redirect=app,则data=商家id，表示跳转到保养或洗车商家界面,
    //            "available_share": 0  #是否可分享 0:不可分享 1：可分享,
    //            "share_url": ""  #分享链接e_info": "更新"  #app更新信息
    public String image_url;
    public String need_login;
    public String redirect_type;
    public String redirect_data;
    public String available_share;
    public String share_url;
}
