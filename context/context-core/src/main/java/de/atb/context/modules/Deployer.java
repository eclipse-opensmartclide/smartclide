/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uninova.context.modules;

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


import de.atb.context.ui.config.ConfigurationDeployerDialog;
import de.atb.context.ui.modules.DeployPopUp;
import de.atb.context.ui.modules.DeployerFrame;
import de.atb.context.ui.util.interfaces.ICommunicationBetweenUIs;
import org.slf4j.LoggerFactory;
import pt.uninova.context.infrastructure.ConnectedDeployer;
import pt.uninova.context.infrastructure.ConnectedServices;
import pt.uninova.context.infrastructure.Node;
import pt.uninova.context.infrastructure.ServiceInfo;
import pt.uninova.context.modules.broker.status.ontology.StatusVocabulary;
import pt.uninova.context.modules.config.DeployerConfigurationObject;
import pt.uninova.context.services.SWServiceContainer;
import pt.uninova.context.services.infrastructure.IServiceRegistryService;
import pt.uninova.context.services.interfaces.IPrimitiveService;
import pt.uninova.context.services.manager.ServiceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Giovanni
 */
public class Deployer {

    private static final long serialVersionUID = 1L;
    private final org.slf4j.Logger logger = LoggerFactory
            .getLogger(Deployer.class);
    private DeployerConfigurationObject configuration = null;
    private IServiceRegistryService client = null;
    private DeployerFrame frame;
    private boolean connected = false;
    private ConfigurationDeployerDialog cdd = null;

    public Deployer() {
        //
    }

    private ConnectedDeployer getConnectedDeployerAndServices() {
        ServiceInfo deployerConfig = new ServiceInfo();
        ConnectedServices services = new ConnectedServices();
        List<ServiceInfo> configs = new ArrayList<>();
        for (SWServiceContainer container : ServiceManager.getLSWServiceContainer()) {
            if (container.getService().getName().contains("Deployer")) {
                deployerConfig.setId(container.getService().getId());
                deployerConfig.setName(container.getService().getName());
                deployerConfig.setHost(container.getService().getHost());
                deployerConfig.setLocation(container.getService().getLocation()
                        .toString());
                deployerConfig.setProxy(container.getService().getProxyClass()
                        .getName());
                deployerConfig.setServer(container.getService()
                        .getServerClass().getName());
                deployerConfig.setType(container.getService().getServerClass()
                        .getName());
                deployerConfig.setStatus(StatusVocabulary.FREE.getStatusName());
            } else if (!container.getService().getName().contains("Registry")
                    && !container.getService().getName().contains("Broker")) {
                ServiceInfo config = new ServiceInfo();
                config.setId(container.getService().getId());
                config.setName(container.getService().getName());
                config.setHost(container.getService().getHost());
                config.setLocation(container.getService().getLocation()
                        .toString());
                config.setProxy(container.getService().getProxyClass()
                        .getName());
                config.setServer(container.getService().getServerClass()
                        .getName());
                config.setStatus(StatusVocabulary.FREE.getStatusName());
                config.setType(container.getServerClass().getSimpleName());
                configs.add(config);
            }
        }
        services.setConfig(configs);
        services.setDeployer(deployerConfig);
        return new ConnectedDeployer(services, deployerConfig);
    }

    public final boolean registerServices(final String URL, final int port,
                                          final Class<? extends IPrimitiveService> clazz) {
        this.client = (IServiceRegistryService) ServiceManager.getWebservice(
                URL, port, clazz);
        Node node = new Node(this.getConnectedDeployerAndServices());
        if (node.getDeployer().getServices() != null
                && node.getDeployer().getServices().getConfig().size() > 0
                && node.getDeployer().getConfig() != null) {
            client.registerSWServices(node);
            this.setConnected(true);
        } else {
            DeployPopUp popup = new DeployPopUp(frame);
            popup.setVisible(true);
        }

        return this.connected;
    }

    public final boolean unregisterServices() {
        if (client != null && this.isConnected()) {
            client.unregisterSWServices(new Node(this
                    .getConnectedDeployerAndServices()));
            client = null;
            configuration = null;
            this.setConnected(false);
            return !this.connected;
        }
        return false;
    }

    public final void startDeployerFrame(final ICommunicationBetweenUIs CommInterface) {
        if (frame == null) {
            frame = new DeployerFrame(this, CommInterface);
        } else {
            logger.info("Deployer Frame already active");
        }
        frame.setVisible(true);
    }

    public final void startConfigurationDialog(final ICommunicationBetweenUIs CommInterface) {
        if (cdd == null) {
            cdd = new ConfigurationDeployerDialog(this, CommInterface);
        } else {
            logger.info("Deployer Configuration Dialog already active");
        }
        cdd.setVisible(true);
        // cdd.start_auto_reg();
    }

    public final void setDeployerConfigurationObject(
            final DeployerConfigurationObject configuration) {
        this.configuration = configuration;
    }

    public final DeployerConfigurationObject getConfiguration() {
        return configuration;
    }

    public final DeployerFrame getFrame() {
        return frame;
    }

    public final void setFrame(final DeployerFrame frame) {
        this.frame = frame;
    }

    public final boolean isConnected() {
        return connected;
    }

    public final void setConnected(final boolean connected) {
        this.connected = connected;
    }

    public final void unregisterRegistry() {
        this.setConnected(false);
        client = null;
        configuration = null;
        this.setConnected(false);
        this.frame.unregisterRegistry();

    }

    public final boolean isDeployerFrameOn() {
        return this.frame != null;
    }

    public final void disposeDeployerFrames() {
        if (cdd != null) {
            cdd.dispose();
            cdd = null;
        }
        if (frame.DetailsFrameOn()) {
            frame.DetailsFrameDispose();
            frame.setDetailsFrameStatus(null);
        }
        this.frame.dispose();
        this.setFrame(null);
    }
}
