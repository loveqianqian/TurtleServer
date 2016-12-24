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

import com.heren.turtle.server.agent.INIAgent;
import com.heren.turtle.server.agent.IOAAgent;
import com.heren.turtle.server.constant.MessageConstant;
import com.heren.turtle.server.exception.LackElementException;
import com.heren.turtle.server.service.NIService;
import com.heren.turtle.server.service.OAService;
import com.heren.turtle.server.service.Summoner;
import com.heren.turtle.server.utils.BooleanUtils;
import com.heren.turtle.server.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.service.service.impl
 *
 * @author zhiwei
 * @create 2016-10-11 15:17.
 */
@Component("niWebService")
@javax.jws.WebService(endpointInterface = "com.heren.turtle.server.service.NIService", serviceName = "niWebService", targetNamespace = "http://service.server.turtle.heren.com/")
public class NIWebService extends Summoner implements NIService {

    @Autowired
    private INIAgent niAgent;


    /**
     * 当需要科室数据的时候调用
     *
     * @param message
     * @return
     */
    @Override
    public String getNIDept(String message) {
        this.logger.info("getNIDept receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                String deptCode = null;
                Map<String, Object> params = XmlUtils.getMessage(message);
                if (BooleanUtils.putMapBoolean(params, "dept_code")) {
                    deptCode = (String) params.get("dept_code");
                }
                List<Map<String, Object>> resultList = niAgent.getDept(deptCode);
                String dept = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getNIDept result:" + dept);
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

    /**
     * 当需要人员数据的时候调用
     *
     * @param message
     * @return
     */
    @Override
    public String getNIEmp(String message) {
        this.logger.info("getNIEmp receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> paramsMap = BooleanUtils.putMapBooleanList(params,
                        "emp_no",
                        "dept_code");
                List<Map<String, Object>> resultList = niAgent.getEmp(paramsMap);
                String dept = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getNIEmp result:" + dept);
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

    /**
     * 当需要病人基本信息的时候调用
     *
     * @param message
     * @return
     */
    @Override
    public String getNIPatient(String message) {
        this.logger.info("getNIPatient receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                if (BooleanUtils.putMapBoolean(params, "req_no")
                        || BooleanUtils.putMapBoolean(params, "current_dist")
                        || BooleanUtils.putMapBoolean(params, "current_dept")
                        || (BooleanUtils.putMapBoolean(params, "start_time") && BooleanUtils.putMapBoolean(params, "end_time"))) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "req_no",
                            "current_dist",
                            "current_dept",
                            "start_time",
                            "end_time");
                } else {
                    throw new LackElementException("can't be without the key of the parameters");
                }
                List<Map<String, Object>> resultList = niAgent.getPatient(queryMap);
                String dept = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getNIPatient result:" + dept);
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

    /**
     * 当需要医嘱数据的时候调用
     *
     * @param message
     * @return
     */
    @Override
    public String getNIOrders(String message) {
        this.logger.info("getNIOrders receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                if (BooleanUtils.putMapBoolean(params, "req_no")
                        || (BooleanUtils.putMapBoolean(params, "end_time") && BooleanUtils.putMapBoolean(params, "start_time"))) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "req_no",
                            "start_time",
                            "end_time");
                } else {
                    throw new LackElementException("can't be without the key of the parameters");
                }
                List<Map<String, Object>> resultList = niAgent.getOrders(queryMap);
                String dept = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getNIOrders result:" + dept);
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

    /**
     * 当需要转院转科数据的时候调用
     *
     * @param message
     * @return
     */
    @Override
    public String getNITransfer(String message) {
        this.logger.info("getNITransfer receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                if (BooleanUtils.putMapBoolean(params, "req_no")
                        || (BooleanUtils.putMapBoolean(params, "end_time") && BooleanUtils.putMapBoolean(params, "start_time"))) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "req_no",
                            "start_time",
                            "end_time");
                }else {
                    throw new LackElementException("can't be without the key of the parameters");
                }
                List<Map<String, Object>> resultList = niAgent.getTransfer(queryMap);
                String dept = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getNITransfer result:" + dept);
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

    /**
     * 当需要诊断数据的时候调用
     *
     * @param message
     * @return
     */
    @Override
    public String getNIDiagnosis(String message) {
        this.logger.info("getNIDiagnosis receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                if (BooleanUtils.putMapBoolean(params, "req_no")
                        || (BooleanUtils.putMapBoolean(params, "end_time") && BooleanUtils.putMapBoolean(params, "start_time"))) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "req_no",
                            "start_time",
                            "end_time");
                }else {
                    throw new LackElementException("can't be without the key of the parameters");
                }
                List<Map<String, Object>> resultList = niAgent.getDiagnosis(queryMap);
                String dept = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getNIOrders result:" + dept);
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

    /**
     * 当需要抗生素的用药信息的时候可以调用
     *
     * @param message
     * @return
     */
    @Override
    public String getNIDrug(String message) {
        this.logger.info("getNIDrug receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                if (BooleanUtils.putMapBoolean(params, "req_no")
                        || (BooleanUtils.putMapBoolean(params, "end_time") && BooleanUtils.putMapBoolean(params, "start_time"))) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "req_no",
                            "start_time",
                            "end_time");
                }else {
                    throw new LackElementException("can't be without the key of the parameters");
                }
                List<Map<String, Object>> resultList = niAgent.getDrug(queryMap);
                String dept = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getNIOrders result:" + dept);
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


}
