/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.services.infrastructure;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.infrastructure.Node;
import de.atb.context.infrastructure.Nodes;
import de.atb.context.modules.Server;
import de.atb.context.services.SWServiceContainer;
import de.atb.context.services.config.models.SWService;
import de.atb.context.services.infrastructure.datalayer.IServiceRegistryRepositoryService;
import de.atb.context.services.manager.ServiceManager;
import de.atb.context.services.wrapper.ServiceRegistryRepositoryServiceWrapper;

/**
 *
 * @author Giovanni
 */
public final class ServiceRegistryService implements IServiceRegistryService {

    private static final Logger logger = LoggerFactory
            .getLogger(ServiceRegistryService.class.getName());
    protected IServiceRegistryRepositoryService service;
    protected ServiceRegistryRepositoryServiceWrapper repos;
    protected SWService repositoryConfigData = new SWService();
    private final Server server = new Server();

    public ServiceRegistryService() {
        initializeServices();
    }

    @Override
    public void start() throws ContextFault {
        logger.info(String.format("Starting %s ...", this.getClass()
                .getSimpleName()));
    }

    @Override
    public void stop() throws ContextFault {
        logger.info(String.format("Stopping %s ...", this.getClass()
                .getSimpleName()));
    }

    @Override
    public void restart() throws ContextFault {
        logger.info(String.format("Restarting %s ...", this.getClass()
                .getSimpleName()));
    }

    @Override
    public String ping() throws ContextFault {
        logger.info(String.format("%s was pinged", this.getClass()
                .getSimpleName()));
        return ServiceManager.PING_RESPONSE;
    }

    @Override
    public boolean registerSWServices(final Node node) {
        return repos.storeServices(node) != null;
    }

    @Override
    public boolean unregisterSWServices(final Node node) {
        return repos.removeServices(node);
    }

    @Override
    public Nodes getConnectedServices() {
        return this.repos.getConnectedServices();
    }

    public Server getServerInstance() {
        return this.server;
    }

    protected synchronized void initializeServices() {
        try {
            SWServiceContainer serviceContainer = null;
            if ((service == null) || !ServiceManager.isPingable(service)) {
                for (SWServiceContainer container : ServiceManager.getLSWServiceContainer()) {
                    if (container.getServerClass().toString()
                            .contains("ServiceRegistryRepository")) {
                        service = ServiceManager.getWebservice(container);
                        serviceContainer = container;
                    }
                }
            }
            if ((service != null) && ServiceManager.isPingable(service)
                    && serviceContainer != null) {
                repos = new ServiceRegistryRepositoryServiceWrapper(service);
                this.server.setRepos(repos);
                this.repositoryConfigData.setName(serviceContainer.getName());
                this.repositoryConfigData.setHost(serviceContainer.getHost());
                this.repositoryConfigData.setLocation(serviceContainer
                        .getLocation());
                this.repositoryConfigData.setProxyClass(serviceContainer
                        .getProxyClass());
                this.repositoryConfigData.setServerClass(serviceContainer
                        .getServerClass());
                this.repositoryConfigData.setId(serviceContainer.getId());

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public SWService getReposData() {
        return repositoryConfigData;
    }

}
