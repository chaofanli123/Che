package com.victor.che.domain;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 * 门店服务列表接口
 */

public class GoodCategory implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<Channel> used_goods_category;
    private List<Channel> unUsed_goods_category;

    public List<Channel> getUsed_goods_category() {
        return used_goods_category;
    }

    public void setUsed_goods_category(List<Channel> used_goods_category) {
        this.used_goods_category = used_goods_category;
    }

    public List<Channel> getUnUsed_goods_category() {
        return unUsed_goods_category;
    }

    public void setUnUsed_goods_category(List<Channel> unUsed_goods_category) {
        this.unUsed_goods_category = unUsed_goods_category;
    }
}
