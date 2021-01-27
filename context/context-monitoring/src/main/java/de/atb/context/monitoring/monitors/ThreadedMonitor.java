package de.atb.context.monitoring.monitors;

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

import de.atb.context.monitoring.config.models.*;
import de.atb.context.monitoring.events.MonitoringProgressListener;
import de.atb.context.monitoring.parser.IndexingParser;
import de.atb.context.services.wrapper.AmIMonitoringDataRepositoryServiceWrapper;
import de.atb.context.monitoring.index.Document;
import de.atb.context.tools.ontology.AmIMonitoringConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ThreadedMonitor
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public abstract class ThreadedMonitor<P, A> implements Runnable {
    protected boolean running = false;
    protected DataSource dataSource;
    protected Interpreter interpreter;
    protected InterpreterConfiguration interpreterConfiguration;
    protected Monitor monitor;
    protected AmIMonitoringConfiguration amiConfiguration;
    protected AmIMonitoringDataRepositoryServiceWrapper amiRepository;
    protected List<MonitoringProgressListener<P, A>> progressListeners = new ArrayList<>();

    private Thread thread;

    protected ThreadedMonitor(final DataSource dataSource,
                              final Interpreter fileSet, final Monitor monitor,
                              final AmIMonitoringConfiguration configuration) {
        this.dataSource = dataSource;
        this.interpreter = fileSet;
        this.monitor = monitor;
        this.amiConfiguration = configuration;
    }

    public void setAmiRepository(AmIMonitoringDataRepositoryServiceWrapper amiRepository) {
        this.amiRepository = amiRepository;
    }

    /**
     * Gets whether the ThreadedMonitor is currently running or paused.
     *
     * @return <code>true</code> if the resources are monitored right now by
     * this ThreadedMonitor, <code>false</code> otherwise.
     */
    public abstract boolean isRunning();

    /**
     * Pauses this thread
     */
    public abstract void pause();

    /**
     * Resumes this thread
     */
    public abstract void restart();

    protected abstract void shutdown();

    protected abstract void shutdown(long timeOut, TimeUnit units);

    public abstract void monitor();

    public final void stop() {
        // if (this.isRunning()) {
        this.shutdown();
        if (this.thread != null) {
            this.thread.interrupt();
        }
        // }
    }

    public final void start() {
        // if (!this.isRunning()) {
        this.thread = new Thread(this);
        this.thread.start();
        // }
    }

    /**
     * Adds a MonitoringProgressListener to the list of subscribers. Subscribers
     * will be informed about and Document added to an Index by this
     * MonitoringThread.
     *
     * @param listener the MonitoringProgressListener to be added to the list of
     *                 subscribers.
     */
    public final void addProgressListener(
        final MonitoringProgressListener<P, A> listener) {
        synchronized (this.progressListeners) {
            this.progressListeners.add(listener);
        }
    }

    /**
     * Removes the MonitoringProgressListener from the list of subscribers.
     * Removed subscribers will no longer be informed about and Document added
     * to an Index by this MonitoringThread.
     *
     * @param listener the MonitoringProgressListener to be removed from the list of
     *                 subscribers.
     */
    public final void removeProgressListener(
        final MonitoringProgressListener<P, A> listener) {
        synchronized (this.progressListeners) {
            this.progressListeners.remove(listener);
        }
    }

    /**
     * Notifies all registered MonitoringProgressListeners about the document
     * that and the underlying resource that was parsed recently.
     *
     * @param document the document for the resource that just has been parsed.
     * @param parsed   the resource that has just been parsed.
     */
    protected final void raiseParsedEvent(final P parsed,
                                          final Document document) {
        for (MonitoringProgressListener<P, A> mpl : this.progressListeners) {
            mpl.documentParsed(parsed, document);
        }
    }

    /**
     * Notifies all registered MonitoringProgressListeners about the document
     * that and the underlying resource that was analysed recently.
     *
     * @param document the document for the resource that just has been analysed.
     * @param parsed   the original resource that has just been analysed.
     * @param analysed the outcome of the analysing progress.
     */
    protected final void raiseAnalysedEvent(final List<A> analysed,
                                            final P parsed, final Document document) {
        for (MonitoringProgressListener<P, A> mpl : this.progressListeners) {
            mpl.documentAnalysed(analysed, parsed, document);
        }
    }

    /**
     * Gets the DataSource associated with this ThreadedMonitor.
     *
     * @return the DataSource associated with this ThreadedMonitor.
     */
    public final DataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * Gets the Interpreter associated with this ThreadedMonitor.
     *
     * @return the Interpreter associated with this ThreadedMonitor.
     */
    public final Interpreter getInterpreter() {
        return this.interpreter;
    }
}
