/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.services.config;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.common.io.FileUtils;
import de.atb.context.services.config.models.ISWServiceConfiguration;
import de.atb.context.services.config.models.SWService;
import de.atb.context.services.config.models.SWServiceConfig;
import de.atb.context.services.manager.SWService_Configuration_FileMngr;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Giovanni
 */
public final class SWServiceConfiguration extends
        SWService_Configuration_FileMngr<SWServiceConfig> implements
        ISWServiceConfiguration {

    private static final Logger logger = LoggerFactory
            .getLogger(SWServiceConfiguration.class);

    private static volatile Map<String, SWServiceConfiguration> settings = new HashMap<>();
    private static/* final */ String defaultFileName = "resources" + File.separator + "services-config.xml";
    private static String internalJarFileName = "/resources/services-config.xml";

    public static SWServiceConfiguration getInstance() {
        String configFilePath = "";
        try {
            File fileInSystem = new File(HOME_CONFIG_PATH + defaultFileName);
            configFilePath = fileInSystem.getPath();
            logger.info("System Filepath: &s", configFilePath);
            if (!fileInSystem.exists()) {
                // changed because of the jar execution
                InputStream fileInJarUrl = SWServiceConfiguration.class.getResourceAsStream(internalJarFileName);
                if (fileInJarUrl != null) {
                    String fileString = IOUtils.toString(fileInJarUrl);
                    if (FileUtils.ensureDirectoryExists(HOME_CONFIG_PATH + defaultFileName)) {
                        FileUtils.writeStringToFile(fileString, HOME_CONFIG_PATH + defaultFileName);
                        configFilePath = HOME_CONFIG_PATH + defaultFileName;
                        logger.info("filepath created by Jar internal configuration: %s", configFilePath);
                    }
                } else {
                    logger.error("No file found in any location");
                    return null;
                }
            }
            if (settings.get(/* defaultFileName */configFilePath) == null) {
                settings.put(/* defaultFileName */configFilePath,
                        new SWServiceConfiguration(
                                /* defaultFileName */configFilePath));
            }

            return settings.get(/* defaultFileName */configFilePath);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return null;
        }

    }

    public static SWServiceConfiguration getInstance(final String configFileName) {
        if (settings.get(configFileName) == null) {
            settings.put(configFileName, new SWServiceConfiguration(
                    configFileName));
        }
        return settings.get(configFileName);
    }

    public static SWServiceConfiguration getInstance(final String configFileName,
            final String configFilePath) {
        if (settings.get(configFileName) == null) {
            settings.put(configFileName, new SWServiceConfiguration(
                    configFileName, configFilePath));
        }
        return settings.get(configFileName);
    }

    private SWServiceConfiguration(final String givenName) {
        super(givenName, null, SWServiceConfig.class, "Service Configuration");
    }

    private SWServiceConfiguration(final String givenName, final String givenPath) {
        super(givenName, givenPath, SWServiceConfig.class,
                "Service Configuration");
    }

    public void setServices(final Collection<SWService> services) {
        this.configurationBean.setServices(services);
    }

    public static String getDefaultFileName() {
        return defaultFileName;
    }

    public static Map<String, SWServiceConfiguration> getSettings() {
        return settings;
    }

    public static SWServiceConfiguration getSettingByString(final String name) {
        for (Map.Entry<String, SWServiceConfiguration> entry : settings
                .entrySet()) {
            String key = entry.getKey();
            if (key.contains(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public List<SWService> getServices() {
        return this.configurationBean.getServices();
    }

    @Override
    public SWService getService(final String id) {
        return this.configurationBean.getService(id);
    }

    @Override
    protected void checkConsistency() throws ConfigurationException {
        throw new UnsupportedOperationException("Not supported yet."); // To
        // change
        // body
        // of
        // generated
        // methods,
        // choose
        // Tools
        // |
        // Templates.
    }

    public SWService[] getServicesArray() {
        if ((this.configurationBean != null)
                && (this.configurationBean.getServices() != null)) {
            return this.configurationBean.getServices().toArray(
                    new SWService[0]);
        }
        return new SWService[0];
    }

}
