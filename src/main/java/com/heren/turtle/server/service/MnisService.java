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
public interface MnisService {

    /**
     * 科室信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getDept(String message);

    /**
     * 病区信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getWard(String message);

    /**
     * 用户信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getUserInfo(String message);

    /**
     * 在院病人信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getPatInHos(String message);

    /**
     * 出院病人信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getPatOutHos(String message);

    /**
     * 转科转院
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getTurnDeptTurnBed(String message);

    /**
     * 所有医嘱
     *
     * @param message
     * @return
     */
    String getOrder(String message);

    /**
     * 药品类医嘱 order_class A B
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getDrugOrder(String message);

    /**
     * 回写医嘱信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String writeBackOrder(String message);

    /**
     * 回写pio信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String writeBackPio(String message);

    /**
     * 回写体征信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String writeBackSign(String message);

    /**
     * 获取检验信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getLisInfo(String message);

    /**
     * 获取输血信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getBloodInfo(String message);


    /**
     * 获取输血申请单信息
     *
     * @param message String.valueOf(xml)
     * @return String.valueOf(xml)
     */
    String getBloodRecInfo(String message);
}

