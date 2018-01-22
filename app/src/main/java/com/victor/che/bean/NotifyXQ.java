package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jcc on 2018/1/21.
 */

public class NotifyXQ implements Serializable {


    /**
     * oaNotify : {"id":"5b628443d4e34279bb960b7d391a0dc1","isNewRecord":false,"remarks":null,"createDate":"2018-01-12 10:00:19","updateDate":"2018-01-12 10:00:19","type":"1","title":"测试","content":"文本A","files":",","status":"1","readNum":"0","unReadNum":"2","readFlag":null,"oaNotifyRecordList":[{"id":"74aee07711d7488398ece4ee9429c4c6","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"oaNotify":{"id":"5b628443d4e34279bb960b7d391a0dc1","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"type":null,"title":null,"content":null,"files":null,"status":null,"readNum":null,"unReadNum":null,"readFlag":null,"oaNotifyRecordList":[],"oaNotifyRecordIds":"","oaNotifyRecordNames":"","self":false},"user":{"id":"9b44211d04574136b414955cc16f9468","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"李冰冰","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"readFlag":"0","readDate":null,"readReply":null},{"id":"d961d4c096ea49c6a87cb09ec1e40d6b","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"oaNotify":{"id":"5b628443d4e34279bb960b7d391a0dc1","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"type":null,"title":null,"content":null,"files":null,"status":null,"readNum":null,"unReadNum":null,"readFlag":null,"oaNotifyRecordList":[],"oaNotifyRecordIds":"","oaNotifyRecordNames":"","self":false},"user":{"id":"7374fe91d19a4b739ae649334c0cc273","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"林心如","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"readFlag":"0","readDate":null,"readReply":null}],"oaNotifyRecordIds":"9b44211d04574136b414955cc16f9468,7374fe91d19a4b739ae649334c0cc273","oaNotifyRecordNames":"李冰冰,林心如","self":false}
     */

    private OaNotifyBeanX oaNotify;

    public OaNotifyBeanX getOaNotify() {
        return oaNotify;
    }

    public void setOaNotify(OaNotifyBeanX oaNotify) {
        this.oaNotify = oaNotify;
    }

    public static class OaNotifyBeanX {
        /**
         * id : 5b628443d4e34279bb960b7d391a0dc1
         * isNewRecord : false
         * remarks : null
         * createDate : 2018-01-12 10:00:19
         * updateDate : 2018-01-12 10:00:19
         * type : 1
         * title : 测试
         * content : 文本A
         * files : ,
         * status : 1
         * readNum : 0
         * unReadNum : 2
         * readFlag : null
         * oaNotifyRecordList : [{"id":"74aee07711d7488398ece4ee9429c4c6","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"oaNotify":{"id":"5b628443d4e34279bb960b7d391a0dc1","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"type":null,"title":null,"content":null,"files":null,"status":null,"readNum":null,"unReadNum":null,"readFlag":null,"oaNotifyRecordList":[],"oaNotifyRecordIds":"","oaNotifyRecordNames":"","self":false},"user":{"id":"9b44211d04574136b414955cc16f9468","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"李冰冰","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"readFlag":"0","readDate":null,"readReply":null},{"id":"d961d4c096ea49c6a87cb09ec1e40d6b","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"oaNotify":{"id":"5b628443d4e34279bb960b7d391a0dc1","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"type":null,"title":null,"content":null,"files":null,"status":null,"readNum":null,"unReadNum":null,"readFlag":null,"oaNotifyRecordList":[],"oaNotifyRecordIds":"","oaNotifyRecordNames":"","self":false},"user":{"id":"7374fe91d19a4b739ae649334c0cc273","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"林心如","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"readFlag":"0","readDate":null,"readReply":null}]
         * oaNotifyRecordIds : 9b44211d04574136b414955cc16f9468,7374fe91d19a4b739ae649334c0cc273
         * oaNotifyRecordNames : 李冰冰,林心如
         * self : false
         */

        private String id;
        private boolean isNewRecord;
        private Object remarks;
        private String createDate;
        private String updateDate;
        private String type;
        private String title;
        private String content;
        private String files;
        private String status;
        private String readNum;
        private String unReadNum;
        private Object readFlag;
        private String oaNotifyRecordIds;
        private String oaNotifyRecordNames;
        private boolean self;
        private List<OaNotifyRecordListBean> oaNotifyRecordList;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFiles() {
            return files;
        }

        public void setFiles(String files) {
            this.files = files;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReadNum() {
            return readNum;
        }

        public void setReadNum(String readNum) {
            this.readNum = readNum;
        }

        public String getUnReadNum() {
            return unReadNum;
        }

        public void setUnReadNum(String unReadNum) {
            this.unReadNum = unReadNum;
        }

        public Object getReadFlag() {
            return readFlag;
        }

        public void setReadFlag(Object readFlag) {
            this.readFlag = readFlag;
        }

        public String getOaNotifyRecordIds() {
            return oaNotifyRecordIds;
        }

        public void setOaNotifyRecordIds(String oaNotifyRecordIds) {
            this.oaNotifyRecordIds = oaNotifyRecordIds;
        }

        public String getOaNotifyRecordNames() {
            return oaNotifyRecordNames;
        }

        public void setOaNotifyRecordNames(String oaNotifyRecordNames) {
            this.oaNotifyRecordNames = oaNotifyRecordNames;
        }

        public boolean isSelf() {
            return self;
        }

        public void setSelf(boolean self) {
            this.self = self;
        }

        public List<OaNotifyRecordListBean> getOaNotifyRecordList() {
            return oaNotifyRecordList;
        }

        public void setOaNotifyRecordList(List<OaNotifyRecordListBean> oaNotifyRecordList) {
            this.oaNotifyRecordList = oaNotifyRecordList;
        }

        public static class OaNotifyRecordListBean {
            /**
             * id : 74aee07711d7488398ece4ee9429c4c6
             * isNewRecord : false
             * remarks : null
             * createDate : null
             * updateDate : null
             * oaNotify : {"id":"5b628443d4e34279bb960b7d391a0dc1","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"type":null,"title":null,"content":null,"files":null,"status":null,"readNum":null,"unReadNum":null,"readFlag":null,"oaNotifyRecordList":[],"oaNotifyRecordIds":"","oaNotifyRecordNames":"","self":false}
             * user : {"id":"9b44211d04574136b414955cc16f9468","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"李冰冰","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""}
             * readFlag : 0
             * readDate : null
             * readReply : null
             */

            private String id;
            private boolean isNewRecord;
            private Object remarks;
            private Object createDate;
            private Object updateDate;
            private OaNotifyBean oaNotify;
            private UserBean user;
            private String readFlag;
            private Object readDate;
            private Object readReply;

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

            public OaNotifyBean getOaNotify() {
                return oaNotify;
            }

            public void setOaNotify(OaNotifyBean oaNotify) {
                this.oaNotify = oaNotify;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public String getReadFlag() {
                return readFlag;
            }

            public void setReadFlag(String readFlag) {
                this.readFlag = readFlag;
            }

            public Object getReadDate() {
                return readDate;
            }

            public void setReadDate(Object readDate) {
                this.readDate = readDate;
            }

            public Object getReadReply() {
                return readReply;
            }

            public void setReadReply(Object readReply) {
                this.readReply = readReply;
            }

            public static class OaNotifyBean {
                /**
                 * id : 5b628443d4e34279bb960b7d391a0dc1
                 * isNewRecord : false
                 * remarks : null
                 * createDate : null
                 * updateDate : null
                 * type : null
                 * title : null
                 * content : null
                 * files : null
                 * status : null
                 * readNum : null
                 * unReadNum : null
                 * readFlag : null
                 * oaNotifyRecordList : []
                 * oaNotifyRecordIds :
                 * oaNotifyRecordNames :
                 * self : false
                 */

                private String id;
                private boolean isNewRecord;
                private Object remarks;
                private Object createDate;
                private Object updateDate;
                private Object type;
                private Object title;
                private Object content;
                private Object files;
                private Object status;
                private Object readNum;
                private Object unReadNum;
                private Object readFlag;
                private String oaNotifyRecordIds;
                private String oaNotifyRecordNames;
                private boolean self;
                private List<?> oaNotifyRecordList;

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

                public Object getType() {
                    return type;
                }

                public void setType(Object type) {
                    this.type = type;
                }

                public Object getTitle() {
                    return title;
                }

                public void setTitle(Object title) {
                    this.title = title;
                }

                public Object getContent() {
                    return content;
                }

                public void setContent(Object content) {
                    this.content = content;
                }

                public Object getFiles() {
                    return files;
                }

                public void setFiles(Object files) {
                    this.files = files;
                }

                public Object getStatus() {
                    return status;
                }

                public void setStatus(Object status) {
                    this.status = status;
                }

                public Object getReadNum() {
                    return readNum;
                }

                public void setReadNum(Object readNum) {
                    this.readNum = readNum;
                }

                public Object getUnReadNum() {
                    return unReadNum;
                }

                public void setUnReadNum(Object unReadNum) {
                    this.unReadNum = unReadNum;
                }

                public Object getReadFlag() {
                    return readFlag;
                }

                public void setReadFlag(Object readFlag) {
                    this.readFlag = readFlag;
                }

                public String getOaNotifyRecordIds() {
                    return oaNotifyRecordIds;
                }

                public void setOaNotifyRecordIds(String oaNotifyRecordIds) {
                    this.oaNotifyRecordIds = oaNotifyRecordIds;
                }

                public String getOaNotifyRecordNames() {
                    return oaNotifyRecordNames;
                }

                public void setOaNotifyRecordNames(String oaNotifyRecordNames) {
                    this.oaNotifyRecordNames = oaNotifyRecordNames;
                }

                public boolean isSelf() {
                    return self;
                }

                public void setSelf(boolean self) {
                    this.self = self;
                }

                public List<?> getOaNotifyRecordList() {
                    return oaNotifyRecordList;
                }

                public void setOaNotifyRecordList(List<?> oaNotifyRecordList) {
                    this.oaNotifyRecordList = oaNotifyRecordList;
                }
            }

            public static class UserBean {
                /**
                 * id : 9b44211d04574136b414955cc16f9468
                 * isNewRecord : false
                 * remarks : null
                 * createDate : null
                 * updateDate : null
                 * loginName : null
                 * no : null
                 * name : 李冰冰
                 * email : null
                 * phone : null
                 * mobile : null
                 * userType : null
                 * loginIp : null
                 * loginDate : null
                 * loginFlag : 1
                 * photo : null
                 * qrCode : null
                 * oldLoginName : null
                 * newPassword : null
                 * sign : null
                 * ys7Id : null
                 * oldLoginIp : null
                 * oldLoginDate : null
                 * role : null
                 * admin : false
                 * roleNames :
                 */

                private String id;
                private boolean isNewRecord;
                private Object remarks;
                private Object createDate;
                private Object updateDate;
                private Object loginName;
                private Object no;
                private String name;
                private Object email;
                private Object phone;
                private Object mobile;
                private Object userType;
                private Object loginIp;
                private Object loginDate;
                private String loginFlag;
                private Object photo;
                private Object qrCode;
                private Object oldLoginName;
                private Object newPassword;
                private Object sign;
                private Object ys7Id;
                private Object oldLoginIp;
                private Object oldLoginDate;
                private Object role;
                private boolean admin;
                private String roleNames;

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

                public Object getLoginName() {
                    return loginName;
                }

                public void setLoginName(Object loginName) {
                    this.loginName = loginName;
                }

                public Object getNo() {
                    return no;
                }

                public void setNo(Object no) {
                    this.no = no;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getEmail() {
                    return email;
                }

                public void setEmail(Object email) {
                    this.email = email;
                }

                public Object getPhone() {
                    return phone;
                }

                public void setPhone(Object phone) {
                    this.phone = phone;
                }

                public Object getMobile() {
                    return mobile;
                }

                public void setMobile(Object mobile) {
                    this.mobile = mobile;
                }

                public Object getUserType() {
                    return userType;
                }

                public void setUserType(Object userType) {
                    this.userType = userType;
                }

                public Object getLoginIp() {
                    return loginIp;
                }

                public void setLoginIp(Object loginIp) {
                    this.loginIp = loginIp;
                }

                public Object getLoginDate() {
                    return loginDate;
                }

                public void setLoginDate(Object loginDate) {
                    this.loginDate = loginDate;
                }

                public String getLoginFlag() {
                    return loginFlag;
                }

                public void setLoginFlag(String loginFlag) {
                    this.loginFlag = loginFlag;
                }

                public Object getPhoto() {
                    return photo;
                }

                public void setPhoto(Object photo) {
                    this.photo = photo;
                }

                public Object getQrCode() {
                    return qrCode;
                }

                public void setQrCode(Object qrCode) {
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

                public Object getSign() {
                    return sign;
                }

                public void setSign(Object sign) {
                    this.sign = sign;
                }

                public Object getYs7Id() {
                    return ys7Id;
                }

                public void setYs7Id(Object ys7Id) {
                    this.ys7Id = ys7Id;
                }

                public Object getOldLoginIp() {
                    return oldLoginIp;
                }

                public void setOldLoginIp(Object oldLoginIp) {
                    this.oldLoginIp = oldLoginIp;
                }

                public Object getOldLoginDate() {
                    return oldLoginDate;
                }

                public void setOldLoginDate(Object oldLoginDate) {
                    this.oldLoginDate = oldLoginDate;
                }

                public Object getRole() {
                    return role;
                }

                public void setRole(Object role) {
                    this.role = role;
                }

                public boolean isAdmin() {
                    return admin;
                }

                public void setAdmin(boolean admin) {
                    this.admin = admin;
                }

                public String getRoleNames() {
                    return roleNames;
                }

                public void setRoleNames(String roleNames) {
                    this.roleNames = roleNames;
                }
            }
        }
    }
}
