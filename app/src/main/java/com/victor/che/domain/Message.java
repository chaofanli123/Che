package com.victor.che.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/2/22 0022 14:09
 */
public class Message implements Serializable {
    //    "title": "付款成功通知"  #消息标题,
    //            "content": "会员消费1元已到帐"  #消息内容,
    //            "create_time": "2017-02-21 10:56:42"  #发送时间,
    //            "status": 0  #处理状态 0-未读 1-已读
    public String title;
    public String content;
    public String create_time;
    public String status;
    public int what;
    public Map<String, String> obj;
}
