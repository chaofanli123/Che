package com.victor.che.api;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 新API通用格式
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月22日 上午10:00:58
 */
public class Element implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success;// true 是代表 成功，false 失败
    public  String errorCode;// -1
    public String msg;// 前端显示的提示信息
    public String body;// 实际数据
    public int count;// 总记录数
    public JSONObject options;
    /**
     * 聚合证件识别接口返回参数
     **/
    public int error_code;//	返回码
    public String reason;//返回说明
    public String result;//	返回结果集

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return body;
    }

    public void setData(String data) {
        this.body = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public JSONObject getOptions() {
        return options;
    }

    public void setOptions(JSONObject options) {
        this.options = options;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Element{" +
                "success=" + success +
                ", errorCode=" + errorCode +
                ", msg='" + msg + '\'' +
                ", body='" + body + '\'' +
                ", count=" + count +
                ", options=" + options +
                ", error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    /**
     * 是否是最后一页
     * @param pageSize
     * @param page
     * @return
     */
    public boolean isLastPage(int pageSize, int page) {
        if (getCount() <= pageSize * page) {
            return true;
        } else {
            return false;
        }
    }

}

