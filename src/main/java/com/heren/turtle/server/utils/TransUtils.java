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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhiwei on 2016/5/11.
 *
 * @desp: trans_code
 */

@Component
@Scope("prototype")
public class TransUtils {

    @Value("${orgType}")
    private String orgType;

    @Value("${newType}")
    private String newType;


    public List<Map<String, Object>> toTrans(List<Map<String, Object>> params) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (params == null || params.isEmpty() || params.size() == 0) {
            return params;
        } else {
            for (Map<String, Object> param : params) {
                Map<String, Object> newMap = toTrans(param);
                resultList.add(newMap);
            }
            return resultList;
        }
    }

    public Map<String, Object> toTrans(Map<String, Object> params) {
        if (params != null && !params.isEmpty() && params.size() > 0) {
            Map<String, Object> resultMap = new HashMap<>();
            for (String key : params.keySet()) {
                Object value = params.get(key);
                Object newValue = toTrans(value);
                resultMap.put(key, newValue);
            }
            return resultMap;
        } else {
            return params;
        }
    }

    public Object toTrans(Object params) {
        String result = null;
        Object object = null;
        try {
            if (params != null) {
                result = new String(params.toString().getBytes(orgType), newType);
            }
            object = result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return object;
    }

    public Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> resultMap = new HashMap<>();
        if (obj == null) {
            return null;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    resultMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("transBean2Map Error " + e);
        }
        return resultMap;
    }

    public Object toTransObject(Object obj) {
        if (obj == null) return null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (value instanceof String) {
                        Method setter = property.getWriteMethod();
                        Object newValue = toTrans(value);
                        setter.invoke(obj, newValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Map<String, Object> U2I(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        for (String key : params.keySet()) {
            Object value = params.get(key);
            Object newValue = null;
            if (value != null) {
                if (value instanceof String) {
                    newValue = U2I(String.valueOf(value));
                } else {
                    newValue = value;
                }
            }
            resultMap.put(key, newValue);
        }
        return resultMap;
    }

    public Object U2IObject(Object obj) {
        Object newObj = new Object();
        if (obj == null) {
            return null;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (value instanceof String) {
                        Method setter = property.getWriteMethod();
                        Object newValue = U2I((String) value);
                        setter.invoke(obj, newValue);
                    }
                }
            }
            newObj = obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newObj;
    }

    public String U2I(String params) {
        String result = "";
        try {
            result = new String(params.getBytes(newType), orgType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
