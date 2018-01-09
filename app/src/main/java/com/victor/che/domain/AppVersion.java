package com.victor.che.domain;

import java.io.Serializable;

/**
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月22日 下午3:27:21
 */
public class AppVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    //     "app_version": "1"  #app版本,
    //            "app_url": "http://image.cheweifang.cn//update/android.apk"  #app下载url,
    //            "upgrade_info": "更新"  #app更新信息
    public String app_version;
    public String app_url;
    public String upgrade_info;


}
