package de.atb.context.tools.datalayer.models.structure;

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


import de.atb.context.tools.datalayer.models.MetaData;
import de.atb.context.tools.datalayer.models.ValueDescription;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ModelField {

    String id;
    String name;
    String implementingClass;
    MetaData metadata;
    List<ModelField> hasFields;
    ValueDescription hasValue;

    public ModelField(List<ModelField> field) {
        this.hasFields=new ArrayList<>();
        this.hasFields = field;
    }

    public ModelField() {
        this.hasFields=new ArrayList<>();
    }

    public ModelField(String id) {
        this.hasFields=new ArrayList<>();
        this.id = id;
    }

    public String getImplementingClass() {
        return implementingClass;
    }

    public void setImplementingClass(String implementingClass) {
        this.implementingClass = implementingClass;
    }

    public boolean hasFields() {
        return (this.hasFields != null && !this.hasFields.isEmpty());
    }

    public void addField(ModelField field) {
        this.hasFields.add(field);
    }

    public ModelField getField(String id) {
        for (ModelField field : this.hasFields) {
            if (field.getId().equals(id)) {
                return field;
            }
        }
        return null;
    }

    public boolean addListOfFieldsToField(String id, List<ModelField> list) {
        if(this.id.equals(id)){
            this.hasFields.addAll(list);
            return true;
        }else if(this.hasFields()){
            for(ModelField field:this.hasFields){
                if(field.addListOfFieldsToField(id, list))
                    return true;
            }
        }
        return false;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public List<ModelField> getFields() {
        return hasFields;
    }

    public void setFields(List<ModelField> fields) {
        this.hasFields = fields;
    }

    public ValueDescription getHasValue() {
        return hasValue;
    }

    public void setHasValue(ValueDescription hasValue) {
        this.hasValue = hasValue;
    }

}
