package de.atb.context.monitoring.monitors.file;

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

import de.atb.context.monitoring.analyser.IndexingAnalyser;
import de.atb.context.monitoring.config.models.*;
import de.atb.context.monitoring.config.models.datasources.FileSystemDataSource;
import de.atb.context.monitoring.events.MonitoringProgressListener;
import de.atb.context.monitoring.models.IFileSystem;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.monitoring.monitors.ThreadedMonitor;
import de.atb.context.monitoring.parser.IndexingParser;
import de.atb.context.services.faults.ContextFault;
import name.pachler.nio.file.*;
import name.pachler.nio.file.ext.ExtendedWatchEventKind;
import de.atb.context.monitoring.index.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.tools.ontology.AmIMonitoringConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardWatchEventKinds;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * FileSystemMonitor
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public class FileSystemMonitor extends
    ThreadedMonitor<File, IMonitoringDataModel<?, ?>> implements MonitoringProgressListener<IFileSystem, IMonitoringDataModel<?, ?>>, Runnable {

    protected File pathToMonitor;
    protected Thread watchDaemon;

    protected Map<String, Long> filesToDates = new HashMap<>();

    private static final Long EVENT_FIRING_OFFSET = 100L;

    private final Logger logger = LoggerFactory
        .getLogger(FileSystemMonitor.class);

    public FileSystemMonitor(final DataSource dataSource,
                             final Interpreter fileSet, final Monitor monitor,
                             final AmIMonitoringConfiguration amiConfiguration) {
        super(dataSource, fileSet, monitor, amiConfiguration);
        if (dataSource.getType().equals(DataSourceType.FileSystem)
            && (dataSource instanceof FileSystemDataSource)) {
            this.dataSource = dataSource;
        } else {
            throw new IllegalArgumentException(
                "Given dataSource must be of type FileSystemDataSource!");
        }

        this.logger.info("Initializing " + this.getClass().getSimpleName()
            + " for uri: " + dataSource.getUri());
        this.pathToMonitor = new File(this.dataSource.getUri())
            .getAbsoluteFile();
    }

    @Override
    public final void pause() {
        this.running = false;
        stopWatcher();
    }

    @Override
    public final void restart() {
        this.running = true;
        stopWatcher();
        this.filesToDates.clear();
        startWatcher();
    }

    @Override
    public final void run() {
        try {
            Thread.currentThread().setName(
                this.getClass().getSimpleName() + " ("
                    + this.dataSource.getId() + ")");
            monitor();
            startWatcher();
        } catch (Exception e) {
            this.logger.error("Error starting FileSystemMonitor! ", e);
        }
    }

    @Override
    public final void monitor() {
        this.logger.info("Starting monitoring for path " + this.pathToMonitor);
        this.filesToDates.clear();
        if (this.pathToMonitor.isDirectory()) {
            this.running = true;
        }
    }

    protected final void stopWatcher() {
        if (this.watchDaemon != null) {
            this.watchDaemon.interrupt();
            try {
                this.watchDaemon.join(2000);
            } catch (InterruptedException e) {
                this.logger.warn(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    protected final void prepareWatcher() {
        final File finalPath = this.pathToMonitor;
        Thread t = new Thread(() -> iterateFiles(finalPath), this.getClass().getSimpleName() + " Iterator Thread");
        t.setDaemon(true);
        t.start();
    }

    protected final void iterateFiles(final File directory) {
        logger.debug("Iterating files in directory '" + directory + "'");
        if ((directory == null) || (directory.listFiles() == null)) {
            return;
        }
        File[] files = directory
            .listFiles(this.interpreter.getFilenameFilter());
        for (File f : files) {
            if (!this.running) {
                return;
            }

            if (f == null) {
                continue;
            }
            try {
                if (f.isFile() && this.interpreter.accepts(f)) {
                    fileExisting(f.getAbsolutePath(),
                        new Date().getTime(),
                        this.interpreter.getConfiguration(f));
                } else if (f.isFile() && !this.interpreter.accepts(f)) {
                    this.logger.debug("Skipping file " + f.getAbsolutePath()
                        + ", interpreter does not accept it");
                } else if (f.isDirectory()) {
                    iterateFiles(f);
                }
            } catch (Throwable t) {
                this.logger.error("Error parsing file " + f.getAbsolutePath(),
                    t);
            }
        }
    }

    protected final void startWatcher() {
        prepareWatcher();
        final File pathToMonitor = this.pathToMonitor;
        this.watchDaemon = new Thread(new Runnable() {
            private final Logger logger = LoggerFactory.getLogger(getClass());

            @Override
            public void run() {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Path watchedPath = Paths.get(pathToMonitor.getAbsolutePath());
                this.logger.debug("Started monitoring '" + watchedPath + "'");
                WatchKey signalledKey;
                try {
                    watchedPath.register(watchService,
                        StandardWatchEventKind.ENTRY_CREATE,
                        StandardWatchEventKind.ENTRY_MODIFY,
                        StandardWatchEventKind.ENTRY_DELETE,
                        StandardWatchEventKind.OVERFLOW,
                        ExtendedWatchEventKind.ENTRY_RENAME_FROM,
                        ExtendedWatchEventKind.ENTRY_RENAME_TO);
                    while (true) {
                        try {
                            signalledKey = watchService.take();
                            Long time = System.currentTimeMillis();
                            handleWatchEvents(signalledKey.pollEvents(), time);
                            signalledKey.reset();
                        } catch (InterruptedException ix) {
                            this.logger
                                .info("Watch service was interrupted, closing...");
                            this.logger.debug(ix.getMessage(), ix);
                            watchService.close();
                            Thread.currentThread().interrupt();
                            break;
                        } catch (ClosedWatchServiceException cwse) {
                            this.logger
                                .info("Watch service closed, terminating...");
                            this.logger.debug(cwse.getMessage(), cwse);
                            break;
                        }
                    }
                } catch (IOException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
        }, "File watch service Thread");
        this.watchDaemon.start();
    }

    protected final void handleWatchEvents(final List<WatchEvent<?>> events,
                                           final Long time) {
        String watchedPath = String.valueOf(Paths.get(this.pathToMonitor
            .getAbsolutePath()));
        String from = null;
        for (WatchEvent<?> e : events) {
            Path context = (Path) e.context();
            String file = watchedPath + java.io.File.separator
                + context.toString();
            InterpreterConfiguration setting = this.interpreter
                .getConfiguration(file);
            if (e.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                fileCreated(file, time, setting);
            } else if (e.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                fileModified(file, time, setting);
            } else if (e.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                fileDeleted(file, time, setting);
            } else if (e.kind() == ExtendedWatchEventKind.ENTRY_RENAME_FROM) {
                from = file;
            } else if (e.kind() == ExtendedWatchEventKind.ENTRY_RENAME_TO) {
                fileRenamed(from, file, time, setting);
                from = null;
            } else {
                this.logger.debug("Event " + e.kind() + " will be ignored");
            }
        }
    }

    protected final void fileExisting(final String file, final Long time,
                                      final InterpreterConfiguration setting) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(file
                + " already existed at "
                + this.getDefaultDateFormat().format(
                new Date(time)));
        }
        if (setting != null) {
            this.filesToDates.put(file, time);
        }
        handleFile(file, time, setting);
    }

    protected final void fileRenamed(final String from, final String to,
                                     final Long time, final InterpreterConfiguration setting) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(from
                + " renamed to "
                + to
                + " at "
                + this.getDefaultDateFormat().format(
                new Date(time)));
        }
        if (setting != null) {
            this.filesToDates.put(to, time);
            this.filesToDates.remove(from);
        }
        handleFile(to, time, setting);
    }

    protected final void fileCreated(final String file, final Long time,
                                     final InterpreterConfiguration setting) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(file
                + " created at "
                + this.getDefaultDateFormat().format(
                new Date(time)));
        }
        if (setting != null) {
            this.filesToDates.put(file, time);
        }
        handleFile(file, time, setting);
    }

    protected final void fileDeleted(final String file, final Long time,
                                     final InterpreterConfiguration setting) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(file
                + " deleted at "
                + this.getDefaultDateFormat().format(
                new Date(time)));
        }
        this.filesToDates.remove(file);
    }

    protected final boolean fileModified(final String file, final Long time,
                                         final InterpreterConfiguration setting) {
        boolean modified = false;
        Long oldTime = this.filesToDates.get(file);
        if (oldTime == null || oldTime + EVENT_FIRING_OFFSET < time) {
            modified = this.filesToDates.put(file, time) != null;
        }

        if (this.logger.isTraceEnabled() && modified) {
            this.logger.trace(file
                + " modified at "
                + this.getDefaultDateFormat().format(
                new Date(time)));
        }
        if (modified) {
            handleFile(file, time, setting);
        }
        return modified;
    }

    @SuppressWarnings("unchecked")
    protected final void handleFile(final String fileName, final Long time,
                                    final InterpreterConfiguration setting) {
        if (setting != null) {
            File file = new File(fileName);
            this.logger.debug("Handling file " + fileName + "...");
            IndexingParser<File> parser = setting.createFileParser(
                this.dataSource, this.amiConfiguration);
            IndexingAnalyser<IMonitoringDataModel<?, ?>, File> analyser = (IndexingAnalyser<IMonitoringDataModel<?, ?>, File>) parser
                .getAnalyser();
            if (parser.parse(file)) {
                this.raiseParsedEvent(file, parser.getDocument());
                List<IMonitoringDataModel<?, ?>> analysedModels = analyser
                    .analyse(file);
                this.raiseAnalysedEvent(analysedModels, file,
                    analyser.getDocument());
            }
        } else {
            this.logger.debug("File " + fileName + " will be ignored!");
        }
    }

    protected final DateFormat getDefaultDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
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

    /*
     * (non-Javadoc)
     *
     * @see ThreadedMonitor#shutdown()
     */
    @Override
    public final void shutdown() {
        this.running = false;
        stopWatcher();
        this.filesToDates.clear();
    }

    /*
     * (non-Javadoc)
     *
     * @see ThreadedMonitor#shutdown(long)
     */
    @Override
    protected final void shutdown(final long timeOut, final TimeUnit unit) {
        if (this.isRunning() && (this.watchDaemon != null)) {
            try {
                this.watchDaemon.join(unit.toMillis(timeOut));
                stopWatcher();
            } catch (InterruptedException e) {
                this.logger.warn(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void documentAnalysed(List<IMonitoringDataModel<?, ?>> analysedList, IFileSystem parsed, Document document) {
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
    public void documentParsed(IFileSystem parsed, Document document) {
    }

    @Override
    public void documentIndexed(String indexId, Document document) {
    }
}
