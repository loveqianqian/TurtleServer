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
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by luoxiaoming on 16-2-26.
 */
@Component("hisLisDao")
public interface HisLisDao extends BaseDao {

    List<Map<String, Object>> queryLabTranView(Map<String, Object> params);

    List<Map<String, Object>> queryLabReportSample(@Param(value = "patientId") String patientId,
                                             @Param(value = "visitId") String visitId);

    List<Map<String, Object>> queryLabReport(@Param(value = "testNo") String testNo);

    void modifyLabCharge(Map<String, Object> params);

    void modifyMicrobeCharge(Map<String, Object> params);

    void modifyStatus(Map<String, Object> params);

    void modifyReport(Map<String, Object> params);

    Integer queryFromMnis(@Param(value = "testNo") String testNo);

    void modifyFromMnis(@Param(value = "endTime") String endTime, @Param(value = "testNo") String testNo);

}
