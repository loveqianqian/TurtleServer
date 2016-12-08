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

package com.heren.turtle.server.agent;

import java.util.Map;

/**
 * com.heren.turtle.server.agent
 *
 * @author zhiwei
 * @create 2016-12-08 23:57.
 */
public interface IOAAgent {

    /**
     * 当OA系统用科室变化的时候，会进行增删改工作，成功返回true，失败返回false
     *
     * @param actionName
     * @param params
     * @return
     */
    boolean setDept(String actionName, Map<String, Object> params);

    /**
     * 当OA系统有人员变化的时候，会进行增删改工作，成功返回true，失败返回false
     *
     * @param actionName
     * @param params
     * @return
     */
    boolean setEmp(String actionName,Map<String, Object> params);
}
