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

package com.heren.turtle.server.service.impl;

import com.heren.turtle.server.constant.MessageConstant;
import com.heren.turtle.server.dao.hisDao.*;
import com.heren.turtle.server.dao.turtleDao.TurtleOrdersDao;
import com.heren.turtle.server.dao.turtleDao.TurtlePioDao;
import com.heren.turtle.server.dao.turtleDao.TurtleSignDao;
import com.heren.turtle.server.exception.LackElementException;
import com.heren.turtle.server.service.MnisService;
import com.heren.turtle.server.service.Summoner;
import com.heren.turtle.server.utils.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.heren.turtle.server.service.Summoner.spells.*;

/**
 * com.heren.turtle.service.service.impl
 *
 * @author zhiwei
 * @create 2016-10-11 15:17.
 */
@Component("mnisWebService")
@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
@javax.jws.WebService(endpointInterface = "com.heren.turtle.server.service.MnisService", serviceName = "mnisWebService", targetNamespace = "http://service.server.turtle.heren.com/")
public class MnisWebService extends Summoner implements MnisService {

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

    @Override
    public String getDept(String message) {
        this.logger.info("getDept receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                String deptCode = null;
                Map<String, Object> params = XmlUtils.getMessage(message);
                if (BooleanUtils.putMapBoolean(params, "dept_code")) {
                    deptCode = (String) params.get("dept_code");
                }
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
                String dept = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getDept result:" + dept);
                return dept;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
    }

    @Override
    public String getWard(String message) {
        this.logger.info("getWard receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                String wardCode = null;
                Map<String, Object> params = XmlUtils.getMessage(message);
                if (BooleanUtils.putMapBoolean(params, "ward_code")) {
                    wardCode = (String) params.get("ward_code");
                }
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
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getWard result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
    }

    @Override
    public String getUserInfo(String message) {
        this.logger.info("getUserInfo receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                String deptCode;
                String userId = null;
                String wardCode;
                Map<String, Object> params = XmlUtils.getMessage(message);
                if (params.containsKey("user_code") && !params.get("user_code").equals("")) {
                    userId = (String) params.get("user_code");
                }
                List<Map<String, Object>> totalMap = new ArrayList<>();
                List<Map<String, Object>> resultList = new ArrayList<>();
                totalMap.addAll(hisUserDao.queryUserDept(userId));
                totalMap.addAll(hisUserDao.queryUserWard(userId));
                if (totalMap.size() > 0) {
                    for (Map<String, Object> map : totalMap) {
                        Map<String, Object> resultMap = new HashMap<>();
                        if (params.containsKey("ward_code") && !params.get("ward_code").equals("")) {
                            wardCode = (String) params.get("ward_code");
                            if (map.get("userWard") != null && map.get("userWard").equals(wardCode)) {
                                userInfoSample(resultList, map, resultMap);
                            }
                        } else if (BooleanUtils.putMapBoolean(params, "dept_code")) {
                            deptCode = (String) params.get("dept_code");
                            if (map.get("userDept") != null && !map.get("userDept").equals(deptCode)) {
                                userInfoSample(resultList, map, resultMap);
                            }
                        } else {
                            userInfoSample(resultList, map, resultMap);
                        }
                    }
                }
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getUserInfo result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
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

    @Override
    public String getPatInHos(String message) {
        this.logger.info("getPatInHos receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryParams = new HashMap<>();
                if (!params.containsKey("ward_code")) {
                    throw new LackElementException("can't be without the key of the parameters : ward_code");
                }
                String wardCode = (String) params.get("ward_code");
                if (wardCode.equals("")) {
                    throw new LackElementException("can't be without the key of the parameters : ward_code");
                }
                queryParams.put("wardCode", wardCode);
                if (params.containsKey("series") && !params.get("series").equals("")) {
                    queryParams.put("visitId", params.get("series"));
                }
                if (params.containsKey("patient_id") && !params.get("patient_id").equals("")) {
                    queryParams.put("patientId", params.get("patient_id"));
                }
                if (params.containsKey("admission_id") && !params.get("admission_id").equals("")) {
                    String admissionId = (String) params.get("admission_id");
                    queryParams.put("visitId", admissionId.split("_")[1]);
                    queryParams.put("patientId", admissionId.split("_")[0]);
                }
                List<Map<String, Object>> resultList = new ArrayList<>();
                List<Map<String, Object>> queryList = hisPatientDao.queryPatInHos(queryParams);
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
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getPatInHos result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
    }

    @Override
    public String getPatOutHos(String message) {
        this.logger.info("getPatOutHos receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                List<String> items = (List<String>) params.get("items");
                List<Map<String, Object>> resultList = new ArrayList<>();
                List<Map<String, Object>> queryList = hisPatientDao.queryPatOutHos(items);
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
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getPatOutHos result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
    }

    @Override
    public String getTurnDeptTurnBed(String message) {
        this.logger.info("getTurnDeptTurnBed receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                if (!params.containsKey("outStartTime") || params.get("outStartTime").equals("")) {
                    throw new LackElementException("");
                }
                if (!params.containsKey("outEndTime") || params.get("outEndTime").equals("")) {
                    throw new LackElementException("");
                }
                String outStartTime = (String) params.get("outStartTime");
                String outEndTime = (String) params.get("outEndTime");
                String patientId = null;
                String visitId = null;
                if (params.containsKey("admission_id") && !params.get("admission_id").equals("")) {
                    String[] minParams = String.valueOf(params.get("admission_id")).split("_");
                    patientId = minParams[0];
                    visitId = minParams[1];
                }
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
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getTurnDeptTurnBed result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
    }

    @Override
    public String getOrder(String message) {
        this.logger.info("getOrder receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryParams = new HashMap<>();
                String startTime;
                String endTime;
                if (!params.containsKey("ward_code")) {
                    throw new LackElementException("can't be without the key of the parameters : ward_code");
                }
                String wardCode = (String) params.get("ward_code");
                if (wardCode.equals("")) {
                    throw new LackElementException("can't be without the key of the parameters : ward_code");
                }
                queryParams.put("wardCode", wardCode);
                if (BooleanUtils.putMapBoolean(params, "items")) {
                    List<String> items = (List<String>) params.get("items");
                    if (items.size() == 1 && items.get(0).equals("")) {
                        queryParams.put("items", null);
                    } else {
                        queryParams.put("items", params.get("items"));
                    }
                }
                if (params.containsKey("admission_id") && !params.get("admission_id").equals("")) {
                    queryParams.put("admissionId", params.get("admission_id"));
                }
                if (params.containsKey("order_class") && !params.get("order_class").equals("")) {
                    String orderClass = (String) params.get("order_class");
                    String[] ocArray = orderClass.split(",");
                    List<String> ocItems = Arrays.asList(ocArray);
                    queryParams.put("orderClassItems", ocItems);
                }
                if (params.containsKey("supply_code") && !params.get("supply_code").equals("")) {
                    String supplyCode = (String) params.get("supply_code");
                    String[] scArray = supplyCode.split(",");
                    List<String> scItems = Arrays.asList(scArray);
                    queryParams.put("supplyCodeItems", scItems);
                }
                if (params.containsKey("long_once_flag") && !params.get("long_once_flag").equals("")) {
                    queryParams.put("longOnceFlag", params.get("long_once_flag"));
                }
                if (params.containsKey("order_status") && !params.get("order_status").equals("")) {
                    queryParams.put("orderStatus", params.get("order_status"));
                }
                if (params.containsKey("start_time") && !params.get("start_time").equals("")) {
                    startTime = (String) params.get("start_time");
                    if (startTime.contains("_")
                            && startTime.split("_").length == 2) {
                        this.logger.info(MessageConstant.timeFormatException);
                        return XmlUtils.errorMessage(MessageConstant.timeFormatException);
                    }
                    queryParams.put("startTime", startTime);
                }
                if (params.containsKey("end_time") && !params.get("end_time").equals("")) {
                    endTime = (String) params.get("end_time");
                    if (endTime.contains("_")
                            && endTime.split("_").length == 2) {
                        this.logger.info(MessageConstant.timeFormatException);
                        return XmlUtils.errorMessage(MessageConstant.timeFormatException);
                    }
                    queryParams.put("endTime", endTime);
                }
                List<Map<String, Object>> resultList = new ArrayList<>();
                List<Map<String, Object>> queryList = hisOrderDao.queryOrders(queryParams);
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
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getOrder result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
    }

    @Override
    public String writebackOrder(String message) {
        this.logger.info("writeBackOrder receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryParams = new HashMap<>();
                if (params.containsKey("patient_id") && !params.get("patient_id").equals("")) {
                    queryParams.put("patientId", params.get("patient_id"));
                }
                if (params.containsKey("series") && !params.get("series").equals("")) {
                    queryParams.put("series", params.get("series"));
                }
                if (params.containsKey("order_no") && !params.get("order_no").equals("")) {
                    queryParams.put("orderNo", params.get("order_no"));
                }
                if (params.containsKey("group_no") && !params.get("group_no").equals("")) {
                    queryParams.put("groupNo", params.get("group_no"));
                }
                if (params.containsKey("order_code") && !params.get("order_code").equals("")) {
                    queryParams.put("orderCode", params.get("order_code"));
                }
                if (params.containsKey("order_status") && !params.get("order_status").equals("")) {
                    queryParams.put("orderStatus", params.get("order_status"));
                }
                if (params.containsKey("ward_code") && !params.get("ward_code").equals("")) {
                    queryParams.put("wardCode", params.get("ward_code"));
                }
                if (BooleanUtils.putMapBoolean(params, "dept_code")) {
                    queryParams.put("deptCode", params.get("dept_code"));
                }
                if (BooleanUtils.putMapBoolean(params, "perform_status")) {
                    queryParams.put("performStatus", params.get("perform_status"));
                }
                if (BooleanUtils.putMapBoolean(params, "perform_result")) {
                    queryParams.put("performResult", params.get("perform_result"));
                }
                if (BooleanUtils.putMapBoolean(params, "variance_cause")) {
                    queryParams.put("varianceCause", params.get("variance_cause"));
                }
                if (BooleanUtils.putMapBoolean(params, "variance_cause_desc")) {
                    queryParams.put("varianceCauseDesc", params.get("variance_cause_desc"));
                }
                if (BooleanUtils.putMapBoolean(params, "skin_result")) {
                    queryParams.put("skinResult", params.get("skin_result"));
                }
                if (BooleanUtils.putMapBoolean(params, "nurse_in_operate")) {
                    queryParams.put("nurseInOperate", params.get("nurse_in_operate"));
                }
                if (BooleanUtils.putMapBoolean(params, "last_perform_date_time")) {
                    queryParams.put("lastPerformDateTime", params.get("last_perform_date_time"));
                }
                if (BooleanUtils.putMapBoolean(params, "update_by")) {
                    queryParams.put("updateBy", params.get("update_by"));
                }
                if (BooleanUtils.putMapBoolean(params, "update_time")) {
                    queryParams.put("updateTime", params.get("update_time"));
                }
                if (BooleanUtils.putMapBoolean(params, "user_id")) {
                    queryParams.put("userId", params.get("user_id"));
                }
                if (BooleanUtils.putMapBoolean(params, "patient_id")) {
                    queryParams.put("patientId", params.get("patient_id"));
                }
                if (BooleanUtils.putMapBoolean(params, "series")) {
                    queryParams.put("series", params.get("series"));
                }
                if (BooleanUtils.putMapBoolean(params, "stop_doc_id")) {
                    queryParams.put("stopDocId", params.get("stop_doc_id"));
                }
                if (BooleanUtils.putMapBoolean(params, "stop_nurse_id")) {
                    queryParams.put("stopNurseId", params.get("stop_nurse_id"));
                }
                if (BooleanUtils.putMapBoolean(params, "stop_order_date_time")) {
                    queryParams.put("stopOrderDateTime", params.get("stop_order_date_time"));
                }
                if (BooleanUtils.putMapBoolean(params, "action_type")) {
                    String actionType = String.valueOf(params.get("action_type"));
                    queryParams.put("actionType", actionType);
                    if (actionType.equalsIgnoreCase(add.name())) {
                        turtleOrdersDao.add(queryParams);
                    } else {
                        turtleOrdersDao.modify(queryParams);
                    }
                }
                hisOrderDao.modify(queryParams);
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("result", true);
                resultMap.put("resultText", "操作成功");
                String resultStr = createXml(resultMap);
                this.logger.info("successful operation");
                this.logger.info("writeBackOrder result:" + resultStr);
                return resultStr;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
    }

    @Override
    public String writebackPio(String message) {
        this.logger.info("writeBackPio receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryParams = new HashMap<>();
                if (BooleanUtils.putMapBoolean(params, "patient_id")) {
                    queryParams.put("patientId", params.get("patient_id"));
                }
                if (BooleanUtils.putMapBoolean(params, "series")) {
                    queryParams.put("series", params.get("series"));
                }
                if (BooleanUtils.putMapBoolean(params, "dept_code")) {
                    queryParams.put("deptCode", params.get("dept_code"));
                }
                if (BooleanUtils.putMapBoolean(params, "problem_code")) {
                    queryParams.put("problemCode", params.get("problem_code"));
                }
                if (BooleanUtils.putMapBoolean(params, "causes_code")) {
                    queryParams.put("causesCode", params.get("causes_code"));
                }
                if (BooleanUtils.putMapBoolean(params, "target_code")) {
                    queryParams.put("targetCode", params.get("target_code"));
                }
                if (BooleanUtils.putMapBoolean(params, "measure_code")) {
                    queryParams.put("measureCode", params.get("measure_code"));
                }
                if (BooleanUtils.putMapBoolean(params, "update_time")) {
                    queryParams.put("updateTime", params.get("update_time"));
                }
                queryParams.put("pioDetails", message);
                queryParams.put("pioDetailsType", type.xml.name());
                if (BooleanUtils.putMapBoolean(params, "action_type")) {
                    String actionType = String.valueOf(params.get("action_type"));
                    queryParams.put("actionType", actionType);
                    if (actionType.equalsIgnoreCase(add.name())) {
                        turtlePioDao.add(queryParams);
                    } else {
                        turtlePioDao.modify(queryParams);
                    }
                }
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("result", true);
                resultMap.put("resultText", "操作成功");
                String resultStr = createXml(resultMap);
                this.logger.info("successful operation");
                this.logger.info("writeBackPio result:" + resultStr);
                return resultStr;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
    }

    @Override
    public String writebackSign(String message) {
        this.logger.info("writeBackSign receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryParams = new HashMap<>();
                if (BooleanUtils.putMapBoolean(params, "patient_id")) {
                    queryParams.put("patientId", params.get("patient_id"));
                }
                if (BooleanUtils.putMapBoolean(params, "series")) {
                    queryParams.put("series", params.get("series"));
                }
                if (BooleanUtils.putMapBoolean(params, "dept_code")) {
                    queryParams.put("deptCode", params.get("dept_code"));
                }
                if (BooleanUtils.putMapBoolean(params, "plan_time")) {
                    queryParams.put("planTime", params.get("plan_time"));
                }
                if (BooleanUtils.putMapBoolean(params, "record_time")) {
                    queryParams.put("recordTime", params.get("record_time"));
                }
                if (BooleanUtils.putMapBoolean(params, "vitalsign_name")) {
                    queryParams.put("vitalSignName", params.get("vitalsign_name"));
                }
                if (BooleanUtils.putMapBoolean(params, "unit")) {
                    queryParams.put("unit", params.get("unit"));
                }
                if (BooleanUtils.putMapBoolean(params, "update_time")) {
                    queryParams.put("updateTime", params.get("update_time"));
                }
                if (BooleanUtils.putMapBoolean(params, "remark")) {
                    queryParams.put("remark", params.get("remark"));
                }
                queryParams.put("vitalSignDetails", message);
                queryParams.put("vitalSignDetailsType", type.xml.name());
                if (BooleanUtils.putMapBoolean(params, "action_type")) {
                    String actionType = String.valueOf(params.get("action_type"));
                    queryParams.put("actionType", actionType);
                    if (actionType.equalsIgnoreCase(add.name())) {
                        turtleSignDao.add(queryParams);
                        hisSignDao.add(queryParams);
                    } else {
                        turtleSignDao.modify(queryParams);
                        hisSignDao.modify(queryParams);
                    }
                }
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("result", true);
                resultMap.put("resultText", "操作成功");
                String resultStr = createXml(resultMap);
                this.logger.info("successful operation");
                this.logger.info("writeBackSign result:" + resultStr);
                return resultStr;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            this.logger.error("exception:" + e.getMessage());
            return XmlUtils.errorMessage(e.getMessage());
        }
    }

}
