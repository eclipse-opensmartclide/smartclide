package de.atb.context.monitoring;

/*
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */


import de.atb.context.common.exceptions.ConfigurationException;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.Interpreter;
import de.atb.context.monitoring.config.models.Monitor;
import de.atb.context.monitoring.index.Indexer;
import de.atb.context.monitoring.monitors.ThreadedMonitor;
import de.atb.context.services.wrapper.AmIMonitoringDataRepositoryServiceWrapper;
import org.slf4j.LoggerFactory;
import de.atb.context.tools.ontology.AmIMonitoringConfiguration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * MetaMonitor
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public class MetaMonitor {

    @SuppressWarnings("unchecked")
    public static <P, A> ThreadedMonitor<P, A> createThreadedMonitor(final Monitor monitor, final DataSource dataSource, final Interpreter interpreter,
                                                                     final Indexer indexer, final AmIMonitoringConfiguration configuration, AmIMonitoringDataRepositoryServiceWrapper amiRepository) throws ConfigurationException {
        Class<?> factory;
        try {
            factory = Class.forName(dataSource.getMonitor());
        } catch (ClassNotFoundException e) {
            LoggerFactory.getLogger(MetaMonitor.class).error(e.getMessage(), e);
            throw new ConfigurationException("DAO-class [%s] not found!", dataSource.getMonitor());
        }
        int modifier = factory.getModifiers();

        if (!Modifier.isAbstract(modifier) && !Modifier.isInterface(modifier) && !Modifier.isStatic(modifier)) {
            try {
                Constructor<?> constructor = factory.getConstructor(DataSource.class, Interpreter.class, Monitor.class,
                    Indexer.class, AmIMonitoringConfiguration.class);
                ThreadedMonitor<P, A> instance = (ThreadedMonitor<P, A>) constructor.newInstance(new Object[]{dataSource, interpreter,
                    monitor, indexer, configuration});
                instance.setAmiRepository(amiRepository);
                return instance;
            } catch (InstantiationException | InvocationTargetException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | SecurityException e) {
                throw new ConfigurationException("Error while instantiating class [%s].", e, dataSource.getMonitor());
            }
        } else {
            throw new ConfigurationException("Can't instantiate DAO-class [%s]!", dataSource.getMonitor());
        }
    }

}
