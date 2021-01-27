package de.atb.context.monitoring.parser.webservice;

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

import java.util.Date;

import de.atb.context.monitoring.parser.IndexingParser;
import de.atb.context.monitoring.index.Document;

import de.atb.context.tools.ontology.AmIMonitoringConfiguration;
import de.atb.context.monitoring.analyser.IndexingAnalyser;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.InterpreterConfiguration;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.monitoring.models.IWebService;

/**
 * WebServiceParser
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public abstract class WebServiceParser extends IndexingParser<IWebService> {

    protected IndexingAnalyser<? extends IMonitoringDataModel<?, ?>, IWebService> serviceAnalyser;

    public WebServiceParser(final DataSource dataSource,
                            final InterpreterConfiguration interpreterConfiguration,
                            final AmIMonitoringConfiguration amiConfiguration) {
        super(dataSource, interpreterConfiguration, amiConfiguration);
        this.serviceAnalyser = this.interpreterConfiguration.createAnalyser(
            dataSource, this.document, amiConfiguration);
    }

    @Override
    public final synchronized boolean parse(final IWebService object) {
        // some generic webservice handling stuff could be done here
        // like indexing webservice status, modification etc.

        /* TODO this.document.add(IndexedFields.createField(Uri,
            String.valueOf(object.getURI())));
        this.document.add(IndexedFields.createField(IndexedFileFields.FilePath,
            String.valueOf(object.getURI())));
        this.document.add(IndexedFields.createField(MonitoredAt,
            DateTools.timeToString(new Date().getTime(),
                DateTools.Resolution.SECOND)));
        */
        // TODO add some webserivce-specific fields to the document's index
        return parseObject(object, this.document);
    }

    @Override
    public final synchronized IndexingAnalyser<? extends IMonitoringDataModel<?, ?>, IWebService> getAnalyser() {
        return this.serviceAnalyser;
    }

    /**
     * Abstract method to be implemented by the webservice specific parser.
     *
     * @param service  the actual webservice to parsed.
     * @param document the document to add indexed fields to.
     * @return <code>true</code> if parsing was successful, <code>false</code>
     * otherwise.
     */
    protected abstract boolean parseObject(IWebService service,
                                           Document document);

}
