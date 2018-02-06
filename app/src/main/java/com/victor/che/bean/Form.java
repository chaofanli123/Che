package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */

public class Form implements Serializable{

    /**
     * govAquLaw : {"id":null,"isNewRecord":true,"remarks":null,"createDate":null,"updateDate":null,"lawName":null,"farm":null,"lawWaters":null,"lawAqu":null,"lawMed":null,"lawPro":null,"lawSal":null,"lawDeli":null,"lawMedi":null,"lawQual":null,"lawTech":null,"lawSta":null,"lawOld":null,"lawTrea":null,"lawProb":null,"lawOther":null,"psonName":null,"userName":null,"lawTime":null,"lawDate":null,"begin":null,"end":null}
     * farms : [{"id":"a74dc4275d784224afeda4a267148e3a","isNewRecord":false,"remarks":"","createDate":"2018-01-24 04:18:34","updateDate":"2018-02-05 15:13:52","farmName":"信阳市南湾水库良种场","area":{"id":"411524","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"商城县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省信阳市商城县","zipcode":"465311","species":"养殖品种","farmMethod":"1","cultureArea":1000,"source":"2","annualOutput":"10000","companyType":"1","foundedTime":"2018-01-23","telephone":"15236598452","begin":null,"end":null},{"id":"98c020a5c0dc48e39ddccb80ad041f69","isNewRecord":false,"remarks":"","createDate":"2018-01-24 04:24:59","updateDate":"2018-02-05 15:13:48","farmName":"兰考腾飞合作社","area":{"id":"410225","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"兰考县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省开封市兰考县","zipcode":"475313","species":"养殖品种","farmMethod":"2","cultureArea":1000,"source":"2","annualOutput":"100","companyType":"1","foundedTime":"2018-01-23","telephone":"15023012036","begin":null,"end":null},{"id":"4ac9f58843304889843a5ad76ef76830","isNewRecord":false,"remarks":"","createDate":"2018-01-24 06:21:29","updateDate":"2018-02-05 15:13:42","farmName":"南阳方城良种场","area":{"id":"411322","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"方城县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省南阳市方城县","zipcode":"451100","species":"淡水鱼","farmMethod":"2","cultureArea":23.21,"source":"1","annualOutput":"23","companyType":"1","foundedTime":"2018-01-23","telephone":"15023012036","begin":null,"end":null},{"id":"983c8f2446b74598902d53044d7a11bc","isNewRecord":false,"remarks":"","createDate":"2018-01-24 06:22:11","updateDate":"2018-02-05 15:13:27","farmName":"商丘和昌饲料有限公司","area":{"id":"411421","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"民权县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省商丘市民权县","zipcode":"451100","species":"淡水鱼","farmMethod":"2","cultureArea":2.3,"source":"1","annualOutput":"23","companyType":"4","foundedTime":"2018-01-23","telephone":"15236598452","begin":null,"end":null},{"id":"0edba9f7ed964ca6a1b60ebce7541757","isNewRecord":false,"remarks":"","createDate":"2018-01-24 08:56:33","updateDate":"2018-02-05 15:13:06","farmName":"固始金海养殖场","area":{"id":"411525","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"固始县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省信阳市固始县","zipcode":"451100","species":"淡水鱼","farmMethod":"2","cultureArea":60.75,"source":"1","annualOutput":"10","companyType":"2","foundedTime":"2018-01-23","telephone":"15236598452","begin":null,"end":null},{"id":"94b7cfa178844da38b0d426d8c0188a7","isNewRecord":false,"remarks":"","createDate":"2018-01-24 09:03:16","updateDate":"2018-02-05 15:12:55","farmName":"栾川辉煌大鲵养殖场","area":{"id":"410324","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"栾川县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省洛阳市栾川县","zipcode":"451100","species":"淡水鱼","farmMethod":"2","cultureArea":2.3,"source":"1","annualOutput":"10","companyType":"5","foundedTime":"2018-01-23","telephone":"15236598452","begin":null,"end":null},{"id":"556467710fce4d0d96c6dbd48c7fadae","isNewRecord":false,"remarks":"","createDate":"2018-01-24 09:03:55","updateDate":"2018-02-05 15:12:17","farmName":"灵宝江辉养殖合作社","area":{"id":"411282","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"灵宝市","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省灵宝市","zipcode":"451100","species":"淡水鱼","farmMethod":"5","cultureArea":3.21,"source":"2","annualOutput":"23","companyType":"3","foundedTime":"2018-01-23","telephone":"15023012036","begin":null,"end":null},{"id":"022f919ff1564f86ac81905e9da905c7","isNewRecord":false,"remarks":"","createDate":"2018-01-24 09:06:06","updateDate":"2018-02-05 15:11:53","farmName":"荥阳水产良种场","area":{"id":"410182","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"荥阳市","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省荥阳市","zipcode":"451100","species":"凡人歌同意","farmMethod":"3","cultureArea":2.3,"source":"1","annualOutput":"10","companyType":"2","foundedTime":"2018-01-23","telephone":"15023012036","begin":null,"end":null},{"id":"3f71d60134dd4441b21ace355fb53866","isNewRecord":false,"remarks":"","createDate":"2018-01-24 09:07:16","updateDate":"2018-02-05 15:11:27","farmName":"驻马店宿鸭湖水库良种场","area":{"id":"411702","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"驿城区","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省驻马店市","zipcode":"451102","species":"淡水鱼","farmMethod":"3","cultureArea":3.21,"source":"1","annualOutput":"23","companyType":"3","foundedTime":"2018-01-23","telephone":"15236598452","begin":null,"end":null},{"id":"2e53f3b61e914a31a599cf97d087c269","isNewRecord":false,"remarks":"","createDate":"2018-01-24 08:57:25","updateDate":"2018-01-24 16:57:25","farmName":"新乡原阳豫黄合作社","area":{"id":"410725","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"原阳县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省新乡市原阳县","zipcode":"","species":"","farmMethod":"4","cultureArea":0,"source":"2","annualOutput":"10","companyType":"3","foundedTime":"2018-01-23","telephone":"15326547854","begin":null,"end":null}]
     */

    private GovAquLawBean govAquLaw;
    private List<FarmsBean> farms;

    public GovAquLawBean getGovAquLaw() {
        return govAquLaw;
    }

    public void setGovAquLaw(GovAquLawBean govAquLaw) {
        this.govAquLaw = govAquLaw;
    }

    public List<FarmsBean> getFarms() {
        return farms;
    }

    public void setFarms(List<FarmsBean> farms) {
        this.farms = farms;
    }

    public static class GovAquLawBean {
        /**
         * id : null
         * isNewRecord : true
         * remarks : null
         * createDate : null
         * updateDate : null
         * lawName : null
         * farm : null
         * lawWaters : null
         * lawAqu : null
         * lawMed : null
         * lawPro : null
         * lawSal : null
         * lawDeli : null
         * lawMedi : null
         * lawQual : null
         * lawTech : null
         * lawSta : null
         * lawOld : null
         * lawTrea : null
         * lawProb : null
         * lawOther : null
         * psonName : null
         * userName : null
         * lawTime : null
         * lawDate : null
         * begin : null
         * end : null
         */

        private Object id;
        private boolean isNewRecord;
        private Object remarks;
        private Object createDate;
        private Object updateDate;
        private Object lawName;
        private Object farm;
        private Object lawWaters;
        private Object lawAqu;
        private Object lawMed;
        private Object lawPro;
        private Object lawSal;
        private Object lawDeli;
        private Object lawMedi;
        private Object lawQual;
        private Object lawTech;
        private Object lawSta;
        private Object lawOld;
        private Object lawTrea;
        private Object lawProb;
        private Object lawOther;
        private Object psonName;
        private Object userName;
        private Object lawTime;
        private Object lawDate;
        private Object begin;
        private Object end;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
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

        public Object getLawName() {
            return lawName;
        }

        public void setLawName(Object lawName) {
            this.lawName = lawName;
        }

        public Object getFarm() {
            return farm;
        }

        public void setFarm(Object farm) {
            this.farm = farm;
        }

        public Object getLawWaters() {
            return lawWaters;
        }

        public void setLawWaters(Object lawWaters) {
            this.lawWaters = lawWaters;
        }

        public Object getLawAqu() {
            return lawAqu;
        }

        public void setLawAqu(Object lawAqu) {
            this.lawAqu = lawAqu;
        }

        public Object getLawMed() {
            return lawMed;
        }

        public void setLawMed(Object lawMed) {
            this.lawMed = lawMed;
        }

        public Object getLawPro() {
            return lawPro;
        }

        public void setLawPro(Object lawPro) {
            this.lawPro = lawPro;
        }

        public Object getLawSal() {
            return lawSal;
        }

        public void setLawSal(Object lawSal) {
            this.lawSal = lawSal;
        }

        public Object getLawDeli() {
            return lawDeli;
        }

        public void setLawDeli(Object lawDeli) {
            this.lawDeli = lawDeli;
        }

        public Object getLawMedi() {
            return lawMedi;
        }

        public void setLawMedi(Object lawMedi) {
            this.lawMedi = lawMedi;
        }

        public Object getLawQual() {
            return lawQual;
        }

        public void setLawQual(Object lawQual) {
            this.lawQual = lawQual;
        }

        public Object getLawTech() {
            return lawTech;
        }

        public void setLawTech(Object lawTech) {
            this.lawTech = lawTech;
        }

        public Object getLawSta() {
            return lawSta;
        }

        public void setLawSta(Object lawSta) {
            this.lawSta = lawSta;
        }

        public Object getLawOld() {
            return lawOld;
        }

        public void setLawOld(Object lawOld) {
            this.lawOld = lawOld;
        }

        public Object getLawTrea() {
            return lawTrea;
        }

        public void setLawTrea(Object lawTrea) {
            this.lawTrea = lawTrea;
        }

        public Object getLawProb() {
            return lawProb;
        }

        public void setLawProb(Object lawProb) {
            this.lawProb = lawProb;
        }

        public Object getLawOther() {
            return lawOther;
        }

        public void setLawOther(Object lawOther) {
            this.lawOther = lawOther;
        }

        public Object getPsonName() {
            return psonName;
        }

        public void setPsonName(Object psonName) {
            this.psonName = psonName;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }

        public Object getLawTime() {
            return lawTime;
        }

        public void setLawTime(Object lawTime) {
            this.lawTime = lawTime;
        }

        public Object getLawDate() {
            return lawDate;
        }

        public void setLawDate(Object lawDate) {
            this.lawDate = lawDate;
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

    public static class FarmsBean {
        /**
         * id : a74dc4275d784224afeda4a267148e3a
         * isNewRecord : false
         * remarks :
         * createDate : 2018-01-24 04:18:34
         * updateDate : 2018-02-05 15:13:52
         * farmName : 信阳市南湾水库良种场
         * area : {"id":"411524","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"商城县","sort":30,"code":null,"type":null,"parentId":"0"}
         * address : 河南省信阳市商城县
         * zipcode : 465311
         * species : 养殖品种
         * farmMethod : 1
         * cultureArea : 1000.0
         * source : 2
         * annualOutput : 10000
         * companyType : 1
         * foundedTime : 2018-01-23
         * telephone : 15236598452
         * begin : null
         * end : null
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String farmName;
        private AreaBean area;
        private String address;
        private String zipcode;
        private String species;
        private String farmMethod;
        private double cultureArea;
        private String source;
        private String annualOutput;
        private String companyType;
        private String foundedTime;
        private String telephone;
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

        public double getCultureArea() {
            return cultureArea;
        }

        public void setCultureArea(double cultureArea) {
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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
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

        public static class AreaBean {
            /**
             * id : 411524
             * isNewRecord : false
             * remarks : null
             * createDate : null
             * updateDate : null
             * parentIds : null
             * name : 商城县
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
