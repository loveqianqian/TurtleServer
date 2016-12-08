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

/**
 * com.heren.turtle.service.service
 *
 * @author zhiwei
 * @create 2016-10-11 15:19.
 */
@javax.jws.WebService
public interface OAService {

    /**
     * 当OA系统有科室变化的时候，主动推送科室信息，根据actionType 来进行增删改的工作
     *
     * @param message
     * @return
     */
    String setDept(String message);

    /**
     * 当OA系统有人员变化的时候，主动推送科室信息，根据actionType 来进行增删改的工作
     *
     * @param message
     * @return
     */
    String setEmp(String message);

}

