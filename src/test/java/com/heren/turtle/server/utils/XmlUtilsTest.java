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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * com.heren.turtle.server.utils
 *
 * @author zhiwei
 * @create 2017-08-08 17:47.
 * @github {@https://github.com/loveqianqian}
 */
public class XmlUtilsTest {
    @Test
    public void isXml() throws Exception {
        String xml = "<payload><request><test_no>1705127874</test_no><item_no>2</item_no><print_order>1</print_order><report_item_name>HY0011</report_item_name><report_item_code>HY0011</report_item_code><result>&lt;111.3</result><units>unit/s</units><abnormal_indicator>H</abnormal_indicator><instrument_id>Cobas8000</instrument_id><send_time>2017-08-09 15:33:20</send_time><ref_range>&lt;10</ref_range><germ_or_normal>0</germ_or_normal><user_id>0040</user_id></Request></payload>";
        boolean xml1 = XmlUtils.isXml(xml);
        System.out.println(xml1);
    }

    @Test
    public void getDocument() throws Exception {
    }

    @Test
    public void getFileDocument() throws Exception {
    }

    @Test
    public void getRootElement() throws Exception {
    }

    @Test
    public void getFileRootElement() throws Exception {
    }

    @Test
    public void getFileElementList() throws Exception {
    }

    @Test
    public void getEachElement() throws Exception {
    }

    @Test
    public void errorMessage() throws Exception {
    }

    @Test
    public void correctMessage() throws Exception {
    }

    @Test
    public void resultMessage() throws Exception {
    }

    @Test
    public void getMessage() throws Exception {
    }

    @Test
    public void getMessageReceiveSample() throws Exception {
    }

    @Test
    public void getMessageReceiveSampleContainItems() throws Exception {
    }

    @Test
    public void getMessageContainsMap() throws Exception {
    }

    @Test
    public void createResultMessage() throws Exception {
    }

    @Test
    public void createResultMessage1() throws Exception {
    }

    @Test
    public void main() throws Exception {
    }

}