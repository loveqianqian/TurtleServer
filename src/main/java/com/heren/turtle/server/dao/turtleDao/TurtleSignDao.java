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

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.server.dao.turtleDao
 *
 * @author zhiwei
 * @create 2016-11-26 0:09.
 */
@Component
public interface TurtleSignDao {

    List<Map<String, Object>> query(Map<String, Object> params);

    void addCommon(Map<String, Object> params);

    void delete(Map<String, Object> params);

    void modify(Map<String, Object> params);

    void modifyForNull(Map<String, Object> params);

    List<Map<String, Object>> queryDict();

    Integer queryCount(@Param(value = "planTime") String planTime,
                       @Param(value = "patientId") String patientId,
                       @Param(value = "series") String series,
                       @Param(value = "deptCode") String deptCode,
                       @Param(value = "wardCode") String wardCode);

    Map<String, Object> queryReal(@Param(value = "planTime") String planTime,
                                  @Param(value = "patientId") String patientId,
                                  @Param(value = "series") String series,
                                  @Param(value = "deptCode") String deptCode,
                                  @Param(value = "wardCode") String wardCode);
}
