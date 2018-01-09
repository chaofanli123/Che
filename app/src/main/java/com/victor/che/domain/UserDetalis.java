package com.victor.che.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 * 用户详情
 */

public class UserDetalis implements Serializable {
    private static final long serialVersionUID = 1L;
/*
    {
        "provider_user_id": 1  #用户id,
            "mobile": "13021960147"  #用户手机号,
            "name": "许继雪"  #用户名称,
            "gender": "未知"  #用户性别,
            "avatar_thumb": "http://image.cheweifang.cn/"  #用户头像,
            "card_num": 1  #会员卡数,
            "car_plate_no": "豫A98309"  #用户默认车辆车牌号,
            "car_brand_series": "奥迪S5 Coupe"  #车辆型号,
            "car_brand_image": "http://image.cheweifang.cn/parentbrand/aodi.jpg"  #车辆品牌图标,
            "consume_time": "2017-03-07"  #最后一次消费时间,
            "car":
        [
        {
            "car_plate_no": "豫A98309"  #车牌号,
                "image": ""  #车品牌图标,
                "car_brand_series": "S5 Coupe"  #车型号,
                "license_img": ""  #行驶证原图
        }
        ]
    }*/

    /**
     * provider_user_id : 1
     * mobile : 13021960147
     * name : 许继雪
     * gender : 未知
     * avatar_thumb : http://image.cheweifang.cn/
     * card_num : 1
     * car_plate_no : 豫A98309
     * car_brand_series : 奥迪S5 Coupe
     * car_brand_image : http://image.cheweifang.cn/parentbrand/aodi.jpg
     * consume_time : 2017-03-07
     * car : [{"car_plate_no":"豫A98309","image":"","car_brand_series":"S5 Coupe","license_img":""}]
     */

    private int provider_user_id;
    private String mobile;
    private String name;
    private String gender;
    private String avatar_thumb;
    private int card_num;
    private String car_plate_no;
    private String car_brand_series;
    private String car_brand_image;
    private String consume_time;
    private List<CarBean> car;

    public int getProvider_user_id() {
        return provider_user_id;
    }

    public void setProvider_user_id(int provider_user_id) {
        this.provider_user_id = provider_user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar_thumb() {
        return avatar_thumb;
    }

    public void setAvatar_thumb(String avatar_thumb) {
        this.avatar_thumb = avatar_thumb;
    }

    public int getCard_num() {
        return card_num;
    }

    public void setCard_num(int card_num) {
        this.card_num = card_num;
    }

    public String getCar_plate_no() {
        return car_plate_no;
    }

    public void setCar_plate_no(String car_plate_no) {
        this.car_plate_no = car_plate_no;
    }

    public String getCar_brand_series() {
        return car_brand_series;
    }

    public void setCar_brand_series(String car_brand_series) {
        this.car_brand_series = car_brand_series;
    }

    public String getCar_brand_image() {
        return car_brand_image;
    }

    public void setCar_brand_image(String car_brand_image) {
        this.car_brand_image = car_brand_image;
    }

    public String getConsume_time() {
        return consume_time;
    }

    public void setConsume_time(String consume_time) {
        this.consume_time = consume_time;
    }

    public List<CarBean> getCar() {
        return car;
    }

    public void setCar(List<CarBean> car) {
        this.car = car;
    }

    public static class CarBean implements Serializable {
        /**
         * "provider_user_car_id": 138,
         * car_plate_no : 豫A98309
         * image :
         * car_brand_series : S5 Coupe
         * license_img :
         *  "car_brand_series_id": 0,
          "car_brand_id": 0,
         */

        private String car_plate_no;
        private String image;
        private String car_brand_series;
        private String license_img;
        private int provider_user_car_id;
        private int car_brand_series_id;
        private int car_brand_id;

        public int getProvider_user_car_id() {
            return provider_user_car_id;
        }

        public void setProvider_user_car_id(int provider_user_car_id) {
            this.provider_user_car_id = provider_user_car_id;
        }

        public int getCar_brand_series_id() {
            return car_brand_series_id;
        }

        public void setCar_brand_series_id(int car_brand_series_id) {
            this.car_brand_series_id = car_brand_series_id;
        }

        public int getCar_brand_id() {
            return car_brand_id;
        }

        public void setCar_brand_id(int car_brand_id) {
            this.car_brand_id = car_brand_id;
        }

        public String getCar_plate_no() {
            return car_plate_no;
        }

        public void setCar_plate_no(String car_plate_no) {
            this.car_plate_no = car_plate_no;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCar_brand_series() {
            return car_brand_series;
        }

        public void setCar_brand_series(String car_brand_series) {
            this.car_brand_series = car_brand_series;
        }

        public String getLicense_img() {
            return license_img;
        }

        public void setLicense_img(String license_img) {
            this.license_img = license_img;
        }
    }
}
