package de.atb.context.monitoring.analyser.file;

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
import java.util.List;

import de.atb.context.monitoring.config.models.InterpreterConfiguration;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.monitoring.index.Document;
import org.javatuples.Pair;

import de.atb.context.tools.ontology.AmIMonitoringConfiguration;
import de.atb.context.monitoring.analyser.IndexingAnalyser;
import de.atb.context.monitoring.config.models.DataSource;

/**
 * FilePairAnalyser
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public abstract class FilePairAnalyser<OutputType extends IMonitoringDataModel<?, ?>> extends
    IndexingAnalyser<OutputType, Pair<File, File>> {

    public FilePairAnalyser(final DataSource dataSource, final InterpreterConfiguration interpreterConfiguration, final Document document, final AmIMonitoringConfiguration amiConfiguration) {
        super(dataSource, interpreterConfiguration, document, amiConfiguration);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IndexingAnalyser#analyseObject
     * (java.lang.Object, Document)
     */
    @Override
    public final List<OutputType> analyseObject(final Pair<File, File> filePair, final Document document) {
        // some generic handling stuff could be done here
        // like indexing file creation, modification etc.

        return analyseObject(filePair);
    }

    public abstract List<OutputType> analyseObject(Pair<File, File> filePair);

}
