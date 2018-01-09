package com.victor.che.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Base64工具类
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/10 0010 11:04
 */
public class Base64Util {

    /**
     * Base64编码
     *
     * @param input
     * @param charset
     * @return
     */
    public static String encode(String input, String charset) {
        try {
            String value = Base64.encodeToString(input.getBytes(charset), Base64.DEFAULT);
            return value;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Base64解码
     *
     * @param input
     * @param charset
     * @return
     */
    public static String decode(String input, String charset) {
        byte[] value = Base64.decode(input, Base64.DEFAULT);
        return new String(value);
    }
}
