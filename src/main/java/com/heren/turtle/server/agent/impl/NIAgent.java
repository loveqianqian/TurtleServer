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

package com.heren.turtle.server.agent.impl;

import com.heren.turtle.server.agent.INIAgent;
import com.heren.turtle.server.dao.hisDao.*;
import com.heren.turtle.server.utils.ConversionUtils;
import com.heren.turtle.server.utils.TransUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.server.agent.impl
 *
 * @author zhiwei
 * @create 2016-12-08 23:57.
 */
@Component("niAgent")
@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class NIAgent implements INIAgent {

    @Autowired
    private HisDiagnosisDao hisDiagnosisDao;

    @Autowired
    private HisDeptDao hisDeptDao;

    @Autowired
    private HisOrderDao hisOrderDao;

    @Autowired
    private HisPatientDao hisPatientDao;

    @Autowired
    private HisUsersDao hisUsersDao;

    @Autowired
    private TransUtils transUtils;

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 当需要科室数据的时候调用
     *
     * @param deptCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDept(String deptCode) {
        logger.info("getDept params:" + deptCode);
        List<Map<String, Object>> resultList = hisDeptDao.queryNIDept(deptCode);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("dept_css", "nullValue");
        }
        return resultList;
    }

    /**
     * 当需要人员数据的时候调用
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getEmp(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("getEmp params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> resultList = hisUsersDao.queryNiEmp(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
        }
        return resultList;
    }

    /**
     * 当需要病人基本信息的时候调用
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPatient(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("getPatient params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> resultList = hisPatientDao.queryNIPatient(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
        }
        return resultList;
    }

    /**
     * 当需要医嘱数据的时候调用
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getOrders(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("getOrders params:" + key + ":" + params.get(key)));
        if (params.containsKey("reqNo")) {
            String[] reqNo = String.valueOf(params.get("reqNo")).split("_");
            params.put("patientId", reqNo[0]);
            params.put("visitId", reqNo[1]);
        }
        List<Map<String, Object>> resultList = hisOrderDao.queryNiOrder(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("doctor_no", "nullValue");
        }
        return resultList;
    }

    /**
     * 当需要转院转科数据的时候调用
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getTransfer(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("getTransfer params:" + key + ":" + params.get(key)));
        if (params.containsKey("reqNo")) {
            String[] reqNo = String.valueOf(params.get("reqNo")).split("_");
            params.put("patientId", reqNo[0]);
            params.put("visitId", reqNo[1]);
        }
        List<Map<String, Object>> resultList = hisDeptDao.queryNiTransfer(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
        }
        return resultList;
    }

    /**
     * 当需要诊断数据的时候调用
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDiagnosis(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("getDiagnosis params:" + key + ":" + params.get(key)));
        if (params.containsKey("reqNo")) {
            String[] reqNo = String.valueOf(params.get("reqNo")).split("_");
            params.put("patientId", reqNo[0]);
            params.put("visitId", reqNo[1]);
        }
        List<Map<String, Object>> resultList = hisDiagnosisDao.queryNiDiagnosis(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("diag_doc", "nullValue");
            resultMap.put("diag_docno", "nullValue");
            resultMap.put("outhostext","nullValue");
        }
        return resultList;
    }
}
