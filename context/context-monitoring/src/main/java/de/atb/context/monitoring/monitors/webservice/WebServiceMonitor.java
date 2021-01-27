package de.atb.context.monitoring.monitors.webservice;

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


import de.atb.context.monitoring.analyser.webservice.WebServiceAnalyser;
import de.atb.context.monitoring.config.models.*;
import de.atb.context.monitoring.events.MonitoringProgressListener;
import de.atb.context.monitoring.parser.webservice.WebServiceParser;
import de.atb.context.monitoring.config.models.datasources.WebServiceDataSource;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.monitoring.models.IWebService;
import de.atb.context.monitoring.monitors.ThreadedMonitor;
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
 * WebServiceMonitor
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public class WebServiceMonitor extends ThreadedMonitor<IWebService, IMonitoringDataModel<?, ?>> implements MonitoringProgressListener<IWebService, IMonitoringDataModel<?, ?>>, Runnable {

    protected WebServiceDataSource dataSource;
    protected ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final Logger logger = LoggerFactory.getLogger(WebServiceMonitor.class);

    public WebServiceMonitor(final DataSource dataSource, final Interpreter interpreter, final Monitor monitor, final AmIMonitoringConfiguration amiConfiguration) {
        super(dataSource, interpreter, monitor, amiConfiguration);
        if (dataSource.getType().equals(DataSourceType.WebService) && (dataSource instanceof WebServiceDataSource)) {
            this.dataSource = (WebServiceDataSource) dataSource;
        } else {
            throw new IllegalArgumentException("Given dataSource must be of type WebServiceDataSource!");
        }
        this.logger.info("Initializing WebServiceMonitor for uri: " + dataSource.getUri());
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
        this.executor.shutdown();
        try {
            if (!this.executor.awaitTermination(2, TimeUnit.SECONDS)) {
                this.executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {
            this.running = false;
        }
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
            addProgressListener(WebServiceMonitor.this);
            this.running = true;
            long period = this.dataSource.getInterval() != null ? this.dataSource.getInterval() : 15000L;
            long initialDelay = this.dataSource.getStartDelay() != null ? this.dataSource.getStartDelay() : period;
            this.executor.scheduleAtFixedRate(new WebServiceMonitoringRunner(this), initialDelay, period, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            this.logger.error("Error starting WebServiceMonitor! ", e);
        }
    }

    public void monitor() {
        this.logger.info("Starting monitoring for WebService at URI: " + this.dataSource.getUri());
        this.running = true;
        InterpreterConfiguration setting = this.interpreter
            .getConfiguration("monitoring-config.xml");
        handleWebService(setting);
    }

    protected final void handleWebService(final InterpreterConfiguration setting) {
        if (setting != null) {
            this.logger.debug("Handling URI " + this.dataSource.getUri() + "...");
            if ((this.dataSource.getUri() != null)) {
                WebServiceParser parser = setting.createParser(
                    this.dataSource, this.amiConfiguration);
                WebServiceAnalyser analyser = (WebServiceAnalyser) parser.getAnalyser();
                if (parser.parse(this.dataSource.toWebService())) {
                    this.raiseParsedEvent(this.dataSource.toWebService(), parser.getDocument());
                    this.raiseAnalysedEvent(analyser.analyse(this.dataSource.toWebService()),
                        this.dataSource.toWebService(), analyser.getDocument());
                }
            }
        } else {
            this.logger.debug("URI " + this.dataSource.getUri() + " will be ignored!");
        }
    }

    protected static final class WebServiceMonitoringRunner implements Runnable {

        private static final Logger logger = LoggerFactory.getLogger(WebServiceMonitoringRunner.class);

        private final WebServiceMonitor parent;

        public WebServiceMonitoringRunner(final WebServiceMonitor parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            try {
                this.parent.monitor();
            } catch (Exception e) {
                logger.error("Error during monitoring of WebService! ", e);
            }
        }

    }

    @Override
    public void documentAnalysed(List<IMonitoringDataModel<?, ?>> analysedList, IWebService parsed, Document document) {
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
    public void documentParsed(IWebService parsed, Document document) {
    }

    @Override
    public void documentIndexed(String indexId, Document document) {
    }
}
