package com.victor.che.api;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求参数
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年4月29日 下午10:40:15
 */
public class MyParams {

    private final List<KeyValue> params = new ArrayList<KeyValue>();

    public void put(String key, Object value) {
        this.params.add(new KeyValue(key, value));
    }

    public List<KeyValue> getParamsList() {
        return this.params;
    }

    /**
     * 判断是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return params.size() <= 0;
    }

    /**
     * 重写toString方法
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        if (params != null && !params.isEmpty()) {
            for (KeyValue item : params) {
                builder.append(item.key).append("=").append(item.value).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("}");
        return builder.toString();
    }
}
