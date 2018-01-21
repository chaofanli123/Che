package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jcc on 2018/1/21.
 */

public class Notify implements Serializable {

    /**
     * type : 1
     * title : 测试
     * content : 文本A
     * files : ,
     * status : 1
     * readNum : 0
     * unReadNum : 2
     * isSelf : false
     * oaNotifyRecordList : []
     * createBy : {"loginFlag":"1","roleList":[],"delFlag":"0","id":"1","isNewRecord":false}
     * createDate : 2018-01-12 18:00:19
     * updateBy : {"loginFlag":"1","roleList":[],"delFlag":"0","id":"1","isNewRecord":false}
     * updateDate : 2018-01-12 18:00:19
     * delFlag : 0
     * id : 5b628443d4e34279bb960b7d391a0dc1
     * isNewRecord : false
     */

    private String type;
    private String title;
    private String content;
    private String files;
    private String status;
    private String readNum;
    private String unReadNum;
    private boolean isSelf;
    private CreateByBean createBy;
    private String createDate;
    private UpdateByBean updateBy;
    private String updateDate;
    private String delFlag;
    private String id;
    private boolean isNewRecord;
    private List<?> oaNotifyRecordList;

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

    public boolean isIsSelf() {
        return isSelf;
    }

    public void setIsSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

    public CreateByBean getCreateBy() {
        return createBy;
    }

    public void setCreateBy(CreateByBean createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public UpdateByBean getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(UpdateByBean updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

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

    public List<?> getOaNotifyRecordList() {
        return oaNotifyRecordList;
    }

    public void setOaNotifyRecordList(List<?> oaNotifyRecordList) {
        this.oaNotifyRecordList = oaNotifyRecordList;
    }

    public static class CreateByBean {
        /**
         * loginFlag : 1
         * roleList : []
         * delFlag : 0
         * id : 1
         * isNewRecord : false
         */

        private String loginFlag;
        private String delFlag;
        private String id;
        private boolean isNewRecord;
        private List<?> roleList;

        public String getLoginFlag() {
            return loginFlag;
        }

        public void setLoginFlag(String loginFlag) {
            this.loginFlag = loginFlag;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

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

        public List<?> getRoleList() {
            return roleList;
        }

        public void setRoleList(List<?> roleList) {
            this.roleList = roleList;
        }
    }

    public static class UpdateByBean {
        /**
         * loginFlag : 1
         * roleList : []
         * delFlag : 0
         * id : 1
         * isNewRecord : false
         */

        private String loginFlag;
        private String delFlag;
        private String id;
        private boolean isNewRecord;
        private List<?> roleList;

        public String getLoginFlag() {
            return loginFlag;
        }

        public void setLoginFlag(String loginFlag) {
            this.loginFlag = loginFlag;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

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

        public List<?> getRoleList() {
            return roleList;
        }

        public void setRoleList(List<?> roleList) {
            this.roleList = roleList;
        }
    }
}
