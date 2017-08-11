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
import com.heren.turtle.server.constant.ActionType;
import com.heren.turtle.server.dao.hisDao.*;
import com.heren.turtle.server.dao.turtleDao.TurtleLisDao;
import com.heren.turtle.server.dao.turtleDao.TurtleOrdersDao;
import com.heren.turtle.server.dao.turtleDao.TurtlePioDao;
import com.heren.turtle.server.dao.turtleDao.TurtleSignDao;
import com.heren.turtle.server.exception.LackElementException;
import com.heren.turtle.server.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.server.agent.impl
 *
 * @author zhiwei
 * @create 2016-12-09 0:05.
 */
@SuppressWarnings("JavaDoc")
@Component("mnisAgent")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
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

    @Autowired
    private HisLisDao hisLisDao;

    @Autowired
    private HisBloodDao hisBloodDao;

    @Autowired
    private TurtleLisDao turtleLisDao;

    @Autowired
    private HisExamDao hisExamDao;

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
            resultMap.keySet().forEach(key ->
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
        params.keySet().forEach(key -> logger.info("mnis getWard params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> resultList = hisDeptDao.queryOnlyWard(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().forEach(key ->
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
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("passwd", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultMap.put("ward_code", "nullValue");
        }
        for (Map<String, Object> resultMap : userWardList) {
            resultMap.keySet().forEach(key ->
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
        params.keySet().forEach(key -> logger.info("mnis getPatInHos params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> resultList = hisPatientDao.queryPatInHos(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().forEach(key ->
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
    public List<Map<String, Object>> getPatOutHos(List<Map<String, Object>> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> param : params) {
            resultList.addAll(hisPatientDao.queryPatOutHos(
                    String.valueOf(param.get("patient_id")),
                    String.valueOf(param.get("series"))
            ));
        }
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
        params.keySet().forEach(key -> logger.info("mnis getTurnDeptTurnBed params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> transferList = hisDeptDao.queryTransfer(params);
        for (Map<String, Object> resultMap : transferList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("turn_in_bed_no", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        if (!params.containsKey("turnOutWardCode")) {
            List<Map<String, Object>> bedList = hisDeptDao.queryTransBed(params);
            for (Map<String, Object> resultMap : bedList) {
                resultMap.keySet().forEach(key ->
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
        params.keySet().forEach(key -> logger.info("mnis getOrder params:" + key + ":" + params.get(key)));
        if (params.containsKey("orderClass")) {
            String orderClass = (String) params.get("orderClass");
            String[] ocArray = orderClass.split(",");
            List<String> ocItems = Arrays.asList(ocArray);
            params.put("orderClassItems", ocItems);
        }
        if (params.containsKey("supplyCode")) {
            String supplyCode = (String) params.get("supplyCode");
            String[] scArray = supplyCode.split(",");
            List<String> scItems = Arrays.asList(scArray);
            params.put("supplyCodeItems", scItems);
        }
        List<String> items = (List<String>) params.get("items");
        List<Map<String, Object>> resultList;
        if (items == null) {
            resultList = hisOrderDao.queryOrders(params);
        } else {
            resultList = new ArrayList<>();
            items.forEach(item -> {
                String[] split = item.split("_");
                params.put("orderNo", split[0]);
                params.put("orderSubNo", split[1]);
                params.put("patientId", split[2]);
                params.put("series", split[3]);
                resultList.addAll(hisOrderDao.queryOrders(params));
            });
        }
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
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
     * 通过参数找出医嘱信息
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getOrderDrug(Map<String, Object> params) {
        params.keySet().forEach(key -> logger.info("mnis getOrderDrug params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> resultList = hisOrderDao.queryDrugOrders(params);
        for (Map<String, Object> resultMap : resultList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("high_risk", "nullValue");
            resultMap.put("today_times", "nullValue");
            resultMap.put("skin_test", "nullValue");
            resultMap.put("provide_by_self", "nullValue");
            resultMap.put("is_aux", "nullValue");
            resultMap.put("exhortation", "nullValue");
            resultMap.put("remark", "nullValue");
            resultMap.put("actionType", "nullValue");
            if (!resultMap.containsKey("stop_time")) {
                resultMap.put("stop_time", "nullValue");
            }
            if (!resultMap.containsKey("stop_doctor_name")) {
                resultMap.put("stop_doctor_name", "nullValue");
            }
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
    public boolean writeBackOrder(Map<String, Object> params) {
        params.keySet().forEach(key -> logger.info("mnis writebackOrder params:" + key + ":" + params.get(key)));
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
    public boolean writeBackPio(Map<String, Object> params) {
        params.keySet().forEach(key -> logger.info("mnis writebackPio params:" + key + ":" + params.get(key)));
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
     * @param paramsList
     * @return
     */
    @Override
    public int writeBackSign(List<Map<String, Object>> paramsList) {
        for (Map<String, Object> queryParams : paramsList) {
            Map<String, Object> params;
            boolean patientIdExist = BooleanUtils.putMapBoolean(queryParams, "patient_id");
            boolean seriesExist = BooleanUtils.putMapBoolean(queryParams, "series");
            boolean deptCodeExist = BooleanUtils.putMapBoolean(queryParams, "dept_code");
            boolean wardCodeExist = BooleanUtils.putMapBoolean(queryParams, "ward_code");
            boolean updateTimeExist = BooleanUtils.putMapBoolean(queryParams, "update_time");
            boolean actionTypeExist = BooleanUtils.putMapBoolean(queryParams, "action_type");
            if (patientIdExist && seriesExist && deptCodeExist && wardCodeExist && updateTimeExist && actionTypeExist) {
                params = BooleanUtils.putMapBooleanList(queryParams,
                        "patient_id",
                        "series",
                        "admission_id",
                        "dept_code",
                        "ward_code",
                        "bed_no",
                        "patient_name",
                        "sex_code",
                        "age_year",
                        "age_month",
                        "disease_diagnose_name",
                        "in_hos_time",
                        "real_in_hos_time",
                        "after_operation_time",
                        "plan_time",
                        "record_time",
                        "sign_name",
                        "sign_code",
                        "sign_val",
                        "sign_unit",
                        "is_ventilator",
                        "observe_name",
                        "observe_code",
                        "observe_val",
                        "observe_unit",
                        "remark",
                        "record_nurse_name",
                        "record_nurse_time",
                        "update_time",
                        "action_type");
            } else {
                throw new LackElementException("can't be without some key parameters");
            }
            params.keySet().forEach(key -> logger.info("mnis writebackSign params:" + key + ":" + params.get(key)));
            String actionType = String.valueOf(params.get("actionType"));
            String planTime = String.valueOf(params.get("planTime"));
            String patientId = String.valueOf(params.get("patientId"));
            String series = String.valueOf(params.get("series"));
            String deptCode = String.valueOf(params.get("deptCode"));
            String wardCode = String.valueOf(params.get("wardCode"));
            boolean observeCodeExist = params.containsKey("observeCode");
            Integer count = turtleSignDao.queryCount(planTime, patientId, series, deptCode, wardCode);
            logger.info("mnis writebackSign find count:" + count);
            Map<String, Object> orgMap = turtleSignDao.queryReal(planTime, patientId, series, deptCode, wardCode);
            boolean orgObserveCodeExist = orgMap != null && orgMap.containsKey("observeCode") && orgMap.get("observeCode") != null;
            if (count > 0 && orgObserveCodeExist && observeCodeExist) {
                String[] observeCodes = String.valueOf(orgMap.get("observeCode")).split("\\^");
                String[] observeVals = String.valueOf(orgMap.get("observeVal")).split("\\^");
                String[] observeUnits = String.valueOf(orgMap.get("observeUnit")).split("\\^");
                int observeCount = -1;
                for (int j = 0; j < observeCodes.length; j++) {
                    if (observeCodes[j].equalsIgnoreCase(String.valueOf(params.get("observeCode")))) {
                        observeCount = j;
                    }
                }
                if (ActionType.discard.name().equalsIgnoreCase(actionType)) {
                    String observeCode = SplitUtils.removeSomeOne(String.valueOf(orgMap.get("observeCode")), observeCount);
                    String observeVal = SplitUtils.removeSomeOne(String.valueOf(orgMap.get("observeVal")), observeCount);
                    String observeName = SplitUtils.removeSomeOne(String.valueOf(orgMap.get("observeName")), observeCount);
                    String observeUnit = SplitUtils.removeSomeOne(String.valueOf(orgMap.get("observeUnit")), observeCount);
                    params.put("observeVal", observeVal);
                    params.put("observeCode", observeCode);
                    params.put("observeName", observeName);
                    params.put("observeUnit", observeUnit);
                } else {
                    if (observeCount >= 0) {
                        String observeVal = SplitUtils.replaceWord(observeVals, params.get("observeVal"), observeCount);
                        String observeUnit = SplitUtils.replaceWord(observeUnits, params.get("observeUnit"), observeCount);
                        params.remove("observeCode");
                        params.remove("observeName");
                        params.put("observeVal", observeVal);
                        params.put("observeUnit", observeUnit);
                    } else {
                        params.put("observeVal", String.valueOf(orgMap.get("observeVal")) + "^" + String.valueOf(params.get("observeVal")));
                        params.put("observeCode", String.valueOf(orgMap.get("observeCode")) + "^" + String.valueOf(params.get("observeCode")));
                        params.put("observeName", String.valueOf(orgMap.get("observeName")) + "^" + String.valueOf(params.get("observeName")));
                        params.put("observeUnit", String.valueOf(orgMap.get("observeUnit")) + "^" + String.valueOf(params.get("observeUnit")));
                    }
                }
                String signCode = String.valueOf(params.get("signCode"));
                String signVal = String.valueOf(params.get("signVal"));
                switch (signCode) {
                    case "1001":
                        params.put("temperature", signVal);
                        break;
                    case "1004":
                        params.put("breatheFrequency", signVal);
                        break;
                    case "1002":
                        params.put("pulse", signVal);
                        break;
                    case "1027":
                        params.put("systolicPressure", signVal);
                        break;
                    case "1028":
                        params.put("diastolicPressure", signVal);
                        break;
                    case "1032":
                        params.put("weight", signVal);
                        break;
                    case "1003":
                        params.put("pacerHeartRate", signVal);
                        break;
                    default:
                        break;
                }
                turtleSignDao.modify(params);
            } else if (count == 0 && actionType.equalsIgnoreCase(ActionType.add.name())) {
                turtleSignDao.addCommon(params);
                String signCode = String.valueOf(params.get("signCode"));
                String signVal = String.valueOf(params.get("signVal"));
                switch (signCode) {
                    case "1001":
                        params.put("temperature", signVal);
                        break;
                    case "1004":
                        params.put("breatheFrequency", signVal);
                        break;
                    case "1002":
                        params.put("pulse", signVal);
                        break;
                    case "1027":
                        params.put("systolicPressure", signVal);
                        break;
                    case "1028":
                        params.put("diastolicPressure", signVal);
                        break;
                    case "1032":
                        params.put("weight", signVal);
                        break;
                    case "1003":
                        params.put("pacerHeartRate", signVal);
                        break;
                    default:
                        break;
                }
                turtleSignDao.modify(params);
            } else if (count > 0 && (actionType.equalsIgnoreCase(ActionType.add.name()) || actionType.equalsIgnoreCase(ActionType.modify.name()))) {
                String signCode = String.valueOf(params.get("signCode"));
                String signVal = String.valueOf(params.get("signVal"));
                switch (signCode) {
                    case "1001":
                        params.put("temperature", signVal);
                        break;
                    case "1004":
                        params.put("breatheFrequency", signVal);
                        break;
                    case "1002":
                        params.put("pulse", signVal);
                        break;
                    case "1027":
                        params.put("systolicPressure", signVal);
                        break;
                    case "1028":
                        params.put("diastolicPressure", signVal);
                        break;
                    case "1032":
                        params.put("weight", signVal);
                        break;
                    case "1003":
                        params.put("pacerHeartRate", signVal);
                        break;
                    default:
                        break;
                }
                turtleSignDao.modify(params);
            } else if (count > 0 && actionType.equalsIgnoreCase(ActionType.discard.name())) {
                boolean flag = false;
                for (Object value : orgMap.values()) {
                    if (value != null && !String.valueOf(value).equals("")) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    turtleSignDao.delete(params);
                } else {
                    String signCode = String.valueOf(params.get("signCode"));
                    switch (signCode) {
                        case "1001":
                            params.put("temperature", "nullValue");
                            break;
                        case "1004":
                            params.put("breatheFrequency", "nullValue");
                            break;
                        case "1002":
                            params.put("pulse", "nullValue");
                            break;
                        case "1027":
                            params.put("systolicPressure", "nullValue");
                            break;
                        case "1028":
                            params.put("diastolicPressure", "nullValue");
                            break;
                        case "1032":
                            params.put("weight", "nullValue");
                            break;
                        case "1003":
                            params.put("pacerHeartRate", "nullValue");
                            break;
                        default:
                            break;
                    }
                    turtleSignDao.modify(params);//delete column
                }
            }
        }
        return -1;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getLisInfo(Map<String, Object> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().forEach(key -> logger.info("mnis getLisInfo params:" + key + ":" + params.get(key)));
        if (params.containsKey("orderNo")) {
            String orderNoOld = String.valueOf(params.get("orderNo"));
            String[] orderNoSplit = orderNoOld.split("_");
            String orderNo = orderNoSplit[0];
            String orderSubNo = orderNoSplit[1];
            params.put("orderNo", orderNo);
            params.put("orderSubNo", orderSubNo);
        }
        List<Map<String, Object>> lisList = hisLisDao.query(params);
        for (Map<String, Object> resultMap : lisList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("results_rpt_date_time", "nullValue");
            resultMap.put("transcriptionist_code", "nullValue");
            resultMap.put("transcriptionist", "nullValue");
            resultMap.put("ordering_provider_code", "nullValue");
            resultMap.put("spcm_sample_date_time", "nullValue");
            resultMap.put("spcm_sample_code", "nullValue");
            resultMap.put("spcm_sample_name", "nullValue");
            resultMap.put("send_time", "nullValue");
            resultMap.put("sender_code", "nullValue");
            resultMap.put("sender_name", "nullValue");
            resultMap.put("spcm_received_date_time", "nullValue");
            resultMap.put("spcm_received_code", "nullValue");
            resultMap.put("spcm_received_name", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getBloodInfo(Map<String, Object> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().forEach(key -> logger.info("mnis getBloodInfo params:" + key + ":" + params.get(key)));
        String bloodProductIdStr = String.valueOf(params.get("bloodProductId"));
        bloodProductIdStr.replace("&lt;", "<");
        List<Map<String, Object>> bloodList = hisBloodDao.query(params);
        for (Map<String, Object> resultMap : bloodList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("age", "nullValue");
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getBloodRecInfo(Map<String, Object> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().forEach(key -> logger.info("mnis getBloodRecInfo params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> bloodList = hisBloodDao.queryRec(String.valueOf(params.get("bloodOutNum")));
        for (Map<String, Object> resultMap : bloodList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 返回检验信息
     * 根据test_no，修改LAB_TEST_MASTER
     *
     * @param params patient_id
     *               series
     *               admission_id
     *               dept_code
     *               ward_code
     *               test_no
     *               record_nurse
     *               start_time
     *               end_time
     *               user_id
     *               update_time
     *               action_type
     * @return 0:no data 1:some exception
     */
    @Override
    public int receiveLisInfo(Map<String, Object> params) {
        String testNo = String.valueOf(params.get("testNo"));
        String endTime = String.valueOf(params.get("endTime"));
        Integer testCount = hisLisDao.queryFromMnis(testNo);
        logger.info("receiveLisInfo find count :" + testCount);
        if (testCount > 0) {
            String endTimeChange = transUtils.U2I(endTime);
            String testNoChange = transUtils.U2I(testNo);
            hisLisDao.modifyFromMnis(endTimeChange, testNoChange);
            turtleLisDao.addMnisLis(params);
            return -1;
        }
        return 0;
    }

    /**
     * 获取检查报告
     *
     * @param params HashMap
     * @return ArrayList
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getExamInfo(Map<String, Object> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().forEach(key -> logger.info("mnis getExamInfo params:" + key + ":" + params.get(key)));
        String patientId = String.valueOf(params.get("patientId"));
        String series = String.valueOf(params.get("series"));
        List<Map<String, Object>> examList = hisExamDao.queryExam(patientId, series);
        for (Map<String, Object> resultMap : examList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 获取检验报告
     *
     * @param params HashMap
     * @return ArrayList
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getLisReportInfoSample(Map<String, Object> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().forEach(key -> logger.info("mnis getLisReportInfoSample params:" + key + ":" + params.get(key)));
        String patientId = String.valueOf(params.get("patientId"));
        String series = String.valueOf(params.get("series"));
        List<Map<String, Object>> labList = hisLisDao.queryLabReportSample(patientId, series);
        for (Map<String, Object> resultMap : labList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 获取检验详细报告
     *
     * @param params HashMap
     * @return ArrayList
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getLisReportInfo(Map<String, Object> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().forEach(key -> logger.info("mnis getLisReportInfo params:" + key + ":" + params.get(key)));
        String testNo = String.valueOf(params.get("testNo"));
        List<Map<String, Object>> labList = hisLisDao.queryLabReport(testNo);
        for (Map<String, Object> resultMap : labList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getBabyInfoIn(Map<String, Object> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().forEach(key -> logger.info("mnis getBabyInfo params:" + key + ":" + params.get(key)));
        String wardCode = String.valueOf(params.get("wardCode"));
        String patientId = String.valueOf(params.get("patientId"));
        String visitId = String.valueOf(params.get("visitId"));
        List<Map<String, Object>> babyList = hisPatientDao.queryBabyInfoIn(patientId, visitId, wardCode);
        for (Map<String, Object> resultMap : babyList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getBabyInfoOut(Map<String, Object> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().forEach(key -> logger.info("mnis getBabyInfo params:" + key + ":" + params.get(key)));
        String patientId = String.valueOf(params.get("patientId"));
        String visitId = String.valueOf(params.get("visitId"));
        List<Map<String, Object>> babyList = hisPatientDao.queryBabyInfoOut(patientId,visitId);
        for (Map<String, Object> resultMap : babyList) {
            resultMap.keySet().forEach(key ->
                    resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultMap.put("actionType", "nullValue");
            resultList.add(resultMap);
        }
        return resultList;
    }

}