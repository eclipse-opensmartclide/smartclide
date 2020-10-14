/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.services.infrastructure.datalayer;

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


import de.atb.context.services.faults.ContextFault;
import de.atb.context.infrastructure.Node;
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.modules.broker.status.ontology.StatusVocabulary;
import de.atb.context.rdf.manager.ServiceRegistryRepository;
import de.atb.context.services.dataLayer.RepositoryService;
import de.atb.context.services.manager.ServiceManager;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Giovanni
 */
public class ServiceRegistryRepositoryService extends RepositoryService implements
        IServiceRegistryRepositoryService {

    private final ServiceRegistryRepository repos = new ServiceRegistryRepository();

    @Override
    public final void start() throws ContextFault {
        logger.info(String.format("Starting %s ...", this.getClass()
                .getSimpleName()));
    }

    @Override
    public final void stop() throws ContextFault {
        logger.info(String.format("Stopping %s ...", this.getClass()
                .getSimpleName()));
    }

    @Override
    public final void restart() throws ContextFault {
        logger.info(String.format("Restarting %s ...", this.getClass()
                .getSimpleName()));
    }

    @Override
    public final String ping() throws ContextFault {
        logger.info(String.format("%s was pinged", this.getClass()
                .getSimpleName()));
        return ServiceManager.PING_RESPONSE;
    }

    private String storeSWServices(final Node node) {
        if (repos.insert(node)) {
            return node.getDeployer().getConfig().getId();
        } else {
            return null;
        }
    }

    private boolean removeSWServices(final Node node) {
        return repos.delete(node);
    }

    private Node getConnectedServicesbyID(final String serviceid) {
        return repos.selectforID(serviceid);
    }

    @Override
    public final List<Node> getConnectedServices() {
        return repos.selectAllConnectedDeployers();
    }

    @Override
    public final String store(final Object element) {
        return this.storeSWServices((Node) element);
    }

    @Override
    public final boolean remove(final Object element) {
        return this.removeSWServices((Node) element);
    }

    @Override
    public final Object get(final String elementId) {
        return this.getConnectedServicesbyID(elementId);
    }

    @Override
    public final boolean updateServicesStatus(final List<String> ids,
            final StatusVocabulary status) {
        return repos.updateStatus(ids, status);
    }

    @Override
    public final List<ServiceInfo> getServicesByType(final String serviceType) {
        return repos.selectforServiceType(serviceType);
    }

    @Override
    public List<ServiceInfo> getFreeServicesByType(String serviceType) {
        return repos.selectForFreeServiceByType(serviceType);
    }

    @Override
    public boolean updateSingleStatusById(String id, StatusVocabulary status) {
        return repos.updateSingleStatusById(id, status);
    }

    @Override
    public boolean setStatusByIds(List<String> listId, StatusVocabulary status) {
        return repos.setStatusByIds(listId, status);
    }

    @Override
    public boolean updateSingleStatusByLocation(String location, StatusVocabulary status) {
        return repos.updateSingleStatusByLocation(location, status);
    }

    @Override
    public Object get(String elementId, Timestamp stamp, String pesId, String serviceId) throws ContextFault {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
