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

package com.heren.turtle.server.service.impl;

import com.heren.turtle.server.utils.XmlUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * com.heren.turtle.server.service.impl
 *
 * @author zhiwei
 * @create 2016-11-15 13:01.
 */
public class MnisWebServiceTest {

    @Test
    public void testGetDept() throws Exception {

    }

    @Test
    public void testGetWard() throws Exception {

    }

    @Test
    public void testGetUserInfo() throws Exception {

    }

    @Test
    public void testGetPatInHos() throws Exception {

    }

    @Test
    public void testGetPatOutHos() throws Exception {
        String xml = "<payload>" +
                "    <request>" +
                "        <items>" +
                "            <admission_id>297659_1</admission_id>" +
                "            <admission_id>297658_1</admission_id>" +
                "        </items>" +
                "        <user_id>0020</user_id>" +
                "    </request>" +
                "</payload>";
        Map<String, Object> message = XmlUtils.getMessage(xml);
//        String items = (String) message.get("items");
//        Document document = DocumentHelper.parseText(items);
//        List elements = document.getRootElement().elements();
//        for (Iterator it = elements.iterator(); it.hasNext(); ) {
//            Element subElement = (Element) it.next();
//            System.out.println(subElement.getName() + ":" + subElement.getText());
//        }
        List<String> items = (List<String>) message.get("items");
        for (String item : items) {
            System.out.println(item);
        }
    }

    @Test
    public void testGetTurnDeptTurnBed() throws Exception {

    }

    @Test
    public void testGetOrder() throws Exception {

    }

    @Test
    public void testWritebackOrder() throws Exception {

    }

    @Test
    public void testWritebackPio() throws Exception {

    }

    @Test
    public void testWritebackSign() throws Exception {

    }
}