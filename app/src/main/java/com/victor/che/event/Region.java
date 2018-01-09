package com.victor.che.event;

import java.io.Serializable;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * 地理区域实体
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年10月28日 上午10:14:55
 */
public class Region implements Serializable, IndexableEntity {

    private static final long serialVersionUID = 1L;

    public int area_id;// 城市id
    public String name;// 城市名称

    public Region() {
        super();
    }

    public Region(String name) {
        super();
        this.name = name;
    }

    /**
     * 排序的字段
     *
     * @return
     */
    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField;
    }

    /**
     * 保存排序field的拼音,在执行比如搜索等功能时有用 （若不需要，空实现该方法即可）
     *
     * @param pinyin
     */
    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
    }

}
