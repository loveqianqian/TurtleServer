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

import com.heren.turtle.server.agent.impl.MnisAgent;
import com.heren.turtle.server.constant.MessageConstant;
import com.heren.turtle.server.constant.ProtocolType;
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
@javax.jws.WebService(endpointInterface = "com.heren.turtle.server.service.MnisService", serviceName = "mnisWebService", targetNamespace = "http://service.server.turtle.heren.com/")
public class MnisWebService extends Summoner implements MnisService {

    @Autowired
    private MnisAgent mnisAgent;

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
                List<Map<String, Object>> resultList = mnisAgent.getDept(deptCode);
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
                List<Map<String, Object>> resultList = mnisAgent.getDept(wardCode);
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
                String userId = null;
                Map<String, Object> params = XmlUtils.getMessage(message);
                if (params.containsKey("user_code") && !params.get("user_code").equals("")) {
                    userId = (String) params.get("user_code");
                }
                List<Map<String, Object>> resultList = mnisAgent.getUserInfo(userId, params);
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
                List<Map<String, Object>> resultList = mnisAgent.getPatInHos(params);
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
                List<Map<String, Object>> resultList = mnisAgent.getPatOutHos(items);
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
                List<Map<String, Object>> resultList = mnisAgent.getTurnDeptTurnBed(outStartTime, outEndTime, patientId, visitId);
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
                if (BooleanUtils.putMapBoolean(params, "admission_id")) {
                    queryParams.put("admissionId", params.get("admission_id"));
                }
                if (BooleanUtils.putMapBoolean(params, "order_class")) {
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
                List<Map<String, Object>> resultList = mnisAgent.getOrder(queryParams);
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
                Map<String, Object> queryParams = BooleanUtils.putMapBooleanList(params,
                        "patient_id",
                        "series",
                        "order_no",
                        "group_no",
                        "order_code",
                        "order_status",
                        "ward_code",
                        "dept_code",
                        "perform_status",
                        "perform_result",
                        "variance_cause",
                        "variance_cause_desc",
                        "skin_result",
                        "nurse_in_operate",
                        "last_perform_date_time",
                        "update_by",
                        "update_time",
                        "user_id",
                        "stop_doc_id",
                        "stop_nurse_id",
                        "stop_order_date_time");
                if (mnisAgent.writebackOrder(queryParams)) {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("result", true);
                    resultMap.put("resultText", "操作成功");
                    String resultStr = createXml(resultMap);
                    this.logger.info("successful operation");
                    this.logger.info("writeBackOrder result:" + resultStr);
                    return resultStr;
                } else {
                    this.logger.info(MessageConstant.sql_save_failed);
                    return XmlUtils.errorMessage(MessageConstant.save_failed);
                }
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
                Map<String, Object> queryParams = BooleanUtils.putMapBooleanList(params,
                        "patient_id",
                        "series",
                        "dept_code",
                        "problem_code",
                        "causes_code",
                        "target_code",
                        "measure_code",
                        "update_time");
                queryParams.put("pioDetails", message);
                queryParams.put("pioDetailsType", ProtocolType.xml.name());
                if (mnisAgent.writebackPio(queryParams)) {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("result", true);
                    resultMap.put("resultText", "操作成功");
                    String resultStr = createXml(resultMap);
                    this.logger.info("successful operation");
                    this.logger.info("writeBackPio result:" + resultStr);
                    return resultStr;
                } else {
                    this.logger.info(MessageConstant.sql_save_failed);
                    return XmlUtils.errorMessage(MessageConstant.save_failed);
                }
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
                Map<String, Object> queryParams = BooleanUtils.putMapBooleanList(params,
                        "patient_id",
                        "series",
                        "dept_code",
                        "plan_time",
                        "record_time",
                        "vitalsign_name",
                        "unit",
                        "update_time",
                        "remark");
                queryParams.put("vitalSignDetails", message);
                queryParams.put("vitalSignDetailsType", ProtocolType.xml.name());
                if (mnisAgent.writebackSign(queryParams)) {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("result", true);
                    resultMap.put("resultText", "操作成功");
                    String resultStr = createXml(resultMap);
                    this.logger.info("successful operation");
                    this.logger.info("writeBackSign result:" + resultStr);
                    return resultStr;
                } else {
                    this.logger.info(MessageConstant.sql_save_failed);
                    return XmlUtils.errorMessage(MessageConstant.save_failed);
                }
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
