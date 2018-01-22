package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jcc on 2018/1/21.
 */

public class Notify implements Serializable {


    /**
     * page : {"pageNo":1,"pageSize":10,"count":1,"list":[{"id":"5b628443d4e34279bb960b7d391a0dc1","isNewRecord":false,"remarks":null,"createDate":"2018-01-12 10:00:19","updateDate":"2018-01-12 10:00:19","type":"1","title":"测试","content":"文本A","files":",","status":"1","readNum":"0","unReadNum":"2","readFlag":null,"oaNotifyRecordList":[],"oaNotifyRecordIds":"","oaNotifyRecordNames":"","self":false}],"maxResults":10,"firstResult":0,"html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 1 条记录，总共 1 条记录<\/span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10<\/span> <span class=\"caret\"><\/span><\/button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100<\/a><\/li><\/ul><\/span> 条记录<\/span><\/div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button active\"><a href=\"javascript:\">1<\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"><\/i><\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"><\/i><\/a><\/li>\n<\/ul><\/div><\/div>"}
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
         * list : [{"id":"5b628443d4e34279bb960b7d391a0dc1","isNewRecord":false,"remarks":null,"createDate":"2018-01-12 10:00:19","updateDate":"2018-01-12 10:00:19","type":"1","title":"测试","content":"文本A","files":",","status":"1","readNum":"0","unReadNum":"2","readFlag":null,"oaNotifyRecordList":[],"oaNotifyRecordIds":"","oaNotifyRecordNames":"","self":false}]
         * maxResults : 10
         * firstResult : 0
         * html : <div class="fixed-table-pagination" style="display: block;"><div class="pull-left pagination-detail"><span class="pagination-info">显示第 1 到第 1 条记录，总共 1 条记录</span><span class="page-list">每页显示 <span class="btn-group dropup"><button type="button" class="btn btn-default  btn-outline dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="page-size">10</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu"><li class="active"><a href="javascript:page(1,10,'');">10</a></li><li class=""><a href="javascript:page(1,25,'');">25</a></li><li class=""><a href="javascript:page(1,50,'');">50</a></li><li class=""><a href="javascript:page(1,100,'');">100</a></li></ul></span> 条记录</span></div><div class="pull-right pagination-roll"><ul class="pagination pagination-outline"><li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-double-left"></i></a></li>
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

        public static class ListBean implements Serializable{
            /**
             * id : 5b628443d4e34279bb960b7d391a0dc1
             * isNewRecord : false
             * remarks : null
             * createDate : 2018-01-12 10:00:19
             * updateDate : 2018-01-12 10:00:19
             * type : 1
             * title : 测试
             * content : 文本A
             * files : ,
             * status : 1
             * readNum : 0
             * unReadNum : 2
             * readFlag : null
             * oaNotifyRecordList : []
             * oaNotifyRecordIds :
             * oaNotifyRecordNames :
             * self : false
             */

            private String id;
            private boolean isNewRecord;
            private Object remarks;
            private String createDate;
            private String updateDate;
            private String type;
            private String title;
            private String content;
            private String files;
            private String status;
            private String readNum;
            private String unReadNum;
            private Object readFlag;
            private String oaNotifyRecordIds;
            private String oaNotifyRecordNames;
            private boolean self;
            private List<?> oaNotifyRecordList;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getFiles() {
                return files;
            }

            public void setFiles(String files) {
                this.files = files;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getReadNum() {
                return readNum;
            }

            public void setReadNum(String readNum) {
                this.readNum = readNum;
            }

            public String getUnReadNum() {
                return unReadNum;
            }

            public void setUnReadNum(String unReadNum) {
                this.unReadNum = unReadNum;
            }

            public Object getReadFlag() {
                return readFlag;
            }

            public void setReadFlag(Object readFlag) {
                this.readFlag = readFlag;
            }

            public String getOaNotifyRecordIds() {
                return oaNotifyRecordIds;
            }

            public void setOaNotifyRecordIds(String oaNotifyRecordIds) {
                this.oaNotifyRecordIds = oaNotifyRecordIds;
            }

            public String getOaNotifyRecordNames() {
                return oaNotifyRecordNames;
            }

            public void setOaNotifyRecordNames(String oaNotifyRecordNames) {
                this.oaNotifyRecordNames = oaNotifyRecordNames;
            }

            public boolean isSelf() {
                return self;
            }

            public void setSelf(boolean self) {
                this.self = self;
            }

            public List<?> getOaNotifyRecordList() {
                return oaNotifyRecordList;
            }

            public void setOaNotifyRecordList(List<?> oaNotifyRecordList) {
                this.oaNotifyRecordList = oaNotifyRecordList;
            }
        }
    }
}
