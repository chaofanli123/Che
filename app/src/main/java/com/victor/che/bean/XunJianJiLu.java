package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 */

public class XunJianJiLu {

    /**
     * page : {"pageNo":1,"pageSize":10,"count":3,"list":[{"id":"b4647cb3a6cd43fd8f813dfa0a991026","isNewRecord":false,"remarks":null,"createDate":"2018-02-02 06:37:33","updateDate":"2018-02-02 14:37:33","user":{"id":"aacd8ad835bf4cbf87ee70452c5c390b","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"农信通","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"checkedName":"test","checkDate":"2018-02-01 16:00:00","aquFarm":{"id":"556467710fce4d0d96c6dbd48c7fadae","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"灵宝江辉养殖合作社","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null},"others":"test","dispose":"test","beginInsDate":null,"endInsDate":null},{"id":"aee4ab3ef69d40a49201b23062f4d79f","isNewRecord":false,"remarks":null,"createDate":"2018-02-02 06:35:46","updateDate":"2018-02-02 14:35:46","user":{"id":"f6158302759f4aafa0640942f739ac13","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"汝南县农委","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"checkedName":"测试哈哈哈","checkDate":"2018-02-01 16:00:00","aquFarm":{"id":"0edba9f7ed964ca6a1b60ebce7541757","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"固始金海养殖场","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null},"others":"测试添加","dispose":"测试添加","beginInsDate":null,"endInsDate":null},{"id":"efa7e42b23744c87aa2870360287b9cb","isNewRecord":false,"remarks":null,"createDate":"2018-02-02 06:35:18","updateDate":"2018-02-02 14:35:18","user":{"id":"aacd8ad835bf4cbf87ee70452c5c390b","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"农信通","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"checkedName":"大大大大大","checkDate":"2018-02-01 16:00:00","aquFarm":{"id":"3f71d60134dd4441b21ace355fb53866","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"驻马店宿鸭湖水库良种场","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null},"others":"大发送到","dispose":"大大非官方","beginInsDate":null,"endInsDate":null}],"html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 3 条记录，总共 3 条记录<\/span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10<\/span> <span class=\"caret\"><\/span><\/button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100<\/a><\/li><\/ul><\/span> 条记录<\/span><\/div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button active\"><a href=\"javascript:\">1<\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"><\/i><\/a><\/li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"><\/i><\/a><\/li>\n<\/ul><\/div><\/div>","maxResults":10,"firstResult":0}
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
         * count : 3
         * list : [{"id":"b4647cb3a6cd43fd8f813dfa0a991026","isNewRecord":false,"remarks":null,"createDate":"2018-02-02 06:37:33","updateDate":"2018-02-02 14:37:33","user":{"id":"aacd8ad835bf4cbf87ee70452c5c390b","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"农信通","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"checkedName":"test","checkDate":"2018-02-01 16:00:00","aquFarm":{"id":"556467710fce4d0d96c6dbd48c7fadae","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"灵宝江辉养殖合作社","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null},"others":"test","dispose":"test","beginInsDate":null,"endInsDate":null},{"id":"aee4ab3ef69d40a49201b23062f4d79f","isNewRecord":false,"remarks":null,"createDate":"2018-02-02 06:35:46","updateDate":"2018-02-02 14:35:46","user":{"id":"f6158302759f4aafa0640942f739ac13","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"汝南县农委","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"checkedName":"测试哈哈哈","checkDate":"2018-02-01 16:00:00","aquFarm":{"id":"0edba9f7ed964ca6a1b60ebce7541757","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"固始金海养殖场","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null},"others":"测试添加","dispose":"测试添加","beginInsDate":null,"endInsDate":null},{"id":"efa7e42b23744c87aa2870360287b9cb","isNewRecord":false,"remarks":null,"createDate":"2018-02-02 06:35:18","updateDate":"2018-02-02 14:35:18","user":{"id":"aacd8ad835bf4cbf87ee70452c5c390b","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"农信通","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""},"checkedName":"大大大大大","checkDate":"2018-02-01 16:00:00","aquFarm":{"id":"3f71d60134dd4441b21ace355fb53866","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"驻马店宿鸭湖水库良种场","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null},"others":"大发送到","dispose":"大大非官方","beginInsDate":null,"endInsDate":null}]
         * html : <div class="fixed-table-pagination" style="display: block;"><div class="pull-left pagination-detail"><span class="pagination-info">显示第 1 到第 3 条记录，总共 3 条记录</span><span class="page-list">每页显示 <span class="btn-group dropup"><button type="button" class="btn btn-default  btn-outline dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="page-size">10</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu"><li class="active"><a href="javascript:page(1,10,'');">10</a></li><li class=""><a href="javascript:page(1,25,'');">25</a></li><li class=""><a href="javascript:page(1,50,'');">50</a></li><li class=""><a href="javascript:page(1,100,'');">100</a></li></ul></span> 条记录</span></div><div class="pull-right pagination-roll"><ul class="pagination pagination-outline"><li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-double-left"></i></a></li>
         <li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-left"></i></a></li>
         <li class="paginate_button active"><a href="javascript:">1</a></li>
         <li class="paginate_button next disabled"><a href="javascript:"><i class="fa fa-angle-right"></i></a></li>
         <li class="paginate_button next disabled"><a href="javascript:"><i class="fa fa-angle-double-right"></i></a></li>
         </ul></div></div>
         * maxResults : 10
         * firstResult : 0
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private String html;
        private int maxResults;
        private int firstResult;
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

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * id : b4647cb3a6cd43fd8f813dfa0a991026
             * isNewRecord : false
             * remarks : null
             * createDate : 2018-02-02 06:37:33
             * updateDate : 2018-02-02 14:37:33
             * user : {"id":"aacd8ad835bf4cbf87ee70452c5c390b","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"loginName":null,"no":null,"name":"农信通","email":null,"phone":null,"mobile":null,"userType":null,"loginIp":null,"loginDate":null,"loginFlag":"1","photo":null,"qrCode":null,"oldLoginName":null,"newPassword":null,"sign":null,"ys7Id":null,"oldLoginIp":null,"oldLoginDate":null,"role":null,"admin":false,"roleNames":""}
             * checkedName : test
             * checkDate : 2018-02-01 16:00:00
             * aquFarm : {"id":"556467710fce4d0d96c6dbd48c7fadae","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"farmName":"灵宝江辉养殖合作社","area":null,"address":null,"zipcode":null,"species":null,"farmMethod":null,"cultureArea":null,"source":null,"annualOutput":null,"companyType":null,"foundedTime":null,"telephone":null,"begin":null,"end":null}
             * others : test
             * dispose : test
             * beginInsDate : null
             * endInsDate : null
             */

            private String id;
            private boolean isNewRecord;
            private Object remarks;
            private String createDate;
            private String updateDate;
            private UserBean user;
            private String checkedName;
            private String checkDate;
            private AquFarmBean aquFarm;
            private String others;
            private String dispose;
            private Object beginInsDate;
            private Object endInsDate;

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

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public String getCheckedName() {
                return checkedName;
            }

            public void setCheckedName(String checkedName) {
                this.checkedName = checkedName;
            }

            public String getCheckDate() {
                return checkDate;
            }

            public void setCheckDate(String checkDate) {
                this.checkDate = checkDate;
            }

            public AquFarmBean getAquFarm() {
                return aquFarm;
            }

            public void setAquFarm(AquFarmBean aquFarm) {
                this.aquFarm = aquFarm;
            }

            public String getOthers() {
                return others;
            }

            public void setOthers(String others) {
                this.others = others;
            }

            public String getDispose() {
                return dispose;
            }

            public void setDispose(String dispose) {
                this.dispose = dispose;
            }

            public Object getBeginInsDate() {
                return beginInsDate;
            }

            public void setBeginInsDate(Object beginInsDate) {
                this.beginInsDate = beginInsDate;
            }

            public Object getEndInsDate() {
                return endInsDate;
            }

            public void setEndInsDate(Object endInsDate) {
                this.endInsDate = endInsDate;
            }

            public static class UserBean {
                /**
                 * id : aacd8ad835bf4cbf87ee70452c5c390b
                 * isNewRecord : false
                 * remarks : null
                 * createDate : null
                 * updateDate : null
                 * loginName : null
                 * no : null
                 * name : 农信通
                 * email : null
                 * phone : null
                 * mobile : null
                 * userType : null
                 * loginIp : null
                 * loginDate : null
                 * loginFlag : 1
                 * photo : null
                 * qrCode : null
                 * oldLoginName : null
                 * newPassword : null
                 * sign : null
                 * ys7Id : null
                 * oldLoginIp : null
                 * oldLoginDate : null
                 * role : null
                 * admin : false
                 * roleNames :
                 */

                private String id;
                private boolean isNewRecord;
                private Object remarks;
                private Object createDate;
                private Object updateDate;
                private Object loginName;
                private Object no;
                private String name;
                private Object email;
                private Object phone;
                private Object mobile;
                private Object userType;
                private Object loginIp;
                private Object loginDate;
                private String loginFlag;
                private Object photo;
                private Object qrCode;
                private Object oldLoginName;
                private Object newPassword;
                private Object sign;
                private Object ys7Id;
                private Object oldLoginIp;
                private Object oldLoginDate;
                private Object role;
                private boolean admin;
                private String roleNames;

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

                public Object getLoginName() {
                    return loginName;
                }

                public void setLoginName(Object loginName) {
                    this.loginName = loginName;
                }

                public Object getNo() {
                    return no;
                }

                public void setNo(Object no) {
                    this.no = no;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getEmail() {
                    return email;
                }

                public void setEmail(Object email) {
                    this.email = email;
                }

                public Object getPhone() {
                    return phone;
                }

                public void setPhone(Object phone) {
                    this.phone = phone;
                }

                public Object getMobile() {
                    return mobile;
                }

                public void setMobile(Object mobile) {
                    this.mobile = mobile;
                }

                public Object getUserType() {
                    return userType;
                }

                public void setUserType(Object userType) {
                    this.userType = userType;
                }

                public Object getLoginIp() {
                    return loginIp;
                }

                public void setLoginIp(Object loginIp) {
                    this.loginIp = loginIp;
                }

                public Object getLoginDate() {
                    return loginDate;
                }

                public void setLoginDate(Object loginDate) {
                    this.loginDate = loginDate;
                }

                public String getLoginFlag() {
                    return loginFlag;
                }

                public void setLoginFlag(String loginFlag) {
                    this.loginFlag = loginFlag;
                }

                public Object getPhoto() {
                    return photo;
                }

                public void setPhoto(Object photo) {
                    this.photo = photo;
                }

                public Object getQrCode() {
                    return qrCode;
                }

                public void setQrCode(Object qrCode) {
                    this.qrCode = qrCode;
                }

                public Object getOldLoginName() {
                    return oldLoginName;
                }

                public void setOldLoginName(Object oldLoginName) {
                    this.oldLoginName = oldLoginName;
                }

                public Object getNewPassword() {
                    return newPassword;
                }

                public void setNewPassword(Object newPassword) {
                    this.newPassword = newPassword;
                }

                public Object getSign() {
                    return sign;
                }

                public void setSign(Object sign) {
                    this.sign = sign;
                }

                public Object getYs7Id() {
                    return ys7Id;
                }

                public void setYs7Id(Object ys7Id) {
                    this.ys7Id = ys7Id;
                }

                public Object getOldLoginIp() {
                    return oldLoginIp;
                }

                public void setOldLoginIp(Object oldLoginIp) {
                    this.oldLoginIp = oldLoginIp;
                }

                public Object getOldLoginDate() {
                    return oldLoginDate;
                }

                public void setOldLoginDate(Object oldLoginDate) {
                    this.oldLoginDate = oldLoginDate;
                }

                public Object getRole() {
                    return role;
                }

                public void setRole(Object role) {
                    this.role = role;
                }

                public boolean isAdmin() {
                    return admin;
                }

                public void setAdmin(boolean admin) {
                    this.admin = admin;
                }

                public String getRoleNames() {
                    return roleNames;
                }

                public void setRoleNames(String roleNames) {
                    this.roleNames = roleNames;
                }
            }

            public static class AquFarmBean {
                /**
                 * id : 556467710fce4d0d96c6dbd48c7fadae
                 * isNewRecord : false
                 * remarks : null
                 * createDate : null
                 * updateDate : null
                 * farmName : 灵宝江辉养殖合作社
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
