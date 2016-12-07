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

import com.heren.turtle.server.dao.turtleDao.TurtleDeptDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.service.dao
 *
 * @author zhiwei
 * @create 2016-10-11 23:46.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml"})
public class DeptDaoTest {

    @Autowired
    private TurtleDeptDao deptDao;

    @Test
    public void testQueryDept() throws Exception {
        List<Map<String, Object>> maps = deptDao.query(new HashMap<>());
        maps.stream().forEach(map -> System.out.println(map.get("deptName")));
    }

    @Test
    public void testAddDept() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("serialNo", "2");
        paramsMap.put("supDeptCode", "A002");
        paramsMap.put("deptCode", "B002");
        paramsMap.put("deptName", "插入科室");
        paramsMap.put("deptProfile", "crks");
        paramsMap.put("isHomePage", "0");
        paramsMap.put("describe", "插入描述");
        paramsMap.put("domainId", "0");
        paramsMap.put("actionType", "update");
        deptDao.add(paramsMap);
    }

    @Test
    public void testModifyDept() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("deptCode", "B002");
        paramsMap.put("serialNo", "1");
        paramsMap.put("deptName", "插入修改科室");
        deptDao.modify(paramsMap);
    }

}