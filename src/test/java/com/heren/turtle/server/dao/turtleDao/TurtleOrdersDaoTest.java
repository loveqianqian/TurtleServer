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

package com.heren.turtle.server.dao.turtleDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * com.heren.turtle.server.dao.turtleDao
 *
 * @author zhiwei
 * @create 2016-11-29 15:14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml"})
public class TurtleOrdersDaoTest {

    @Autowired
    private TurtleOrdersDao ordersDao;

    @Test
    public void testAdd() {
        Map<String, Object> query = new HashMap<>();
        query.put("orderStatus", "1");
        query.put("orderNo", "1");
        query.put("groupNo", "1");
        query.put("orderCode", "101123");
        query.put("performStatus", "12");
        query.put("lastPerformDateTime", "2016-01-01 11:11:11");
        query.put("updateTime", "2016-01-01 11:11:11");
        query.put("actionType", "add");
        query.put("ordersDetails", "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<log4j>" +
                "<appender name=\"console\" class=\"org.apache.log4j.ConsoleAppender\">" +
                "<param name=\"Target\" value=\"System.out\"/>" +
                "<layout class=\"org.apache.log4j.PatternLayout\">" +
                "<param name=\"ConversionPattern\" value=\"%d{MMM dd yyyy HH:mm:ss,SSS} %-5p %c - %m%n\"/>" +
                "</layout>" +
                "</appender>" +
                "</log4j>");
        query.put("ordersDetailsType", "xml");
        query.put("patientId", "1");
        query.put("series", "1");
        ordersDao.add(query);
    }

    @Test
    public void testModify() {
        Map<String, Object> query = new HashMap<>();
        query.put("orderStatus", "1");
        query.put("orderNo", "1");
        query.put("groupNo", "1");
        query.put("performStatus", "12");
        query.put("lastPerformDateTime", "2016-01-01 11:11:11");
        query.put("updateTime", "2016-01-01 11:11:11");
        query.put("actionType", "add");
        query.put("ordersDetails", "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<log4j>" +
                "<appender name=\"console\" class=\"org.apache.log4j.ConsoleAppender\">" +
                "<param name=\"Target\" value=\"System.out\"/>" +
                "<layout class=\"org.apache.log4j.PatternLayout\">" +
                "<param name=\"ConversionPattern\" value=\"%d{MMM dd yyyy HH:mm:ss,SSS} %-5p %c - %m%n\"/>" +
                "</layout>" +
                "</appender>" +
                "</log4j>");
        query.put("ordersDetailsType", "xml");
        query.put("patientId", "1");
        query.put("series", "1");
        ordersDao.modify(query);
    }


}