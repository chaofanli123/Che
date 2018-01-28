package com.victor.che.bean;

/**
 * Created by jcc on 2018/1/28.
 */

public class YangZhiChangXiangQing {

    /**
     * appAquFarm : {"id":"022f919ff1564f86ac81905e9da905c7","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 09:06:06","updateDate":"2018-01-24 09:06:06","farmName":"荥阳水产良种场","area":{"id":"410182","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"荥阳市","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省荥阳市","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null}
     */

    private AppAquFarmBean appAquFarm;

    public AppAquFarmBean getAppAquFarm() {
        return appAquFarm;
    }

    public void setAppAquFarm(AppAquFarmBean appAquFarm) {
        this.appAquFarm = appAquFarm;
    }

    public static class AppAquFarmBean {
        /**
         * id : 022f919ff1564f86ac81905e9da905c7
         * isNewRecord : false
         * remarks : null
         * createDate : 2018-01-24 09:06:06
         * updateDate : 2018-01-24 09:06:06
         * farmName : 荥阳水产良种场
         * area : {"id":"410182","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"荥阳市","sort":30,"code":null,"type":null,"parentId":"0"}
         * address : 河南省荥阳市
         * zipcode :
         * species :
         * farmMethod :
         * cultureArea : null
         * source :
         * annualOutput :
         * companyType :
         * foundedTime : 2018-01-23 16:00:00
         * telephone : null
         * begin : null
         * end : null
         * appPage : null
         * coordinateX : null
         * coordinateY : null
         * picture : null
         * mainProduct : null
         * comSynopsis : null
         */

        private String id;
        private boolean isNewRecord;
        private Object remarks;
        private String createDate;
        private String updateDate;
        private String farmName;
        private AreaBean area;
        private String address;
        private String zipcode;
        private String species;
        private String farmMethod;
        private Object cultureArea;
        private String source;
        private String annualOutput;
        private String companyType;
        private String foundedTime;
        private Object telephone;
        private Object begin;
        private Object end;
        private Object appPage;
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

        public String getFarmName() {
            return farmName;
        }

        public void setFarmName(String farmName) {
            this.farmName = farmName;
        }

        public AreaBean getArea() {
            return area;
        }

        public void setArea(AreaBean area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public String getFarmMethod() {
            return farmMethod;
        }

        public void setFarmMethod(String farmMethod) {
            this.farmMethod = farmMethod;
        }

        public Object getCultureArea() {
            return cultureArea;
        }

        public void setCultureArea(Object cultureArea) {
            this.cultureArea = cultureArea;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAnnualOutput() {
            return annualOutput;
        }

        public void setAnnualOutput(String annualOutput) {
            this.annualOutput = annualOutput;
        }

        public String getCompanyType() {
            return companyType;
        }

        public void setCompanyType(String companyType) {
            this.companyType = companyType;
        }

        public String getFoundedTime() {
            return foundedTime;
        }

        public void setFoundedTime(String foundedTime) {
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

        public Object getAppPage() {
            return appPage;
        }

        public void setAppPage(Object appPage) {
            this.appPage = appPage;
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

        public static class AreaBean {
            /**
             * id : 410182
             * isNewRecord : false
             * remarks : null
             * createDate : null
             * updateDate : null
             * parentIds : null
             * name : 荥阳市
             * sort : 30
             * code : null
             * type : null
             * parentId : 0
             */

            private String id;
            private boolean isNewRecord;
            private Object remarks;
            private Object createDate;
            private Object updateDate;
            private Object parentIds;
            private String name;
            private int sort;
            private Object code;
            private Object type;
            private String parentId;

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

            public Object getParentIds() {
                return parentIds;
            }

            public void setParentIds(Object parentIds) {
                this.parentIds = parentIds;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public Object getCode() {
                return code;
            }

            public void setCode(Object code) {
                this.code = code;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }
        }
    }
}
