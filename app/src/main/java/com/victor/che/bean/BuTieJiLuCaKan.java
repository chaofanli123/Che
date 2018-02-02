package com.victor.che.bean;

/**
 * Created by Administrator on 2018/2/2.
 */

public class BuTieJiLuCaKan {


    /**
     * govFirmSubsidy : {"id":"027fb3f1569346258e7232513685def9","isNewRecord":false,"remarks":"999","createDate":"2018-02-02 02:11:15","updateDate":"2018-02-02 14:16:10","firm":{"id":"2e53f3b61e914a31a599cf97d087c269","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"新乡原阳豫黄合作社","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null},"year":"2017","subsidyItem":"事实上没有","subsidyMoney":"2000.00","grantFirm":"中央","grantDate":1515340800000,"receipt":"|/aims/userfiles/1/images/sta/staFirmSubsidy/2018/02/u%3D1618097094%2C4154452434%26fm%3D77%26w_h%3D121_75%26cs%3D423647557%2C799948659.jpg|/aims/userfiles/1/images/sta/staFirmSubsidy/2018/02/fb_bg.png"}
     */

    private GovFirmSubsidyBean govFirmSubsidy;

    public GovFirmSubsidyBean getGovFirmSubsidy() {
        return govFirmSubsidy;
    }

    public void setGovFirmSubsidy(GovFirmSubsidyBean govFirmSubsidy) {
        this.govFirmSubsidy = govFirmSubsidy;
    }

    public static class GovFirmSubsidyBean {
        /**
         * id : 027fb3f1569346258e7232513685def9
         * isNewRecord : false
         * remarks : 999
         * createDate : 2018-02-02 02:11:15
         * updateDate : 2018-02-02 14:16:10
         * firm : {"id":"2e53f3b61e914a31a599cf97d087c269","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"新乡原阳豫黄合作社","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null}
         * year : 2017
         * subsidyItem : 事实上没有
         * subsidyMoney : 2000.00
         * grantFirm : 中央
         * grantDate : 1515340800000
         * receipt : |/aims/userfiles/1/images/sta/staFirmSubsidy/2018/02/u%3D1618097094%2C4154452434%26fm%3D77%26w_h%3D121_75%26cs%3D423647557%2C799948659.jpg|/aims/userfiles/1/images/sta/staFirmSubsidy/2018/02/fb_bg.png
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private FirmBean firm;
        private String year;
        private String subsidyItem;
        private String subsidyMoney;
        private String grantFirm;
        private long grantDate;
        private String receipt;

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

        public FirmBean getFirm() {
            return firm;
        }

        public void setFirm(FirmBean firm) {
            this.firm = firm;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getSubsidyItem() {
            return subsidyItem;
        }

        public void setSubsidyItem(String subsidyItem) {
            this.subsidyItem = subsidyItem;
        }

        public String getSubsidyMoney() {
            return subsidyMoney;
        }

        public void setSubsidyMoney(String subsidyMoney) {
            this.subsidyMoney = subsidyMoney;
        }

        public String getGrantFirm() {
            return grantFirm;
        }

        public void setGrantFirm(String grantFirm) {
            this.grantFirm = grantFirm;
        }

        public long getGrantDate() {
            return grantDate;
        }

        public void setGrantDate(long grantDate) {
            this.grantDate = grantDate;
        }

        public String getReceipt() {
            return receipt;
        }

        public void setReceipt(String receipt) {
            this.receipt = receipt;
        }

        public static class FirmBean {
            /**
             * id : 2e53f3b61e914a31a599cf97d087c269
             * isNewRecord : false
             * remarks : null
             * createDate : null
             * updateDate : null
             * farmName : 新乡原阳豫黄合作社
             * area : null
             * address : null
             * zipcode : null
             * species : null
             * farmMethod : null
             * cultureArea : null
             * source : null
             * annualOutput : null
             * companyType : null
             * foundedTime : null
             * telephone : null
             * begin : null
             * end : null
             */

            private String id;
            private boolean isNewRecord;
            private Object remarks;
            private Object createDate;
            private Object updateDate;
            private String farmName;
            private Object area;
            private Object address;
            private Object zipcode;
            private Object species;
            private Object farmMethod;
            private Object cultureArea;
            private Object source;
            private Object annualOutput;
            private Object companyType;
            private Object foundedTime;
            private Object telephone;
            private Object begin;
            private Object end;

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

            public String getFarmName() {
                return farmName;
            }

            public void setFarmName(String farmName) {
                this.farmName = farmName;
            }

            public Object getArea() {
                return area;
            }

            public void setArea(Object area) {
                this.area = area;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getZipcode() {
                return zipcode;
            }

            public void setZipcode(Object zipcode) {
                this.zipcode = zipcode;
            }

            public Object getSpecies() {
                return species;
            }

            public void setSpecies(Object species) {
                this.species = species;
            }

            public Object getFarmMethod() {
                return farmMethod;
            }

            public void setFarmMethod(Object farmMethod) {
                this.farmMethod = farmMethod;
            }

            public Object getCultureArea() {
                return cultureArea;
            }

            public void setCultureArea(Object cultureArea) {
                this.cultureArea = cultureArea;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }

            public Object getAnnualOutput() {
                return annualOutput;
            }

            public void setAnnualOutput(Object annualOutput) {
                this.annualOutput = annualOutput;
            }

            public Object getCompanyType() {
                return companyType;
            }

            public void setCompanyType(Object companyType) {
                this.companyType = companyType;
            }

            public Object getFoundedTime() {
                return foundedTime;
            }

            public void setFoundedTime(Object foundedTime) {
                this.foundedTime = foundedTime;
            }

            public Object getTelephone() {
                return telephone;
            }

            public void setTelephone(Object telephone) {
                this.telephone = telephone;
            }

            public Object getBegin() {
                return begin;
            }

            public void setBegin(Object begin) {
                this.begin = begin;
            }

            public Object getEnd() {
                return end;
            }

            public void setEnd(Object end) {
                this.end = end;
            }
        }
    }
}
