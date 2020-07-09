package de.atb.context.monitoring.index;

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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import de.atb.context.monitoring.config.models.Index;
import de.atb.context.monitoring.parser.IndexedFields;
import de.atb.context.monitoring.parser.IndexingParser;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Indexer
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public class Indexer {

    /**
     * Model of the index this indexer is responsible for.
     */
    protected Index index;

    /**
     * IndexWriter which allows adding documents to an index.
     */
    protected IndexWriter writer;

    /**
     * IndexSearcher which enables searching for indexed documents based on
     * search terms.
     */
    protected IndexSearcher searcher;

    /**
     * Logger used for logging output.
     */
    protected final Logger logger = LoggerFactory.getLogger(Indexer.class);

    /**
     * Creates an Indexer for the given Index. The Indexer is able to add
     * documents to the given index.
     *
     * @param index The index this Indexer is responsible for.
     */
    public Indexer(final Index index) {
        if (this.logger.isInfoEnabled()) {
            this.logger.info(String.format("Initiating Indexer for '%s'...", index.getId()));
        }
        this.index = index;
        createShutdownHook();
        createIndexWriter();
        createSearcher();
    }

    /**
     * Gets the Identifier of the Index this Indexer is responsible for.
     *
     * @return the Identifier of the Index this Indexer is responsible for.
     */
    public final String getIndexId() {
        return this.index.getId();
    }

    /**
     * Gets the Index this Indexer is responsible for.
     *
     * @return the Index this Indexer is responsible for.
     */
    public final Index getIndex() {
        return this.index;
    }

    /**
     * Adds the given Document to the Index underlying this indexer.
     * <p>
     * Please note that calling this method will not check if the given document
     * already exists in the index. Therefore checking has to be implemented by
     * other classes (see {@link IndexingParser#isIndexUpToDate(String, long)}.
     *
     * @param document the Document to be indexed.
     */
    public final synchronized void addDocumentToIndex(final Document document) {
        try {
            // Add Indexer version for later debugging and updates
            document.add(IndexedFields.createField(IndexedFields.IndexerVersion, de.atb.context.common.Version.INDEXER.getVersionString()));

            // Here the document is really sent to the index
            if (this.writer != null) {
                this.writer.addDocument(document);
            }

        } catch (CorruptIndexException cie) {
            this.logger.error("Index seems to be corrupt: " + this.index.getLocation(), cie);

        } catch (IOException ioe) {
            this.logger.error("Could not access the Index: " + this.index.getLocation(), ioe);

        } catch (Throwable e) {
            this.logger.error("An error occured during indexing: " + this.index.getLocation(), e);
        }
    }

    /**
     * Returns the indexed Document with the given URI from the Index underlying
     * this Indexer.
     *
     * @param uri the URI of the indexed Document to be retrieved.
     * @return the indexed Document with the given URI or <code>null</code> if
     * no such Document is indexed.
     */
    public final synchronized Document getDocumentByUri(final String uri) {
        try {
            TopDocs topDocs = this.searcher.search(new TermQuery(new Term("uri", uri)), null, 1);
            if (topDocs.totalHits > 0) {
                ScoreDoc scoreDoc = topDocs.scoreDocs[0];
                return this.searcher.doc(scoreDoc.doc);
            }
        } catch (IOException ioe) {
            this.logger.error("Could not search for uri " + uri, ioe);
        }
        return null;
    }

    /**
     * Deletes the document(s) containing term.
     *
     * @param term the term to identify the documents to be deleted.
     */
    public final synchronized void deleteDocuments(final Term term) {
        try {
            this.writer.deleteDocuments(term);
        } catch (IOException e) {
            this.logger.error("Could not delete the existing document by term " + term.toString(), e);
        }
    }

    /**
     * Deletes the document(s) containing a URI term with the given value.
     *
     * @param uri the URI to identify the documents to be deleted.
     */
    public final synchronized void deleteDocumentByUri(final String uri) {
        deleteDocuments(new Term("uri", uri));
    }

    /**
     * Add Shutdown Hook to close Writer and Searcher
     */
    protected final void createShutdownHook() {
        Thread shutdownHook = new Thread(this::close, "Shutdown Hook Thread (" + this.index.getId() + ")");
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    public final void close() {
        try {
            this.logger.info("Closing index with id '" + this.index.getId() + "'!");
            if (this.writer != null) {
                this.writer.close();
            }
            //if (this.searcher != null) {
                //this.searcher.close();
            //}
        } catch (IOException ioe) {
            this.logger.error(ioe.getMessage(), ioe);
        }
    }

    /**
     * Initializes an index-search
     */
    protected final void createSearcher() {
        try {
            //if (this.searcher != null) {
                //this.searcher.close();
            //}
            File tmpDir = new File(this.index.getLocation()); // TODO DRM API?
            if (!tmpDir.exists() && !tmpDir.mkdirs()) {
                this.logger.warn("Index directory " + tmpDir.getAbsolutePath() + " does not exist and could not be created!");
            }
            FSDirectory dir = FSDirectory.open(tmpDir);
            DirectoryReader reader = DirectoryReader.open(dir);
            this.searcher = new IndexSearcher(reader);
        } catch (Throwable e) {
            this.logger.error("Unable to read index-files from directory " + this.index.getLocation(), e);
        }
    }

    public final void dropIndex() {
        closeSearcher();
        closeIndexWriter();
        dropIndex(this.index);
        createIndexWriter();
        createSearcher();
    }

    public static void dropIndex(final Index index) {
        if (index != null) {
            dropIndex(index.getLocation());
        }
    }

    protected static void dropIndex(final String indexLocation) {
        File dir = new File(indexLocation); // TODO DRM API?
        LoggerFactory.getLogger(Indexer.class).info("Dropping Index at " + dir.getAbsolutePath());
        if (dir.exists() && dir.isDirectory()) {
            String[] genFiles = dir.list((dir1, name) -> (name != null) && name.startsWith("segments") && name.endsWith(".gen"));
            // "segments.gen" was found, folder seems correct
            if (genFiles.length > 0) {
                deleteDir(dir);
            }
        } else {
            LoggerFactory.getLogger(Indexer.class).warn("Directory " + dir.getAbsolutePath() + " does not exist!");
        }
    }

    protected static boolean deleteDir(final File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child)); // TODO DRM API?
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    protected final void closeIndexWriter() {
        if (this.writer != null) {
            try {
                this.writer.close();
            } catch (Exception we) {
                this.logger.error(we.getMessage(), we);
            }
        }
    }

    protected final void closeSearcher() {
        /*if (this.searcher != null) {
            try {
                this.searcher.close();
            } catch (Exception we) {
                this.logger.error(we.getMessage(), we);
            }
        }*/
    }

    protected final void createIndexWriter() {
        PerFieldAnalyzerWrapper analyzerWrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(Version.LUCENE_4_9));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_9, analyzerWrapper);
        indexWriterConfig.setRAMBufferSizeMB(48.00);

        File tmpDir = new File(this.index.getLocation()); // TODO DRM API?
        if (!tmpDir.exists() && !tmpDir.mkdirs()) {
            this.logger.warn("Index directory " + tmpDir.getAbsolutePath() + " does not exist and could not be created!");
        }
        try {
            FSDirectory dir = FSDirectory.open(tmpDir);
            this.writer = new IndexWriter(dir, indexWriterConfig);

            // Index does not exist => create!
        } catch (FileNotFoundException fnfe) {
            this.logger.info("Lucene index at '" + this.index.getLocation() + "' does not exist yet, creating it.");
            this.logger.warn(fnfe.getMessage());
            try {
                FSDirectory dir = FSDirectory.open(new File(this.index.getLocation())); // TODO DRM API?
                this.writer = new IndexWriter(dir, indexWriterConfig);
                this.writer.commit();
            } catch (Throwable e) {
                this.logger.error("Could not access the Index: " + this.index.getLocation(), e);
                if (this.writer != null) {
                    try {
                        this.writer.close();
                    } catch (Exception ex) {
                        this.logger.error(e.getMessage(), ex);
                    }
                }
                return;
            }
        } catch (IOException e) {
            this.logger.error("Could not access the Index: " + this.index.getLocation(), e);
            if (this.writer != null) {
                try {
                    this.writer.close();
                } catch (Exception we) {
                    this.logger.error(we.getMessage(), we);
                }
            }
            return;
        }
        if (this.logger.isInfoEnabled()) {
            this.logger.info(String.format("Indexer for '%s' initiated!", this.index.getId()));
        }
    }
}
