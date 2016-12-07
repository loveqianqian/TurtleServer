/*
 *
 *  *****************************************************************************
 *  * Copyright ( c ) 2016 Heren Tianjin Inc. All Rights Reserved.
 *  *
 *  * This software is the confidential and proprietary information of Heren Tianjin Inc
 *  * ("Confidential Information").  You shall not disclose such Confidential Information
 *  *  and shall use it only in accordance with the terms of the license agreement
 *  *  you entered into with Heren Tianjin or a Heren Tianjin authorized
 *  *  reseller (the "License Agreement").
 *  ****************************************************************************
 *  *
 */

package com.heren.turtle.server.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * com.heren.turtle.server.utils
 *
 * @author zhiwei
 * @create 2016-07-20 13:38.
 */
public class JsonUtils extends JSON {

    /**
     * is json
     *
     * @param value
     * @return
     */
    public static boolean isJson(String value) {
        try {
            JSON.parse(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }


    /**
     * JSONObject to map
     *
     * @param object
     */
    public static Map<String, Object> jsonToMap(JSONObject object) {
        Map<String, Object> map = new HashMap<>();
        for (String key : object.keySet()) {
            Object value = object.get(key);
            if (value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * JSONObject to map
     *
     * @param object
     */
    public static ConcurrentMap<String, String> jsonToConcurrentMap(JSONObject object) {
        ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
        for (String key : object.keySet()) {
            String value = (String) object.get(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * JSONArray to List
     *
     * @param array
     */
    public static List<Object> jsonToList(JSONArray array) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     * JSONArray to List
     *
     * @param array
     */
    public static List<String> jsonToListString(JSONArray array) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            String value = (String) array.get(i);
            list.add(value);
        }
        return list;
    }

    /**
     * back failure information
     *
     * @param result
     * @param message
     * @return
     */
    public static String errorMessage(boolean result, String message) {
        Map<String, Object> msg = new HashMap<>();
        Map<String, Object> subMsg = new HashMap<>();
        Map<String, Object> sonSubMsg = new HashMap<>();
        msg.put("payload", subMsg);
        subMsg.put("response", sonSubMsg);
        sonSubMsg.put("result", result);
        sonSubMsg.put("resultText", message);
        sonSubMsg.put("userId", "0001");
        return JSON.toJSONString(msg);
    }

    /**
     * back successful information
     *
     * @return
     */
    public static String resultMessage() {
        Map<String, Object> msg = new HashMap<>();
        Map<String, Object> subMsg = new HashMap<>();
        Map<String, Object> sonSubMsg = new HashMap<>();
        msg.put("payload", subMsg);
        subMsg.put("response", sonSubMsg);
        sonSubMsg.put("result", true);
        sonSubMsg.put("resultText", "操作成功");
        sonSubMsg.put("userId", "0001");
        return JSON.toJSONString(msg);
    }

    /**
     * delete the label of json that is not use
     *
     * @param message
     * @return
     */
    public static JSONObject getMessage(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        JSONObject payload = jsonObject.getJSONObject("payload");
        return payload.getJSONObject("request");
    }

    /**
     * use the key to distill
     *
     * @param key
     * @return
     */
    public static String distill(String message, String key) {
        JSONObject json = JSON.parseObject(message);
        if (json.containsKey(key)) {
            return json.getString(key);
        }
        return null;
    }
}
