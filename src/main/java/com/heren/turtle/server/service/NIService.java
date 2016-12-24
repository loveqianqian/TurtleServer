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
public interface NIService {

    /**
     * 当需要科室数据的时候调用
     *
     * @param message
     * @return
     */
    String getNIDept(String message);

    /**
     * 当需要人员数据的时候调用
     *
     * @param message
     * @return
     */
    String getNIEmp(String message);

    /**
     * 当需要病人基本信息的时候调用
     *
     * @param message
     * @return
     */
    String getNIPatient(String message);

    /**
     * 当需要医嘱数据的时候调用
     *
     * @param message
     * @return
     */
    String getNIOrders(String message);


    /**
     * 当需要转院转科数据的时候调用
     *
     * @param message
     * @return
     */
    String getNITransfer(String message);

    /**
     * 当需要诊断数据的时候调用
     *
     * @param message
     * @return
     */
    String getNIDiagnosis(String message);


    /**
     * 当需要抗生素的用药信息的时候可以调用
     *
     * @param message
     * @return
     */
    String getNIDrug(String message);

}

