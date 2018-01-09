package com.victor.che.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Driving implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 保留 :
     * 号牌号码 :
     * 车辆类型 : 小型轿车
     * 所有人 :
     * 住址 :
     * 品牌型号 :
     * 车辆识别代号 :
     * 发动机号码 :
     * 注册日期 :
     * 发证日期 :
     * 使用性质 : 非营运
     */
    private String 保留;
    private String 号牌号码;
    private String 车辆类型;
    private String 所有人;
    private String 住址;
    private String 品牌型号;
    private String 车辆识别代号;
    private String 发动机号码;
    private String 注册日期;
    private String 发证日期;
    private String 使用性质;

    public String get保留() {
        return 保留;
    }

    public void set保留(String 保留) {
        this.保留 = 保留;
    }

    public String get号牌号码() {
        return 号牌号码;
    }

    public void set号牌号码(String 号牌号码) {
        this.号牌号码 = 号牌号码;
    }

    public String get车辆类型() {
        return 车辆类型;
    }

    public void set车辆类型(String 车辆类型) {
        this.车辆类型 = 车辆类型;
    }

    public String get所有人() {
        return 所有人;
    }

    public void set所有人(String 所有人) {
        this.所有人 = 所有人;
    }

    public String get住址() {
        return 住址;
    }

    public void set住址(String 住址) {
        this.住址 = 住址;
    }

    public String get品牌型号() {
        return 品牌型号;
    }

    public void set品牌型号(String 品牌型号) {
        this.品牌型号 = 品牌型号;
    }

    public String get车辆识别代号() {
        return 车辆识别代号;
    }

    public void set车辆识别代号(String 车辆识别代号) {
        this.车辆识别代号 = 车辆识别代号;
    }

    public String get发动机号码() {
        return 发动机号码;
    }

    public void set发动机号码(String 发动机号码) {
        this.发动机号码 = 发动机号码;
    }

    public String get注册日期() {
        return 注册日期;
    }

    public void set注册日期(String 注册日期) {
        this.注册日期 = 注册日期;
    }

    public String get发证日期() {
        return 发证日期;
    }

    public void set发证日期(String 发证日期) {
        this.发证日期 = 发证日期;
    }

    public String get使用性质() {
        return 使用性质;
    }

    public void set使用性质(String 使用性质) {
        this.使用性质 = 使用性质;
    }
}
