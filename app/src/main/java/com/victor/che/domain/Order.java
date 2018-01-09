package com.victor.che.domain;

import com.victor.che.R;

import java.io.Serializable;
import java.util.List;

/**
 * 订单实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 17:12
 */
public class Order implements Serializable {

    /**
     * order_id : 1$###订单id
     * order_no : X170121000001###订单编号
     * create_time : 2017-01-21 15:02:58###下单时间
     * total_actual_price : 0.00###订单实际总金额
     * order_status : 1$###订单状态(1未支付，2已支付，3待评价，4已评价 6退款)
     * pay_method_id : 1$###支付方式 1-支付宝 2-微信 3或4-卡支付 6-现金 7-年卡
     * order_category_name : 赠品###订单类别名
     * order_detail : [{"goods_name":"商品1###商品名称","buy_num":"1$###购买数量","sale_price":"12.00###单价","goods_category_name":"洗车###服务类别名称"}]
     * mobile : 18521516231###订单对应用户手机号
     * car_plate_no : 豫AQ2Q11###订单对应车辆车牌号
     */
    private static final long serialVersionUID = 1L;
    public List<OrderDetailBean> order_detail;

    public String order_id;
    public String order_no;
    public String create_time;
    public double total_actual_price;
    public int order_status;
    public String order_category_name;
    public int order_category_id;
    public String goods_name;
    public String buy_num;
    public double sale_price;
    public int pay_method_id;
    public String mobile;//订单对应用户手机号
    public String car_plate_no;//车牌号
    public double coupon_amount;//优惠券面值


    /**
     * 获取支付方式
     * #支付方式 1-支付宝 2-微信 3或4-卡支付 6-现金 7 -年卡
     *
     * @return
     */
    public String getPayMethod() {
        String result = "";
        switch (pay_method_id) {
            case 1:
                result = "支付宝";
                break;
            case 2:
                result = "微信";
                break;
            case 3:
            case 4:
                result = "会员卡";
                break;
            case 6:
                result = "现金";
                break;
            case 7:
                result="年卡";
                break;
            default:
                result = "异常状态";
                break;
        }
        return result;
    }

    /**
     * 获取订单状态
     * #订单状态(1未支付，2已支付，3待评价，4已完成 6退款)
     *
     * @return
     */
    public String getOrderState() {
        String result = "";
        switch (order_status) {
            case 1:
                result = "未支付";
                break;
            case 2:
                result = "待使用";
                break;
            case 3:
                result = "待评价";
                break;
            case 4:
                result = "已完成";
                break;
            case 5:
                result = "退款";
                break;
            default:
                result = "异常状态";
                break;
        }
        return result;
    }

    /**
     * 获取订单类型的icon
     *
     * @return
     */
    public int getOrderCategoryIcon() {
        //  #订单类别id 2-服务 3-开卡 4-充值,
        switch (order_category_id) {
            case 3:
                return R.drawable.ic_ordertype_allocate_vipcard;
            case 4:
                return R.drawable.ic_ordertype_recharge;
            case 5:
                return R.drawable.ic_gift;
            default:
                return R.drawable.ic_ordertype_service;
        }
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


    public String getOrder_category_name() {
        return order_category_name;
    }

    public void setOrder_category_name(String order_category_name) {
        this.order_category_name = order_category_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCar_plate_no() {
        return car_plate_no;
    }

    public void setCar_plate_no(String car_plate_no) {
        this.car_plate_no = car_plate_no;
    }

    public List<OrderDetailBean> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrderDetailBean> order_detail) {
        this.order_detail = order_detail;
    }

    public static class OrderDetailBean implements Serializable {
        /**
         * goods_name : 商品1###商品名称
         * buy_num : 1$###购买数量
         * sale_price : 12.00###单价
         * goods_category_name : 洗车###服务类别名称
         */

        private String goods_name;
        private int buy_num;
        private String sale_price;
        private String goods_category_name;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getBuy_num() {
            return buy_num;
        }

        public void setBuy_num(int buy_num) {
            this.buy_num = buy_num;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getGoods_category_name() {
            return goods_category_name;
        }

        public void setGoods_category_name(String goods_category_name) {
            this.goods_category_name = goods_category_name;
        }
    }
}
