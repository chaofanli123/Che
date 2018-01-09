package com.victor.che.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/10 0010 10:57.
 * 报价方案
 */

public class QuoteProgram implements Serializable{

//     "insurance_categorys": "交强险(50.00万),交强险",
//             "insurance_quote":
    public String insurance_categorys;
    public ArrayList<QuoteListDetail> insurance_quote;

}
