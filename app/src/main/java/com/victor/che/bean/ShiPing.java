package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 会员卡可用服务实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/21 0021 10:51
 */
public class ShiPing implements Serializable{


    /**
     * videoList : [{"id":"2be37d0a7c984f768277874c9f766e65","isNewRecord":false,"remarks":"2312","createDate":"2017-12-07 09:17:05","updateDate":"2018-01-17 03:09:31","type":null,"deviceSerial":"811832246","pondId":{"id":"915caa8da396422db70a54adfd7dae86","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"0c1942301e564813817c9c0b6301fb89","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmName":"灵宝江辉养殖合作社","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"2","ezopen":"ezopen://open.ys7.com/811832246/2.live","ezopenhd":"ezopen://open.ys7.com/811832246/2.hd.live","img":"","videoType":null},{"id":"a899712376d04ef78d0bd65ccd0a892f","isNewRecord":false,"remarks":"2323","createDate":"2017-12-07 09:15:49","updateDate":"2018-01-17 03:09:02","type":null,"deviceSerial":"811832246","pondId":{"id":"58dc8c2d83ff4c03b857ad60e5414d5c","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"40352ef75f134d5d83df908fb44e6eba","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmName":"信阳市南湾水库良种场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"1","ezopen":"ezopen://open.ys7.com/811832246/1.live","ezopenhd":"ezopen://open.ys7.com/811832246/1.hd.live","img":"","videoType":null},{"id":"d204527ade4b4bf1a3a4d98009a66f10","isNewRecord":false,"remarks":"东北塘1","createDate":"2017-12-13 02:19:57","updateDate":"2018-01-17 03:08:47","type":null,"deviceSerial":"835510343","pondId":{"id":"8f8ba27633044ed78ec9d65124849e08","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"42252b3f6f4f4801a232921ebf852e1d","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmName":"商丘和昌饲料有限公司","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"1","ezopen":"ezopen://open.ys7.com/835510343/1.live","ezopenhd":"ezopen://open.ys7.com/835510343/1.hd.live","img":"","videoType":null},{"id":"1a27215ea83945bba87a513e492d2490","isNewRecord":false,"remarks":"","createDate":"2018-01-15 08:42:00","updateDate":"2018-01-17 03:08:19","type":null,"deviceSerial":"835510343","pondId":{"id":"5b4fd2721ade4dcf9950f0c1e25d6db6","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"d4b99a334ed249e6a2e9bc1c72df1fd0","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmName":"兰考腾飞合作社","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"2","ezopen":"ezopen://open.ys7.com/835510343/2.live","ezopenhd":"ezopen://open.ys7.com/835510343/2.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/GSJH1.png","videoType":null}]
     * appKey : 566fb0a1d274443f8d32d74212c570e7
     * accessToken : at.5uodi03a8x6e98iq2g9qgxm5b8ayirw3-8du5ec0c57-0k0mh6s-jzr6e85pm
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

    public static class VideoListBean implements Serializable{
        /**
         * id : 2be37d0a7c984f768277874c9f766e65
         * isNewRecord : false
         * remarks : 2312
         * createDate : 2017-12-07 09:17:05
         * updateDate : 2018-01-17 03:09:31
         * type : null
         * deviceSerial : 811832246
         * pondId : {"id":"915caa8da396422db70a54adfd7dae86","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"}
         * firmId : {"id":"0c1942301e564813817c9c0b6301fb89","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmName":"灵宝江辉养殖合作社","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null}
         * channelNo : 2
         * ezopen : ezopen://open.ys7.com/811832246/2.live
         * ezopenhd : ezopen://open.ys7.com/811832246/2.hd.live
         * img :
         * videoType : null
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private Object type;
        private String deviceSerial;
        private PondIdBean pondId;
        private FirmIdBean firmId;
        private String channelNo;
        private String ezopen;
        private String ezopenhd;
        private String img;
        private Object videoType;

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

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getDeviceSerial() {
            return deviceSerial;
        }

        public void setDeviceSerial(String deviceSerial) {
            this.deviceSerial = deviceSerial;
        }

        public PondIdBean getPondId() {
            return pondId;
        }

        public void setPondId(PondIdBean pondId) {
            this.pondId = pondId;
        }

        public FirmIdBean getFirmId() {
            return firmId;
        }

        public void setFirmId(FirmIdBean firmId) {
            this.firmId = firmId;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getVideoType() {
            return videoType;
        }

        public void setVideoType(Object videoType) {
            this.videoType = videoType;
        }

        public static class PondIdBean {
            /**
             * id : 915caa8da396422db70a54adfd7dae86
             * isNewRecord : false
             * remarks : null
             * createDate : null
             * updateDate : null
             * firmId : null
             * name : 1#塘
             */

            private String id;
            private boolean isNewRecord;
            private Object remarks;
            private Object createDate;
            private Object updateDate;
            private Object firmId;
            private String name;

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

            public Object getFirmId() {
                return firmId;
            }

            public void setFirmId(Object firmId) {
                this.firmId = firmId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class FirmIdBean {
            /**
             * id : 0c1942301e564813817c9c0b6301fb89
             * isNewRecord : false
             * remarks : null
             * createDate : null
             * updateDate : null
             * firmName : 灵宝江辉养殖合作社
             * area : null
             * coordinateX : null
             * coordinateY : null
             * picture : null
             * mainProduct : null
             * comSynopsis : null
             */

            private String id;
            private boolean isNewRecord;
            private Object remarks;
            private Object createDate;
            private Object updateDate;
            private String firmName;
            private Object area;
            private Object coordinateX;
            private Object coordinateY;
            private Object picture;
            private Object mainProduct;
            private Object comSynopsis;

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

            public String getFirmName() {
                return firmName;
            }

            public void setFirmName(String firmName) {
                this.firmName = firmName;
            }

            public Object getArea() {
                return area;
            }

            public void setArea(Object area) {
                this.area = area;
            }

            public Object getCoordinateX() {
                return coordinateX;
            }

            public void setCoordinateX(Object coordinateX) {
                this.coordinateX = coordinateX;
            }

            public Object getCoordinateY() {
                return coordinateY;
            }

            public void setCoordinateY(Object coordinateY) {
                this.coordinateY = coordinateY;
            }

            public Object getPicture() {
                return picture;
            }

            public void setPicture(Object picture) {
                this.picture = picture;
            }

            public Object getMainProduct() {
                return mainProduct;
            }

            public void setMainProduct(Object mainProduct) {
                this.mainProduct = mainProduct;
            }

            public Object getComSynopsis() {
                return comSynopsis;
            }

            public void setComSynopsis(Object comSynopsis) {
                this.comSynopsis = comSynopsis;
            }
        }
    }
}
