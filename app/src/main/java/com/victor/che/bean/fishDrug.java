package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jcc on 2018/1/21.
 */

public class fishDrug implements Serializable {

    /**
     * page : {"pageNo":1,"pageSize":10,"count":6,"list":[{"id":"b4e5dacaf15f43809562de8e750f394b","isNewRecord":false,"remarks":null,"createDate":"2017-11-10 03:23:52","updateDate":"2017-11-10 03:23:52","code":1,"medicineName":"克仑特罗及其盐、酯及制剂","englishName":"Clenbuterol","anotherName":"","referenceBasis":"农业部第193号公告\r\n农业部第235号公告\r\n农业部第176号公告"},{"id":"767c4fcf250f47c8ab5520c531e28349","isNewRecord":false,"remarks":null,"createDate":"2017-11-10 03:24:41","updateDate":"2017-11-13 05:55:28","code":2,"medicineName":"己烯雌酚及其盐、酯及制剂","englishName":"Diethylstilbestrol","anotherName":"己烯雌酚","referenceBasis":"农业部第193号公告\r\n农业部第235号公告\r\n农业部31号令    \r\n农业部第176号公告"},{"id":"0cb2b359311d4891b2afb61a0d066932","isNewRecord":false,"remarks":null,"createDate":"2017-11-13 07:45:05","updateDate":"2017-11-13 07:45:05","code":3,"medicineName":"沙丁胺醇及其盐、酯及制剂","englishName":"Salbutamol","anotherName":"","referenceBasis":"农业部第193号公告  农业部第235号公告  农业部第176号公告"},{"id":"306f8b3c48184182a6e722d1a59f5cf4","isNewRecord":false,"remarks":null,"createDate":"2017-11-13 07:45:33","updateDate":"2017-11-13 07:45:33","code":4,"medicineName":"西马特罗及其盐、酯及制剂","englishName":"Cimaterol","anotherName":"","referenceBasis":"农业部第193号公告  农业部第235号公告"},{"id":"dc513426f4754252b8055e463f463c73","isNewRecord":false,"remarks":null,"createDate":"2017-11-13 07:46:08","updateDate":"2017-11-13 07:46:08","code":5,"medicineName":"呋喃唑酮及制剂","englishName":"Furazolidone","anotherName":"痢特灵","referenceBasis":"农业部第193号公告  农业部31号令"},{"id":"69153e37ac634ca1b5db8fbd224f1e7c","isNewRecord":false,"remarks":null,"createDate":"2017-11-20 07:00:00","updateDate":"2017-11-20 07:00:00","code":6,"medicineName":"林丹","englishName":"Lindane或gammaxare","anotherName":"丙体六六六","referenceBasis":"农业部第193号公告\r\n农业部第235号公告\r\n农业部31号令"}],"maxResults":10,"firstResult":0,"html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 6 条记录，总共 6 条记录<\/span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10<\/span> <span class=\"caret\"><\/span><\/button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100<\/a><\/li><\/ul><\/span> 条记录<\/span><\/div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button active\"><a href=\"javascript:\">1<\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"><\/i><\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"><\/i><\/a><\/li>\n<\/ul><\/div><\/div>"}
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
         * count : 6
         * list : [{"id":"b4e5dacaf15f43809562de8e750f394b","isNewRecord":false,"remarks":null,"createDate":"2017-11-10 03:23:52","updateDate":"2017-11-10 03:23:52","code":1,"medicineName":"克仑特罗及其盐、酯及制剂","englishName":"Clenbuterol","anotherName":"","referenceBasis":"农业部第193号公告\r\n农业部第235号公告\r\n农业部第176号公告"},{"id":"767c4fcf250f47c8ab5520c531e28349","isNewRecord":false,"remarks":null,"createDate":"2017-11-10 03:24:41","updateDate":"2017-11-13 05:55:28","code":2,"medicineName":"己烯雌酚及其盐、酯及制剂","englishName":"Diethylstilbestrol","anotherName":"己烯雌酚","referenceBasis":"农业部第193号公告\r\n农业部第235号公告\r\n农业部31号令    \r\n农业部第176号公告"},{"id":"0cb2b359311d4891b2afb61a0d066932","isNewRecord":false,"remarks":null,"createDate":"2017-11-13 07:45:05","updateDate":"2017-11-13 07:45:05","code":3,"medicineName":"沙丁胺醇及其盐、酯及制剂","englishName":"Salbutamol","anotherName":"","referenceBasis":"农业部第193号公告  农业部第235号公告  农业部第176号公告"},{"id":"306f8b3c48184182a6e722d1a59f5cf4","isNewRecord":false,"remarks":null,"createDate":"2017-11-13 07:45:33","updateDate":"2017-11-13 07:45:33","code":4,"medicineName":"西马特罗及其盐、酯及制剂","englishName":"Cimaterol","anotherName":"","referenceBasis":"农业部第193号公告  农业部第235号公告"},{"id":"dc513426f4754252b8055e463f463c73","isNewRecord":false,"remarks":null,"createDate":"2017-11-13 07:46:08","updateDate":"2017-11-13 07:46:08","code":5,"medicineName":"呋喃唑酮及制剂","englishName":"Furazolidone","anotherName":"痢特灵","referenceBasis":"农业部第193号公告  农业部31号令"},{"id":"69153e37ac634ca1b5db8fbd224f1e7c","isNewRecord":false,"remarks":null,"createDate":"2017-11-20 07:00:00","updateDate":"2017-11-20 07:00:00","code":6,"medicineName":"林丹","englishName":"Lindane或gammaxare","anotherName":"丙体六六六","referenceBasis":"农业部第193号公告\r\n农业部第235号公告\r\n农业部31号令"}]
         * maxResults : 10
         * firstResult : 0
         * html : <div class="fixed-table-pagination" style="display: block;"><div class="pull-left pagination-detail"><span class="pagination-info">显示第 1 到第 6 条记录，总共 6 条记录</span><span class="page-list">每页显示 <span class="btn-group dropup"><button type="button" class="btn btn-default  btn-outline dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="page-size">10</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu"><li class="active"><a href="javascript:page(1,10,'');">10</a></li><li class=""><a href="javascript:page(1,25,'');">25</a></li><li class=""><a href="javascript:page(1,50,'');">50</a></li><li class=""><a href="javascript:page(1,100,'');">100</a></li></ul></span> 条记录</span></div><div class="pull-right pagination-roll"><ul class="pagination pagination-outline"><li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-double-left"></i></a></li>
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
             * id : b4e5dacaf15f43809562de8e750f394b
             * isNewRecord : false
             * remarks : null
             * createDate : 2017-11-10 03:23:52
             * updateDate : 2017-11-10 03:23:52
             * code : 1
             * medicineName : 克仑特罗及其盐、酯及制剂
             * englishName : Clenbuterol
             * anotherName :
             * referenceBasis : 农业部第193号公告
             农业部第235号公告
             农业部第176号公告
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
}
