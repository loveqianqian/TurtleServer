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

import com.heren.turtle.server.agent.ILisAgent;
import com.heren.turtle.server.agent.IOAAgent;
import com.heren.turtle.server.constant.MessageConstant;
import com.heren.turtle.server.exception.LackElementException;
import com.heren.turtle.server.service.LisService;
import com.heren.turtle.server.service.OAService;
import com.heren.turtle.server.service.Summoner;
import com.heren.turtle.server.utils.BooleanUtils;
import com.heren.turtle.server.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.service.service.impl
 *
 * @author zhiwei
 * @create 2016-10-11 15:17.
 */
@Component("lisWebService")
@WebService(endpointInterface = "com.heren.turtle.server.service.LisService",
        serviceName = "lisWebService",
        targetNamespace = "http://service.server.turtle.heren.com/")
public class LisWebService extends Summoner implements LisService {

    @Autowired
    private ILisAgent lisAgent;

    @Override
    public String getLisApply(String message) {
        this.logger.info("getLisApply receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                if (BooleanUtils.putMapBoolean(params, "test_no")
                        && BooleanUtils.putMapBoolean(params, "patient_id")
                        && BooleanUtils.putMapBoolean(params, "visit_id")) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "test_no",
                            "patient_id",
                            "visit_id");
                } else {
                    throw new LackElementException("can't be without the key of the parameters");
                }
                List<Map<String, Object>> resultList = lisAgent.getLisApply(queryMap);
                String resultMessage = XmlUtils.createResultMessage(resultList);
                this.logger.info("successful operation");
                this.logger.info("getLisApply result:" + resultMessage);
                return resultMessage;
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
    public String lisCharge(String message) {
        this.logger.info("lisCharge receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                queryMap = BooleanUtils.putMapBooleanList(params,
                        "test_no",
                        "patient_id",
                        "visit_id",
                        "baby_no",
                        "operator_no",
                        "item_flag",
                        "item_no",
                        "item_code");
                Map<String, Object> resultMap = lisAgent.lisCharge(queryMap);
                String resultMessage = XmlUtils.createResultMessage(resultMap);
                this.logger.info("successful operation");
                this.logger.info("lisCharge result:" + resultMessage);
                return resultMessage;
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
    public String microbeCharge(String message) {
        this.logger.info("microbeCharge receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                queryMap = BooleanUtils.putMapBooleanList(params,
                        "test_no",
                        "patient_id",
                        "visit_id",
                        "baby_no",
                        "operator_no",
                        "item_flag",
                        "item_no",
                        "item_code",
                        "charge_code");
                Map<String, Object> resultMap = lisAgent.microbeCharge(queryMap);
                String resultMessage = XmlUtils.createResultMessage(resultMap);
                this.logger.info("successful operation");
                this.logger.info("microbeCharge result:" + resultMessage);
                return resultMessage;
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
    public String lisStatusChange(String message) {
        this.logger.info("lisStatusChange receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                queryMap = BooleanUtils.putMapBooleanList(params,
                        "test_no",
                        "result_status",
                        "reporter",
                        "verified_by");
                Map<String, Object> resultMap = lisAgent.lisStatusChange(queryMap);
                String resultMessage = XmlUtils.createResultMessage(resultMap);
                this.logger.info("successful operation");
                this.logger.info("lisStatusChange result:" + resultMessage);
                return resultMessage;
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
    public String lisReport(String message) {
        this.logger.info("lisReport receive mnis request message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = XmlUtils.getMessage(message);
                Map<String, Object> queryMap;
                if (BooleanUtils.putMapBoolean(params, "germ_or_normal")) {
                    queryMap = BooleanUtils.putMapBooleanList(params,
                            "test_no",
                            "item_no",
                            "print_order",
                            "report_item_name",
                            "report_item_code",
                            "report_item_name2",
                            "report_item_code2",
                            "result",
                            "units",
                            "abnormal_indicator",
                            "instrument_id",
                            "send_time",
                            "verified_time",
                            "result_date_time",
                            "result_doctor",
                            "lower_limit",
                            "upper_limit",
                            "ref_range",
                            "germ_or_normal");
                } else {
                    throw new LackElementException("can't be without the key of the parameters");
                }
                Map<String, Object> resultMap = lisAgent.lisReport(queryMap, message);
                String resultMessage = XmlUtils.createResultMessage(resultMap);
                this.logger.info("successful operation");
                this.logger.info("lisReport result:" + resultMessage);
                return resultMessage;
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
