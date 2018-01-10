package com.victor.che.event;


import com.victor.che.domain.UserCustomMessage;

import java.util.List;

/**
 *
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016/11/18 0018 9:53
 */
public class EndMoneyNumEvent {
    public List<UserCustomMessage> messageArrayList;//
    public  int num;

    public EndMoneyNumEvent(List<UserCustomMessage> messageArrayList, int num) {
        this.messageArrayList = messageArrayList;
        this.num = num;
    }

    public EndMoneyNumEvent() {

    }
}
