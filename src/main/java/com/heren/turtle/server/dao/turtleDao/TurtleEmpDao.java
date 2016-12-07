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

import com.heren.turtle.server.dao.BaseDao;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by diaozhiwei on 16-2-26.
 */
@Component("turtleEmpDao")
public interface TurtleEmpDao extends BaseDao {

    Integer querySimple(Map<String, Object> params);
}
