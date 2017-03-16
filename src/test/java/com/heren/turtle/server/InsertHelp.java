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

package com.heren.turtle.server;

import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Var;
import org.junit.Test;

import java.sql.*;

/**
 * com.heren.turtle.server
 *
 * @author zhiwei
 * @create 2016-12-26 14:56.
 */
public class InsertHelp {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    public void testInsert() throws SQLException {
        String url = "jdbc:oracle:thin:@220.113.132.136:44999/wzxdb";
        String username = "system";
        String password = "manager";
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        for (int i = 112; i < 9998; i++) {
            for (int j = 0; j < 98; j++) {
                statement.addBatch("INSERT INTO orders " +
                        "(PATIENT_ID," +
                        "VISIT_ID," +
                        "ORDER_NO," +
                        "ORDER_SUB_NO," +
                        "REPEAT_INDICATOR," +
                        "ORDER_CLASS," +
                        "ORDER_TEXT," +
                        "ORDER_CODE," +
                        "DOSAGE," +
                        "DOSAGE_UNITS," +
                        "ADMINISTRATION," +
                        "DURATION, DURATION_UNITS, START_DATE_TIME, STOP_DATE_TIME, FREQUENCY," +
                        "FREQ_COUNTER, FREQ_INTERVAL, FREQ_INTERVAL_UNIT, FREQ_DETAIL, PERFORM_SCHEDULE," +
                        "PERFORM_RESULT, ORDERING_DEPT," +
                        "DOCTOR, STOP_DOCTOR, NURSE, STOP_NURSE, ENTER_DATE_TIME," +
                        "STOP_ORDER_DATE_TIME, ORDER_STATUS, DRUG_BILLING_ATTR," +
                        "BILLING_ATTR, LAST_PERFORM_DATE_TIME, LAST_ACCTING_DATE_TIME," +
                        "CURRENT_PRESC_NO, ADR_FLAG, ADR_DATETIME, NWARN, BABY_NO, DISPENSARY_CODE," +
                        "LAST_PERFORM_POINT," +
                        "PERFORMED_BY," +
                        "PZWH, FIRST_ADD_NUM, FIRST_ADD_FLAG," +
                        "DOCTOR_COMMIT_DATE_TIME, DOCTOR_ID, STOP_DOCTOR_ID, NURSE_ID," +
                        "STOP_NURSE_ID" +
                        ")" +
                        "VALUES (" +
                        "'TM091934'," +
                        " 1," +
                        i + "," +
                        j +
                        ",1, " +
                        "'E'," +
                        " '胸腔闭式引流', '120699753', NULL, NULL, NULL, NULL, NULL, to_date('28-01-2010 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), NULL, '1/日', 1, 1, '日', NULL, '9:00', NULL, '30020405', '隋铁泉', '隋铁泉', '邢恩芳', NULL, to_date('28-01-2010 13:08:53', 'dd-mm-yyyy hh24:mi:ss'), NULL, '4', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)");
            }
            statement.executeBatch();
            statement.clearBatch();
        }
    }
}
