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

import java.util.Map;

/**
 * com.heren.turtle.server.utils
 *
 * @author zhiwei
 * @create 2016-11-30 0:55.
 */
public class BooleanUtils {

    /**
     *
     * @param params
     * @param key
     * @return
     */
    public static boolean putMapBoolean(Map<String, Object> params, String key) {
        return params.containsKey(key) && !params.get(key).equals("");
    }
}
