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
public class AmISensorValue {
    private String bandwidthValue;
    private AmIMeasuredValues measuredValues;

    public AmISensorValue() {
    }

    public AmISensorValue(String bandwidhtValue, AmIMeasuredValues measuredValues) {
        this.bandwidthValue = bandwidhtValue;
        this.measuredValues = measuredValues;
    }

    public String getBandwidhtValue() {
        return bandwidthValue;
    }

    public void setBandwidhtValue(String bandwidhtValue) {
        this.bandwidthValue = bandwidhtValue;
    }

    public AmIMeasuredValues getMeasuredValues() {
        return measuredValues;
    }

    public void setMeasuredValues(AmIMeasuredValues measuredValues) {
        this.measuredValues = measuredValues;
    }
    
}
