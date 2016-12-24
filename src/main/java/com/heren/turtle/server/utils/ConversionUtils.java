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

/**
 * com.heren.turtle.server.utils
 *
 * @author zhiwei
 * @create 2016-11-14 10:18.
 */
public class ConversionUtils {

    public static Object isNullValue(Object obj, TransUtils transUtils) {
        String result = "nullValue";
        if (obj == null) {
            return result;
        }
        return transUtils.toTrans(obj);
    }


}
