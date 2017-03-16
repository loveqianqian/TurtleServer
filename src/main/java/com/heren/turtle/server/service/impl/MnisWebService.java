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

import com.heren.turtle.server.agent.IMnisAgent;
import com.heren.turtle.server.constant.MessageConstant;
import com.heren.turtle.server.constant.ProtocolType;
import com.heren.turtle.server.exception.LackElementException;
import com.heren.turtle.server.exception.TooMuchElementException;
import com.heren.turtle.server.service.MnisService;
import com.heren.turtle.server.service.Summoner;
import com.heren.turtle.server.utils.BooleanUtils;
import com.heren.turtle.server.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.*;

/**
 * com.heren.turtle.service.service.impl
 *
 * @author zhiwei
 * @create 2016-10-11 15:17.
 */
@Component("mnisWebService")
@WebService(endpointInterface = "com.heren.turtle.server.service.MnisService",
        serviceName = "mnisWebService",
        targetNamespace = "http://service.server.turtle.heren.com/")
public class MnisWebService extends Summoner implements MnisService {

    @Autowired
    private IMnisAgent mnisAgent;

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
                String dept = listCreateXml(resultList, "dept");
                this.logger.info("successful operation");
                this.logger.info("getDept result:" + dept);
                return dept;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getWard(String message) {
        this.logger.info("getWard receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryParams = BooleanUtils.putMapBooleanList(params,
                        "ward_code",
                        "dept_code");
                List<Map<String, Object>> resultList = mnisAgent.getWard(queryParams);
                String result = listCreateXml(resultList, "ward");
                this.logger.info("successful operation");
                this.logger.info("getWard result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getUserInfo(String message) {
        this.logger.info("getUserInfo receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap = BooleanUtils.putMapBooleanList(params,
                        "user_code",
                        "dept_code");
                List<Map<String, Object>> resultList = mnisAgent.getUserInfo(queryMap);
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getUserInfo result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getPatInHos(String message) {
        this.logger.info("getPatInHos receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap = null;
                if (BooleanUtils.putMapBoolean(params, "ward_code")
                        || (BooleanUtils.putMapBoolean(params, "series") && BooleanUtils.putMapBoolean(params, "patient_id"))) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "ward_code",
                            "series",
                            "patient_id");
                } else {
                    if (BooleanUtils.notContainMapBoolean(params, "ward_code")) {
                        throw new LackElementException("can't be without the key of the parameters : ward_code");
                    } else if (BooleanUtils.notContainMapBoolean(params, "series") || BooleanUtils.notContainMapBoolean(params, "patient_id")) {
                        throw new LackElementException("can't be without the key of the parameters : series || patient_id");
                    }
                }
                List<Map<String, Object>> resultList = mnisAgent.getPatInHos(queryMap);
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getPatInHos result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getPatOutHos(String message) {
        this.logger.info("getPatOutHos receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessageContainsMap(message);
                List<Map<String, Object>> items;
                if (BooleanUtils.putMapBoolean(params, "items")) {
                    items = (List<Map<String, Object>>) params.get("items");
                } else {
                    throw new LackElementException("can't be without the key of the parameters : items");
                }
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
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getTurnDeptTurnBed(String message) {
        this.logger.info("getTurnDeptTurnBed receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                boolean isSeriesExist = BooleanUtils.putMapBoolean(params, "series");
                boolean isPatientIdExist = BooleanUtils.putMapBoolean(params, "patient_id");
                boolean isOutEndTimeExist = BooleanUtils.putMapBoolean(params, "out_end_time");
                boolean isOutStartTimeExist = BooleanUtils.putMapBoolean(params, "out_start_time");
                boolean isTurnOutWardCode = BooleanUtils.putMapBoolean(params, "turn_out_ward_code");
                if ((isSeriesExist && isPatientIdExist)
                        || (isOutEndTimeExist && isOutStartTimeExist && isTurnOutWardCode)) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "series",
                            "patient_id",
                            "turn_out_ward_code",
                            "out_start_time",
                            "out_end_time");
                } else {
                    throw new LackElementException("can't be without some key parameters");
                }
                List<Map<String, Object>> resultList = mnisAgent.getTurnDeptTurnBed(queryMap);
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getTurnDeptTurnBed result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getOrder(String message) {
        this.logger.info("getOrder receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap = new HashMap<>();
                List<String> items = null;
                if (BooleanUtils.putMapBoolean(params, "items")) {
                    items = (List<String>) params.get("items");
                }
                if ((BooleanUtils.putMapBoolean(params, "items") && items != null && items.size() > 0 && !items.get(0).equals(""))
                        || (BooleanUtils.putMapBoolean(params, "series") && BooleanUtils.putMapBoolean(params, "patient_id"))) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "patient_id",
                            "series",
                            "start_time",
                            "end_time",
                            "order_status",
                            "long_once_flag",
                            "supply_code",
                            "ward_code",
                            "order_class",
                            "dept_code",
                            "items");
                    if (queryMap.containsKey("items")) {
                        if (items.size() <= 500 && items.size() >= 1 && items.get(0) != null && !items.get(0).equals("")) {
                            queryMap.put("items", queryMap.get("items"));
                        } else if (items.size() > 500) {
                            throw new TooMuchElementException("items order_no", items.size());
                        }
                        if (items.get(0).equals("")) {
                            queryMap.remove("items");
                        }
                    }
                } else {
                    if (BooleanUtils.notContainMapBoolean(params, "items") || (BooleanUtils.putMapBoolean(params, "items") && items.get(0).equals(""))) {
                        throw new LackElementException("can't be without the key of the parameters : some elements");
                    }
                    if (BooleanUtils.notContainMapBoolean(params, "series") || BooleanUtils.notContainMapBoolean(params, "patient_id")) {
                        throw new LackElementException("can't be without the key of the parameters : some elements");
                    }
                }
                List<Map<String, Object>> resultList = mnisAgent.getOrder(queryMap);
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getOrder result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getDrugOrder(String message) {
        this.logger.info("getDrugOrder receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                if (BooleanUtils.putMapBoolean(params, "ward_code")) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "patient_id",
                            "series",
                            "long_once_flag",
                            "ward_code");
                } else {
                    throw new LackElementException("can't be without the key of the parameters : ward_code");
                }
                List<Map<String, Object>> resultList = mnisAgent.getOrderDrug(queryMap);
                String result = listCreateXml(resultList, "item");
                this.logger.info("successful operation");
                this.logger.info("getOrder result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String writeBackOrder(String message) {
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
                        "stop_order_date_time",
                        "action_type");
                queryParams.put("ordersDetails", message);
                queryParams.put("ordersDetailsType", ProtocolType.xml.name());
                if (mnisAgent.writeBackOrder(queryParams)) {
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
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String writeBackPio(String message) {
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
                        "update_time",
                        "action_type");
                queryParams.put("pioDetails", message);
                queryParams.put("pioDetailsType", ProtocolType.xml.name());
                if (mnisAgent.writeBackPio(queryParams)) {
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
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String writeBackSign(String message) {
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
                        "vitalsign_sval1",
                        "unit",
                        "update_time",
                        "remark",
                        "action_type");
                queryParams.put("vitalSignDetails", message);
                queryParams.put("vitalSignDetailsType", ProtocolType.xml.name());
                if (mnisAgent.writeBackSign(queryParams)) {
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
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getLisInfo(String message) {
        this.logger.info("getLisInfo receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                boolean testNoExist = BooleanUtils.putMapBoolean(params, "test_no");
                boolean patientIdExist = BooleanUtils.putMapBoolean(params, "patient_id");
                boolean orderNoExist = BooleanUtils.putMapBoolean(params, "order_no");
                boolean groupNoExist = BooleanUtils.putMapBoolean(params, "group_no");
                boolean seriesExist = BooleanUtils.putMapBoolean(params, "series");
                if (testNoExist || (patientIdExist && orderNoExist && groupNoExist && seriesExist)) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "test_no",
                            "order_no",
                            "group_no",
                            "patient_id",
                            "series");
                } else {
                    throw new LackElementException("can't be without some key parameters");
                }
                List<Map<String, Object>> resultList = mnisAgent.getLisInfo(queryMap);
                String result = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getLisInfo result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getBloodInfo(String message) {
        this.logger.info("getLisInfo receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                boolean bloodOutIdExist = BooleanUtils.putMapBoolean(params, "blood_out_id");
                boolean bloodProductIdExist = BooleanUtils.putMapBoolean(params, "blood_product_id");
                if (bloodProductIdExist && bloodOutIdExist) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "blood_out_id",
                            "blood_product_id");
                } else {
                    throw new LackElementException("can't be without some key parameters");
                }
                List<Map<String, Object>> resultList = mnisAgent.getBloodInfo(queryMap);
                String result = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getBloodInfo result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

    @Override
    public String getBloodRecInfo(String message) {
        this.logger.info("getBloodRecInfo receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                boolean bloodOutIdExist = BooleanUtils.putMapBoolean(params, "blood_out_num");
                if (bloodOutIdExist) {
                    queryMap = BooleanUtils.putMapBooleanList(params, "blood_out_num");
                } else {
                    throw new LackElementException("can't be without some key parameters");
                }
                List<Map<String, Object>> resultList = mnisAgent.getBloodRecInfo(queryMap);
                String result = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getBloodRecInfo result:" + result);
                return result;
            } else {
                this.logger.info(MessageConstant.formatFailed);
                return XmlUtils.errorMessage(MessageConstant.formatFailed);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            this.logger.info("exception:" + errorMsg);
            e.printStackTrace();
            return XmlUtils.errorMessage(errorMsg);
        }
    }

}
