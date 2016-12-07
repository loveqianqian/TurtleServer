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

package com.heren.turtle.server.dao;

import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.server.dao
 *
 * @author zhiwei
 * @create 2016-10-13 20:32.
 */
public interface BaseDao {

    /**
     * base query
     * @param params
     * @return
     */
    List<Map<String, Object>> query(Map<String, Object> params);

    /**
     * base add
     * @param params
     */
    void add(Map<String, Object> params);

    /**
     * base delete
     * @param params
     */
    void delete(Map<String, Object> params);

    /**
     * base modify
     * @param params
     */
    void modify(Map<String, Object> params);
}
