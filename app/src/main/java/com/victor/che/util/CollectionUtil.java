package com.victor.che.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * 集合的工具类
 *
 * @author Administrator
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> c) {
        return !isNotEmpty(c);
    }

    /**
     * 判断集合是否有元素
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Collection<?> c) {
        return c != null && !c.isEmpty();
    }

    /**
     * 获取集合的size（防止空指针异常）
     *
     * @param c
     * @return
     */
    public static int getSize(Collection<?> c) {
        return c == null ? 0 : c.size();
    }


    /**
     * 用字符串拼接集合
     *
     * @param list
     * @param delimiter
     * @return
     */
    public static String join(List<?> list, String delimiter) {
        return join(list, null, delimiter);
    }

    /**
     * 用字符串拼接集合
     *
     * @param list
     * @param delimiter
     * @return
     */
    public static String join(List<?> list, String fieldName, String delimiter) {
        if (isEmpty(list)) {
            return "";
        }
        // 默认为,号分割
        if (null == delimiter || "".equalsIgnoreCase(delimiter)) {
            delimiter = ",";
        }

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                buffer.append(delimiter);
            }
            Object item = list.get(i);
            if (item instanceof String) {
                buffer.append(item);
            } else {
                try {
                    // 反射获取某个字段的值
                    Field field = item.getClass().getField(fieldName);
                    buffer.append(field.get(item));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }
}
