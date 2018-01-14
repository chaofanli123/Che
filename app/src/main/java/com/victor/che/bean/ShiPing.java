package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 会员卡可用服务实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/21 0021 10:51
 */
public class ShiPing {


    /**
     * videoList : [{"id":"2be37d0a7c984f768277874c9f766e65","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":"c18eb1c484194a4f91e0a56ea38eb278","deviceSerial":"811832246","channelNo":"2","ezopen":"ezopen://open.ys7.com/811832246/2.live","ezopenhd":"ezopen://open.ys7.com/811832246/2.hd.live"},{"id":"a899712376d04ef78d0bd65ccd0a892f","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":"963e1e5831b5461e8187d7c02917e40f","deviceSerial":"811832246","channelNo":"1","ezopen":"ezopen://open.ys7.com/811832246/1.live","ezopenhd":"ezopen://open.ys7.com/811832246/1.hd.live"},{"id":"d204527ade4b4bf1a3a4d98009a66f10","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":"e4d67767488b41a19bcc53078141e8f7","deviceSerial":"806232611","channelNo":"1","ezopen":"ezopen://open.ys7.com/806232611/1.live","ezopenhd":"ezopen://open.ys7.com/806232611/1.hd.live"}]
     * appKey : 566fb0a1d274443f8d32d74212c570e7
     * accessToken : at.28gc2dpy7nsonz369optc6ct01snjkxf-5by87prlie-0426rcc-21o540kee
     */

    private String appKey;
    private String accessToken;
    private List<VideoListBean> videoList;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoListBean> videoList) {
        this.videoList = videoList;
    }

    public static class VideoListBean {
        /**
         * id : 2be37d0a7c984f768277874c9f766e65
         * isNewRecord : false
         * remarks : null
         * createDate : null
         * updateDate : null
         * firmId : c18eb1c484194a4f91e0a56ea38eb278
         * deviceSerial : 811832246
         * channelNo : 2
         * ezopen : ezopen://open.ys7.com/811832246/2.live
         * ezopenhd : ezopen://open.ys7.com/811832246/2.hd.live
         */

        private String id;
        private boolean isNewRecord;
        private Object remarks;
        private Object createDate;
        private Object updateDate;
        private String firmId;
        private String deviceSerial;
        private String channelNo;
        private String ezopen;
        private String ezopenhd;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }

        public String getFirmId() {
            return firmId;
        }

        public void setFirmId(String firmId) {
            this.firmId = firmId;
        }

        public String getDeviceSerial() {
            return deviceSerial;
        }

        public void setDeviceSerial(String deviceSerial) {
            this.deviceSerial = deviceSerial;
        }

        public String getChannelNo() {
            return channelNo;
        }

        public void setChannelNo(String channelNo) {
            this.channelNo = channelNo;
        }

        public String getEzopen() {
            return ezopen;
        }

        public void setEzopen(String ezopen) {
            this.ezopen = ezopen;
        }

        public String getEzopenhd() {
            return ezopenhd;
        }

        public void setEzopenhd(String ezopenhd) {
            this.ezopenhd = ezopenhd;
        }
    }
}
