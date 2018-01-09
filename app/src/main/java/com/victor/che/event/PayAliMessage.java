package com.victor.che.event;

/**
 * Created by Administrator on 2017/4/18.
 */

public class PayAliMessage {

    /**
     * payDic : app_id=2017032406383486&method=alipay.trade.app.pay&timestamp=2017-04-18+11%3A31%3A17&charset=utf-8&version=1.0&notify_url=http%3A%2F%2Fxapitest.cheweifang.cn%2Fapp%2Fnotify%2Fali_notify%2Fnotify_url.php&biz_content=%7B%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22out_trade_no%22%3A%22201704181131171492486277705%22%2C%22total_amount%22%3A0.01%2C%22subject%22%3A%22201704181131171492486277705%22%7D&sign_type=RSA2&sign=iddKJtMWO%2BmfunjI%2F4gW2qf6yQpOTaSeuVtd3dUgbTnh7kjro90KBaVvQOGHySuWrr%2BFRmA9TSHf6lrorNzeP%2F6I3wtoeZ5AA8GUXlUKaTquP4B5UU3ercPrjlwOkX5rAzEKHqqyKwzGyRza7E7lZeWDED1W7d0TuJw8VmtmOOFbiMiNtlIkuSKb006%2FElVACvaoMGoqoocf%2Bdh8o7BWeiS6G7xBr0Xepkz7qdMm6zVM7MTVncG4ztaA2iOZbpIQYTUJagtJPHF5h08gl5k1%2BupgIG%2FVckZkcqX5p%2B8ZovjYPahivQcDKIiVZt0laf6NPWeuUD%2BT8MFEBpzKBImV3A%3D%3D
     */


    private String payDic;
    private String shopping_order_id;

    public String getShopping_order_id() {
        return shopping_order_id;
    }

    public void setShopping_order_id(String shopping_order_id) {
        this.shopping_order_id = shopping_order_id;
    }

    public String getPayDic() {
        return payDic;
    }

    public void setPayDic(String payDic) {
        this.payDic = payDic;
    }
}
