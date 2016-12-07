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

package com.heren.turtle.server.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * com.heren.turtle.server.utils
 *
 * @author zhiwei
 * @create 2016-11-15 17:21.
 */
public class SampleDateUtils extends DateUtils {

    public static final String SAMPLE_DATE = "yyyy-MM-dd";
    public static final String SAMPLE_TIME = "HH:mm:ss";
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SAMPLE_YEAR = "yyyy";

    public static Date getDate(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date parseDate = null;
        try {
            parseDate = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDate;
    }

    public static boolean isSameDay(String startTime, String endTime) {
        Date startDate = getDate(startTime, SAMPLE_DATE);
        Date endDate = getDate(endTime, SAMPLE_DATE);
        return isSameDay(startDate, endDate);
    }

    public static String addDay(String time, int day) {
        Date date = getDate(time, SAMPLE_DATE);
        Date addDate = addDays(date, day);
        SimpleDateFormat format = new SimpleDateFormat(SAMPLE_DATE);
        return format.format(addDate);
    }

    public static void main(String[] args) {
        Date d1 = new Date();
        Date d2 = new Date();
        System.out.println(d1.after(d2));
        boolean sameDay = DateUtils.isSameDay(d1, d2);
        System.out.println(sameDay);
        System.out.println(addDay("2016-11-16", 3));
    }

}
