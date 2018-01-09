package com.victor.che.util;

import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;

/**
 * URL网址参数解析类
 * 
 * @author Administrator
 *
 */
public class URLUtil {

	private static final String PARAMETER_SEPARATOR = "&";// 分隔符
	private static final String NAME_VALUE_SEPARATOR = "=";// 分配符

	/**
	 * 获取url后应该拼接的字符， 是？还是&
	 * 
	 * @param url
	 * @return
	 */
	public static String getSepartor(String url) {
		if (url == null) {
			return "";
		}
		return url.contains("?") ? PARAMETER_SEPARATOR : "?";
	}

	/**
	 * 获取url中？之前的部分
	 * 
	 * @param
	 * 
	 * @return
	 */
	public static String getUrlDomain(String url) {
		if (TextUtils.isEmpty(url)) {
			return "";
		}

		if (!url.contains("?")) {
			return url;
		}

		return url.substring(0, url.indexOf("?"));
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static String truncateUrlPage(String strURL) {
		if (TextUtils.isEmpty(strURL)) {
			return null;
		}
		String strAllParam = null;
		strURL = strURL.trim().toLowerCase();
		String[] arrSplit = strURL.split("[?]");

		// 有参数
		if (arrSplit.length > 1) {
			if (arrSplit[1] != null) {
				strAllParam = arrSplit[1];
			}
		}

		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> getRequestParamMap(String URL) {
		if (TextUtils.isEmpty(URL)) {
			return null;
		}

		String strUrlParam = truncateUrlPage(URL);// 得到参数
		if (TextUtils.isEmpty(strUrlParam)) {
			return null;
		}

		Map<String, String> mapRequest = new HashMap<String, String>();
		// 每个键值为一组
		String[] arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				if (!TextUtils.isEmpty(arrSplitEqual[1])) {
					mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);// 正确解析
				} else {
					mapRequest.put(arrSplitEqual[0], "");// 无value
				}
			}
		}
		return mapRequest;
	}

	/**
	 * 根据域名和请求参数拼接成url
	 * 
	 * @param urlDomain
	 * @param urlParams
	 * @return
	 */
	public static String getUrl(String urlDomain, Map<String, String> urlParams) {
		StringBuilder builder = new StringBuilder();
		if (urlParams != null && urlParams.size() > 0) {
			if (!urlDomain.contains("?")) {
				builder.append("?");
			}
			for (Map.Entry<String, String> entry : urlParams.entrySet()) {
				if (builder.length() > 1) {
					builder.append(PARAMETER_SEPARATOR);
				}
				builder.append(entry.getKey()).append(NAME_VALUE_SEPARATOR).append(entry.getValue());
			}
		}
		builder.insert(0, urlDomain == null ? "" : urlDomain);// 在开始部分插入域名
		return builder.toString();
	}
}
