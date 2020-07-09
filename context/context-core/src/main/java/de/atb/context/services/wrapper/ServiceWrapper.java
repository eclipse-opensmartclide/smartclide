/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uninova.context.services.wrapper;

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


import pt.uninova.context.infrastructure.ServiceInfo;
import pt.uninova.context.modules.broker.process.services.PESFlowSpecs;
import pt.uninova.context.services.interfaces.IService;
import pt.uninova.context.services.manager.ServiceManager;
import pt.uninova.context.tools.datalayer.models.OutputDataModel;
import pt.uninova.context.tools.ontology.Configuration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Giovanni
 */
public class ServiceWrapper<Service extends IService> {

	protected Service service;

	public ServiceWrapper(final Service service) {
		this.service = service;
		if (!ServiceManager.isPingable(service)) {
			throw new IllegalArgumentException("Service is not pingable!");
		}
	}

	public final void start() {
		service.start();
	}

	public final void stop() {
		service.stop();
	}

	public final void restart() {
		service.restart();
	}

	public final String ping() {
		return service.ping();
	}

	public final <T extends Configuration> boolean configureService(final T Configuration) {
		return service.configureService(Configuration);
	}

        public boolean setNotifierClientAndDataOutputId(String host, int port, String className,
                ArrayList<String> dataOutputIds, String pesId, HashMap<String,PESFlowSpecs> flowSpecs, String serviceId, OutputDataModel outModel){
             return service.setupRuntimeSpecs(host, port, className, dataOutputIds, pesId, flowSpecs, serviceId, outModel);
        };
        
        public boolean setNotifierClient(String host, int port, String className){
             return service.setNotifierClient(host, port, className);
        };
        
        public ServiceInfo getReposInfo(){
             return service.getReposInfo();
        };

	public final boolean runtimeInvoke(String flowId) {
		return service.runtimeInvoke(flowId);
	}
        
}
