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

package com.heren.turtle.server.beans;

import java.io.Serializable;
import java.util.List;

/**
 * com.heren.turtle.server.beans
 *
 * @author zhiwei
 * @create 2017-01-19 16:17.
 */
public class InputMessage implements Serializable {


    private String userId;

    private List<InnerMessage> items;


    class InnerMessage {
        private String patientId;
        private String series;

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }
    }

}
