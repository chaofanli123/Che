package com.victor.che.pay.wxpay.wxpay;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 与新版微信支付的相关参数
 * 
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月28日 下午1:04:48
 */
public class WXPayParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * appid : wxff87f02dac93ce58
	 * partnerid : 1455289802
	 * prepayid : wx201704211831537e774e6df70250588258
	 * package : Sign=WXPay
	 * noncestr : bkp6x21al0jbv4tty95t6zmc4k4kxsa5
	 * timestamp : 1492770713
	 * sign : 253B79AA81412BC12AE43047FCB312AD
	 */

	public String appid;
	public String partnerid;
	public String prepayid;
	@JSONField(name = "package")
	public String packageValue;
	public String noncestr;
	public int timestamp;
	public String sign;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getPackageX() {
		return packageValue;
	}

	public void setPackageX(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
