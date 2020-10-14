package de.atb.context.monitoring.config;

/*
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atb.context.monitoring.config.models.IMonitoringConfiguration;
import de.atb.context.monitoring.config.models.Index;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import de.atb.context.tools.ontology.AmIMonitoringConfiguration;
import de.atb.context.common.Configuration;
import de.atb.context.common.exceptions.ConfigurationException;
import de.atb.context.monitoring.config.models.Config;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.Interpreter;
import de.atb.context.monitoring.config.models.Monitor;

/**
 * Settings
 *
 * @author scholze
 * @version $LastChangedRevision: 250 $
 */
public final class MonitoringConfiguration extends Configuration<Config> implements IMonitoringConfiguration {

    private static volatile Map<String, MonitoringConfiguration> settings = new HashMap<>();
    private static final String DefaultFileName = "monitoring-config.xml";

    public static MonitoringConfiguration getInstance() {
        if (settings.get(DefaultFileName) == null) {
            settings.put(DefaultFileName, new MonitoringConfiguration(DefaultFileName));
        }
        return settings.get(DefaultFileName);
    }

    public static MonitoringConfiguration getInstance(final AmIMonitoringConfiguration config) {
        if (settings.get(config) == null) {
            settings.put(config.getId(), new MonitoringConfiguration(config));
        }
        return settings.get(config.getId());
    }

    public static MonitoringConfiguration getInstance(final String configFileName) {
        if (settings.get(configFileName) == null) {
            settings.put(configFileName, new MonitoringConfiguration(configFileName));
        }
        return settings.get(configFileName);
    }

    public static MonitoringConfiguration getInstance(final String configFileName, final String configFilePath) {
        if (settings.get(configFileName) == null) {
            settings.put(configFileName, new MonitoringConfiguration(configFileName, configFilePath));
        }
        return settings.get(configFileName);
    }

    private MonitoringConfiguration(final String givenName, final String givenPath) {
        super(givenName, givenPath, Config.class, "Monitoring Configuration");
    }

    private MonitoringConfiguration(final String givenName) {
        super(givenName, null, Config.class, "Monitoring Configuration");
    }

    private MonitoringConfiguration(final AmIMonitoringConfiguration config) {
        super(config, Config.class, "Monitoring Configuration");
    }

    protected void readConfigurationFile() {
        InputStream is = null;
        try {
            final Serializer serializer = new Persister();

            String drmHandle = sysCaller.openDRMobject("monitoring-config.xml", "read");
            if (drmHandle != null) {
                byte[] readConfig = sysCaller.getDRMobject("monitoring-config.xml");
                if (readConfig != null) {
                    is = new ByteArrayInputStream(readConfig);
                    this.configurationBean = serializer.read(
                        this.configurationClass, is);
                    is.close();
                    logger.info("" + this.configurationFileName + " loaded!");
                }
                sysCaller.closeDRMobject(drmHandle);
            }
        } catch (final Exception e) {
            logger.error("Could not serialize the " + configurationName
                + " file: " + this.configurationFileName, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (final IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public List<Index> getIndexes() {
        return this.configurationBean.getIndexes();
    }

    @Override
    public Index getIndex(final String id) {
        return this.configurationBean.getIndex(id);
    }

    @Override
    public List<Monitor> getMonitors() {
        return this.configurationBean.getMonitors();
    }

    @Override
    public Monitor getMonitor(final String id) {
        return this.configurationBean.getMonitor(id);
    }

    @Override
    public List<DataSource> getDataSources() {
        return this.configurationBean.getDataSources();
    }

    @Override
    public DataSource getDataSource(final String id) {
        return this.configurationBean.getDataSource(id);
    }

    @Override
    public List<Interpreter> getInterpreters() {
        return this.configurationBean.getInterpreters();
    }

    @Override
    public Interpreter getInterpreter(final String id) {
        return this.configurationBean.getInterpreter(id);
    }

    @Override
    public void checkConsistency() throws ConfigurationException {
        for (Monitor monitor : getMonitors()) {
            if (monitor.getDataSourceId() == null) {
                throw new ConfigurationException("DataSource for Monitor '%s' is null", monitor.getId());
            }
            if (monitor.getInterpreterId() == null) {
                throw new ConfigurationException("Interpreter for Monitor '%s' is null", monitor.getId());
            }
            if (monitor.getIndexId() == null) {
                throw new ConfigurationException("Index for Monitor '%s' is null", monitor.getId());
            }

            if (getDataSource(monitor.getDataSourceId()) == null) {
                throw new ConfigurationException("DataSource '%s' for Monitor '%s' is not configured", monitor.getDataSourceId(),
                    monitor.getId());
            }
            if (getInterpreter(monitor.getInterpreterId()) == null) {
                throw new ConfigurationException("Interpreter '%s' for Monitor '%s' is not configured", monitor.getInterpreterId(),
                    monitor.getId());
            }
            if (getIndex(monitor.getIndexId()) == null) {
                throw new ConfigurationException("Index '%s' for Monitor '%s' is not configured", monitor.getIndexId(), monitor.getId());
            }
        }
    }

}
