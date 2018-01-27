package com.victor.che.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/27.
 */

public class UserInfo implements Serializable{
    /**
     * userInformation : {"id":"1","isNewRecord":false,"remarks":"test","createDate":"2013-05-27 00:00:00","updateDate":"2018-01-24 08:55:34","loginName":"admin","no":"13815874603","name":"admin","email":"","phone":"","mobile":"rr","userType":"1","loginIp":"219.156.102.149","loginDate":"2018-01-27 06:17:03","loginFlag":"1","photo":"/psms/userfiles/1/images/170704131756800.jpg","qrCode":"/aims/userfiles/1/qrcode/test.png","oldLoginName":null,"newPassword":null,"sign":"你好啊","ys7Id":"1","oldLoginIp":"219.156.102.149","oldLoginDate":"2018-01-27 06:17:03","role":null,"roleNames":"管理员,部门管理员","admin":true}
     */

    private UserInformationBean userInformation;

    public UserInformationBean getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformationBean userInformation) {
        this.userInformation = userInformation;
    }

    public static class UserInformationBean implements Serializable{
        /**
         * id : 1
         * isNewRecord : false
         * remarks : test
         * createDate : 2013-05-27 00:00:00
         * updateDate : 2018-01-24 08:55:34
         * loginName : admin
         * no : 13815874603
         * name : admin
         * email :
         * phone :
         * mobile : rr
         * userType : 1
         * loginIp : 219.156.102.149
         * loginDate : 2018-01-27 06:17:03
         * loginFlag : 1
         * photo : /psms/userfiles/1/images/170704131756800.jpg
         * qrCode : /aims/userfiles/1/qrcode/test.png
         * oldLoginName : null
         * newPassword : null
         * sign : 你好啊
         * ys7Id : 1
         * oldLoginIp : 219.156.102.149
         * oldLoginDate : 2018-01-27 06:17:03
         * role : null
         * roleNames : 管理员,部门管理员
         * admin : true
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String loginName;
        private String no;
        private String name;
        private String email;
        private String phone;
        private String mobile;
        private String userType;
        private String loginIp;
        private String loginDate;
        private String loginFlag;
        private String photo;
        private String qrCode;
        private Object oldLoginName;
        private Object newPassword;
        private String sign;
        private String ys7Id;
        private String oldLoginIp;
        private String oldLoginDate;
        private Object role;
        private String roleNames;
        private boolean admin;

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

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getLoginIp() {
            return loginIp;
        }

        public void setLoginIp(String loginIp) {
            this.loginIp = loginIp;
        }

        public String getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(String loginDate) {
            this.loginDate = loginDate;
        }

        public String getLoginFlag() {
            return loginFlag;
        }

        public void setLoginFlag(String loginFlag) {
            this.loginFlag = loginFlag;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public Object getOldLoginName() {
            return oldLoginName;
        }

        public void setOldLoginName(Object oldLoginName) {
            this.oldLoginName = oldLoginName;
        }

        public Object getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(Object newPassword) {
            this.newPassword = newPassword;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getYs7Id() {
            return ys7Id;
        }

        public void setYs7Id(String ys7Id) {
            this.ys7Id = ys7Id;
        }

        public String getOldLoginIp() {
            return oldLoginIp;
        }

        public void setOldLoginIp(String oldLoginIp) {
            this.oldLoginIp = oldLoginIp;
        }

        public String getOldLoginDate() {
            return oldLoginDate;
        }

        public void setOldLoginDate(String oldLoginDate) {
            this.oldLoginDate = oldLoginDate;
        }

        public Object getRole() {
            return role;
        }

        public void setRole(Object role) {
            this.role = role;
        }

        public String getRoleNames() {
            return roleNames;
        }

        public void setRoleNames(String roleNames) {
            this.roleNames = roleNames;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }
    }
}
