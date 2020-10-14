package de.atb.context.monitoring.config.models;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

/**
 * Config
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
@Root
public class Config implements IMonitoringConfiguration {

    @Attribute(required = false)
    protected String schemaLocation;

    /**
     * Represents the List of Indexes from the XML configuration file.
     */
    @ElementList(name = "indexes", entry = "index")
    protected List<Index> indexes = new ArrayList<>();

    /**
     * Represents the List of Monitors from the XML configuration file.
     */
    @ElementList(name = "monitors", entry = "monitor")
    protected List<Monitor> monitors = new ArrayList<>();

    /**
     * Represents the List of DataSources from the XML configuration file.
     */
    @ElementList(name = "datasources", entry = "datasource")
    protected List<DataSource> dataSources = new ArrayList<>();

    /**
     * Represents the List of Interpreters from the XML configuration file.
     */
    @ElementList(name = "interpreters", entry = "interpreter")
    protected List<Interpreter> interpreters = new ArrayList<>();

    /**
     * Maintains a Map of Monitors, Monitors are identified by their ids.
     */
    protected Map<String, Monitor> monitorsMap = new HashMap<>();

    /**
     * Maintains a Map of Interpreters, Interpreters are identified by their
     * ids.
     */
    protected Map<String, Interpreter> interpretersMap = new HashMap<>();

    /**
     * Maintains a Map of DataSources, DataSources are identified by their ids.
     */
    protected Map<String, DataSource> datasourcesMap = new HashMap<>();

    /**
     * Maintains a Map of Indexes, Indexes are identified by their ids.
     */
    protected Map<String, Index> indexesMap = new HashMap<>();

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.monitoring.config.models.ISettings#getIndexes()
     */
    @Override
    public final List<Index> getIndexes() {
        return this.indexes;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.monitoring.config.models.ISettings#getIndex(java.
     * lang.String)
     */
    @Override
    public final Index getIndex(final String id) {
        return this.indexesMap.get(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.monitoring.config.models.ISettings#getMonitors()
     */
    @Override
    public final List<Monitor> getMonitors() {
        return this.monitors;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.monitoring.config.models.ISettings#getMonitor(java
     * .lang.String)
     */
    @Override
    public final Monitor getMonitor(final String id) {
        return this.monitorsMap.get(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.monitoring.config.models.ISettings#getDataSources()
     */
    @Override
    public final List<DataSource> getDataSources() {
        return this.dataSources;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.monitoring.config.models.ISettings#getDataSource(
     * java.lang.String)
     */
    @Override
    public final DataSource getDataSource(final String id) {
        return this.datasourcesMap.get(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.monitoring.config.models.ISettings#getInterpreters()
     */
    @Override
    public final List<Interpreter> getInterpreters() {
        return this.interpreters;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.monitoring.config.models.ISettings#getInterpreter
     * (java.lang.String)
     */
    @Override
    public final Interpreter getInterpreter(final String id) {
        return this.interpretersMap.get(id);
    }

    /**
     * Maps all Lists retrieved from the XML configuration file to Maps, so that
     * FileSets, DataSources and Indexes are accessible via their ids.
     */
    @Commit
    protected final void createIdMappings() {
        this.monitorsMap.clear();
        for (Monitor monitor : this.monitors) {
            this.monitorsMap.put(monitor.getId(), monitor);
            monitor.setParentContainer(this);
        }

        this.interpretersMap.clear();
        for (Interpreter interpreter : this.interpreters) {
            this.interpretersMap.put(interpreter.getId(), interpreter);
        }

        this.indexesMap.clear();
        for (Index index : this.indexes) {
            this.indexesMap.put(index.getId(), index);
        }

        this.datasourcesMap.clear();
        for (DataSource dataSource : this.dataSources) {
            this.datasourcesMap.put(dataSource.getId(), dataSource);
        }
    }

}
