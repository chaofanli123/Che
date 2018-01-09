package com.victor.che.pay.wxpay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.ui.AppointResultActivity;
import com.victor.che.ui.PayResultActivity;

import java.util.Map;

/**
 * 支付宝支付
 *
 * @author Administrator
 */
public class AlipayUtil {

    private static final int SDK_PAY_FLAG = 1;// 支付宝支付标记

    private static Activity activity;
    private static String out_trade_no;// 商户订单号
    public  AlipayUtil(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("HandlerLeak")
    public static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (SDK_PAY_FLAG != msg.what) {// 非支付宝支付的回调
                return;
            }
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultStatus = payResult.getResultStatus();
            if ("1".equalsIgnoreCase(resultStatus)) {// “9000”则代表支付成功
                // 访问服务器，确认支付结果
                gotoPayResult(true);
            } else {// 判断resultStatus 为非“9000”则代表可能支付失败
                if ("0".equalsIgnoreCase(resultStatus)) {// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    MyApplication.showToast("支付结果确认中");
                } else if ("-1".equals(resultStatus)) {// 6001：用户中途取消支付操作

                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    // 字符串类型；错误码，取值范围如下：
                    // 9000：支付成功
                    // 8000：正在处理中
                    // 4000：系统异常
                    // 4001：数据格式不正确
                    // 4003：该用户绑定的支付宝账户被冻结或不允许支付
                    // 4004：该用户已解除绑定
                    // 4005：绑定失败或没有绑定
                    // 4006：订单支付失败
                    // 4010：重新绑定账户
                    // 6000：支付服务正在进行升级操作
                    // 6002： 网络连接出错
                    gotoPayResult(false);// 支付失败
                }
            }
        }
    };

    /**
     * 去支付结果界面
     *
     * @param mPaySuccess 表示成功或失败
     */
    public static void gotoPayResult(boolean mPaySuccess) {
        if (ConstantValue.APPOINT == 1) {// 表示预约
            Bundle bundle = new Bundle();
            bundle.putBoolean("mPaySuccess", mPaySuccess);// 是否成功
          MyApplication.openActivity(activity, AppointResultActivity.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean("mPaySuccess", mPaySuccess);// 是否成功
            bundle.putString("mTransactNo", out_trade_no);// 交易流水号
            bundle.putString("mPayType", ConstantValue.PAY_TYPE_ALIPAY);// 支付方式， 支付宝
            MyApplication.openActivity(activity, PayResultActivity.class, bundle);
        }
    }
    /**
     * 开启支付
     *
     * @param alipaystr    支付宝支付参数
     * @param out_trade_no 商户订单号
     * @param saveAmount   节省多少钱
     */
    public static void startAlipay(final String alipaystr, String out_trade_no, double saveAmount) {
        AlipayUtil.out_trade_no = out_trade_no;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(alipaystr, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
