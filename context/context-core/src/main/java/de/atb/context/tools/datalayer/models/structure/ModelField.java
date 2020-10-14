package de.atb.context.tools.datalayer.models.structure;

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
