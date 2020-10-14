/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.tools.datalayer.models;

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
 * @author Guilherme
 */
public class ValueDescription {
    
    String classType;
    String unit;

    public ValueDescription() {
        
    }

    public ValueDescription(String classType, String unit) {
        this.classType = classType;
        this.unit = unit;
    }
    
    
}
