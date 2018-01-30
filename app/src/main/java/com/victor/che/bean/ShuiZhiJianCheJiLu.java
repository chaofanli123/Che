package com.victor.che.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ShuiZhiJianCheJiLu {

    private List<FirmListBean> firmList;

    public List<FirmListBean> getFirmList() {
        return firmList;
    }

    public void setFirmList(List<FirmListBean> firmList) {
        this.firmList = firmList;
    }

    public static class FirmListBean {
        /**
         * name : 信阳市南湾水库良种场
         * pId : 0
         * id : 40352ef75f134d5d83df908fb44e6eba
         * pIds : 0
         */

        private String name;
        private String pId;
        private String id;
        private String pIds;
        public String firstLetter;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPIds() {
            return pIds;
        }

        public void setPIds(String pIds) {
            this.pIds = pIds;
        }
    }
}
