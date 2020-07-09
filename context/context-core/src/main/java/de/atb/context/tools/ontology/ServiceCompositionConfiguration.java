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
