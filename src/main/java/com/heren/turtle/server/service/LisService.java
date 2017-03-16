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

package com.heren.turtle.server.service;

import javax.jws.WebService;

/**
 * com.heren.turtle.service.service
 *
 * @author zhiwei
 * @create 2016-10-11 15:19.
 */
@WebService
public interface LisService {

    /**
     * lis获取检验申请单信息
     *
     * @param message
     * @return
     */
    String getLisApply(String message);

    /**
     * 检验计费
     *
     * @param message
     * @return
     */
    String lisCharge(String message);

    /**
     * 微生物计费
     *
     * @param message
     * @return
     */
    String microbeCharge(String message);

    /**
     * 检验状态更新
     *
     * @param message
     * @return
     */
    String lisStatusChange(String message);

    /**
     * 检验报告
     *
     * @param message
     * @return
     */
    String lisReport(String message);


}

