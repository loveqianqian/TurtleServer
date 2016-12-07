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

package com.heren.turtle.server.exception;

/**
 * com.heren.turtle.server.exception
 *
 * @author zhiwei
 * @create 2016-11-29 23:44.
 */

@SuppressWarnings("serial")
public class LackElementException extends RuntimeException {

    public LackElementException() {
    }

    public LackElementException(String message) {
        super(message);
    }

    public LackElementException(String message, Throwable cause) {
        super(message, cause);
    }

    public LackElementException(Throwable cause) {
        super(cause);
    }

    public LackElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
