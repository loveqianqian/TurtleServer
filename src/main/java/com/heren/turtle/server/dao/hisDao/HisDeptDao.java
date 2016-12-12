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
 * Created by luoxiaoming on 16-2-26.
 */
@Component("hisDeptDao")
public interface HisDeptDao extends BaseDao {

    /**
     * query the dictionary of department use department code
     *
     * @param deptCode
     * @return
     */
    String queryDeptNameByDeptCode(@Param(value = "deptCode") String deptCode);

    /**
     * query the dictionary of ward use department code
     *
     * @param deptCode
     * @return
     */
    Map<String, Object> queryWardByDeptCode(@Param(value = "deptCode") String deptCode);

    /**
     * query all of the dept
     *
     * @return
     */
    List<Map<String, Object>> queryOnlyDept(@Param(value = "deptCode") String deptCode);


    /**
     * query all of the ward
     *
     * @return
     */
    List<Map<String, Object>> queryOnlyWard(@Param(value = "wardCode") String wardCode);


    /**
     * query the patients who transferred the bed label
     *
     * @param outStartTime
     * @param outEndTime
     * @param patientId
     * @param visitId
     * @return
     */
    List<Map<String, Object>> queryTransBed(@Param(value = "outStartTime") String outStartTime,
                                            @Param(value = "outEndTime") String outEndTime,
                                            @Param(value = "patientId") String patientId,
                                            @Param(value = "visitId") String visitId);


    /**
     * @param outStartTime
     * @param outEndTime
     * @param patientId
     * @param visitId
     * @return
     */
    List<Map<String, Object>> queryTransfer(@Param(value = "outStartTime") String outStartTime,
                                            @Param(value = "outEndTime") String outEndTime,
                                            @Param(value = "patientId") String patientId,
                                            @Param(value = "visitId") String visitId);


    /**
     * @param deptCode
     * @return
     */
    List<Map<String, Object>> queryNIDept(@Param(value = "deptCode") String deptCode);

    /**
     * @param params
     * @return
     */
    List<Map<String, Object>> queryNiTransfer(Map<String, Object> params);

}
