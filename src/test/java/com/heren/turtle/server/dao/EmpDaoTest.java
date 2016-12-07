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

package com.heren.turtle.server.dao;

import com.heren.turtle.server.dao.turtleDao.TurtleEmpDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.server.dao
 *
 * @author zhiwei
 * @create 2016-10-12 1:53.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml"})
public class EmpDaoTest {

    @Autowired
    private TurtleEmpDao empDao;

    @Test
    public void testQueryEmp() throws Exception {
        List<Map<String, Object>> maps = empDao.query(new HashMap<>());
        maps.stream().forEach(map -> System.out.println(map.get("permissions")));
    }

    @Test
    public void testAddEmp() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("serialNo", "2");
        paramsMap.put("deptCode", "A003");
        paramsMap.put("userName", "测试人员");
        paramsMap.put("birth", "2016-10-12 11:23:00");
        paramsMap.put("permissions", "{\"lis\":\"123\"}");
        paramsMap.put("actionType", "add");
        empDao.add(paramsMap);
    }

    @Test
    public void testModifyEmp() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("serialNo", "2");
        paramsMap.put("id", "3");
        paramsMap.put("userName", "测试人员2");
        paramsMap.put("actionType", "modify");
        empDao.modify(paramsMap);
    }

}