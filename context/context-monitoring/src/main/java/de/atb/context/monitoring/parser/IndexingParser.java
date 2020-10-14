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

import static de.atb.context.monitoring.parser.IndexedFields.IndexId;

import de.atb.context.monitoring.config.models.Index;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.atb.context.tools.ontology.AmIMonitoringConfiguration;
import de.atb.context.common.Version;
import de.atb.context.monitoring.analyser.IndexingAnalyser;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.InterpreterConfiguration;
import de.atb.context.monitoring.index.Indexer;
import de.atb.context.monitoring.models.IMonitoringDataModel;

/**
 * IndexingParser
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public abstract class IndexingParser<T> {

    protected Indexer indexer;
    protected Document document;
    protected DataSource dataSource;
    protected InterpreterConfiguration interpreterConfiguration;

    protected boolean duplicateCheckEnabled = true;

    protected final Logger logger = LoggerFactory
        .getLogger(IndexingParser.class);

    public IndexingParser(final DataSource dataSource,
                          final InterpreterConfiguration interpreterConfiguration,
                          final Indexer indexer, final AmIMonitoringConfiguration amiConfiguration) {
        this.indexer = indexer;
        this.dataSource = dataSource;
        this.interpreterConfiguration = interpreterConfiguration;
        document = new Document();
        document.add(IndexedFields.createField(IndexId, indexer.getIndexId()));
    }

    public final Version getIndexerVersion() {
        return Version.INDEXER;
    }

    public final Version getMonitoringDataVersion() {
        return Version.INDEXER;
    }

    public final Indexer getIndexer() {
        return this.indexer;
    }

    public final Index getIndex() {
        return this.indexer.getIndex();
    }

    public final Document createNewDocument() {
        Document document = new Document();
        document.add(IndexedFields.createField(IndexId, indexer.getIndexId()));
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

    /**
     * Returns whether a document with the given URI is already indexed and up
     * to date.
     * <p>
     * This check takes into account the timestamp of the object (e.g. for a
     * file it's last modification time) and the current version of the indexer.
     * If the object or the version have changed since the last indexing run,
     * the object will have be indexed again and therefore the method will
     * return <code>true</code>.
     *
     * @param uri          the URI of the object which is about to be re-indexed.
     * @param lastModified the timestamp (in miliseconds) of the last modification time
     *                     of the object to be re-indexed.
     * @return <code>true</code> if the object with the given URI needs to be
     * indexed, <code>false</code> otherwise.
     */
    public final boolean isIndexUpToDate(final String uri,
                                         final long lastModified) {
        if (!isDuplicateCheckEnabled()) {
            this.logger.debug("Skipping check for duplicates, re-index " + uri);
            return false;
        }

        this.logger.debug("Checking if Document for '" + uri
            + "' is already indexed...");

        boolean indexUpToDate = false;
        Document existingDoc = this.indexer.getDocumentByUri(uri);
        if (existingDoc != null) {
            String storedIndexerVersion = IndexedFields.IndexerVersion
                .getString(existingDoc);
            String storedModification = IndexedFields.LastModified
                .getString(existingDoc);
            String currentModification = DateTools.timeToString(lastModified,
                DateTools.Resolution.MINUTE);

            if (currentModification.equalsIgnoreCase(storedModification)
                && storedIndexerVersion.equals(Version.CONTEXT
                .getVersionString())) {
                indexUpToDate = true;
            } else {
                this.indexer.deleteDocumentByUri(uri);
            }
        }

        this.logger.debug("Document '" + uri + "' was "
            + (indexUpToDate ? "already" : "not") + " indexed");
        return indexUpToDate;
    }

}
