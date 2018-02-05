package com.victor.che.bean;

/**
 * Created by jcc on 2018/1/28.
 */

public class YangZhiChangXiangQing {


    /**
     * appAquFarm : {"id":"931d4eb8b2264636a16ed72366da2fb7","isNewRecord":false,"remarks":"河南省规模水产养殖场信息管理系统建设根据河南省农业厅水产局对规模水产养殖场管理监督工作要求，实现我省规模水产养殖场空间分析查询、企业舆情监管、统计分析预警、信息发布管理、系统管理配置等功能。","createDate":"2018-01-15 10:23:10","updateDate":"2018-01-25 10:07:51","aquFarm":{"id":"2e53f3b61e914a31a599cf97d087c269","isNewRecord":false,"remarks":"","createDate":"2018-01-24 08:57:25","updateDate":"2018-01-24 16:57:25","farmName":"新乡原阳豫黄合作社","area":{"id":"410725","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"原阳县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省新乡市原阳县","zipcode":"","species":"","farmMethod":"4","cultureArea":0,"source":"2","annualOutput":"10","companyType":"3","foundedTime":"2018-01-23","telephone":"15326547854","begin":null,"end":null},"coordinateX":"12684663.249996","coordinateY":"4170979.749999","picture":"/aims/userfiles/1/images/firminfo/staFirm/2018/01/LBXB6(1).png","mainProduct":"渔业、水产养殖","comSynopsis":"水产养殖场信息管理主要通过对养殖场视频信息、水质信息、气象信息、地理空间信息、养殖场基础信息、渔政信息的采集、处理、加工形成水产养殖综合管理数据库，该数据库主要为渔政管理人员提供水产养殖企业信息查询、认证管理、舆情管理、空间查询等服务，同时通过综合数据库，完成对我省规模产养殖情况的统计分析及相关水产养殖场信息的发布功能。","end":null,"begin":null}
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
         * id : 931d4eb8b2264636a16ed72366da2fb7
         * isNewRecord : false
         * remarks : 河南省规模水产养殖场信息管理系统建设根据河南省农业厅水产局对规模水产养殖场管理监督工作要求，实现我省规模水产养殖场空间分析查询、企业舆情监管、统计分析预警、信息发布管理、系统管理配置等功能。
         * createDate : 2018-01-15 10:23:10
         * updateDate : 2018-01-25 10:07:51
         * aquFarm : {"id":"2e53f3b61e914a31a599cf97d087c269","isNewRecord":false,"remarks":"","createDate":"2018-01-24 08:57:25","updateDate":"2018-01-24 16:57:25","farmName":"新乡原阳豫黄合作社","area":{"id":"410725","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"原阳县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省新乡市原阳县","zipcode":"","species":"","farmMethod":"4","cultureArea":0,"source":"2","annualOutput":"10","companyType":"3","foundedTime":"2018-01-23","telephone":"15326547854","begin":null,"end":null}
         * coordinateX : 12684663.249996
         * coordinateY : 4170979.749999
         * picture : /aims/userfiles/1/images/firminfo/staFirm/2018/01/LBXB6(1).png
         * mainProduct : 渔业、水产养殖
         * comSynopsis : 水产养殖场信息管理主要通过对养殖场视频信息、水质信息、气象信息、地理空间信息、养殖场基础信息、渔政信息的采集、处理、加工形成水产养殖综合管理数据库，该数据库主要为渔政管理人员提供水产养殖企业信息查询、认证管理、舆情管理、空间查询等服务，同时通过综合数据库，完成对我省规模产养殖情况的统计分析及相关水产养殖场信息的发布功能。
         * end : null
         * begin : null
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private AquFarmBean aquFarm;
        private String coordinateX;
        private String coordinateY;
        private String picture;
        private String mainProduct;
        private String comSynopsis;
        private Object end;
        private Object begin;

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

        public AquFarmBean getAquFarm() {
            return aquFarm;
        }

        public void setAquFarm(AquFarmBean aquFarm) {
            this.aquFarm = aquFarm;
        }

        public String getCoordinateX() {
            return coordinateX;
        }

        public void setCoordinateX(String coordinateX) {
            this.coordinateX = coordinateX;
        }

        public String getCoordinateY() {
            return coordinateY;
        }

        public void setCoordinateY(String coordinateY) {
            this.coordinateY = coordinateY;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getMainProduct() {
            return mainProduct;
        }

        public void setMainProduct(String mainProduct) {
            this.mainProduct = mainProduct;
        }

        public String getComSynopsis() {
            return comSynopsis;
        }

        public void setComSynopsis(String comSynopsis) {
            this.comSynopsis = comSynopsis;
        }

        public Object getEnd() {
            return end;
        }

        public void setEnd(Object end) {
            this.end = end;
        }

        public Object getBegin() {
            return begin;
        }

        public void setBegin(Object begin) {
            this.begin = begin;
        }

        public static class AquFarmBean {
            /**
             * id : 2e53f3b61e914a31a599cf97d087c269
             * isNewRecord : false
             * remarks :
             * createDate : 2018-01-24 08:57:25
             * updateDate : 2018-01-24 16:57:25
             * farmName : 新乡原阳豫黄合作社
             * area : {"id":"410725","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"原阳县","sort":30,"code":null,"type":null,"parentId":"0"}
             * address : 河南省新乡市原阳县
             * zipcode :
             * species :
             * farmMethod : 4
             * cultureArea : 0.0
             * source : 2
             * annualOutput : 10
             * companyType : 3
             * foundedTime : 2018-01-23
             * telephone : 15326547854
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
                 * id : 410725
                 * isNewRecord : false
                 * remarks : null
                 * createDate : null
                 * updateDate : null
                 * parentIds : null
                 * name : 原阳县
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
}
