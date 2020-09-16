package de.atb.context.services.wrapper;

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


import de.atb.context.infrastructure.Node;
import de.atb.context.infrastructure.Nodes;
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.modules.broker.status.ontology.StatusVocabulary;
import de.atb.context.services.infrastructure.datalayer.IServiceRegistryRepositoryService;

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
