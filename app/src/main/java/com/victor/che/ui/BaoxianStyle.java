package com.victor.che.ui;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class BaoxianStyle implements Serializable{
    private static final long serialVersionUID = 1L;
  /*  {
             "商业险":
             [
             ],
             "交强险":
             [
                    {
                           "insurance_category_id": 3  #险种id,
                           "name": "车船税险"  #险种名称,
                           "is_free": 0  #是否不计免陪 0：否；1：是,
                           "is_coverage": 1  #是否枚举可选保额。0：否；1：是,
                           "coverage":
                           [
                                  "20",
                                  "50"
                           ]
                    }
             ]
      }*/

    private List<商业险Bean> syx;
    private List<交强险Bean> jqx;
    private boolean ison_off;
    private String mc;//方案名称
    private String nr;//哪些保险
    private String faxh;//就是方案号

    public String getFaxh() {
        return faxh;
    }

    public void setFaxh(String faxh) {
        this.faxh = faxh;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public boolean ison_off() {
        return ison_off;
    }

    public void setIson_off(boolean ison_off) {
        this.ison_off = ison_off;
    }

    public List<商业险Bean> get商业险() {
        return syx;
    }

    public void set商业险(List<商业险Bean> 商业险) {
        this.syx = 商业险;
    }

    public List<交强险Bean> get交强险() {
        return jqx;
    }

    public void set交强险(List<交强险Bean> 交强险) {
        this.jqx = 交强险;
    }

    public static class 商业险Bean implements Serializable {
        /**
         * insurance_category_id : 3
         * name : 车船税险
         * is_free : 0
         * is_coverage : 1
         * coverage : ["20","50"]
         */

        private int insurance_category_id;
        private String name;
        private int is_free;
        private int is_coverage;
        private List<String> coverage;
        private String Tb;
        private String Je;

        public String getJe() {
            return Je;
        }

        public void setJe(String je) {
            Je = je;
        }

        public String getTb() {
            return Tb;
        }

        public void setTb(String tb) {
            Tb = tb;
        }

        public int getInsurance_category_id() {
            return insurance_category_id;
        }

        public void setInsurance_category_id(int insurance_category_id) {
            this.insurance_category_id = insurance_category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_free() {
            return is_free;
        }

        public void setIs_free(int is_free) {
            this.is_free = is_free;
        }

        public int getIs_coverage() {
            return is_coverage;
        }

        public void setIs_coverage(int is_coverage) {
            this.is_coverage = is_coverage;
        }

        public List<String> getCoverage() {
            return coverage;
        }

        public void setCoverage(List<String> coverage) {
            this.coverage = coverage;
        }
    }

    public static class 交强险Bean implements Serializable{
        /**
         * insurance_category_id : 3
         * name : 车船税险
         * is_free : 0
         * is_coverage : 1
         * coverage : ["20","50"]
         */

        private int insurance_category_id;
        private String name;
        private int is_free;
        private int is_coverage;
        private List<String> coverage;

        public int getInsurance_category_id() {
            return insurance_category_id;
        }

        public void setInsurance_category_id(int insurance_category_id) {
            this.insurance_category_id = insurance_category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_free() {
            return is_free;
        }

        public void setIs_free(int is_free) {
            this.is_free = is_free;
        }

        public int getIs_coverage() {
            return is_coverage;
        }

        public void setIs_coverage(int is_coverage) {
            this.is_coverage = is_coverage;
        }

        public List<String> getCoverage() {
            return coverage;
        }

        public void setCoverage(List<String> coverage) {
            this.coverage = coverage;
        }
    }
}
