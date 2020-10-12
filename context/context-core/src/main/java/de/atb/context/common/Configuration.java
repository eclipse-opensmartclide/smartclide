package de.atb.context.common;

/*
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

import de.atb.context.common.exceptions.ConfigurationException;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pm2.common.application.SysCaller;
import pm2.common.application.SysCallerImpl;
import de.atb.context.tools.ontology.AmIMonitoringConfiguration;

import java.io.File;

/**
 * Configuration
 *
 * @param <T> T
 * @author scholze
 * @version $LastChangedRevision: 703 $
 */
public abstract class Configuration<T> {
    public final Logger logger = LoggerFactory.getLogger(Configuration.class);
    public final SysCaller sysCaller = new SysCallerImpl(false);

    protected static final String HOME_CONFIG_PATH = System
            .getProperty("user.home")
            + File.separator
            + ".context"
            + File.separator;
    protected Class<? extends T> configurationClass;
    protected String configurationLookupPath;
    protected String configurationFileName;
    protected String configurationName;
    protected T configurationBean;

    protected Configuration(final AmIMonitoringConfiguration config,
                            final Class<? extends T> clazz, final String configurationName) {
        this.configurationClass = clazz;
        this.configurationName = configurationName;

        logger.info("Loading %s...", configurationName);
        final Serializer serializer = new Persister();
        try {
            this.configurationBean = serializer.read(this.configurationClass,
                    config.getServiceConfiguration());
            checkConsistency();
        } catch (final Exception e) {
            logger.debug("Could not serialize the " + configurationName
                    + " file: " + config.getId(), e);
            this.configurationFileName = "monitoring-config.xml";
            logger.info("Trying to load " + configurationFileName + "...");
            readConfigurationFile();
        }
    }

    protected Configuration(final String configFileName,
                            final String configurationLookupPath,
                            final Class<? extends T> clazz, final String configurationName) {
        this.configurationLookupPath = configurationLookupPath;
        this.configurationClass = clazz;
        this.configurationFileName = configFileName;
        this.configurationName = configurationName;
        logger.info("Loading %s...", configurationName);
        readConfigurationFile();

    }

    protected abstract void readConfigurationFile();

    public final void save() { // TODO this should maybe replace by a call to DRM API
        logger.info("Saving configuration...");
        final Serializer serializer = new Persister();

        final File source = new File(Configuration.HOME_CONFIG_PATH
                + this.configurationFileName);
        final File sourceLocation = new File(System.getProperty("user.home")
                + File.separator + ".context");
        if (!sourceLocation.mkdir() && !sourceLocation.exists()) {
            logger.warn("Could not create directory "
                    + sourceLocation.getAbsolutePath()
                    + " for saving configuration file!");
        }
        try {
            if (source.exists() || source.createNewFile()) {
                serializer.write(getConfig(), source);
                logger.info("%s saved!", this.configurationName);
            } else {
                logger.warn("%s could not be saved, because the file %s could not be created!", this.configurationName, source.getAbsolutePath());
            }
        } catch (final Exception e) {
            logger.error("Could not save the " + this.configurationName
                    + " file: " + this.configurationFileName, e);
        }
    }

    public final void refresh() {
        logger.info("Reloading %s...", this.configurationName);
        readConfigurationFile();
    }

    protected abstract void checkConsistency() throws ConfigurationException;

    protected final T getConfig() {
        return this.configurationBean;
    }
}
