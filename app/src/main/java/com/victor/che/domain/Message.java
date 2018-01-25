package com.victor.che.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 执法查询列表
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/2/22 0022 14:09
 */
public class Message implements Serializable {

    /**
     * page : {"pageNo":1,"pageSize":10,"count":1,"list":[{"id":"114bdc4e138a4e82bb120929388b978e","isNewRecord":false,"remarks":"意见","createDate":"2018-01-21 08:55:39","updateDate":"2018-01-21 09:21:14","lawName":"商丘和昌","lawWaters":1,"lawAqu":0,"lawMed":1,"lawPro":1,"lawSal":0,"lawDeli":1,"lawMedi":1,"lawQual":1,"lawTech":0,"lawSta":0,"lawOld":1,"lawTrea":1,"lawProb":"1","lawOther":"测试项目","psonName":"/aims/userfiles/河南省监管中心\\2018\\01\\idea64.exe","userName":"/aims/userfiles/河南省监管中心\\2018\\01\\文本.tex","lawTime":"2017-12-31 16:00:00","lawDate":1516636800000,"begin":null,"end":null}],"maxResults":10,"firstResult":0,"html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 1 条记录，总共 1 条记录<\/span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10<\/span> <span class=\"caret\"><\/span><\/button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100<\/a><\/li><\/ul><\/span> 条记录<\/span><\/div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"><\/i><\/a><\/li> <li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"><\/i><\/a><\/li> <li class=\"paginate_button active\"><a href=\"javascript:\">1<\/a><\/li> <li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"><\/i><\/a><\/li> <li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"><\/i><\/a><\/li> <\/ul><\/div><\/div>"}
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
         * count : 1
         * list : [{"id":"114bdc4e138a4e82bb120929388b978e","isNewRecord":false,"remarks":"意见","createDate":"2018-01-21 08:55:39","updateDate":"2018-01-21 09:21:14","lawName":"商丘和昌","lawWaters":1,"lawAqu":0,"lawMed":1,"lawPro":1,"lawSal":0,"lawDeli":1,"lawMedi":1,"lawQual":1,"lawTech":0,"lawSta":0,"lawOld":1,"lawTrea":1,"lawProb":"1","lawOther":"测试项目","psonName":"/aims/userfiles/河南省监管中心\\2018\\01\\idea64.exe","userName":"/aims/userfiles/河南省监管中心\\2018\\01\\文本.tex","lawTime":"2017-12-31 16:00:00","lawDate":1516636800000,"begin":null,"end":null}]
         * maxResults : 10
         * firstResult : 0
         * html : <div class="fixed-table-pagination" style="display: block;"><div class="pull-left pagination-detail"><span class="pagination-info">显示第 1 到第 1 条记录，总共 1 条记录</span><span class="page-list">每页显示 <span class="btn-group dropup"><button type="button" class="btn btn-default  btn-outline dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="page-size">10</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu"><li class="active"><a href="javascript:page(1,10,'');">10</a></li><li class=""><a href="javascript:page(1,25,'');">25</a></li><li class=""><a href="javascript:page(1,50,'');">50</a></li><li class=""><a href="javascript:page(1,100,'');">100</a></li></ul></span> 条记录</span></div><div class="pull-right pagination-roll"><ul class="pagination pagination-outline"><li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-double-left"></i></a></li> <li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-left"></i></a></li> <li class="paginate_button active"><a href="javascript:">1</a></li> <li class="paginate_button next disabled"><a href="javascript:"><i class="fa fa-angle-right"></i></a></li> <li class="paginate_button next disabled"><a href="javascript:"><i class="fa fa-angle-double-right"></i></a></li> </ul></div></div>
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
             * id : 114bdc4e138a4e82bb120929388b978e
             * isNewRecord : false
             * remarks : 意见
             * createDate : 2018-01-21 08:55:39
             * updateDate : 2018-01-21 09:21:14
             * lawName : 商丘和昌  //单位名称
             * lawWaters : 1
             * lawAqu : 0
             * lawMed : 1
             * lawPro : 1
             * lawSal : 0
             * lawDeli : 1
             * lawMedi : 1
             * lawQual : 1
             * lawTech : 0
             * lawSta : 0
             * lawOld : 1
             * lawTrea : 1
             * lawProb : 1
             * lawOther : 测试项目
             * psonName : /aims/userfiles/河南省监管中心\2018\01\idea64.exe
             * userName : /aims/userfiles/河南省监管中心\2018\01\文本.tex
             * lawTime : 2017-12-31 16:00:00
             * lawDate : 1516636800000
             * begin : null
             * end : null
             */
            public boolean checked = false;// 本地变量，是否被选中
            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String createDate;
            private String updateDate;
            private String lawName;
            private int lawWaters;
            private int lawAqu;
            private int lawMed;
            private int lawPro;
            private int lawSal;
            private int lawDeli;
            private int lawMedi;
            private int lawQual;
            private int lawTech;
            private int lawSta;
            private int lawOld;
            private int lawTrea;
            private String lawProb;
            private String lawOther;
            private String psonName;
            private String userName;
            private String lawTime;
            private long lawDate;
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

            public String getLawName() {
                return lawName;
            }

            public void setLawName(String lawName) {
                this.lawName = lawName;
            }

            public int getLawWaters() {
                return lawWaters;
            }

            public void setLawWaters(int lawWaters) {
                this.lawWaters = lawWaters;
            }

            public int getLawAqu() {
                return lawAqu;
            }

            public void setLawAqu(int lawAqu) {
                this.lawAqu = lawAqu;
            }

            public int getLawMed() {
                return lawMed;
            }

            public void setLawMed(int lawMed) {
                this.lawMed = lawMed;
            }

            public int getLawPro() {
                return lawPro;
            }

            public void setLawPro(int lawPro) {
                this.lawPro = lawPro;
            }

            public int getLawSal() {
                return lawSal;
            }

            public void setLawSal(int lawSal) {
                this.lawSal = lawSal;
            }

            public int getLawDeli() {
                return lawDeli;
            }

            public void setLawDeli(int lawDeli) {
                this.lawDeli = lawDeli;
            }

            public int getLawMedi() {
                return lawMedi;
            }

            public void setLawMedi(int lawMedi) {
                this.lawMedi = lawMedi;
            }

            public int getLawQual() {
                return lawQual;
            }

            public void setLawQual(int lawQual) {
                this.lawQual = lawQual;
            }

            public int getLawTech() {
                return lawTech;
            }

            public void setLawTech(int lawTech) {
                this.lawTech = lawTech;
            }

            public int getLawSta() {
                return lawSta;
            }

            public void setLawSta(int lawSta) {
                this.lawSta = lawSta;
            }

            public int getLawOld() {
                return lawOld;
            }

            public void setLawOld(int lawOld) {
                this.lawOld = lawOld;
            }

            public int getLawTrea() {
                return lawTrea;
            }

            public void setLawTrea(int lawTrea) {
                this.lawTrea = lawTrea;
            }

            public String getLawProb() {
                return lawProb;
            }

            public void setLawProb(String lawProb) {
                this.lawProb = lawProb;
            }

            public String getLawOther() {
                return lawOther;
            }

            public void setLawOther(String lawOther) {
                this.lawOther = lawOther;
            }

            public String getPsonName() {
                return psonName;
            }

            public void setPsonName(String psonName) {
                this.psonName = psonName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getLawTime() {
                return lawTime;
            }

            public void setLawTime(String lawTime) {
                this.lawTime = lawTime;
            }

            public long getLawDate() {
                return lawDate;
            }

            public void setLawDate(long lawDate) {
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
    }
}
