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
import com.heren.turtle.server.dao.turtleDao.TurtleDeptDao;
import com.heren.turtle.server.dao.turtleDao.TurtleEmpDao;
import com.heren.turtle.server.service.OAService;
import com.heren.turtle.server.service.Summoner;
import com.heren.turtle.server.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * com.heren.turtle.service.service.impl
 *
 * @author zhiwei
 * @create 2016-10-11 15:17.
 */
@Component("oaWebService")
@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
@javax.jws.WebService(endpointInterface = "com.heren.turtle.server.service.OAService", serviceName = "oaWebService", targetNamespace = "http://service.server.turtle.heren.com/")
public class OAWebService extends Summoner implements OAService {

    @Autowired
    private TurtleEmpDao turtleEmpDao;

    @Autowired
    private TurtleDeptDao turtleDeptDao;

    @Override
    public String setDept(String message) {
        this.logger.info("receive message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = new HashMap<>();
                String summonName = summon(message, params);
                if (spells.add.name().equalsIgnoreCase(summonName)) {
                    turtleDeptDao.add(params);
                } else {
                    if (turtleDeptDao.querySimple(params) == 0) {
                        logger.info("Identification is fault.actionType is " + summonName + " ,but message were not exist");
                        params.put("actionType", "add");
                        turtleDeptDao.add(params);
                    }
                }
                this.logger.info("successful operation");
                return XmlUtils.resultMessage();
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
    public String setEmp(String message) {
        this.logger.info("receive message:\n" + message);
        try {
            if (XmlUtils.isXml(message)) {
                Map<String, Object> params = new HashMap<>();
                String summonName = summon(message, params);
                if (spells.add.name().equalsIgnoreCase(summonName)) {
                    turtleEmpDao.add(params);
                } else {
                    if (turtleEmpDao.querySimple(params) == 0) {
                        logger.info("Identification is fault.actionType is " + summonName + " ,but message were not exist");
                        params.put("actionType", "add");
                        turtleEmpDao.add(params);
                    }
                }
                this.logger.info("successful operation");
                return XmlUtils.resultMessage();
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
