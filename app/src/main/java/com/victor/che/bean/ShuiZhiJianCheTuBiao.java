package com.victor.che.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ShuiZhiJianCheTuBiao {

    private List<IotListBean> iotList;

    public List<IotListBean> getIotList() {
        return iotList;
    }

    public void setIotList(List<IotListBean> iotList) {
        this.iotList = iotList;
    }

    public static class IotListBean {
        /**
         * id : 90897ce47ce74f63a02b690a43b32496
         * isNewRecord : false
         * remarks : null
         * createDate : null
         * updateDate : null
         * gatewaysn : 1272
         * nodesn : 1273
         * name : 溶解氧
         * channel : 5
         * val : 12.63
         * r : 31 54
         * time : 2018-01-30 13:31:53
         * createTime : 2018-01-30 05:31:33
         * beginTime : null
         * endTime : null
         * staPond : {"id":"092f36c8b55d42da994493ddd244727b","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"}
         * fishpond : {"id":"40352ef75f134d5d83df908fb44e6eba","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"信阳市南湾水库良种场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null}
         * poolId : null
         * unit : mg/l
         */

        private String id;
        private boolean isNewRecord;
        private Object remarks;
        private Object createDate;
        private Object updateDate;
        private int gatewaysn;
        private int nodesn;
        private String name;
        private int channel;
        private double val;
        private String r;
        private String time;
        private String createTime;
        private Object beginTime;
        private Object endTime;
        private StaPondBean staPond;
        private FishpondBean fishpond;
        private Object poolId;
        private String unit;

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

        public int getGatewaysn() {
            return gatewaysn;
        }

        public void setGatewaysn(int gatewaysn) {
            this.gatewaysn = gatewaysn;
        }

        public int getNodesn() {
            return nodesn;
        }

        public void setNodesn(int nodesn) {
            this.nodesn = nodesn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public double getVal() {
            return val;
        }

        public void setVal(double val) {
            this.val = val;
        }

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public StaPondBean getStaPond() {
            return staPond;
        }

        public void setStaPond(StaPondBean staPond) {
            this.staPond = staPond;
        }

        public FishpondBean getFishpond() {
            return fishpond;
        }

        public void setFishpond(FishpondBean fishpond) {
            this.fishpond = fishpond;
        }

        public Object getPoolId() {
            return poolId;
        }

        public void setPoolId(Object poolId) {
            this.poolId = poolId;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public static class StaPondBean {
            /**
             * id : 092f36c8b55d42da994493ddd244727b
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

        public static class FishpondBean {
            /**
             * id : 40352ef75f134d5d83df908fb44e6eba
             * isNewRecord : false
             * remarks : null
             * createDate : null
             * updateDate : null
             * govAquFarmId : null
             * firmName : 信阳市南湾水库良种场
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
            private Object govAquFarmId;
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

            public Object getGovAquFarmId() {
                return govAquFarmId;
            }

            public void setGovAquFarmId(Object govAquFarmId) {
                this.govAquFarmId = govAquFarmId;
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
