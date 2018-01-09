package com.victor.che.domain;


import com.victor.che.util.MathUtil;
import com.victor.che.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 会员卡
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/2/7 0007 10:50
 */
public class Vipcard implements Serializable {

    private static final long serialVersionUID = 1L;

    //    "provider_user_card_id": "1"  #用户卡id,
    //            "card_category_id": 1  #卡类型 1-次卡 2-储值卡,
    //            "money": "12"  #剩余余额,
    //            "num": 7  #剩余次数,
    //            "card_no": "00100001"  #会员卡号,
    //            "name": "10次卡"  #卡名称,
    //            "description": ""  #卡描述,
    //            "used_goods":
    //            [
    //            1,
    //            "##可以使用的商品id集合"
    //            ]

    //    "provider_card_id": 2  #服务商卡id,
    //            "name": "5次卡"  #卡名称,
    //            "card_category_id": 1  #卡类别,
    //            "face_money": "15.00"  #卡面值,
    //            "available_num": "4"  #$卡次数,
    //            "price": "20.00"  #原价,
    //            "sale_price": "15.00"  #售价,
    //            "description": ""  #卡描述,
    //            "count": 1  #卡数量
    //    code 卡前缀

    public String provider_user_card_id;
    public int card_category_id;
    public double money = -1; /*赋初值，用来判断json是否包含此字段*/
    public int num = -1;/*赋初值，用来判断json是否包含此字段*/
    public String card_no;
    public String name;//卡的名称
    public String card_name;
    public String description;
    public ArrayList<String> used_goods;// 适用的服务类别
    public String used_goods_text;// 适用的服务文本
    public AllService all_service;
    public int is_expire;//会员卡是否过期 0-未过期，1-过期

    public String provider_card_id;
    public double face_money;
    public int available_num;
    public String available_year;// 卡使用年限
    public double price;
    public double sale_price;
    public int count = 0;// 开卡的数量

    public String code;

    public String end_time;
    public String mobile;
    public String cwf_no;

    public boolean is_valid = false;//是否可用(本地使用)
    public double recharge_eb;//充值的金额
    public String car_plate_no;

    /**
     * 获取可用服务文本
     *
     * @return
     */
    public String getUsedGoodsText() {
        return StringUtil.isEmpty(used_goods_text) ? "全部" : used_goods_text;
    }

    /**
     * 获取卡类型名称
     *
     * @return
     */
    public String getCardTypeName() {
        String result = "";
        switch (card_category_id) {
            case 1:// 次卡
                result = "次卡";
                break;
            case 2:// 储值卡
                result = "储值卡";
                break;
            case 3:// 年卡
                result = "年卡";
                break;
            default:
                result = "其它";
                break;
        }
        return result;
    }

    /**
     * 获取卡类型文本标签
     *
     * @return
     */
    public String getCardTypeLabel() {
        String result = "";
        switch (card_category_id) {
            case 1:// 次卡
            case 3:// 年卡
                result = "余次";
                break;
            case 2:// 储值卡
                result = "余额";
                break;
            default:
                result = "异常类别";
                break;
        }
        return result;
    }

    /**
     * 获取剩余金额
     *
     * @return
     */
    public String getRemainValue() {
        String result = "";
        switch (card_category_id) {
            case 1://次卡
                result = num + "次";
                break;
            case 2:// 储值卡
                result = MathUtil.getMoneyText(money);
                break;
            case 3://年卡
                result = "无限";
                break;
            default:
                result = "异常类别";
                break;
        }
        return result;
    }

    /**
     * 卡的面额
     *
     * @return
     */
    public String getFaceValue() {
        //  #卡类型 1-次卡 2-储值卡,, 3-年卡
        if (1 == card_category_id) {
            return available_num + "次";
        } else if (2 == card_category_id) {
            return MathUtil.getFinanceValue(face_money) + "元";
        } else if (3 == card_category_id) {
            return "无限";
        } else {
            return "卡类型异常";
        }
    }

    /**
     * 卡的面额标签
     *
     * @return
     */
    public String getFaceValueLabel() {
        //  #卡类型 1-次卡 2-储值卡, 3-年卡
        if (1 == card_category_id || 3 == card_category_id) {
            return "可用次数";
        } else if (2 == card_category_id) {
            return "可用金额";
        } else {
            return "卡类型异常";
        }
    }

    /**
     * 获取有效期
     *
     * @return
     */
    public String getEndTime() {
        if (StringUtil.isEmpty(end_time)) {
            return "永久";
        }
        return end_time;
    }

    /**
     * 是否过期
     *
     * @return
     */
    public boolean isExpire() {
        return is_expire == 1;
    }

    /**
     * 获取服务项目
     *
     * @return
     */
    public String getServiceContent() {
        return StringUtil.isEmpty(used_goods_text) ? "全部服务" : used_goods_text;
    }

    /**
     * 获取服务项目
     *
     * @return
     */
    public String getNewServiceContent() {
        return all_service == null ? "全部服务" : all_service.used_service_text;
    }
}
