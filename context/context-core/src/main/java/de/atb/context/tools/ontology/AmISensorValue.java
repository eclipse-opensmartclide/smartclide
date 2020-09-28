package de.atb.context.tools.ontology;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
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
