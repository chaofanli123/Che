package com.victor.che.domain;

import java.io.Serializable;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/17 0017 13:56.
 */

public class Store implements Serializable {

    /**
     {
     "provider_id": 3,
     "name": ""  #服务商名称,
     "address": ""  #服务商地址,
     "thumb_image_url": ""  #服务商门店缩略图,
     "image_url": ""  #服务商门店原图,
     "service_tel": ""  #客服电话,
     "service_mobile": ""  #短信通知电话,
     "wxmp_qrcode": ""  #公众号二维码地址,
     "business_start_time": "00:00"  #营业开始时间,
     "business_end_time": "00:00"  #营业结束时间
     }
     */

    private int provider_id;
    private String name;
    private String address;
    private String thumb_image_url;
    private String image_url;
    private String service_tel;
    private String service_mobile;
    private String wxmp_qrcode;
    private String business_start_time;
    private String business_end_time;

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setThumb_image_url(String thumb_image_url) {
        this.thumb_image_url = thumb_image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setService_tel(String service_tel) {
        this.service_tel = service_tel;
    }

    public void setService_mobile(String service_mobile) {
        this.service_mobile = service_mobile;
    }

    public void setWxmp_qrcode(String wxmp_qrcode) {
        this.wxmp_qrcode = wxmp_qrcode;
    }

    public void setBusiness_start_time(String business_start_time) {
        this.business_start_time = business_start_time;
    }

    public void setBusiness_end_time(String business_end_time) {
        this.business_end_time = business_end_time;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getThumb_image_url() {
        return thumb_image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getService_tel() {
        return service_tel;
    }

    public String getService_mobile() {
        return service_mobile;
    }

    public String getWxmp_qrcode() {
        return wxmp_qrcode;
    }

    public String getBusiness_start_time() {
        return business_start_time;
    }

    public String getBusiness_end_time() {
        return business_end_time;
    }
}
