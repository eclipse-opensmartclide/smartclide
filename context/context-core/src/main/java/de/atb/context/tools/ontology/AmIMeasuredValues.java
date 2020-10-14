package de.atb.context.tools.ontology;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


/**
 *
 * @author scholze
 * @version $LastChangedRevision: 144 $
 */
public class AmIMeasuredValues {
    private String signalId;
    private String timeStamp;
    private String value;

    public AmIMeasuredValues() {
    }

    public AmIMeasuredValues(String signalId, String timeStamp, String value) {
        this.signalId = signalId;
        this.timeStamp = timeStamp;
        this.value = value;
    }

    public String getSignalId() {
        return signalId;
    }

    public void setSignalId(String signalId) {
        this.signalId = signalId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
