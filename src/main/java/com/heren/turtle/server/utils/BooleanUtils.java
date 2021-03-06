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
 *
 */

package com.heren.turtle.server.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * com.heren.turtle.server.utils
 *
 * @author zhiwei
 * @create 2016-11-30 0:55.
 */
public class BooleanUtils {

    /**
     * @param params
     * @param key
     * @return
     */
    public static boolean putMapBoolean(Map<String, Object> params, String key) {
        return params.containsKey(key) && !params.get(key).equals("");
    }

    public static boolean notContainMapBoolean(Map<String, Object> params, String key) {
        return !putMapBoolean(params, key);
    }

    public static Map<String, Object> putMapBooleanList(Map<String, Object> params, String... keys) {
        Map<String, Object> resultMap = new HashMap<>();
        Arrays.stream(keys)
              .filter(key -> params.containsKey(key) && !params.get(key).equals(""))
              .forEach(key -> resultMap.put(change(key), params.get(key)));
        return resultMap;
    }

    private static String change(String param) {
        if (param.contains("_")) {
            String[] split = param.split("_");
            StringBuilder sb = new StringBuilder(split[0]);
            for (int i = 1; i < split.length; i++) {
                sb.append(split[i].substring(0, 1).toUpperCase()).append(split[i].substring(1));
            }
            return sb.toString();
        } else {
            return param;
        }
    }
}
