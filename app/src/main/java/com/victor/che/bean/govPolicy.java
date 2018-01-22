package com.victor.che.bean;

/**
 * Created by Administrator on 2018/1/22.
 */

public class govPolicy {

    /**
     * govPolicy : {"id":"843f0cd077c14873ba92cc666cc85457","isNewRecord":false,"remarks":"规章制度","createDate":"2017-11-28 01:21:50","updateDate":"2017-11-28 01:21:50","title":"规章","type":"2","content":"&lt;span style=&quot;background-color: rgb(255, 0, 0);&quot;&gt;&lt;span style=&quot;text-decoration: underline;&quot;&gt;&lt;span style=&quot;font-style: italic;&quot;&gt;&lt;span style=&quot;font-weight: bold;&quot;&gt;规章制度&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;","addfiles":"|/aims/userfiles/1/files/usermanagement/governmentPolicies/2017/11/LBXB6.png","sendtime":"2017-11-20 16:00:00","isLook":null,"beginDate":null,"endDate":null}
     */

    private GovPolicyBean govPolicy;

    public GovPolicyBean getGovPolicy() {
        return govPolicy;
    }

    public void setGovPolicy(GovPolicyBean govPolicy) {
        this.govPolicy = govPolicy;
    }

    public static class GovPolicyBean {
        /**
         * id : 843f0cd077c14873ba92cc666cc85457
         * isNewRecord : false
         * remarks : 规章制度
         * createDate : 2017-11-28 01:21:50
         * updateDate : 2017-11-28 01:21:50
         * title : 规章
         * type : 2
         * content : &lt;span style=&quot;background-color: rgb(255, 0, 0);&quot;&gt;&lt;span style=&quot;text-decoration: underline;&quot;&gt;&lt;span style=&quot;font-style: italic;&quot;&gt;&lt;span style=&quot;font-weight: bold;&quot;&gt;规章制度&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;
         * addfiles : |/aims/userfiles/1/files/usermanagement/governmentPolicies/2017/11/LBXB6.png
         * sendtime : 2017-11-20 16:00:00
         * isLook : null
         * beginDate : null
         * endDate : null
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String title;
        private String type;
        private String content;
        private String addfiles;
        private String sendtime;
        private Object isLook;
        private Object beginDate;
        private Object endDate;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddfiles() {
            return addfiles;
        }

        public void setAddfiles(String addfiles) {
            this.addfiles = addfiles;
        }

        public String getSendtime() {
            return sendtime;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }

        public Object getIsLook() {
            return isLook;
        }

        public void setIsLook(Object isLook) {
            this.isLook = isLook;
        }

        public Object getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(Object beginDate) {
            this.beginDate = beginDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }
    }
}
