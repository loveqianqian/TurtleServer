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

import com.heren.turtle.server.dao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.server.dao
 *
 * @author zhiwei
 * @create 2016-11-03 11:11.
 */
@Component("hisPatientDao")
public interface HisPatientDao extends BaseDao {

    /**
     * 查询在院病人
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryPatInHos(Map<String, Object> params);

    /**
     * 查询出院病人
     *
     * @param items
     * @return
     */
    List<Map<String, Object>> queryPatOutHos(@Param(value = "items") List<String> items);

}
