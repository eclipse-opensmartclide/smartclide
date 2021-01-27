package de.atb.context.monitoring.parser;

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

import de.atb.context.monitoring.index.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.atb.context.tools.ontology.AmIMonitoringConfiguration;
import de.atb.context.common.Version;
import de.atb.context.monitoring.analyser.IndexingAnalyser;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.InterpreterConfiguration;
import de.atb.context.monitoring.models.IMonitoringDataModel;

/**
 * IndexingParser
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public abstract class IndexingParser<T> {

    protected Document document;
    protected DataSource dataSource;
    protected InterpreterConfiguration interpreterConfiguration;

    protected boolean duplicateCheckEnabled = true;

    protected final Logger logger = LoggerFactory
        .getLogger(IndexingParser.class);

    public IndexingParser(final DataSource dataSource,
                          final InterpreterConfiguration interpreterConfiguration,
                          final AmIMonitoringConfiguration amiConfiguration) {
        this.dataSource = dataSource;
        this.interpreterConfiguration = interpreterConfiguration;
        document = new Document();
        // TODO document.add(IndexedFields.createField(IndexId, indexer.getIndexId()));
    }

    public final Version getIndexerVersion() {
        return Version.INDEXER;
    }

    public final Version getMonitoringDataVersion() {
        return Version.INDEXER;
    }

    public final Document createNewDocument() {
        Document document = new Document();
        // TODO document.add(IndexedFields.createField(IndexId, indexer.getIndexId()));
        return document;
    }

    public final Document getDocument() {
        return this.document;
    }

    public final void setDuplicateCheckEnabled(
        final boolean duplicateCheckEnabled) {
        this.duplicateCheckEnabled = duplicateCheckEnabled;
    }

    public final boolean isDuplicateCheckEnabled() {
        return this.duplicateCheckEnabled;
    }

    /**
     * Determines if the given object needs to be parsed and indexed. If so,
     * parsing is executed, otherwise nothing happens.
     * <p>
     * Parsing the given object in this case creates or updates the index of the
     * given object in the underlying index. Checking if the object itself has
     * been updated and if therefore the index has to be updated has to be
     * determined by the implementing class.
     *
     * @param object The object to be parsed.
     * @return <code>true</code> if the given object was (re-)parsed and
     * (re-)indexed, <code>false</code> otherwise.
     */
    public abstract boolean parse(T object);

    public abstract IndexingAnalyser<? extends IMonitoringDataModel<?, ?>, T> getAnalyser();
}
