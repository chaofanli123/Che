package com.victor.che.domain;

import java.io.Serializable;

/**
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月22日 下午3:27:21
 */
public class AppVersion implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * data : {"id":"562182a5ded84c1b9758aba2bb0555ae","isNewRecord":false,"remarks":"","createDate":"2018-02-24 03:42:04","updateDate":"2018-02-24 11:42:04","ver":2,"showVer":"1.0.1","downPath":"userfiles/1/apk/appDownload/2018/02/app1_0_1.apk","qrcode":"/aims/userfiles/app/qrcode/20180224114203.png","msg":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 562182a5ded84c1b9758aba2bb0555ae
         * isNewRecord : false
         * remarks :
         * createDate : 2018-02-24 03:42:04
         * updateDate : 2018-02-24 11:42:04
         * ver : 2
         * showVer : 1.0.1
         * downPath : userfiles/1/apk/appDownload/2018/02/app1_0_1.apk
         * qrcode : /aims/userfiles/app/qrcode/20180224114203.png
         * msg : null
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private int ver;
        private String showVer;
        private String downPath;
        private String qrcode;
        private Object msg;

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

        public int getVer() {
            return ver;
        }

        public void setVer(int ver) {
            this.ver = ver;
        }

        public String getShowVer() {
            return showVer;
        }

        public void setShowVer(String showVer) {
            this.showVer = showVer;
        }

        public String getDownPath() {
            return downPath;
        }

        public void setDownPath(String downPath) {
            this.downPath = downPath;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public Object getMsg() {
            return msg;
        }

        public void setMsg(Object msg) {
            this.msg = msg;
        }
    }
}
