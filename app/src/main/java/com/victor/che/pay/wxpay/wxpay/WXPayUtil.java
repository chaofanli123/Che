package com.victor.che.pay.wxpay.wxpay;

import android.content.Context;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.victor.che.app.MyApplication;

/**
 * 微信支付工具类
 * 
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月28日 下午1:20:07
 */
public class WXPayUtil {

	private static Context mContext;
	private static IWXAPI api;

	public static String out_trade_no = "";// 商户订单号
	public static double saveAmount = -1;// 节省下多少钱

	private WXPayUtil() {
	}

	/**
	 * 是否已安装微信
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWXAppInstalled(Context context) {
		mContext = context;
		if (api == null) {
			api = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID);
		}
		return api.isWXAppInstalled();
	}

	/**
	 * 是否支持微信支付
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isSupportWXPay(Context context) {
		mContext = context;
		if (api == null) {
			api = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID);
		}
		return api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
	}

	/**
	 * 开始微信支付
	 * 
	 * @param context
	 *            微信支付字符串
	 */
	public static void startWXPay(Context context, WXPayParams wxparams, String order_no) {
		mContext = context;
		out_trade_no = order_no;

		if (wxparams == null) {
			MyApplication.showToast("支付参数为空");
			return;
		}
		if (api == null) {
			api = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID);
		}
		try {
			PayReq req = new PayReq();
			req.appId = wxparams.appid;
			req.partnerId = wxparams.partnerid;
			req.prepayId = wxparams.prepayid;
			req.nonceStr = wxparams.noncestr;
			req.timeStamp = wxparams.timestamp+"";
			req.packageValue = wxparams.packageValue;
			req.sign = wxparams.sign;
			// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
			api.sendReq(req);
		} catch (Exception e) {
			MyApplication.showToast("异常：" + e.getMessage());
		}
	}
}
