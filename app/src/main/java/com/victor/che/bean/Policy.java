package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jcc on 2018/1/21.
 */

public class Policy implements Serializable {


    /**
     * page : {"pageNo":1,"pageSize":10,"count":4,"list":[{"id":"843f0cd077c14873ba92cc666cc85457","isNewRecord":false,"remarks":"规章制度","createDate":"2017-11-28 01:21:50","updateDate":"2017-11-28 01:21:50","title":"规章","type":"2","content":"&lt;span style=&quot;background-color: rgb(255, 0, 0);&quot;&gt;&lt;span style=&quot;text-decoration: underline;&quot;&gt;&lt;span style=&quot;font-style: italic;&quot;&gt;&lt;span style=&quot;font-weight: bold;&quot;&gt;规章制度&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;","addfiles":"|/aims/userfiles/1/files/usermanagement/governmentPolicies/2017/11/LBXB6.png","sendtime":"2017-11-20 16:00:00","isLook":null,"beginDate":null,"endDate":null},{"id":"b2b0fa406e244a21b11849ec6979a78c","isNewRecord":false,"remarks":"","createDate":"2017-11-22 09:08:03","updateDate":"2017-11-27 07:04:58","title":"dsfhjk","type":"3","content":"","addfiles":"|/aims/userfiles/1/files/oa/notify/2017/11/Chrysanthemum.jpg","sendtime":"2017-11-22 16:00:00","isLook":null,"beginDate":null,"endDate":null},{"id":"07b81e2addde43f9b952a8c18e39583c","isNewRecord":false,"remarks":"","createDate":"2017-11-20 12:27:59","updateDate":"2017-11-20 12:27:59","title":"金华市水利渔业局2016年度部门决算","type":"3","content":"","addfiles":"","sendtime":"2017-10-28 16:00:00","isLook":null,"beginDate":null,"endDate":null},{"id":"fe5affba4cc24f76aa3df4f04d53b111","isNewRecord":false,"remarks":"","createDate":"2017-11-20 01:47:09","updateDate":"2017-11-20 01:47:09","title":"中华人民共和国渔业法（2013年修订）","type":"2","content":"","addfiles":"","sendtime":"2017-11-19 16:00:00","isLook":null,"beginDate":null,"endDate":null}],"maxResults":10,"firstResult":0,"html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 4 条记录，总共 4 条记录<\/span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10<\/span> <span class=\"caret\"><\/span><\/button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100<\/a><\/li><\/ul><\/span> 条记录<\/span><\/div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button active\"><a href=\"javascript:\">1<\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"><\/i><\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"><\/i><\/a><\/li>\n<\/ul><\/div><\/div>"}
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
         * count : 4
         * list : [{"id":"843f0cd077c14873ba92cc666cc85457","isNewRecord":false,"remarks":"规章制度","createDate":"2017-11-28 01:21:50","updateDate":"2017-11-28 01:21:50","title":"规章","type":"2","content":"&lt;span style=&quot;background-color: rgb(255, 0, 0);&quot;&gt;&lt;span style=&quot;text-decoration: underline;&quot;&gt;&lt;span style=&quot;font-style: italic;&quot;&gt;&lt;span style=&quot;font-weight: bold;&quot;&gt;规章制度&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;","addfiles":"|/aims/userfiles/1/files/usermanagement/governmentPolicies/2017/11/LBXB6.png","sendtime":"2017-11-20 16:00:00","isLook":null,"beginDate":null,"endDate":null},{"id":"b2b0fa406e244a21b11849ec6979a78c","isNewRecord":false,"remarks":"","createDate":"2017-11-22 09:08:03","updateDate":"2017-11-27 07:04:58","title":"dsfhjk","type":"3","content":"","addfiles":"|/aims/userfiles/1/files/oa/notify/2017/11/Chrysanthemum.jpg","sendtime":"2017-11-22 16:00:00","isLook":null,"beginDate":null,"endDate":null},{"id":"07b81e2addde43f9b952a8c18e39583c","isNewRecord":false,"remarks":"","createDate":"2017-11-20 12:27:59","updateDate":"2017-11-20 12:27:59","title":"金华市水利渔业局2016年度部门决算","type":"3","content":"","addfiles":"","sendtime":"2017-10-28 16:00:00","isLook":null,"beginDate":null,"endDate":null},{"id":"fe5affba4cc24f76aa3df4f04d53b111","isNewRecord":false,"remarks":"","createDate":"2017-11-20 01:47:09","updateDate":"2017-11-20 01:47:09","title":"中华人民共和国渔业法（2013年修订）","type":"2","content":"","addfiles":"","sendtime":"2017-11-19 16:00:00","isLook":null,"beginDate":null,"endDate":null}]
         * maxResults : 10
         * firstResult : 0
         * html : <div class="fixed-table-pagination" style="display: block;"><div class="pull-left pagination-detail"><span class="pagination-info">显示第 1 到第 4 条记录，总共 4 条记录</span><span class="page-list">每页显示 <span class="btn-group dropup"><button type="button" class="btn btn-default  btn-outline dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="page-size">10</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu"><li class="active"><a href="javascript:page(1,10,'');">10</a></li><li class=""><a href="javascript:page(1,25,'');">25</a></li><li class=""><a href="javascript:page(1,50,'');">50</a></li><li class=""><a href="javascript:page(1,100,'');">100</a></li></ul></span> 条记录</span></div><div class="pull-right pagination-roll"><ul class="pagination pagination-outline"><li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-double-left"></i></a></li>
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
             * id : 843f0cd077c14873ba92cc666cc85457
             * isNewRecord : false
             * remarks : 规章制度
             * createDate : 2017-11-28 01:21:50
             * updateDate : 2017-11-28 01:21:50
             * title : 规章
             * type : 2
             * content : &lt;span style=&quot;background-color: rgb(255, 0, 0);&quot;&gt;&lt;span style=&quot;text-decoration: underline;&quot;&gt;&lt;span style=&quot;font-style: italic;&quot;&gt;&lt;span style=&quot;font-weight: bold;&quot;&gt;规章制度&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;
             * addfiles : |/aims/userfiles/1/files/usermanagement/governmentPolicies/2017/11/LBXB6.png
             * sendtime : 2017-11-20 16:00:00
             * isLook : null
             * beginDate : null
             * endDate : null
             */

            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String createDate;
            private String updateDate;
            private String title;
            private String type;
            private String content;
            private String addfiles;
            private String sendtime;
            private Object isLook;
            private Object beginDate;
            private Object endDate;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddfiles() {
                return addfiles;
            }

            public void setAddfiles(String addfiles) {
                this.addfiles = addfiles;
            }

            public String getSendtime() {
                return sendtime;
            }

            public void setSendtime(String sendtime) {
                this.sendtime = sendtime;
            }

            public Object getIsLook() {
                return isLook;
            }

            public void setIsLook(Object isLook) {
                this.isLook = isLook;
            }

            public Object getBeginDate() {
                return beginDate;
            }

            public void setBeginDate(Object beginDate) {
                this.beginDate = beginDate;
            }

            public Object getEndDate() {
                return endDate;
            }

            public void setEndDate(Object endDate) {
                this.endDate = endDate;
            }
        }
    }
}
