package com.victor.che.util;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解决部分手机上Log.d不打印的bug
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 13:35
 */
public class MyLogger {
    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;

    public static void json(String json) {
        if (json == null || json.length() == 0) {
            Logger.e("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                Logger.e(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                Logger.e(message);
                return;
            }
            Logger.e("Invalid Json");
        } catch (JSONException e) {
            Logger.e("Invalid Json");
        }
    }
}
