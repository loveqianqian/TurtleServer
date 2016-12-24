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
import java.util.Objects;

/**
 * com.heren.turtle.server.agent
 *
 * @author zhiwei
 * @create 2016-12-08 23:57.
 */
public interface INIAgent {

    /**
     * 当需要科室数据的时候调用
     *
     * @param deptCode
     * @return
     */
    List<Map<String, Object>> getDept(String deptCode);

    /**
     * 当需要人员数据的时候调用
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> getEmp(Map<String, Object> params);

    /**
     * 当需要病人基本信息的时候调用
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> getPatient(Map<String, Object> params);

    /**
     * 当需要医嘱数据的时候调用
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> getOrders(Map<String, Object> params);


    /**
     * 当需要转院转科数据的时候调用
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> getTransfer(Map<String, Object> params);

    /**
     * 当需要诊断数据的时候调用
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> getDiagnosis(Map<String, Object> params);

    /**
     * 当需要抗生素用药数据的时候调用
     * @param params
     * @return
     */
    List<Map<String, Object>> getDrug(Map<String, Object> params);
}
