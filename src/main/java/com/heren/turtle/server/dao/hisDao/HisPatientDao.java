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
import java.util.Objects;

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
     * @param series
     * @param patientId
     * @return
     */
    List<Map<String, Object>> queryPatOutHos(@Param(value = "patientId") String patientId, @Param(value = "series") String series);

    /**
     * @param params
     * @return
     */
    List<Map<String, Object>> queryNIPatient(Map<String, Object> params);

    /**
     * @param params
     * @return
     */
    List<Map<String, Object>> queryNIPatientOut(Map<String, Object> params);

    /**
     * @param patientId
     * @param visitId
     */
    List<Map<String, Object>> queryBabyInfoOut(@Param(value = "patientId") String patientId,
                                               @Param(value = "visitId") String visitId);

    /**
     * @param patientId
     * @param visitId
     * @param wardCode
     */
    List<Map<String, Object>> queryBabyInfoIn(@Param(value = "patientId") String patientId,
                                              @Param(value = "visitId") String visitId,
                                              @Param(value = "wardCode") String wardCode);
}
