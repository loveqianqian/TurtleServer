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

import com.heren.turtle.server.utils.XmlUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.jdbc.support.nativejdbc.JBossNativeJdbcExtractor;

import javax.smartcardio.TerminalFactory;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.service.service
 *
 * @author zhiwei
 * @create 2016-10-11 15:16.
 */
public abstract class Summoner {

    public enum spells {
        add,
        modify,
        discard,
        invalid
    }

    protected Logger logger=Logger.getLogger(this.getClass());

    /**
     * get the element of actionType
     *
     * @param message
     * @param params
     * @return
     */
    public String summon(String message, final Map<String, Object> params) {
        try {
            params.putAll(XmlUtils.getMessage(message));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        assert params != null;
        String actionType = (String) params.get("actionType");
        this.logger.info("actionType:" + actionType);
        EnumSet<spells> spellsEnumSet = EnumSet.allOf(spells.class);
        return spellsEnumSet.stream().filter(spe -> actionType.equals(spe.name())).map(Enum::name).findAny().orElse("");
    }

    /**
     * create list xml
     *
     * @param params
     * @param listElement
     * @return
     */
    public String listCreateXml(List<Map<String, Object>> params, String listElement) {
        Document document = null;
        try {
            if (params != null && params.size() > 0) {
                document = DocumentHelper.createDocument();
                Element root = document.addElement("payload");
                Element response = root.addElement("response");
                for (Map<String, Object> param : params) {
                    Element dept = response.addElement(listElement);
                    param.keySet().stream().forEach(key -> {
                        Element element = dept.addElement(key);
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
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return document != null ? document.asXML() : null;
    }

    /**
     * create normal xml
     *
     * @param params
     * @return
     */
    public String createXml(Map<String, Object> params) {
        Document document = null;
        try {
            document = DocumentHelper.createDocument();
            Element root = document.addElement("payload");
            Element response = root.addElement("response");
            params.keySet().stream().forEach(key -> {
                Element element = response.addElement(key);
                element.setText(String.valueOf(params.get(key)));
            });
            Element userId = response.addElement("user_id");
            userId.setText("0001");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return document != null ? document.asXML() : null;
    }

}
