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


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.*;

/**
 * com.myloverqian.util JettyService
 * <p>
 * import dom4j
 * </p>
 *
 * @author zhiwei
 * @create 2016-07-19 15:09.
 */
public class XmlUtils {

    /**
     * is xml
     *
     * @param value
     * @return
     */
    public static boolean isXml(String value) {
        try {
            DocumentHelper.parseText(value);
        } catch (DocumentException e) {
            return false;
        }
        return true;
    }

    /**
     * @param message
     * @return
     * @throws DocumentException
     */
    public static Document getDocument(String message) throws DocumentException {
        return DocumentHelper.parseText(message);
    }

    /**
     * @param fileName
     * @return
     * @throws DocumentException
     */
    public static Document getFileDocument(String fileName) throws DocumentException {
        return new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
    }

    /**
     * @param message
     * @return
     * @throws DocumentException
     */
    public static Element getRootElement(String message) throws DocumentException {
        return getDocument(message).getRootElement();
    }

    /**
     * @param fileName
     * @return
     * @throws DocumentException
     */
    public static Element getFileRootElement(String fileName) throws DocumentException {
        return getFileDocument(fileName).getRootElement();
    }

    /**
     * @param fileName
     * @param qName
     * @return
     * @throws DocumentException
     */
    public static List getFileElementList(String fileName, String qName) throws DocumentException {
        return getFileRootElement(fileName).elements(qName);
    }

    /**
     * @param elements
     * @return
     * @throws DocumentException
     */
    public static List<Map<String, String>> getEachElement(List elements) throws DocumentException {
        List<Map<String, String>> resultList = new ArrayList<>();
        if (elements.size() > 0) {
            for (Iterator it = elements.iterator(); it.hasNext(); ) {
                Map<String, String> resultMap = new HashMap<>();
                Element subElement = (Element) it.next();
                resultMap.put("eleName", subElement.getName());
                List subElements = subElement.elements();
                for (Iterator subIt = subElements.iterator(); subIt.hasNext(); ) {
                    Element son = (Element) subIt.next();
                    String name = son.getName();
                    String text = son.getText();
                    resultMap.put(name, text);
                }
                resultList.add(resultMap);
            }
        } else {
            throw new DocumentException("this element is disappeared");
        }
        return resultList;
    }

    /**
     * back failure information
     *
     * @param message
     * @return
     */
    public static String errorMessage(String message) {
        Document document = DocumentHelper.createDocument();
        Element payload = DocumentHelper.createElement("payload");
        document.setRootElement(payload);
        Element response = payload.addElement("response");
        Element result = response.addElement("result");
        result.setText("true");
        Element resultText = response.addElement("resultText");
        resultText.setText(message);
        Element userId = response.addElement("userId");
        userId.setText("0001");
        return document.asXML();
    }

    /**
     * back successful information
     *
     * @return
     */
    public static String resultMessage() {
        Document document = DocumentHelper.createDocument();
        Element payload = DocumentHelper.createElement("payload");
        document.setRootElement(payload);
        Element response = payload.addElement("response");
        Element result = response.addElement("result");
        result.setText("true");
        Element resultText = response.addElement("resultText");
        resultText.setText("操作成功");
        Element userId = response.addElement("userId");
        userId.setText("0001");
        return document.asXML();
    }

    /**
     * delete the label of xml that is not use
     *
     * @param message
     * @return
     */
    public static Map<String, Object> getMessage(String message) throws DocumentException {
        Document document = DocumentHelper.parseText(message);
        Element rootElement = document.getRootElement();
        Element request = rootElement.element("request");
        List elements = request.elements();
        Map<String, Object> result = new HashMap<>();
        for (Iterator it = elements.iterator(); it.hasNext(); ) {
            Element subElement = (Element) it.next();
            if (subElement.isTextOnly()) {
                result.put(subElement.getName(), subElement.getTextTrim());
            } else {
                List<String> subList = new ArrayList<>();
                List subEle = subElement.elements();
                for (Iterator iterator = subEle.iterator(); iterator.hasNext(); ) {
                    Element itemElements = (Element) iterator.next();
                    subList.add(itemElements.getTextTrim());
                }
                result.put(subElement.getName(), subList);
            }
        }
        return result;
    }

    /**
     * @param params
     * @return
     */
    public static String createResultMessage(List<Map<String, Object>> params) throws Exception {
        Document document;
        if (params != null && params.size() > 0) {
            document = DocumentHelper.createDocument();
            Element root = document.addElement("payload");
            Element response = root.addElement("response");
            Element items = response.addElement("items");
            for (Map<String, Object> param : params) {
                Element item = items.addElement("item");
                param.keySet().stream().forEach(key -> {
                    Element element = item.addElement(key);
                    element.setText(String.valueOf(param.get(key)));
                });
            }
            Element userId = response.addElement("user_id");
            userId.setText("0001");
        } else {
            document = DocumentHelper.createDocument();
            Element root = document.addElement("payload");
            Element response = root.addElement("response");
            Element userId = response.addElement("user_id");
            userId.setText("0001");
        }
        return document.asXML();
    }

}
