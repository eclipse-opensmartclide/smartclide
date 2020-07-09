package pt.uninova.context.tools.ontology;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2016 - 2020 ATB
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
