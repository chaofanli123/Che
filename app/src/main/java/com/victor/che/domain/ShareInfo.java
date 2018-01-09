package com.victor.che.domain;

import java.io.Serializable;

/**
 * 首页分享内容
 * 
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月26日 下午3:14:18
 */
public class ShareInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	// "share_title": "share_title", #标题
	// "share_img": "share_img", #图片地址
	// "share_url": "share_url", #链接地址
	// "share_con": "share_con", #分享文字内容

	public String share_title;
	public String share_img;
	public String share_url;
	public String share_con;
}
