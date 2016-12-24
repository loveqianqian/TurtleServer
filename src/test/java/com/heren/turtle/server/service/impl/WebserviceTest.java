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

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * com.heren.turtle.server.service.impl
 *
 * @author zhiwei
 * @create 2016-12-13 16:07.
 */
public class WebserviceTest {

    @Test
    public void testHttpPostGetMessage() {
        String s = sendPost("http://localhost:8082/niWebService?wsdl",
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.server.turtle.heren.com/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <ser:getNIDrug>" +
                        "         <!--Optional:-->" +
                        "         <arg0><![CDATA[" +
                        "         <payload>" +
                        "    <request>" +
                        "        <req_no>00091953_1</req_no>" +
                        "        <start_time></start_time>" +
                        "        <end_time></end_time>" +
                        "        <user_id>0030</user_id>" +
                        "    </request>" +
                        "</payload>" +
                        "         ]]></arg0>" +
                        "      </ser:getNIDrug>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>");
        System.out.println(s);
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
