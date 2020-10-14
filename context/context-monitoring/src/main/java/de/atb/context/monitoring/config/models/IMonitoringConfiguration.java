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


import java.util.List;

/**
 * ISettings
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public interface IMonitoringConfiguration {

    /**
     * Gets a List containing all configured Indexes.
     *
     * @return a List containing all configured Indexes.
     */
    List<Index> getIndexes();

    /**
     * Gets the Index with the given id from the List of configured Indexes. May
     * be <code>null</code> if there exists no Index with the given id.
     *
     * @param id the Id of the Index to be returned (as written in the XML
     *           configuration file).
     * @return the Index with the given id from the List of configured Indexes
     * or <code>null</code> if no such Index exists.
     */
    Index getIndex(String id);

    /**
     * Gets a List containing all configured Monitors.
     *
     * @return a List containing all configured Monitors.
     */
    List<Monitor> getMonitors();

    /**
     * Gets the Monitor with the given id from the List of configured Monitors.
     * May be <code>null</code> if there exists no Monitor with the given id.
     *
     * @param id the Id of the Monitor to be returned (as written in the XML
     *           configuration file).
     * @return the Monitor with the given id from the List of configured Monitor
     * or <code>null</code> if no such Monitor exists.
     */
    Monitor getMonitor(String id);

    /**
     * Gets a List containing all configured DataSources.
     *
     * @return a List containing all configured DataSources.
     */
    List<DataSource> getDataSources();

    /**
     * Gets the DataSource with the given id from the List of configured
     * DataSources. May be <code>null</code> if there exists no DataSource with
     * the given id.
     *
     * @param id the Id of the DataSource to be returned (as written in the XML
     *           configuration file).
     * @return the DataSource with the given id from the List of configured
     * DataSources or <code>null</code> if no such DataSource exists.
     */
    DataSource getDataSource(String id);

    /**
     * Gets a List containing all configured Interpreters.
     *
     * @return a List containing all configured Interpreters.
     */
    List<Interpreter> getInterpreters();

    /**
     * Gets the Interpreter with the given id from the List of configured
     * Interpreters. May be <code>null</code> if there exists no Interpreter
     * with the given id.
     *
     * @param id the Id of the Interpreter to be returned (as written in the
     *           XML configuration file).
     * @return the Interpreter with the given id from the List of configured
     * Interpreters or <code>null</code> if no such Interpreter exists.
     */
    Interpreter getInterpreter(String id);

}
