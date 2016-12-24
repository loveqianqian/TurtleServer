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

import com.heren.turtle.server.agent.IMnisAgent;
import com.heren.turtle.server.dao.hisDao.*;
import com.heren.turtle.server.dao.turtleDao.TurtleOrdersDao;
import com.heren.turtle.server.dao.turtleDao.TurtlePioDao;
import com.heren.turtle.server.dao.turtleDao.TurtleSignDao;
import com.heren.turtle.server.utils.BooleanUtils;
import com.heren.turtle.server.utils.ConversionUtils;
import com.heren.turtle.server.utils.TimeUtils;
import com.heren.turtle.server.utils.TransUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.heren.turtle.server.service.Summoner.spells.add;

/**
 * com.heren.turtle.server.agent.impl
 *
 * @author zhiwei
 * @create 2016-12-09 0:05.
 */
@SuppressWarnings("JavaDoc")
@Component("mnisAgent")
@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class MnisAgent implements IMnisAgent {

    @Autowired
    private HisDeptDao hisDeptDao;

    @Autowired
    private HisPatientDao hisPatientDao;

    @Autowired
    private HisOrderDao hisOrderDao;

    @Autowired
    private HisUsersDao hisUserDao;

    @Autowired
    private TurtleOrdersDao turtleOrdersDao;

    @Autowired
    private HisSignDao hisSignDao;

    @Autowired
    private TurtlePioDao turtlePioDao;

    @Autowired
    private TurtleSignDao turtleSignDao;

    @Autowired
    private TransUtils transUtils;

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 通过科室代码查找科室
     *
     * @param deptCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDept(String deptCode) {
        logger.info("mnis getDept params:" + deptCode);
        List<Map<String, Object>> resultList = hisDeptDao.queryOnlyDept(deptCode);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("parent_dept_code", "nullValue");
            resultMap.put("parent_dept_name", "nullValue");
            resultMap.put("actionType", "nullValue");
        }
        return resultList;
    }

    /**
     * 通过病区代码查找病区信息
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getWard(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("mnis getWard params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> resultList = hisDeptDao.queryOnlyWard(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("parent_dept_name", "nullValue");
            resultMap.put("actionType", "nullValue");
        }
        return resultList;
    }

    /**
     * 通过userId查找用户信息
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getUserInfo(Map<String, Object> params) {
        List<Map<String, Object>> totalList = new ArrayList<>();
        boolean deptCodeContain = params.containsKey("deptCode");
        String deptCode = null;
        if (deptCodeContain) {
            deptCode = String.valueOf(params.get("deptCode"));
        }
        List<Map<String, Object>> userDeptList = hisUserDao.queryUserDept(params);
        List<Map<String, Object>> userWardList = hisUserDao.queryUserWard(params);
        for (Map<String, Object> resultMap : userDeptList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("passwd", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultMap.put("ward_code", "nullValue");
        }
        for (Map<String, Object> resultMap : userWardList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("passwd", "nullValue");
            resultMap.put("actionType", "nullValue");
            if (!deptCodeContain) {
                resultMap.put("dept_code", "nullValue");
            } else {
                if (deptCode != null) {
                    resultMap.put("dept_code", deptCode);
                } else {
                    resultMap.put("dept_code", "nullValue");
                }
            }
        }
        totalList.addAll(userWardList);
        totalList.addAll(userDeptList);
        return totalList;
    }

    /**
     * 通过参数查找在院病人信息
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPatInHos(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("mnis getPatInHos params:" + key + ":" + params.get(key)));
        if (params.containsKey("admissionId")) {
            String[] reqNo = String.valueOf(params.get("admissionId")).split("_");
            params.put("patientId", reqNo[0]);
            params.put("visitId", reqNo[1]);
        }
        List<Map<String, Object>> resultList = hisPatientDao.queryPatInHos(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            String birthDay = String.valueOf(resultMap.get("birthday"));
            try {
                resultMap.put("age", TimeUtils.getAge(birthDay));
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("age", "nullValue");
            }
            resultMap.put("status", "住院");
            resultMap.put("diet", "nullValue");
            resultMap.put("balance", "nullValue");
            resultMap.put("charge_type_name", "nullValue");
            resultMap.put("contact_info", "nullValue");
            resultMap.put("weight", "nullValue");
            resultMap.put("height", "nullValue");
            resultMap.put("actionType", "nullValue");
        }
        return resultList;
    }

    /**
     * 通过参数朝找出院病人信息
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPatOutHos(List<String> params) {
        List<Map<String, Object>> resultList = hisPatientDao.queryPatOutHos(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key -> resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("diet", "nullValue");
            resultMap.put("arrear_flag", "nullValue");
            resultMap.put("status", "出院");
            resultMap.put("age", "nullValue");
            resultMap.put("weight", "nullValue");
            resultMap.put("height", "nullValue");
            resultMap.put("contact_info", "nullValue");
            resultMap.put("professional", "nullValue");
            resultMap.put("bed_no", "nullValue");
            resultMap.put("nursing_class", "nullValue");
            resultMap.put("charge_type_name", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultMap.put("admission_ward_time", "nullValue");
            resultMap.put("diagnosis", "nullValue");
            resultMap.put("patient_condition", "nullValue");
            resultMap.put("pre_payment", "nullValue");
            resultMap.put("balance", "nullValue");
        }
        return resultList;
    }

    /**
     * 通过参数找出转院转科信息
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getTurnDeptTurnBed(Map<String, Object> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().stream().forEach(key -> logger.info("mnis getTurnDeptTurnBed params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> transferList = hisDeptDao.queryTransfer(params);
        for (Map<String, Object> resultMap : transferList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("turn_out_bed_no", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        List<Map<String, Object>> bedList = hisDeptDao.queryTransBed(params);
        for (Map<String, Object> resultMap : bedList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("turn_out_dept_code", "nullValue");
            resultMap.put("turn_out_dept_name", "nullValue");
            resultMap.put("turn_out_ward_code", "nullValue");
            resultMap.put("turn_out_ward_name", "nullValue");
            resultMap.put("turn_out_bed_no", "nullValue");
            resultMap.put("turn_out_time", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 通过参数找出医嘱信息
     *
     * @param params
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getOrder(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("mnis getOrder params:" + key + ":" + params.get(key)));
        if (params.containsKey("order_class")) {
            String orderClass = (String) params.get("order_class");
            String[] ocArray = orderClass.split(",");
            List<String> ocItems = Arrays.asList(ocArray);
            params.put("orderClassItems", ocItems);
        }
        if (params.containsKey("supply_code")) {
            String supplyCode = (String) params.get("supply_code");
            String[] scArray = supplyCode.split(",");
            List<String> scItems = Arrays.asList(scArray);
            params.put("supplyCodeItems", scItems);
        }
        List<Map<String, Object>> resultList = hisOrderDao.queryOrders(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().stream().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("drug_spec", "nullValue");
            resultMap.put("high_risk", "nullValue");
            resultMap.put("today_times", "nullValue");
            resultMap.put("skin_test", "nullValue");
            resultMap.put("provide_by_self", "nullValue");
            resultMap.put("is_aux", "nullValue");
            resultMap.put("exhortation", "nullValue");
            resultMap.put("remark", "nullValue");
            resultMap.put("actionType", "nullValue");
        }
        return resultList;
    }

    /**
     * 通过参数回写医嘱信息
     *
     * @param params
     * @return
     */
    @Override
    public boolean writebackOrder(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("mnis writebackOrder params:" + key + ":" + params.get(key)));
        try {
            if (BooleanUtils.putMapBoolean(params, "actionType")) {
                String actionType = String.valueOf(params.get("actionType"));
                params.put("actionType", actionType);
                logger.info("actionType:" + actionType);
                turtleOrdersDao.add(params);
                hisOrderDao.modify(params);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 通过参数回写pio信息
     *
     * @param params
     * @return
     */
    @Override
    public boolean writebackPio(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("mnis writebackPio params:" + key + ":" + params.get(key)));
        try {
            if (BooleanUtils.putMapBoolean(params, "actionType")) {
                String actionType = String.valueOf(params.get("actionType"));
                params.put("actionType", actionType);
                logger.info("actionType:" + actionType);
                turtlePioDao.add(params);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 通过参数回写体征信息
     *
     * @param params
     * @return
     */
    @Override
    public boolean writebackSign(Map<String, Object> params) {
        params.keySet().stream().forEach(key -> logger.info("mnis writebackSign params:" + key + ":" + params.get(key)));
        try {
            if (BooleanUtils.putMapBoolean(params, "actionType")) {
                String actionType = String.valueOf(params.get("actionType"));
                params.put("actionType", actionType);
                logger.info("actionType:" + actionType);
                turtleSignDao.add(params);
//                if (actionType.equalsIgnoreCase(add.name())) {
//                    hisSignDao.add(params);
//                } else {
//                    hisSignDao.modify(params);
//                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
