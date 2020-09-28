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
 * @version $LastChangedRevision: 241 $
 */
public class AmISensor {
    private String name;
    private String description;
    private String id;
    private AmISensorCharacteristics sensorCharacteristics;
    private AmISensorValue outputValue;

    public AmISensor() {
    }

    public AmISensor(String name, String description, String id, AmISensorCharacteristics sensorCharacteristics, AmISensorValue outputValue) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.sensorCharacteristics = sensorCharacteristics;
        this.outputValue = outputValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
  
    public AmISensorValue getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(AmISensorValue outputValue) {
        this.outputValue = outputValue;
    }

    public AmISensorCharacteristics getSensorCharacteristics() {
        return sensorCharacteristics;
    }

    public void setSensorCharacteristics(AmISensorCharacteristics sensorCharacteristics) {
        this.sensorCharacteristics = sensorCharacteristics;
    }
    
}
