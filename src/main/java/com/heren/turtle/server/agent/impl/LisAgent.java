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

import com.heren.turtle.server.agent.ILisAgent;
import com.heren.turtle.server.agent.IOAAgent;
import com.heren.turtle.server.constant.ActionType;
import com.heren.turtle.server.dao.hisDao.HisLisDao;
import com.heren.turtle.server.dao.turtleDao.TurtleDeptDao;
import com.heren.turtle.server.dao.turtleDao.TurtleEmpDao;
import com.heren.turtle.server.dao.turtleDao.TurtleLisDao;
import com.heren.turtle.server.utils.ConversionUtils;
import com.heren.turtle.server.utils.TransUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.server.agent.impl
 *
 * @author zhiwei
 * @create 2016-12-08 23:57.
 */
@Component("lisAgent")
@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class LisAgent implements ILisAgent {

    @Autowired
    private HisLisDao hisLisDao;

    @Autowired
    private TurtleLisDao turtleLisDao;

    @Autowired
    private TransUtils transUtils;

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getLisApply(Map<String, Object> params) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<>();
        params.keySet().forEach(key -> logger.info("lis getLisApply params:" + key + ":" + params.get(key)));
        List<Map<String, Object>> labTransList = hisLisDao.queryLabTranView(params);
        for (Map<String, Object> resultMap : labTransList) {
            resultMap.keySet().forEach(key -> resultMap.put(key, ConversionUtils.isNullValue(resultMap.get(key), transUtils)));
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public Map<String, Object> lisCharge(Map<String, Object> params) throws Exception {
        params.keySet().forEach(key -> logger.info("lis lisCharge params:" + key + ":" + params.get(key)));
        Map<String, Object> changeMap = transUtils.U2I(params);
        hisLisDao.modifyLabCharge(changeMap);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("price", ConversionUtils.isNullValue(changeMap.get("price"), transUtils));
        resultMap.put("charges", ConversionUtils.isNullValue(changeMap.get("charges"), transUtils));
        resultMap.put("execStat", ConversionUtils.isNullValue(changeMap.get("execStat"), transUtils));
        return resultMap;
    }

    @Override
    public Map<String, Object> microbeCharge(Map<String, Object> params) throws Exception {
        params.keySet().forEach(key -> logger.info("lis microbeCharge params:" + key + ":" + params.get(key)));
        Map<String, Object> changeMap = transUtils.U2I(params);
        hisLisDao.modifyMicrobeCharge(changeMap);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("charges", ConversionUtils.isNullValue(changeMap.get("charges"), transUtils));
        resultMap.put("execStat", ConversionUtils.isNullValue(changeMap.get("execStat"), transUtils));
        return resultMap;
    }

    @Override
    public Map<String, Object> lisStatusChange(Map<String, Object> params) throws Exception {
        params.keySet().forEach(key -> logger.info("lis lisStatusChange params:" + key + ":" + params.get(key)));
        Map<String, Object> changeMap = transUtils.U2I(params);
        hisLisDao.modifyStatus(changeMap);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("ret", ConversionUtils.isNullValue(changeMap.get("ret"), transUtils));
        return resultMap;
    }

    @Override
    public Map<String, Object> lisReport(Map<String, Object> params, String message) throws Exception {
        params.keySet().forEach(key -> logger.info("lis lisReport params:" + key + ":" + params.get(key)));
        Map<String, Object> changeMap = transUtils.U2I(params);
        hisLisDao.modifyReport(changeMap);
        Map<String, Object> resultMap = new HashMap<>();
        String ret = String.valueOf(ConversionUtils.isNullValue(changeMap.get("ret"), transUtils));
        resultMap.put("ret", ret);
        if (ret.equals("0")) {
            Integer queryInt = turtleLisDao.queryInt(params);
            if (queryInt > 0) {
                turtleLisDao.modify(params);
            }
            params.put("actionDetails", message.trim());
            params.put("actionDetailsType", "xml");
            turtleLisDao.add(params);
        }
        return resultMap;
    }
}
