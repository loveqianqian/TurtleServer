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

package com.heren.turtle.server.dao.hisDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * com.heren.turtle.server.dao.hisDao
 *
 * @author zhiwei
 * @create 2016-11-29 16:01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml"})
public class HisSignDaoTest {

    @Autowired
    private HisSignDao signDao;

    @Test
    public void testAdd() {
        Map<String, Object> query = new HashMap<>();
        query.put("patientId","1234");
        query.put("series","12");
        query.put("recordTime","2016-12-12 12:12:12");
        query.put("planTime","2016-12-12 12:12:12");
        query.put("vitalSignName","15.0");
        query.put("unit","千克");
        query.put("remark","0");
        signDao.add(query);
    }

    @Test
    public void testModify(){
        Map<String,Object> query=new HashMap<>();
        query.put("patientId","1234");
        query.put("series","12");
        query.put("recordTime","2016-12-12 12:12:12");
        query.put("planTime","2016-12-12 12:12:12");
        query.put("vitalSignName","13.0");
        query.put("unit","千克");
        query.put("remark","0");
        signDao.modify(query);
    }

}