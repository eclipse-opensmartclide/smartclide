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


import pt.uninova.context.infrastructure.Node;
import pt.uninova.context.infrastructure.Nodes;
import pt.uninova.context.infrastructure.ServiceInfo;
import pt.uninova.context.modules.broker.status.ontology.StatusVocabulary;
import pt.uninova.context.services.infrastructure.datalayer.IServiceRegistryRepositoryService;

import java.util.List;

/**
 *
 * @author Giovanni
 */
public class ServiceRegistryRepositoryServiceWrapper extends
		RepositoryServiceWrapper<IServiceRegistryRepositoryService> {

	public ServiceRegistryRepositoryServiceWrapper(
			final IServiceRegistryRepositoryService service) {
		super(service);
	}

	public final String storeServices(final Node node) {
		return service.store(node);
	}

	public final boolean removeServices(final Node node) {
		return service.remove(node);
	}

	public final Nodes getConnectedServices() {
		return new Nodes(service.getConnectedServices());
	}

	public final Node getConnectedServicesbyID(final String serviceid) {
		return (Node) service.get(serviceid);
	}
        
        public final List<ServiceInfo> getConnectedServicesByType(final String type){
            return service.getServicesByType(type);
        }

	public final boolean updateServiceeStatus(final List<String> IDs,
			final StatusVocabulary status) {
		return service.updateServicesStatus(IDs, status);
	}

}
