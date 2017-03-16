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
 * @create 2016-12-09 0:05.
 */
public interface IMnisAgent {

    /**
     * 通过科室代码查找科室
     *
     * @param deptCode String
     * @return ArrayList
     */
    List<Map<String, Object>> getDept(String deptCode);

    /**
     * 通过病区代码查找病区信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getWard(Map<String, Object> params);

    /**
     * 通过userId查找用户信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getUserInfo(Map<String, Object> params);

    /**
     * 通过参数查找在院病人信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getPatInHos(Map<String, Object> params);


    /**
     * 通过参数朝找出院病人信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getPatOutHos(List<Map<String, Object>> params);

    /**
     * 通过参数找出转院转科信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getTurnDeptTurnBed(Map<String, Object> params);

    /**
     * 通过参数找出医嘱信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getOrder(Map<String, Object> params);

    /**
     * 通过参数找出医嘱信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getOrderDrug(Map<String, Object> params);

    /**
     * 通过参数回写医嘱信息
     *
     * @param params HashMap
     * @return boolean
     */
    boolean writeBackOrder(Map<String, Object> params);

    /**
     * 通过参数回写pio信息
     *
     * @param params HashMap
     * @return boolean
     */
    boolean writeBackPio(Map<String, Object> params);

    /**
     * 通过参数回写体征信息
     *
     * @param params HashMap
     * @return boolean
     */
    boolean writeBackSign(Map<String, Object> params);

    /**
     * 通过参数获取Lis信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getLisInfo(Map<String, Object> params);

    /**
     * 通过参数获取输血信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getBloodInfo(Map<String, Object> params);

    /**
     * 通过参数获取输血申请单信息
     *
     * @param params HashMap
     * @return ArrayList
     */
    List<Map<String, Object>> getBloodRecInfo(Map<String, Object> params);

}
