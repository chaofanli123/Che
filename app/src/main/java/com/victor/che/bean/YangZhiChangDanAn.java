package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jcc on 2018/1/27.
 */

public class YangZhiChangDanAn {

    /**
     * page : {"pageNo":1,"pageSize":10,"count":0,"list":[{"id":"022f919ff1564f86ac81905e9da905c7","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 09:06:06","updateDate":"2018-01-24 09:06:06","farmName":"荥阳水产良种场","area":{"id":"410182","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"荥阳市","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省荥阳市","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"0edba9f7ed964ca6a1b60ebce7541757","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 08:56:33","updateDate":"2018-01-24 08:56:33","farmName":"固始金海养殖场","area":{"id":"411525","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"固始县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省信阳市固始县","zipcode":"","species":"","farmMethod":"","cultureArea":60.75,"source":"1","annualOutput":"10","companyType":"2","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"2e53f3b61e914a31a599cf97d087c269","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 08:57:25","updateDate":"2018-01-24 08:57:25","farmName":"新乡原阳豫黄合作社","area":{"id":"410725","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"原阳县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省新乡市原阳县","zipcode":"","species":"","farmMethod":"4","cultureArea":null,"source":"2","annualOutput":"10","companyType":"3","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"3f71d60134dd4441b21ace355fb53866","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 09:07:16","updateDate":"2018-01-24 09:07:16","farmName":"驻马店宿鸭湖水库良种场","area":{"id":"411702","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"驿城区","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省驻马店市","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"4ac9f58843304889843a5ad76ef76830","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 06:21:29","updateDate":"2018-01-24 06:21:29","farmName":"南阳方城良种场","area":{"id":"411322","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"方城县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省南阳市方城县","zipcode":"","species":"","farmMethod":"2","cultureArea":null,"source":"1","annualOutput":"","companyType":"1","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"556467710fce4d0d96c6dbd48c7fadae","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 09:03:55","updateDate":"2018-01-24 09:03:55","farmName":"灵宝江辉养殖合作社","area":{"id":"411282","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"灵宝市","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省灵宝市","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"94b7cfa178844da38b0d426d8c0188a7","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 09:03:16","updateDate":"2018-01-24 09:03:16","farmName":"栾川辉煌大鲵养殖场","area":{"id":"410324","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"栾川县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省洛阳市栾川县","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"1","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"983c8f2446b74598902d53044d7a11bc","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 06:22:11","updateDate":"2018-01-24 06:22:11","farmName":"商丘和昌饲料有限公司","area":{"id":"411421","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"民权县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省商丘市民权县","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"98c020a5c0dc48e39ddccb80ad041f69","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 04:24:59","updateDate":"2018-01-24 04:24:59","farmName":"兰考腾飞合作社","area":{"id":"410225","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"兰考县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省开封市兰考县","zipcode":"475313","species":"养殖品种","farmMethod":"2","cultureArea":1000,"source":"2","annualOutput":"100","companyType":"1","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"a74dc4275d784224afeda4a267148e3a","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 04:18:34","updateDate":"2018-01-24 04:22:51","farmName":"信阳市南湾水库良种场","area":{"id":"411524","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"商城县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省信阳市商城县","zipcode":"465311","species":"养殖品种","farmMethod":"1","cultureArea":1000,"source":"2","annualOutput":"10000","companyType":"1","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null}],"maxResults":10,"firstResult":0,"html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 0 条记录，总共 0 条记录<\/span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10<\/span> <span class=\"caret\"><\/span><\/button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100<\/a><\/li><\/ul><\/span> 条记录<\/span><\/div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button active\"><a href=\"javascript:\">1<\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"><\/i><\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"><\/i><\/a><\/li>\n<\/ul><\/div><\/div>"}
     */

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * count : 0
         * list : [{"id":"022f919ff1564f86ac81905e9da905c7","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 09:06:06","updateDate":"2018-01-24 09:06:06","farmName":"荥阳水产良种场","area":{"id":"410182","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"荥阳市","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省荥阳市","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"0edba9f7ed964ca6a1b60ebce7541757","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 08:56:33","updateDate":"2018-01-24 08:56:33","farmName":"固始金海养殖场","area":{"id":"411525","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"固始县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省信阳市固始县","zipcode":"","species":"","farmMethod":"","cultureArea":60.75,"source":"1","annualOutput":"10","companyType":"2","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"2e53f3b61e914a31a599cf97d087c269","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 08:57:25","updateDate":"2018-01-24 08:57:25","farmName":"新乡原阳豫黄合作社","area":{"id":"410725","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"原阳县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省新乡市原阳县","zipcode":"","species":"","farmMethod":"4","cultureArea":null,"source":"2","annualOutput":"10","companyType":"3","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"3f71d60134dd4441b21ace355fb53866","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 09:07:16","updateDate":"2018-01-24 09:07:16","farmName":"驻马店宿鸭湖水库良种场","area":{"id":"411702","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"驿城区","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省驻马店市","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"4ac9f58843304889843a5ad76ef76830","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 06:21:29","updateDate":"2018-01-24 06:21:29","farmName":"南阳方城良种场","area":{"id":"411322","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"方城县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省南阳市方城县","zipcode":"","species":"","farmMethod":"2","cultureArea":null,"source":"1","annualOutput":"","companyType":"1","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"556467710fce4d0d96c6dbd48c7fadae","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 09:03:55","updateDate":"2018-01-24 09:03:55","farmName":"灵宝江辉养殖合作社","area":{"id":"411282","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"灵宝市","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省灵宝市","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"94b7cfa178844da38b0d426d8c0188a7","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 09:03:16","updateDate":"2018-01-24 09:03:16","farmName":"栾川辉煌大鲵养殖场","area":{"id":"410324","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"栾川县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省洛阳市栾川县","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"1","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"983c8f2446b74598902d53044d7a11bc","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 06:22:11","updateDate":"2018-01-24 06:22:11","farmName":"商丘和昌饲料有限公司","area":{"id":"411421","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"民权县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省商丘市民权县","zipcode":"","species":"","farmMethod":"","cultureArea":null,"source":"","annualOutput":"","companyType":"","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"98c020a5c0dc48e39ddccb80ad041f69","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 04:24:59","updateDate":"2018-01-24 04:24:59","farmName":"兰考腾飞合作社","area":{"id":"410225","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"兰考县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省开封市兰考县","zipcode":"475313","species":"养殖品种","farmMethod":"2","cultureArea":1000,"source":"2","annualOutput":"100","companyType":"1","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},{"id":"a74dc4275d784224afeda4a267148e3a","isNewRecord":false,"remarks":null,"createDate":"2018-01-24 04:18:34","updateDate":"2018-01-24 04:22:51","farmName":"信阳市南湾水库良种场","area":{"id":"411524","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"parentIds":null,"name":"商城县","sort":30,"code":null,"type":null,"parentId":"0"},"address":"河南省信阳市商城县","zipcode":"465311","species":"养殖品种","farmMethod":"1","cultureArea":1000,"source":"2","annualOutput":"10000","companyType":"1","foundedTime":"2018-01-23 16:00:00","telephone":null,"begin":null,"end":null,"appPage":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null}]
         * maxResults : 10
         * firstResult : 0
         * html : <div class="fixed-table-pagination" style="display: block;"><div class="pull-left pagination-detail"><span class="pagination-info">显示第 1 到第 0 条记录，总共 0 条记录</span><span class="page-list">每页显示 <span class="btn-group dropup"><button type="button" class="btn btn-default  btn-outline dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="page-size">10</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu"><li class="active"><a href="javascript:page(1,10,'');">10</a></li><li class=""><a href="javascript:page(1,25,'');">25</a></li><li class=""><a href="javascript:page(1,50,'');">50</a></li><li class=""><a href="javascript:page(1,100,'');">100</a></li></ul></span> 条记录</span></div><div class="pull-right pagination-roll"><ul class="pagination pagination-outline"><li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-double-left"></i></a></li>
         <li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-left"></i></a></li>
         <li class="paginate_button active"><a href="javascript:">1</a></li>
         <li class="paginate_button next disabled"><a href="javascript:"><i class="fa fa-angle-right"></i></a></li>
         <li class="paginate_button next disabled"><a href="javascript:"><i class="fa fa-angle-double-right"></i></a></li>
         </ul></div></div>
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private int maxResults;
        private int firstResult;
        private String html;
        private List<ListBean> list;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }

        public int getFirstResult() {
            return firstResult;
        }

        public void setFirstResult(int firstResult) {
            this.firstResult = firstResult;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
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
}
