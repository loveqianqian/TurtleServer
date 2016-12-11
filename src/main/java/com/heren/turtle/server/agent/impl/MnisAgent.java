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

    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 通过科室代码查找科室
     *
     * @param deptCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDept(String deptCode) {
        List<Map<String, Object>> maps = hisDeptDao.queryOnlyDept(deptCode);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("dept_code", ConversionUtils.isNullValue(map.get("deptCode"), transUtils));
            resultMap.put("dept_name", ConversionUtils.isNullValue(map.get("deptName"), transUtils));
            resultMap.put("dept_alias", ConversionUtils.isNullValue(map.get("deptAlias"), transUtils));
            resultMap.put("pinyin", ConversionUtils.isNullValue(map.get("inputCode"), transUtils));
            resultMap.put("parent_dept_code", "nullValue");
            resultMap.put("parent_dept_name", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 通过病区代码查找病区信息
     *
     * @param wardCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getWard(String wardCode) {
        List<Map<String, Object>> maps = hisDeptDao.queryOnlyWard(wardCode);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("ward_code", ConversionUtils.isNullValue(map.get("wardCode"), transUtils));
            resultMap.put("dept_code", ConversionUtils.isNullValue(map.get("deptCode"), transUtils));
            resultMap.put("ward_name", ConversionUtils.isNullValue(map.get("wardName"), transUtils));
            resultMap.put("ward_alias", ConversionUtils.isNullValue(map.get("wardAlias"), transUtils));
            resultMap.put("pinyin", ConversionUtils.isNullValue(map.get("inputCode"), transUtils));
            resultMap.put("parent_dept_name", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 通过userId查找用户信息
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getUserInfo(String userId, Map<String, Object> params) {
        List<Map<String, Object>> totalMap = new ArrayList<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        totalMap.addAll(hisUserDao.queryUserDept(userId));
        totalMap.addAll(hisUserDao.queryUserWard(userId));
        if (totalMap.size() > 0) {
            for (Map<String, Object> map : totalMap) {
                Map<String, Object> resultMap = new HashMap<>();
                if (params.containsKey("ward_code") && !params.get("ward_code").equals("")) {
                    String wardCode = (String) params.get("ward_code");
                    if (map.get("userWard") != null && map.get("userWard").equals(wardCode)) {
                        userInfoSample(resultList, map, resultMap);
                    }
                } else if (BooleanUtils.putMapBoolean(params, "dept_code")) {
                    String deptCode = (String) params.get("dept_code");
                    if (map.get("userDept") != null && !map.get("userDept").equals(deptCode)) {
                        userInfoSample(resultList, map, resultMap);
                    }
                } else {
                    userInfoSample(resultList, map, resultMap);
                }
            }
        }
        return resultList;
    }

    private void userInfoSample(List<Map<String, Object>> resultList, Map<String, Object> map, Map<String, Object> resultMap) {
        resultMap.put("login_id", ConversionUtils.isNullValue(map.get("userId"), transUtils));
        resultMap.put("passwd", "nullValue");
        resultMap.put("name", ConversionUtils.isNullValue(map.get("userName"), transUtils));
        resultMap.put("user_code", ConversionUtils.isNullValue(map.get("userId"), transUtils));
        resultMap.put("dept_code", ConversionUtils.isNullValue(map.get("userDept"), transUtils));
        resultMap.put("ward_code", ConversionUtils.isNullValue(map.get("userWard"), transUtils));
        resultMap.put("actionType", "nullValue");
        resultList.add(resultMap);
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
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Map<String, Object>> queryList = hisPatientDao.queryPatInHos(params);
        for (Map<String, Object> map : queryList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("patient_id", ConversionUtils.isNullValue(map.get("patientId"), transUtils));
            resultMap.put("series", ConversionUtils.isNullValue(map.get("visitId"), transUtils));
            resultMap.put("admission_id", ConversionUtils.isNullValue(map.get("admissionId"), transUtils));
            resultMap.put("name", ConversionUtils.isNullValue(map.get("name"), transUtils));
            resultMap.put("sex", ConversionUtils.isNullValue(map.get("sex"), transUtils));
            resultMap.put("dept_code", ConversionUtils.isNullValue(map.get("deptCode"), transUtils));
            resultMap.put("dept_name", ConversionUtils.isNullValue(map.get("deptName"), transUtils));
            resultMap.put("ward_code", ConversionUtils.isNullValue(map.get("wardCode"), transUtils));
            resultMap.put("ward_name", ConversionUtils.isNullValue(map.get("wardName"), transUtils));
            resultMap.put("bed_no", ConversionUtils.isNullValue(map.get("bedNo"), transUtils));
            resultMap.put("birthday", ConversionUtils.isNullValue(map.get("birthday"), transUtils));
            resultMap.put("age", ConversionUtils.isNullValue(map.get("age"), transUtils));
            resultMap.put("phone", ConversionUtils.isNullValue(map.get("phone"), transUtils));
            resultMap.put("address", ConversionUtils.isNullValue(map.get("address"), transUtils));
            resultMap.put("professional", ConversionUtils.isNullValue(map.get("professional"), transUtils));
            resultMap.put("contact_info", ConversionUtils.isNullValue(map.get("contactInfo"), transUtils));
            resultMap.put("weight", ConversionUtils.isNullValue(map.get("weight"), transUtils));
            resultMap.put("height", ConversionUtils.isNullValue(map.get("height"), transUtils));
            resultMap.put("admission_time", ConversionUtils.isNullValue(map.get("admissionTime"), transUtils));
            resultMap.put("admission_ward_time", ConversionUtils.isNullValue(map.get("admissionWardTime"), transUtils));
            resultMap.put("discharge_time", ConversionUtils.isNullValue(map.get("dischargeTime"), transUtils));
            resultMap.put("diagnosis", ConversionUtils.isNullValue(map.get("diagnosis"), transUtils));
            resultMap.put("nursing_class", ConversionUtils.isNullValue(map.get("nursingClass"), transUtils));
            resultMap.put("patient_condition", ConversionUtils.isNullValue(map.get("patientCondition"), transUtils));
            resultMap.put("charge_type", ConversionUtils.isNullValue(map.get("chargeType"), transUtils));
            resultMap.put("charge_type_name", ConversionUtils.isNullValue(map.get("chargeTypeName"), transUtils));
            resultMap.put("total_cost", ConversionUtils.isNullValue(map.get("totalCost"), transUtils));
            resultMap.put("pre_payment", ConversionUtils.isNullValue(map.get("prePayment"), transUtils));
            resultMap.put("self_payment", ConversionUtils.isNullValue(map.get("selfPayment"), transUtils));
            resultMap.put("balance", ConversionUtils.isNullValue(map.get("balance"), transUtils));
            resultMap.put("arrear_flag", ConversionUtils.isNullValue(map.get("arrearFlag"), transUtils));
            resultMap.put("diet", ConversionUtils.isNullValue(map.get("diet"), transUtils));
            resultMap.put("doctor_name", ConversionUtils.isNullValue(map.get("doctorName"), transUtils));
            resultMap.put("status", ConversionUtils.isNullValue(map.get("status"), transUtils));
            resultMap.put("allergy", ConversionUtils.isNullValue(map.get("allergy"), transUtils));
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
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
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Map<String, Object>> queryList = hisPatientDao.queryPatOutHos(params);
        for (Map<String, Object> map : queryList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("patient_id", ConversionUtils.isNullValue(map.get("patientId"), transUtils));
            resultMap.put("series", ConversionUtils.isNullValue(map.get("series"), transUtils));
            resultMap.put("admission_id", ConversionUtils.isNullValue(map.get("admissionId"), transUtils));
            resultMap.put("name", ConversionUtils.isNullValue(map.get("name"), transUtils));
            resultMap.put("sex", ConversionUtils.isNullValue(map.get("sex"), transUtils));
            resultMap.put("dept_code", ConversionUtils.isNullValue(map.get("deptCode"), transUtils));
            resultMap.put("dept_name", ConversionUtils.isNullValue(map.get("deptName"), transUtils));
            resultMap.put("ward_code", ConversionUtils.isNullValue(map.get("wardCode"), transUtils));
            resultMap.put("ward_name", ConversionUtils.isNullValue(map.get("wardName"), transUtils));
            resultMap.put("bed_no", ConversionUtils.isNullValue(map.get("bedNo"), transUtils));
            resultMap.put("birthday", ConversionUtils.isNullValue(map.get("birthday"), transUtils));
            resultMap.put("age", ConversionUtils.isNullValue(map.get("age"), transUtils));
            resultMap.put("phone", ConversionUtils.isNullValue(map.get("phone"), transUtils));
            resultMap.put("address", ConversionUtils.isNullValue(map.get("address"), transUtils));
            resultMap.put("professional", ConversionUtils.isNullValue(map.get("professional"), transUtils));
            resultMap.put("contact_info", ConversionUtils.isNullValue(map.get("contactInfo"), transUtils));
            resultMap.put("weight", ConversionUtils.isNullValue(map.get("weight"), transUtils));
            resultMap.put("height", ConversionUtils.isNullValue(map.get("height"), transUtils));
            resultMap.put("admission_time", ConversionUtils.isNullValue(map.get("admissionTime"), transUtils));
            resultMap.put("admission_ward_time", ConversionUtils.isNullValue(map.get("admissionWardTime"), transUtils));
            resultMap.put("discharge_time", ConversionUtils.isNullValue(map.get("dischargeTime"), transUtils));
            resultMap.put("diagnosis", ConversionUtils.isNullValue(map.get("diagnosis"), transUtils));
            resultMap.put("nursing_class", ConversionUtils.isNullValue(map.get("nursingClass"), transUtils));
            resultMap.put("patient_condition", ConversionUtils.isNullValue(map.get("patientCondition"), transUtils));
            resultMap.put("charge_type", ConversionUtils.isNullValue(map.get("chargeType"), transUtils));
            resultMap.put("charge_type_name", ConversionUtils.isNullValue(map.get("chargeTypeName"), transUtils));
            resultMap.put("total_cost", ConversionUtils.isNullValue(map.get("totalCost"), transUtils));
            resultMap.put("pre_payment", ConversionUtils.isNullValue(map.get("prePayment"), transUtils));
            resultMap.put("self_payment", ConversionUtils.isNullValue(map.get("selfPayment"), transUtils));
            resultMap.put("balance", ConversionUtils.isNullValue(map.get("balance"), transUtils));
            resultMap.put("arrear_flag", ConversionUtils.isNullValue(map.get("arrearFlag"), transUtils));
            resultMap.put("diet", ConversionUtils.isNullValue(map.get("diet"), transUtils));
            resultMap.put("doctor_name", ConversionUtils.isNullValue(map.get("doctorName"), transUtils));
            resultMap.put("status", ConversionUtils.isNullValue(map.get("status"), transUtils));
            resultMap.put("allergy", ConversionUtils.isNullValue(map.get("allergy"), transUtils));
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 通过参数找出转院转科信息
     *
     * @param outStartTime
     * @param outEndTime
     * @param patientId
     * @param visitId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getTurnDeptTurnBed(String outStartTime, String outEndTime, String patientId, String visitId) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Map<String, Object>> transferList = hisDeptDao.queryTransfer(outStartTime, outEndTime, patientId, visitId);
        List<Map<String, Object>> bedList = hisDeptDao.queryTransBed(outStartTime, outEndTime, patientId, visitId);
        for (Map<String, Object> map : transferList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("patient_id", ConversionUtils.isNullValue(map.get("patientId"), transUtils));
            resultMap.put("series", ConversionUtils.isNullValue(map.get("visitId"), transUtils));
            resultMap.put("admission_id", ConversionUtils.isNullValue(map.get("admissionId"), transUtils));
            resultMap.put("turn_in_dept_code", ConversionUtils.isNullValue(map.get("deptTransferedTo"), transUtils));
            resultMap.put("turn_in_dept_name", ConversionUtils.isNullValue(map.get("deptTransferedToName"), transUtils));
            resultMap.put("turn_in_ward_code", ConversionUtils.isNullValue(map.get("wardTransuferedTo"), transUtils));
            resultMap.put("turn_in_ward_name", ConversionUtils.isNullValue(map.get("wardTransferedToName"), transUtils));
            resultMap.put("turn_in_bed_no", "nullValue");
            resultMap.put("turn_in_time", ConversionUtils.isNullValue(map.get("dischargeDateTime"), transUtils));
            resultMap.put("turn_out_dept_code", ConversionUtils.isNullValue(map.get("deptStayed"), transUtils));
            resultMap.put("turn_out_dept_name", ConversionUtils.isNullValue(map.get("deptStayedName"), transUtils));
            resultMap.put("turn_out_ward_code", ConversionUtils.isNullValue(map.get("wardStayed"), transUtils));
            resultMap.put("turn_out_ward_name", ConversionUtils.isNullValue(map.get("wardStayedName"), transUtils));
            resultMap.put("turn_out_bed_no", "nullValue");
            resultMap.put("turn_out_time", ConversionUtils.isNullValue(map.get("admissionDateTime"), transUtils));
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        for (Map<String, Object> map : bedList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("patient_id", ConversionUtils.isNullValue(map.get("patientId"), transUtils));
            resultMap.put("series", ConversionUtils.isNullValue(map.get("visitId"), transUtils));
            resultMap.put("admission_id", ConversionUtils.isNullValue(map.get("admissionId"), transUtils));
            resultMap.put("turn_in_dept_code", ConversionUtils.isNullValue(map.get("deptCode"), transUtils));
            resultMap.put("turn_in_dept_name", ConversionUtils.isNullValue(map.get("deptName"), transUtils));
            resultMap.put("turn_in_ward_code", ConversionUtils.isNullValue(map.get("wardCode"), transUtils));
            resultMap.put("turn_in_ward_name", ConversionUtils.isNullValue(map.get("wardName"), transUtils));
            resultMap.put("turn_in_bed_no", ConversionUtils.isNullValue(map.get("bedLabel"), transUtils));
            resultMap.put("turn_in_time", ConversionUtils.isNullValue(map.get("logDateTime"), transUtils));
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
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Map<String, Object>> queryList = hisOrderDao.queryOrders(params);
        for (Map<String, Object> map : queryList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("order_no", ConversionUtils.isNullValue(map.get("orderNo"), transUtils));
            resultMap.put("group_no", ConversionUtils.isNullValue(map.get("groupNo"), transUtils));
            resultMap.put("order_code", ConversionUtils.isNullValue(map.get("orderCode"), transUtils));
            resultMap.put("order_name", ConversionUtils.isNullValue(map.get("orderName"), transUtils));
            resultMap.put("patient_id", ConversionUtils.isNullValue(map.get("patientId"), transUtils));
            resultMap.put("series", ConversionUtils.isNullValue(map.get("series"), transUtils));
            resultMap.put("admission_id", ConversionUtils.isNullValue(map.get("admissionId"), transUtils));
            resultMap.put("dept_code", ConversionUtils.isNullValue(map.get("deptCode"), transUtils));
            resultMap.put("warde_code", ConversionUtils.isNullValue(map.get("wardeCode"), transUtils));
            resultMap.put("frequency_code", ConversionUtils.isNullValue(map.get("frequencyCode"), transUtils));
            resultMap.put("dosage", ConversionUtils.isNullValue(map.get("dosage"), transUtils));
            resultMap.put("dosage_units", ConversionUtils.isNullValue(map.get("dosageUnits"), transUtils));
            resultMap.put("supply_name", ConversionUtils.isNullValue(map.get("supplyName"), transUtils));
            resultMap.put("supply_code", ConversionUtils.isNullValue(map.get("supplyCode"), transUtils));
            resultMap.put("order_status", ConversionUtils.isNullValue(map.get("orderStatus"), transUtils));
            resultMap.put("order_class", ConversionUtils.isNullValue(map.get("orderClass"), transUtils));
            resultMap.put("order_class_name", ConversionUtils.isNullValue(map.get("orderClassName"), transUtils));
            resultMap.put("long_once_flag", ConversionUtils.isNullValue(map.get("longOnceFlag"), transUtils));
            resultMap.put("start_time", ConversionUtils.isNullValue(map.get("startTime"), transUtils));
            resultMap.put("enter_time", ConversionUtils.isNullValue(map.get("enterTime"), transUtils));
            resultMap.put("stop_time", ConversionUtils.isNullValue(map.get("stopTime"), transUtils));
            resultMap.put("doctor_name", ConversionUtils.isNullValue(map.get("doctorName"), transUtils));
            resultMap.put("stop_doctor_name", ConversionUtils.isNullValue(map.get("stopDoctorName"), transUtils));
            resultMap.put("drug_spec", "nullValue");
            resultMap.put("high_risk", "nullValue");
            resultMap.put("today_times", "nullValue");
            resultMap.put("skin_test", "nullValue");
            resultMap.put("provide_by_self", "nullValue");
            resultMap.put("is_aux", "nullValue");
            resultMap.put("exhortation", "nullValue");
            resultMap.put("remark", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
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
        try {
            if (BooleanUtils.putMapBoolean(params, "action_type")) {
                String actionType = String.valueOf(params.get("action_type"));
                params.put("actionType", actionType);
                if (actionType.equalsIgnoreCase(add.name())) {
                    turtleOrdersDao.add(params);
                } else {
                    turtleOrdersDao.modify(params);
                }
            }
            hisOrderDao.modify(params);
            return true;
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
        try {
            if (BooleanUtils.putMapBoolean(params, "action_type")) {
                String actionType = String.valueOf(params.get("action_type"));
                params.put("actionType", actionType);
                if (actionType.equalsIgnoreCase(add.name())) {
                    turtlePioDao.add(params);
                } else {
                    turtlePioDao.modify(params);
                }
            }
            return true;
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
        try {
            if (BooleanUtils.putMapBoolean(params, "action_type")) {
                String actionType = String.valueOf(params.get("action_type"));
                params.put("actionType", actionType);
                if (actionType.equalsIgnoreCase(add.name())) {
                    turtleSignDao.add(params);
                    hisSignDao.add(params);
                } else {
                    turtleSignDao.modify(params);
                    hisSignDao.modify(params);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
