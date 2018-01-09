package com.victor.che.event;

import java.io.Serializable;

/**
 * API通用格式
 * 
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月22日 上午10:00:58
 */
public class Element implements Serializable {

	private static final long serialVersionUID = 1L;

	public int status;// 返回值，表示成功失败, 数字对应意义见各接口 1 表示成功
	public String code;// 提示信息，成功或者失败原因
	public String result;// 请求成功后返回的数据
	public String advanceid;// #result为空返回预购套餐id，根据此id请求套餐详情

	
	/**** 临时数据，为了add_car *****/
	public String data;

	@Override
	public String toString() {
		return "Element [status=" + status + ", code=" + code + ", result=" + result + ", advanceid=" + advanceid
				+ ", data=" + data + "]";
	}

}
