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

import com.alibaba.fastjson.JSONObject;
import com.heren.turtle.server.utils.JsonUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.util.*;

/**
 * com.heren.turtle.server.service
 *
 * @author zhiwei
 * @create 2016-10-13 21:23.
 */
public class SummonerTest {

    public enum spells {
        add,
        modify,
        discard
    }

    @Test
    public void testSummon() throws Exception {
        Map<String, Object> params = new HashMap<>();
        JSONObject bodyJson = JsonUtils.getMessage("{\"payload\": {\"request\": {\"actionType\": \"add\"}}}");
        bodyJson.keySet().stream().forEach(key -> params.put(key.trim(), bodyJson.get(key).toString().trim()));
        String actionType = bodyJson.getString("actionType").trim();
        EnumSet<spells> spellsEnumSet = EnumSet.allOf(spells.class);
        System.out.println(spellsEnumSet.stream().filter(spe -> actionType.equals(spe.name())).map(Enum::name).findAny().orElse(""));
    }

    @Test
    public void testXml() throws Exception {
        String xml="<payload><request><serialNo>2</serialNo><permissions><permission><modual>his</modual><flag>1</flag></permission></permissions><actionType>modify</actionType></request></payload>";
        Document document = DocumentHelper.parseText(xml);
        Element rootElement = document.getRootElement();
        Element request = rootElement.element("request");
        List elements = request.elements();
        Map<String, Object> result = new HashMap<>();
        for (Iterator it = elements.iterator(); it.hasNext(); ) {
            Element subElement = (Element) it.next();
            if (subElement.isTextOnly()) {
                result.put(subElement.getName(), subElement.getTextTrim());
            }
        }
        System.out.println("test"+result.get("serialNo"));
    }

    @Test
    public void testListCreateXml(){
        List<Map<String,Object>> result=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "");
        result.add(map);
        System.out.println(listCreateXml(result, "test"));
    }

    private String listCreateXml(List<Map<String, Object>> params, String listElement) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("payload");
        Element response = root.addElement("response");
        for (Map<String, Object> param : params) {
            Element dept = response.addElement("listElement");
            param.keySet().stream().forEach(key -> {
                Element element = dept.addElement(key);
                element.setText(String.valueOf(param.get(key)));
            });
        }
        return document.asXML();
    }
}