/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uninova.context.services.infrastructure;

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


import de.atb.context.services.faults.ContextFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.uninova.context.infrastructure.Node;
import pt.uninova.context.infrastructure.Nodes;
import pt.uninova.context.modules.Server;
import pt.uninova.context.services.SWServiceContainer;
import pt.uninova.context.services.config.models.SWService;
import pt.uninova.context.services.infrastructure.datalayer.IServiceRegistryRepositoryService;
import pt.uninova.context.services.manager.ServiceManager;
import pt.uninova.context.services.wrapper.ServiceRegistryRepositoryServiceWrapper;

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
