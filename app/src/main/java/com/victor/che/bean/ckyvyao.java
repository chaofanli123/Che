package com.victor.che.bean;

/**
 * Created by Administrator on 2018/1/23.
 */

public class ckyvyao {

    /**
     * fishDrug : {"id":"dc513426f4754252b8055e463f463c73","isNewRecord":false,"remarks":null,"createDate":"2017-11-13 07:46:08","updateDate":"2017-11-13 07:46:08","code":5,"medicineName":"呋喃唑酮及制剂","englishName":"Furazolidone","anotherName":"痢特灵","referenceBasis":"农业部第193号公告  农业部31号令"}
     */

    private FishDrugBean fishDrug;

    public FishDrugBean getFishDrug() {
        return fishDrug;
    }

    public void setFishDrug(FishDrugBean fishDrug) {
        this.fishDrug = fishDrug;
    }

    public static class FishDrugBean {
        /**
         * id : dc513426f4754252b8055e463f463c73
         * isNewRecord : false
         * remarks : null
         * createDate : 2017-11-13 07:46:08
         * updateDate : 2017-11-13 07:46:08
         * code : 5
         * medicineName : 呋喃唑酮及制剂
         * englishName : Furazolidone
         * anotherName : 痢特灵
         * referenceBasis : 农业部第193号公告  农业部31号令
         */

        private String id;
        private boolean isNewRecord;
        private Object remarks;
        private String createDate;
        private String updateDate;
        private int code;
        private String medicineName;
        private String englishName;
        private String anotherName;
        private String referenceBasis;

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

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public void setMedicineName(String medicineName) {
            this.medicineName = medicineName;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getAnotherName() {
            return anotherName;
        }

        public void setAnotherName(String anotherName) {
            this.anotherName = anotherName;
        }

        public String getReferenceBasis() {
            return referenceBasis;
        }

        public void setReferenceBasis(String referenceBasis) {
            this.referenceBasis = referenceBasis;
        }
    }
}
