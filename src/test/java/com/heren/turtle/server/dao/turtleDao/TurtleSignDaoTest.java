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
 * @create 2016-11-29 14:52.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml"})
public class TurtleSignDaoTest {

    @Autowired
    private TurtleSignDao signDao;

    @Test
    public void testAdd() {
        Map<String, Object> query = new HashMap<>();
        query.put("series", "1");
        query.put("patientId", "1");
        query.put("deptCode", "10010");
        query.put("planTime", "2016-01-01 11:11:11");
        query.put("recordTime", "2016-01-02 11:12:13");
        query.put("vitalSignName", "测试");
        query.put("updateTime", "2015-01-02 12:32:11");
        query.put("actionType", "add");
        query.put("vitalSignDetails", "<payload>" +
                "<response>" +
                "<result>true</result>" +
                "<resultText>操作成功</resultText>" +
                "<user_id>0001</user_id>" +
                "</response>" +
                "</payload>");
        query.put("vitalSignDetailsType", "xml");
        signDao.add(query);
    }

    @Test
    public void testModify(){
        Map<String,Object> query=new HashMap<>();
        query.put("series", "1");
        query.put("patientId", "1");
        query.put("deptCode", "10110");
        query.put("planTime", "2016-01-01 11:11:11");
        query.put("recordTime", "2016-01-02 11:12:13");
        query.put("vitalSignName", "测试");
        query.put("updateTime", "2015-01-02 12:32:11");
        query.put("actionType", "add");
        query.put("vitalSignDetails", "<payload>" +
                "<response>" +
                "<result>true</result>" +
                "<resultText>操作成功</resultText>" +
                "<user_id>0001</user_id>" +
                "</response>" +
                "</payload>");
        query.put("vitalSignDetailsType", "xml");
        signDao.modify(query);
    }
}