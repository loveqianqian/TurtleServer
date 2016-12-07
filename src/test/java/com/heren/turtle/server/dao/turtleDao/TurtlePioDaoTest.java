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
 * @create 2016-11-29 14:04.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml"})
public class TurtlePioDaoTest {

    @Autowired
    private TurtlePioDao pioDao;

    @Test
    public void testAdd() {
        Map<String, Object> query = new HashMap<>();
        query.put("series", "1");
        query.put("patientId", "1");
        query.put("deptCode", "10010");
        query.put("updateTime", "2016-01-01 11:12:11");
        query.put("actionType", "add");
        query.put("pioDetails", "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<log4j>" +
                "<appender name=\"console\" class=\"org.apache.log4j.ConsoleAppender\">" +
                "<param name=\"Target\" value=\"System.out\"/>" +
                "<layout class=\"org.apache.log4j.PatternLayout\">" +
                "<param name=\"ConversionPattern\" value=\"%d{MMM dd yyyy HH:mm:ss,SSS} %-5p %c - %m%n\"/>" +
                "</layout>" +
                "</appender>" +
                "</log4j>");
        query.put("pioDetailsType", "xml");
        query.put("problemCode", "1000");
        query.put("causesCode", "1001");
        query.put("targetCode", "1002");
        query.put("measureCode", "1003");
        pioDao.add(query);
    }

    @Test
    public void testModify() {
        Map<String, Object> query = new HashMap<>();
        query.put("series", "1");
        query.put("patientId", "1");
        query.put("deptCode", "10010");
        query.put("updateTime", "2016-01-01 11:12:11");
        query.put("actionType", "add");
        query.put("pioDetails", "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<log4j>" +
                "<appender name=\"console\" class=\"org.apache.log4j.ConsoleAppender\">" +
                "<param name=\"Target\" value=\"System.out\"/>" +
                "<layout class=\"org.apache.log4j.PatternLayout\">" +
                "<param name=\"ConversionPattern\" value=\"%d{MMM dd yyyy HH:mm:ss,SSS} %-5p %c - %m%n\"/>" +
                "</layout>" +
                "</appender>" +
                "</log4j>");
        query.put("pioDetailsType", "xml");
        query.put("problemCode", "0006");
        query.put("causesCode", "1001");
        query.put("targetCode", "1002");
        query.put("measureCode", "1003");
        pioDao.modify(query);
    }

}