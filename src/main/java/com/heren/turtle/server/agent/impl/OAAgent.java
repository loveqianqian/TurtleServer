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

import com.heren.turtle.server.agent.IOAAgent;
import com.heren.turtle.server.constant.ActionType;
import com.heren.turtle.server.dao.turtleDao.TurtleDeptDao;
import com.heren.turtle.server.dao.turtleDao.TurtleEmpDao;
import com.heren.turtle.server.service.Summoner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * com.heren.turtle.server.agent.impl
 *
 * @author zhiwei
 * @create 2016-12-08 23:57.
 */
@Component("oaAgent")
@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class OAAgent implements IOAAgent {

    @Autowired
    private TurtleEmpDao turtleEmpDao;

    @Autowired
    private TurtleDeptDao turtleDeptDao;

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 当OA系统用科室变化的时候，会进行增删改工作，成功返回true，失败返回false
     *
     * @param actionName
     * @param params
     * @return
     */
    @Override
    public boolean setDept(String actionName, Map<String, Object> params) {
        boolean result = false;
        try {
            if (ActionType.add.name().equalsIgnoreCase(actionName)) {
                turtleDeptDao.add(params);
            } else {
                if (turtleDeptDao.querySimple(params) == 0) {
                    logger.info("Identification is fault.actionType is " + actionName + " ,but message were not exist");
                    params.put("actionType", "add");
                    turtleDeptDao.add(params);
                } else {
                    turtleDeptDao.modify(params);
                }
            }
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 当OA系统有人员变化的时候，会进行增删改工作，成功返回true，失败返回false
     *
     * @param actionName
     * @param params
     * @return
     */
    @Override
    public boolean setEmp(String actionName, Map<String, Object> params) {
        boolean result = false;
        try {
            if (ActionType.add.name().equalsIgnoreCase(actionName)) {
                turtleEmpDao.add(params);
            } else {
                if (turtleEmpDao.querySimple(params) == 0) {
                    logger.info("Identification is fault.actionType is " + actionName + " ,but message were not exist");
                    params.put("actionType", "add");
                    turtleEmpDao.add(params);
                } else {
                    turtleEmpDao.modify(params);
                }
            }
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
