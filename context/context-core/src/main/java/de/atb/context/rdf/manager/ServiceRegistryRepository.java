/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.rdf.manager;

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


import org.apache.commons.io.IOUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.common.io.FileUtils;
import de.atb.context.infrastructure.ConnectedDeployer;
import de.atb.context.infrastructure.ConnectedServices;
import de.atb.context.infrastructure.Node;
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.modules.broker.status.ontology.StatusVocabulary;
import de.atb.context.rdf.registry.Deployer_OntologyWrapper;
import de.atb.context.rdf.registry.MyFactory;
import de.atb.context.rdf.registry.SW_Service_Configuration_OntologyWrapper;
import de.atb.context.rdf.registry.SW_Service_OntologyWrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Giovanni
 */
public final class ServiceRegistryRepository implements
        IServiceRegistryRepository {

    private static final Logger logger = LoggerFactory
            .getLogger(ServiceRegistryRepository.class);

    private static final String TEMPLATE_ONTOLOGY_PATH = "/resources/rdfs/SoftwareServiceOntology_v0.7.owl";
    private static final String WORKING_ONTOLOGY_PATH = "/resources/rdfs/SoftwareServiceOntology_v0.7_working.owl";
    protected static final String HOME_CONFIG_PATH = System
            .getProperty("user.home")
            + File.separator
            + ".context";
    public static final String CONFIGURATION = "//Configuration";

    private OWLOntologyManager manager;
    private MyFactory factory;
    private boolean initialized = false;

    public ServiceRegistryRepository() {
        this.initializeRepository();
    }

    @Override
    public boolean initializeRepository() {
        try {
            this.manager = OWLManager.createOWLOntologyManager();
            //Create a local working copy of the ontology;
            InputStream streamTemplate = getClass().getResourceAsStream(TEMPLATE_ONTOLOGY_PATH);
            InputStream streamWorking = getClass().getResourceAsStream(WORKING_ONTOLOGY_PATH);
            String templateOntology = "";
            String workingOntology = "";
            if (streamTemplate != null) {
                String templateFileString = IOUtils.toString(streamTemplate);
                if (FileUtils.ensureDirectoryExists(HOME_CONFIG_PATH + TEMPLATE_ONTOLOGY_PATH.replace("/", File.separator))) {
                    FileUtils.writeStringToFile(templateFileString, HOME_CONFIG_PATH + TEMPLATE_ONTOLOGY_PATH.replace("/", File.separator));
                    templateOntology = HOME_CONFIG_PATH + TEMPLATE_ONTOLOGY_PATH.replace("/", File.separator);
                    logger.info("filepath created by Jar internal configuration Template Ontology Registry Filepath: %s", templateOntology);
                    if (streamWorking != null) {
                        String workingFileString = IOUtils.toString(streamWorking);

                        FileUtils.writeStringToFile(workingFileString, HOME_CONFIG_PATH + WORKING_ONTOLOGY_PATH.replace("/", File.separator));
                        workingOntology = HOME_CONFIG_PATH + WORKING_ONTOLOGY_PATH.replace("/", File.separator);
                        logger.info("filepath created by Jar internal configuration Working Ontology Registry Filepath: %s", workingOntology);
                    } else {
                        logger.info("File does not exists: %s", WORKING_ONTOLOGY_PATH);
                        initialized = false;
                        return initialized;
                    }
                }
            } else {
                logger.info("File does not exists: %s", TEMPLATE_ONTOLOGY_PATH);
                initialized = false;
                return initialized;
            }
            if (!templateOntology.equals("") && !workingOntology.equals("")) {
                File templateOntoFile = new File(templateOntology);
                File workingOntoFile = new File(workingOntology);
                manager.saveOntology(manager.loadOntologyFromOntologyDocument(templateOntoFile), IRI.create(workingOntoFile));
                manager = OWLManager.createOWLOntologyManager();
                //Load the working copy
                factory = new MyFactory(manager.loadOntologyFromOntologyDocument(workingOntoFile));
                initialized = true;
                return initialized;
            } else {
                logger.info("Problem in creating the filepaths");
                initialized = false;
                return initialized;
            }

        } catch (OWLOntologyStorageException | OWLOntologyCreationException | IOException ex) {
            logger.error(ex.getMessage());
            initialized = false;
            return initialized;
        }
    }

    @Override
    public boolean insert(final Node node) {
        ConnectedDeployer deployer = node.getDeployer();
        boolean inserted = false;
        if (initialized && (deployer != null && deployer.getConfig() != null
                && deployer.getServices().getConfig() != null
                && !deployer.getServices().getConfig().isEmpty())) {
            try {
                Deployer_OntologyWrapper deployerWrapper = factory
                        .createDeployerOntologyWrapper(deployer
                                .getConfig().getLocation()
                                + "//"
                                + deployer.getConfig().getName());
                SW_Service_Configuration_OntologyWrapper deployerConfig = factory
                        .createSWServiceConfigurationOntologyWrapper(deployer
                                .getConfig().getLocation()
                                + "//"
                                + deployer.getConfig().getName()
                                + CONFIGURATION);
                deployerConfig.addHost(deployer.getConfig().getHost());
                deployerConfig.addIdName(deployer.getConfig().getId());
                deployerConfig.addLocation(deployer.getConfig()
                        .getLocation());
                deployerConfig.addProxy(deployer.getConfig().getProxy());
                deployerConfig.addServer(deployer.getConfig().getServer());
                deployerConfig.addType(deployer.getConfig().getType());
                deployerWrapper.addStatus(deployer.getConfig().getStatus());
                deployerWrapper
                        .addHasSWServiceConfiguration(deployerConfig);
                deployerConfig
                        .addHasConfigurationSWService(deployerWrapper);

                for (ServiceInfo configuration : deployer.getServices()
                        .getConfig()) {
                    SW_Service_OntologyWrapper serviceWrapper = factory
                            .createSWServiceOntologyWrapper(configuration
                                    .getLocation()
                                    + "//"
                                    + configuration.getName());
                    SW_Service_Configuration_OntologyWrapper serviceConfig = factory
                            .createSWServiceConfigurationOntologyWrapper(configuration
                                    .getLocation()
                                    + "//"
                                    + configuration.getName()
                                    + CONFIGURATION);
                    serviceConfig.addHost(configuration.getHost());
                    serviceConfig.addIdName(configuration.getId());
                    serviceConfig.addLocation(configuration.getLocation());
                    serviceConfig.addProxy(configuration.getProxy());
                    serviceConfig.addServer(configuration.getServer());
                    serviceConfig.addType(configuration.getType());
                    serviceWrapper
                            .addHasSWServiceConfiguration(serviceConfig);
                    serviceWrapper
                            .addHasSWServiceDeployer(deployerWrapper);
                    serviceWrapper.addStatus(configuration.getStatus());
                    serviceConfig
                            .addHasConfigurationSWService(serviceWrapper);
                    deployerWrapper
                            .addHasDeployerSWServices(serviceWrapper);
                }
                factory.saveOwlOntology();
                inserted = true;
            } catch (OWLOntologyStorageException ex) {
                logger.error(ex.getMessage());
                return inserted;
            }
        }
        return inserted;
    }

    @Override
    public boolean delete(final Node node) {
        ConnectedDeployer deployer = node.getDeployer();
        boolean removed = false;
        if (initialized && (deployer != null && deployer.getConfig() != null
                && !deployer.getServices().getConfig().isEmpty())) {
            try {
                Deployer_OntologyWrapper deployerWrapper = factory
                        .getDeployerOntologyWrapper(deployer.getConfig()
                                .getLocation()
                                + "//"
                                + deployer.getConfig().getName());
                deployerWrapper.delete();
                SW_Service_Configuration_OntologyWrapper deployerConfig = factory
                        .getSWServiceConfigurationOntologyWrapper(deployer
                                .getConfig().getLocation()
                                + "//"
                                + deployer.getConfig().getName()
                                + CONFIGURATION);
                deployerConfig.delete();

                for (ServiceInfo configuration : deployer.getServices()
                        .getConfig()) {
                    SW_Service_OntologyWrapper serviceWrapper = factory
                            .getSWServiceOntologyWrapper(configuration
                                    .getLocation()
                                    + "//"
                                    + configuration.getName());
                    SW_Service_Configuration_OntologyWrapper serviceConfig = factory
                            .getSWServiceConfigurationOntologyWrapper(configuration
                                    .getLocation()
                                    + "//"
                                    + configuration.getName()
                                    + CONFIGURATION);
                    serviceWrapper.delete();
                    serviceConfig.delete();
                }

                factory.saveOwlOntology();
                removed = true;
            } catch (OWLOntologyStorageException ex) {
                java.util.logging.Logger.getLogger(
                        ServiceRegistryRepository.class.getName()).log(
                        Level.SEVERE, null, ex);
                return removed;
            }
        }
        return removed;
    }

    @Override
    public Node selectforID(final String id) {
        if (initialized) {
            Deployer_OntologyWrapper deployerWrapper = factory
                    .getDeployerOntologyWrapper(id);
            ConnectedDeployer connectedDeployer = new ConnectedDeployer();
            SW_Service_Configuration_OntologyWrapper deployerConfigWrapper = factory
                    .getSWServiceConfigurationOntologyWrapper(id
                            + CONFIGURATION);
            ServiceInfo deployerConfig = new ServiceInfo();
            deployerConfig.setId((new ArrayList<String>(deployerConfigWrapper
                    .getIdName())).get(0));
            deployerConfig.setName((new ArrayList<String>(deployerConfigWrapper
                    .getName())).get(0));
            deployerConfig.setLocation((new ArrayList<String>(
                    deployerConfigWrapper.getLocation())).get(0));
            deployerConfig.setHost((new ArrayList<String>(deployerConfigWrapper
                    .getHost())).get(0));
            deployerConfig.setProxy((new ArrayList<String>(
                    deployerConfigWrapper.getProxy())).get(0));
            deployerConfig.setServer((new ArrayList<String>(
                    deployerConfigWrapper.getServer())).get(0));
            deployerConfig.setStatus((new ArrayList<String>(deployerWrapper
                    .getStatus())).get(0));
            deployerConfig.setType((new ArrayList<Object>(deployerConfigWrapper
                    .getType())).get(0).toString());
            connectedDeployer.setConfig(deployerConfig);
            ConnectedServices connectedServices = new ConnectedServices();
            List<ServiceInfo> servicesConfigurations = new ArrayList<>();
            List<SW_Service_OntologyWrapper> connectedServicesWrapper = new ArrayList<>(
                    deployerWrapper.getHasDeployerSWServices());
            for (SW_Service_OntologyWrapper connectedServiceWrapper : connectedServicesWrapper) {
                SW_Service_Configuration_OntologyWrapper serviceConfigWrapper = factory
                        .getSWServiceConfigurationOntologyWrapper(connectedServiceWrapper
                                .getIdName() + CONFIGURATION);
                ServiceInfo serviceConfig = new ServiceInfo();
                serviceConfig.setId((new ArrayList<String>(serviceConfigWrapper
                        .getIdName())).get(0));
                serviceConfig.setName((new ArrayList<String>(
                        serviceConfigWrapper.getName())).get(0));
                serviceConfig.setLocation((new ArrayList<String>(
                        serviceConfigWrapper.getLocation())).get(0));
                serviceConfig.setHost((new ArrayList<String>(
                        serviceConfigWrapper.getHost())).get(0));
                serviceConfig.setProxy((new ArrayList<String>(
                        serviceConfigWrapper.getProxy())).get(0));
                serviceConfig.setServer((new ArrayList<String>(
                        serviceConfigWrapper.getServer())).get(0));
                serviceConfig
                        .setStatus((new ArrayList<String>(
                                connectedServiceWrapper.getStatus())).get(0));
                serviceConfig.setType((new ArrayList<Object>(
                        serviceConfigWrapper.getType())).get(0).toString());
                servicesConfigurations.add(serviceConfig);
            }
            connectedServices.setConfig(servicesConfigurations);
            connectedServices.setDeployer(deployerConfig);
            connectedDeployer.setServices(connectedServices);
            return new Node(connectedDeployer);
        }
        return null;
    }

    @Override
    public List<ServiceInfo> selectforServiceType(final String typeID) {
        List<ServiceInfo> result = new ArrayList<>();
        if (initialized) {
            List<SW_Service_Configuration_OntologyWrapper> lswServiceOntologywrapper = new ArrayList<>(
                    factory.getAllSWServiceConfigurationOntologyWrapperInstances());
            for (SW_Service_Configuration_OntologyWrapper element : lswServiceOntologywrapper) {
                if (new ArrayList<Object>(element.getType()).get(0).toString()
                        .equals(typeID)) {
                    ServiceInfo info = new ServiceInfo();
                    info.setId(element.getOwlIndividual()
                            .getIRI().toString());
                    info.setHost(new ArrayList<String>(element.getHost())
                            .get(0));
                    info.setLocation(new ArrayList<String>(element
                            .getLocation()).get(0));
                    info.setName(new ArrayList<String>(element.getIdName())
                            .get(0));
                    info.setProxy(new ArrayList<String>(element.getProxy())
                            .get(0));
                    info.setServer(new ArrayList<String>(element.getServer())
                            .get(0));
                    info.setType(new ArrayList<Object>(element.getType())
                            .get(0).toString());
                    SW_Service_OntologyWrapper wrapper = new ArrayList<SW_Service_OntologyWrapper>(
                            element.getHasConfigurationSWService()).get(0);
                    info.setStatus(new ArrayList<String>(wrapper.getStatus())
                            .get(0));
                    result.add(info);
                }
            }
        }
        return result;
    }

    @Override
    public List<Node> selectAllConnectedDeployers() {
        if (initialized) {
            List<Deployer_OntologyWrapper> deployersWrapper = new ArrayList<>(
                    factory.getAllDeployerOntologyWrapperInstances());
            List<Node> nodes = new ArrayList<>();
            for (Deployer_OntologyWrapper deployerWrapper : deployersWrapper) {
                ConnectedDeployer connectedDeployer = new ConnectedDeployer();
                logger.info(deployerWrapper.getOwlIndividual().getIRI()
                        .toString()
                        + CONFIGURATION);
                SW_Service_Configuration_OntologyWrapper deployerConfigWrapper = factory
                        .getSWServiceConfigurationOntologyWrapper(deployerWrapper
                                .getOwlIndividual().getIRI().toString()
                                + CONFIGURATION);
                ServiceInfo deployerConfig = new ServiceInfo();
                deployerConfig.setId(deployerWrapper.getOwlIndividual()
                        .getIRI().toString()
                        + CONFIGURATION);
                deployerConfig.setName((new ArrayList<String>(
                        deployerConfigWrapper.getIdName())).get(0));
                deployerConfig
                        .setLocation((new ArrayList<String>(
                                deployerConfigWrapper.getLocation())).get(0));
                deployerConfig.setHost((new ArrayList<String>(
                        deployerConfigWrapper.getHost())).get(0));
                deployerConfig.setProxy((new ArrayList<String>(
                        deployerConfigWrapper.getProxy())).get(0));
                deployerConfig.setServer((new ArrayList<String>(
                        deployerConfigWrapper.getServer())).get(0));
                deployerConfig.setStatus((new ArrayList<String>(deployerWrapper
                        .getStatus())).get(0));
                deployerConfig.setType((new ArrayList<Object>(
                        deployerConfigWrapper.getType())).get(0).toString());
                connectedDeployer.setConfig(deployerConfig);
                ConnectedServices connectedServices = new ConnectedServices();
                List<ServiceInfo> servicesConfigurations = new ArrayList<>();
                List<SW_Service_OntologyWrapper> connectedServicesWrapper = new ArrayList<>(
                        deployerWrapper.getHasDeployerSWServices());
                for (SW_Service_OntologyWrapper connectedServiceWrapper : connectedServicesWrapper) {
                    SW_Service_Configuration_OntologyWrapper serviceConfigWrapper = factory
                            .getSWServiceConfigurationOntologyWrapper(connectedServiceWrapper
                                    .getOwlIndividual().getIRI().toString()
                                    + CONFIGURATION);
                    ServiceInfo serviceConfig = new ServiceInfo();
                    serviceConfig.setId(connectedServiceWrapper
                            .getOwlIndividual().getIRI().toString()
                            + CONFIGURATION);
                    serviceConfig
                            .setName((new ArrayList<String>(
                                    serviceConfigWrapper.getIdName()).get(0)));
                    serviceConfig.setLocation((new ArrayList<String>(
                            serviceConfigWrapper.getLocation())).get(0));
                    serviceConfig.setHost((new ArrayList<String>(
                            serviceConfigWrapper.getHost())).get(0));
                    serviceConfig
                            .setProxy((new ArrayList<String>(
                                    serviceConfigWrapper.getProxy())).get(0));
                    serviceConfig.setServer((new ArrayList<String>(
                            serviceConfigWrapper.getServer())).get(0));
                    serviceConfig.setStatus((new ArrayList<String>(
                            connectedServiceWrapper.getStatus())).get(0));
                    serviceConfig.setType((new ArrayList<Object>(
                            serviceConfigWrapper.getType())).get(0).toString());
                    servicesConfigurations.add(serviceConfig);
                }
                connectedServices.setConfig(servicesConfigurations);
                connectedServices.setDeployer(deployerConfig);
                connectedDeployer.setServices(connectedServices);
                nodes.add(new Node(connectedDeployer));
            }

            return nodes;
        }
        return new ArrayList<>();
    }

    @Override
    public boolean updateStatus(final List<String> idList, final StatusVocabulary status) {
        boolean updated = false;
        try {
            for (String id : idList) {
                SW_Service_OntologyWrapper service = factory
                        .getSWServiceOntologyWrapper(id);
                if (status.getStatusName().equals(
                        StatusVocabulary.BUSY.getStatusName())) {
                    service.removeStatus(StatusVocabulary.FREE.getStatusName());
                } else {
                    service.removeStatus(StatusVocabulary.BUSY.getStatusName());
                }
                service.addStatus(status.getStatusName());
            }
            factory.saveOwlOntology();
            updated = true;
        } catch (OWLOntologyStorageException ex) {
            logger.error(ex.getMessage());
        }
        return updated;
    }

    @Override
    public List<ServiceInfo> selectForFreeServiceByType(String typeID) {
        List<ServiceInfo> result = new ArrayList<>();
        if (initialized) {
            List<SW_Service_Configuration_OntologyWrapper> lswServiceOntologywrapper = new ArrayList<>(
                    factory.getAllSWServiceConfigurationOntologyWrapperInstances());
            for (SW_Service_Configuration_OntologyWrapper element : lswServiceOntologywrapper) {
                if (new ArrayList<Object>(element.getType()).get(0).toString()
                        .equals(typeID)) {
                    SW_Service_OntologyWrapper wrapper = new ArrayList<SW_Service_OntologyWrapper>(
                            element.getHasConfigurationSWService()).get(0);
                    if (new ArrayList<String>(wrapper.getStatus())
                            .get(0).equals(StatusVocabulary.FREE.getStatusName())) {
                        ServiceInfo info = new ServiceInfo();
                        info.setId(element.getOwlIndividual()
                                .getIRI().toString());
                        info.setHost(new ArrayList<String>(element.getHost())
                                .get(0));
                        info.setLocation(new ArrayList<String>(element
                                .getLocation()).get(0));
                        info.setName(new ArrayList<String>(element.getIdName())
                                .get(0));
                        info.setProxy(new ArrayList<String>(element.getProxy())
                                .get(0));
                        info.setServer(new ArrayList<String>(element.getServer())
                                .get(0));
                        info.setType(new ArrayList<Object>(element.getType())
                                .get(0).toString());
                        info.setStatus(new ArrayList<String>(wrapper.getStatus())
                                .get(0));
                        result.add(info);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean updateSingleStatusById(String id, StatusVocabulary status) {
        boolean updated = false;
        if (initialized) {
            List<SW_Service_Configuration_OntologyWrapper> lswServiceOntologywrapper = new ArrayList<>(
                    factory.getAllSWServiceConfigurationOntologyWrapperInstances());
            for (SW_Service_Configuration_OntologyWrapper element : lswServiceOntologywrapper) {
                if (element.getOwlIndividual().getIRI().toString().equals(id)) {
                    try {
                        SW_Service_OntologyWrapper wrapper = new ArrayList<SW_Service_OntologyWrapper>(
                                element.getHasConfigurationSWService()).get(0);

                        if (status.getStatusName().equals(
                                StatusVocabulary.BUSY.getStatusName())) {
                            wrapper.removeStatus(StatusVocabulary.FREE.getStatusName());
                        } else {
                            wrapper.removeStatus(StatusVocabulary.BUSY.getStatusName());
                        }
                        wrapper.addStatus(status.getStatusName());
                        updated = true;
                        factory.saveOwlOntology();
                        break;
                    } catch (OWLOntologyStorageException ex) {
                        java.util.logging.Logger.getLogger(ServiceRegistryRepository.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return updated;
    }

    @Override
    public boolean setStatusByIds(List<String> listId, StatusVocabulary status) {
        boolean res = true;

        for (String id : listId) {
            try {
                if (!updateSingleStatusById(id, status)) {
                    res = false;
                }
            } catch (Exception e) {
                res = false;
            }
        }
        return res;
    }

    @Override
    public boolean updateSingleStatusByLocation(String location, StatusVocabulary status) {
        boolean updated = false;
        if (initialized) {
            List<SW_Service_Configuration_OntologyWrapper> lswServiceOntologywrapper = new ArrayList<>(
                    factory.getAllSWServiceConfigurationOntologyWrapperInstances());
            for (SW_Service_Configuration_OntologyWrapper element : lswServiceOntologywrapper) {
                if (new ArrayList<Object>(element.getLocation()).get(0).toString()
                        .equals(location)) {
                    try {
                        SW_Service_OntologyWrapper wrapper = new ArrayList<SW_Service_OntologyWrapper>(
                                element.getHasConfigurationSWService()).get(0);

                        if (status.getStatusName().equals(
                                StatusVocabulary.BUSY.getStatusName())) {
                            wrapper.removeStatus(StatusVocabulary.FREE.getStatusName());
                        } else {
                            wrapper.removeStatus(StatusVocabulary.BUSY.getStatusName());
                        }
                        wrapper.addStatus(status.getStatusName());
                        updated = true;
                        factory.saveOwlOntology();
                        break;
                    } catch (OWLOntologyStorageException ex) {
                        java.util.logging.Logger.getLogger(ServiceRegistryRepository.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return updated;
    }


}
