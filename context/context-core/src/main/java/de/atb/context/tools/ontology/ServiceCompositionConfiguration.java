/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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


import de.atb.context.modules.broker.process.services.PESRunningService;

import java.util.HashMap;

/**
 *
 * @author Giovanni
 */
public class ServiceCompositionConfiguration extends Configuration {

    HashMap<String, PESRunningService> runningServices;

    public ServiceCompositionConfiguration() {
        super(ServiceCompositionConfiguration.class.getSimpleName());
    }

    public HashMap<String, PESRunningService> getRunningServices() {
        return runningServices;
    }

    public void setRunningServices(HashMap<String, PESRunningService> runningServices) {
        this.runningServices = runningServices;
    }
    
    
}
