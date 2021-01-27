package de.atb.context.monitoring.parser.file;

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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.atb.context.monitoring.analyser.IndexingAnalyser;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.InterpreterConfiguration;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.monitoring.parser.IndexingParser;
import org.apache.commons.io.FilenameUtils;
import org.javatuples.Triplet;

import de.atb.context.tools.ontology.AmIMonitoringConfiguration;

/**
 * FileTripletParser
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public abstract class FileTripletParser extends
    IndexingParser<Triplet<File, File, File>> {

    protected IndexingAnalyser<? extends IMonitoringDataModel<?, ?>, Triplet<File, File, File>> fileTripletAnalyser;

    public FileTripletParser(final DataSource dataSource,
                             final InterpreterConfiguration interpreterConfiguration,
                             final AmIMonitoringConfiguration amiConfiguration) {
        super(dataSource, interpreterConfiguration, amiConfiguration);
        this.fileTripletAnalyser = this.interpreterConfiguration
            .createAnalyser(dataSource, this.document, amiConfiguration);
    }

    /*
     * (non-Javadoc)
     *
     * @see IndexingParser#parse(java.lang.
     * Object)
     */
    @Override
    public final boolean parse(final Triplet<File, File, File> triplet) {
        File fileOne = triplet.getValue0();

        // TODO handle fileTwo
        @SuppressWarnings("unused")
        File fileTwo = triplet.getValue0();

        // some generic file handling stuff could be done here
        // like indexing file creation, modification etc.
        String uri = fileOne.getAbsolutePath();

        String filepath = fileOne.getAbsolutePath()
            .substring(
                0,
                fileOne.getAbsolutePath().length()
                    - fileOne.getName().length());

        /* TODO this.document.add(IndexedFields.createField(IndexedFields.Uri, uri));
        this.document
            .add(IndexedFields.createField(IndexedFileFields.FileName, fileOne.getName()));
        this.document.add(IndexedFields.createField(IndexedFileFields.FilePath, filepath));
        this.document.add(IndexedFields.createField(IndexedFileFields.Hidden,
            Boolean.toString(fileOne.isHidden())));
        this.document.add(IndexedFields.createField(IndexedFields.LastModified, DateTools
            .timeToString(fileOne.lastModified(),
                DateTools.Resolution.MINUTE)));
        this.document.add(IndexedFields.createField(IndexedFileFields.FileSize,
            Long.toString(fileOne.length())));
        this.document.add(IndexedFields.createField(IndexedFileFields.Hash,
            Hashing.getMD5Checksum(fileOne)));
        this.document.add(IndexedFields.createField(IndexedFields.MonitoredAt,
            DateTools.timeToString(new Date().getTime(),
                DateTools.Resolution.SECOND)));
        */
        return parseObject(triplet);
    }

    /*
     * (non-Javadoc)
     *
     * @see IndexingParser#getAnalyser()
     */
    @Override
    public final IndexingAnalyser<? extends IMonitoringDataModel<?, ?>, Triplet<File, File, File>> getAnalyser() {
        return this.fileTripletAnalyser;
    }

    /**
     * Abstract method to be implemented by the file pair specific parser.
     *
     * @param frileTriplet the actual pair of files to be parsed.
     * @return <code>true</code> if parsing was successful, <code>false</code>
     * otherwise.
     */
    protected abstract boolean parseObject(
        Triplet<File, File, File> frileTriplet);

    public static List<Triplet<File, File, File>> getTripletsFromFiles(
        final File[] files, final String extensionOne,
        final String extensionTwo, final String extensionThree) {
        if (files == null) {
            throw new IllegalArgumentException("Files may not be null");
        }

        List<Triplet<File, File, File>> fileTriplets = new ArrayList<>(
            files.length);
        if (files.length == 0) {
            return fileTriplets;
        } else {
            List<File> fileWithExt1 = filterFilesByExtension(files,
                extensionOne);
            List<File> fileWithExt2 = filterFilesByExtension(files,
                extensionTwo);
            List<File> fileWithExt3 = filterFilesByExtension(files,
                extensionThree);
            ensureMaximumListSizeEquality(fileWithExt1, fileWithExt2,
                fileWithExt3);
            for (int i = 0; i < fileWithExt1.size(); i++) {
                fileTriplets.add(Triplet.with(fileWithExt1.get(i),
                    fileWithExt2.get(i), fileWithExt3.get(i)));
            }
        }
        return fileTriplets;
    }

    @SuppressWarnings("unchecked")
    protected static void ensureMaximumListSizeEquality(final List<File> l1,
                                                        final List<File> l2, final List<File> l3) {
        List<File>[] arr = new List[]{l1, l2, l3};
        Arrays.sort(arr, (o1, o2) -> Integer.compare(o1.size(), o2.size())
            * -1);
        ensureListCapacity(l1, arr[0].size());
        ensureListCapacity(l2, arr[0].size());
        ensureListCapacity(l3, arr[0].size());
    }

    public static void ensureListCapacity(final List<File> files,
                                          final int capacity) {
        if (files.size() >= capacity) {
            return;
        }
        for (int i = files.size(); i < capacity; i++) {
            files.add(null);
        }
    }

    protected static List<File> filterFilesByExtension(final File[] fileList,
                                                       final String extension) {
        List<File> files = new ArrayList<>();
        for (File file : fileList) {
            final String fileExtension = FilenameUtils.getExtension(file
                .getAbsolutePath());
            if (extension != null
                && !"".equals(extension) && fileExtension.equals(extension)) {
                files.add(file);
            }
        }
        return files;
    }
}
