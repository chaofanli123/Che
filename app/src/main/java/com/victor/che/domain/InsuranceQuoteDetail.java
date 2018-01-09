package com.victor.che.domain;

import java.util.List;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/10 0010 15:59.
 */

public class InsuranceQuoteDetail {

    public InsuranceCategory insurance_categorys;
    public List<InsuranceEndTime> insurance_end_time;
    public List<InsuranceStartTime> insurance_start_time;

    public class InsuranceCategory {

        public BaoXian jqx;
        public BaoXian syx;

        public class BaoXian {
//               "free": "0"  #不计免赔金额,
//                    "is_free": -1  #界面是否显示不计免赔金额， -1:不显示 1:显示,
//                    "insurance_categorys":
            public String free;
            public int is_free;
            public List<InCategorys> insurance_categorys;
        }

        public class InCategorys {
            //                   "insurance_category_name": "车船税险"  #险种名称,
            //                        "coverage": "15.00"  #保额(单位：元),
            //                                         "premium": "13.00"  #保费（单位：元）,
            //                        "is_free": -1  #该险种是否有不计免赔， -1 ：无 0：有且不计免赔 1:有且计免赔
            public String insurance_category_name;
            public String coverage;
            public String premium;
            public int is_free;
        }
    }

    public class InsuranceEndTime {
        //         "name": "交强险",
        //                 "used_by_time": "0000-00-00"
        public String name;
        public String used_by_time;
    }
    public class InsuranceStartTime {
        //         "name": "商业险",
//                           "restart_by_time": "0000-00-00"
        public String name;
        public String restart_by_time;
    }
}
