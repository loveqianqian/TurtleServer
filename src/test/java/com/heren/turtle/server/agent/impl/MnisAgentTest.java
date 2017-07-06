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

package com.heren.turtle.server.agent.impl;

import com.heren.turtle.server.core.ClientCallBackHandler;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * com.heren.turtle.server.agent.impl
 *
 * @author zhiwei
 * @create 2017-03-16 10:55.
 */
public class MnisAgentTest {
    @Test
    public void getDept() throws Exception {

    }

    @Test
    public void getWard() throws Exception {

    }

    @Test
    public void getUserInfo() throws Exception {

    }

    @Test
    public void getPatInHos() throws Exception {

    }

    @Test
    public void getPatOutHos() throws Exception {

    }

    @Test
    public void getTurnDeptTurnBed() throws Exception {

    }

    @Test
    public void getOrder() throws Exception {

    }

    @Test
    public void getOrderDrug() throws Exception {
        Map<String, Object> outProps = new HashMap<>();
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        outProps.put(WSHandlerConstants.USER, "mnis");
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new ClientCallBackHandler());

        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient("http://localhost:8085/mnisWebService?wsdl");
        List<Interceptor<? extends Message>> outInterceptors = client.getOutInterceptors();
        outInterceptors.add(new SAAJOutInterceptor());
        outInterceptors.add(new WSS4JOutInterceptor(outProps));
        Object[] invoke = client.invoke("getDrugOrder", "test");
        System.out.println(invoke[0]);
    }

    @Test
    public void writeBackOrder() throws Exception {

    }

    @Test
    public void writeBackPio() throws Exception {

    }

    @Test
    public void writeBackSign() throws Exception {

    }

    @Test
    public void getLisInfo() throws Exception {

    }

    @Test
    public void getBloodInfo() throws Exception {

    }

    @Test
    public void getBloodRecInfo() throws Exception {

    }

}