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
public interface MnisService {

    String getDept(String message);

    String getWard(String message);

    String getUserInfo(String message);

    String getPatInHos(String message);

    String getPatOutHos(String message);

    String getTurnDeptTurnBed(String message);

    String getOrder(String message);

    String writebackOrder(String message);

    String writebackPio(String message);

    String writebackSign(String message);

}

