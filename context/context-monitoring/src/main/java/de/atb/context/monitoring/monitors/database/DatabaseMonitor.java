package de.atb.context.monitoring.monitors.database;

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


import de.atb.context.monitoring.analyser.database.DatabaseAnalyser;
import de.atb.context.monitoring.config.models.*;
import de.atb.context.monitoring.config.models.datasources.DatabaseDataSource;
import de.atb.context.monitoring.events.MonitoringProgressListener;
import de.atb.context.monitoring.models.IDatabase;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.monitoring.monitors.ThreadedMonitor;
import de.atb.context.monitoring.parser.database.DatabaseParser;
import de.atb.context.services.faults.ContextFault;
import de.atb.context.monitoring.index.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.tools.ontology.AmIMonitoringConfiguration;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * DatabaseMonitor
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public class DatabaseMonitor extends ThreadedMonitor<IDatabase, IMonitoringDataModel<?, ?>> implements MonitoringProgressListener<IDatabase, IMonitoringDataModel<?, ?>>, Runnable {

    protected DatabaseDataSource dataSource;
    protected ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final Logger logger = LoggerFactory.getLogger(DatabaseMonitor.class);

    public DatabaseMonitor(final DataSource dataSource, final Interpreter interpreter, final Monitor monitor, final AmIMonitoringConfiguration amiConfiguration) {
        super(dataSource, interpreter, monitor, amiConfiguration);
        if (dataSource.getType().equals(DataSourceType.Database) && (dataSource instanceof DatabaseDataSource)) {
            this.dataSource = (DatabaseDataSource) dataSource;
        } else {
            throw new IllegalArgumentException("Given dataSource must be of type DatabaseDataSource!");
        }
        this.logger.info("Initializing DatabaseMonitor for uri: " + dataSource.getUri());
    }

    /*
     * (non-Javadoc)
     *
     * @see ThreadedMonitor#isRunning()
     */
    @Override
    public final boolean isRunning() {
        return this.running;
    }

    @Override
    public final void pause() {
        this.running = false;
        this.executor.shutdown();
    }

    @Override
    public final void restart() {
        shutdown();
        run();
    }

    @Override
    public final void shutdown() {
        shutdown(2, TimeUnit.SECONDS);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * ThreadedMonitor#shutdown(long)
     */
    @Override
    protected final void shutdown(final long timeOut, final TimeUnit unit) {
        this.executor.shutdown();
        try {
            if (!this.executor.awaitTermination(timeOut, unit)) {
                this.executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {
            this.running = false;
        }
    }

    @Override
    public final void run() {
        try {
            Thread.currentThread().setName(this.getClass().getSimpleName() + " (" + this.dataSource.getId() + ")");
            addProgressListener(DatabaseMonitor.this);
            this.running = true;
            long period = this.dataSource.getInterval() != null ? this.dataSource.getInterval() : 15000L;
            long initialDelay = this.dataSource.getStartDelay() != null ? this.dataSource.getStartDelay() : period;
            this.executor.scheduleAtFixedRate(new DatabaseMonitoringRunner(this), initialDelay, period, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            this.logger.error("Error starting DatabaseMonitor! ", e);
        }
    }

    public void monitor() {
        this.logger.info("Starting monitoring for DATABASE at URI: " + this.dataSource.getUri());
        this.running = true;
        InterpreterConfiguration setting = this.interpreter
            .getConfiguration("monitoring-config.xml");
        handleDatabase(setting);
    }

    protected final void handleDatabase(final InterpreterConfiguration setting) {
        if (setting != null) {
            this.logger.debug("Handling URI " + this.dataSource.getUri() + "...");
            if ((this.dataSource.getUri() != null)) {
                DatabaseParser parser = setting.createParser(
                    this.dataSource, this.amiConfiguration);
                DatabaseAnalyser analyser = (DatabaseAnalyser) parser.getAnalyser();
                if (parser.parse(this.dataSource.toDatabase())) {
                    this.raiseParsedEvent(this.dataSource.toDatabase(), parser.getDocument());
                    this.raiseAnalysedEvent(analyser.analyse(this.dataSource.toDatabase()),
                        this.dataSource.toDatabase(), analyser.getDocument());
                }
            }
        } else {
            this.logger.debug("URI " + this.dataSource.getUri() + " will be ignored!");
        }
    }

    protected static final class DatabaseMonitoringRunner implements Runnable {

        private static final Logger logger = LoggerFactory.getLogger(DatabaseMonitoringRunner.class);

        private final DatabaseMonitor parent;

        public DatabaseMonitoringRunner(final DatabaseMonitor parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            try {
                this.parent.monitor();
            } catch (Exception e) {
                logger.error("Error during monitoring of DATABASE! ", e);
            }
        }

    }

    @Override
    public void documentAnalysed(List<IMonitoringDataModel<?, ?>> analysedList, IDatabase parsed, Document document) {
        if ((analysedList != null) && (analysedList.size() > 0)) {
            for (IMonitoringDataModel<?, ?> analysed : analysedList) {
                logger.info("Created monitoring data for " + analysed.getApplicationScenario());
                try {
                    this.amiRepository.persist(analysed);
                    logger.info("Persisted monitoring data for " + analysed.getApplicationScenario());
                } catch (ContextFault e1) {
                    logger.error(e1.getMessage(), e1);
                }
            }
        }
    }

    @Override
    public void documentParsed(IDatabase parsed, Document document) {
    }

    @Override
    public void documentIndexed(String indexId, Document document) {
    }
}
