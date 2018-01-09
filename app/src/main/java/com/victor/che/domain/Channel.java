package com.victor.che.domain;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class Channel implements Serializable, MultiItemEntity {
    public static final int TYPE_MY = 1;
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER_CHANNEL = 4;
    public int itemType;
    public String goods_category_id;
    public String name;

    public String Title;//标题
    public String TitleCode;//标题code


    public ArrayList<Product> goods;// 包含的产品

    public boolean checked = false;// 本地变量，是否被全选

    public Channel(String title, String titleCode) {
        this(TYPE_MY_CHANNEL, title, titleCode);
    }

    public Channel(){

    }

    public Channel(int type, String title, String titleCode) {
        Title = title;
        TitleCode = titleCode;
        this.itemType=type;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
