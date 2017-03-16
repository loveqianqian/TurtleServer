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

import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.server.agent
 *
 * @author zhiwei
 * @create 2016-12-08 23:57.
 */
public interface ILisAgent {

    /**
     * lis获取检验申请单信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getLisApply(Map<String, Object> params) throws Exception;

    /**
     * 检验计费
     *
     * @param params HashMap
     * @return HashMap
     */
    Map<String, Object> lisCharge(Map<String, Object> params) throws Exception;

    /**
     * 微生物计费
     *
     * @param params HashMap
     * @return HashMap
     */
    Map<String, Object> microbeCharge(Map<String, Object> params) throws Exception;

    /**
     * 检验状态更新
     *
     * @param params HashMap
     * @return HashMap
     */
    Map<String, Object> lisStatusChange(Map<String, Object> params) throws Exception;

    /**
     * 检验报告
     *
     * @param params HashMap
     * @return HashMap
     */
    Map<String, Object> lisReport(Map<String, Object> params, String message) throws Exception;

}
