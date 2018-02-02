package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 */

public class BuTieJiLu {

    /**
     * page : {"pageNo":1,"pageSize":10,"count":1,"list":[{"id":"52109a484d13464b8584e6a98df86dfb","isNewRecord":false,"remarks":"111","createDate":"2018-02-02 01:04:43","updateDate":"2018-02-02 09:04:43","firm":{"id":"a74dc4275d784224afeda4a267148e3a","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"信阳市南湾水库良种场","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null},"year":"2017","subsidyItem":"111","subsidyMoney":"111.00","grantFirm":"111","receipt":""}],"maxResults":10,"firstResult":0,"html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 1 条记录，总共 1 条记录<\/span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10<\/span> <span class=\"caret\"><\/span><\/button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100<\/a><\/li><\/ul><\/span> 条记录<\/span><\/div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button active\"><a href=\"javascript:\">1<\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"><\/i><\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"><\/i><\/a><\/li>\n<\/ul><\/div><\/div>"}
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
         * list : [{"id":"52109a484d13464b8584e6a98df86dfb","isNewRecord":false,"remarks":"111","createDate":"2018-02-02 01:04:43","updateDate":"2018-02-02 09:04:43","firm":{"id":"a74dc4275d784224afeda4a267148e3a","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"信阳市南湾水库良种场","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null},"year":"2017","subsidyItem":"111","subsidyMoney":"111.00","grantFirm":"111","receipt":""}]
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

        public static class ListBean implements Serializable {
            /**
             * id : 52109a484d13464b8584e6a98df86dfb
             * isNewRecord : false
             * remarks : 111
             * createDate : 2018-02-02 01:04:43
             * updateDate : 2018-02-02 09:04:43
             * firm : {"id":"a74dc4275d784224afeda4a267148e3a","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"信阳市南湾水库良种场","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null}
             * year : 2017
             * subsidyItem : 111
             * subsidyMoney : 111.00
             * grantFirm : 111
             * receipt :
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

            public String getReceipt() {
                return receipt;
            }

            public void setReceipt(String receipt) {
                this.receipt = receipt;
            }

            public static class FirmBean {
                /**
                 * id : a74dc4275d784224afeda4a267148e3a
                 * isNewRecord : false
                 * remarks : null
                 * createDate : null
                 * updateDate : null
                 * farmName : 信阳市南湾水库良种场
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
}
