package com.victor.che.util;


import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.event.Region;

import java.util.List;

/**
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月24日 下午1:38:04
 */
public class CityUtil {

    /**
     * 定位城市在城市列表中的下标
     *
     * @param c
     * @param cityList
     * @return
     */
    public static int indexOf(String c, List<Region> cityList) {
        if (cityList == null || cityList.size() == 0) {
            return -1;
        }
        for (int i = 0; i < cityList.size(); i++) {
            if (c.equalsIgnoreCase(cityList.get(i).name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据城市名找到对应的实体
     *
     * @param cityName
     * @param cityList
     * @return
     */
    public static Region findCityByName(String cityName, List<Region> cityList) {
        if (cityList == null || cityList.size() == 0) {
            return null;
        }
        for (Region city : cityList) {
            if (cityName != null && cityName.equalsIgnoreCase(city.name)) {
                return city;
            }
        }
        return null;
    }

    /**
     * 获取最近的服务城市id(找不到则返回默认城市的id)
     *
     * @param city     城市
     * @param district 区/县
     * @return
     */
    public static Region getNearestRegion(String city, String district) {
        Region defaultRegion = null;
        Region city0 = null;
        Region district0 = null;
        if (CollectionUtil.isNotEmpty(MyApplication.RegionList)) {// 服务城市列表不为空
            defaultRegion = getDefaultRegion();// 首先获取默认城市id
            // 遍历服务城市
            for (int i = 0; i < MyApplication.RegionList.size(); i++) {
                Region item = MyApplication.RegionList.get(i);
                if (district.equalsIgnoreCase(item.name)) {// 区县在列表内
                    district0 = item;
                }
                if (city.equalsIgnoreCase(item.name)) {// 城市在列表内
                    city0 = item;
                }
            }
            if (district0 != null) {// 先判断区县是否在列表内
                return district0;
            }
            if (city0 != null) {// 再判断市是否在列表内
                return city0;
            }
        }
        return defaultRegion;
    }

    /**
     * 获取默认服务区域
     *
     * @return
     */
    private static Region getDefaultRegion() {
        if (CollectionUtil.isNotEmpty(MyApplication.RegionList)) {
            for (int i = 0; i < MyApplication.RegionList.size(); i++) {
                Region item = MyApplication.RegionList.get(i);
                if (ConstantValue.DEFAULT_SERVICE_CITY.equalsIgnoreCase(item.name)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * 判断两个城市是否为同一个城市
     *
     * @param city1
     * @param city2
     * @return
     */
    public static boolean equals(String city1, String city2) {
        if (city1 != null && city2 != null) {
            if (city1.startsWith(city2) || city2.startsWith(city1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取默认城市的id
     *
     * @return
     */
    public static int getDefaultAreaCode() {
        int id = -1;// 传负数，服务器要定位到默认城市
        if (CollectionUtil.isNotEmpty(MyApplication.RegionList)) {
            for (int i = 0; i < MyApplication.RegionList.size(); i++) {
                Region item = MyApplication.RegionList.get(i);
                if (ConstantValue.DEFAULT_SERVICE_CITY.equalsIgnoreCase(item.name)) {
                    return item.area_id;
                }
            }
        }
        return id;
    }
}
