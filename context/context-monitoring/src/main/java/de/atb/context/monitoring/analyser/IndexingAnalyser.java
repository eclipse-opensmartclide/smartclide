package de.atb.context.monitoring.analyser;

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


import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.config.models.InterpreterConfiguration;
import de.atb.context.monitoring.index.Document;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.tools.ontology.AmIMonitoringConfiguration;

import java.util.List;

/**
 * IndexingAnalyser
 *
 * @param <OutputType> the type of output that is returned after analysing the given
 *                     input.
 * @param <InputType>  the type of input that can be analysed by an Analyser extending
 *                     this class.
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public abstract class IndexingAnalyser<OutputType extends IMonitoringDataModel<?, ?>, InputType> {

    protected DataSource dataSource;
    protected InterpreterConfiguration interpreterConfiguration;
    protected Document document;

    private final Logger logger = LoggerFactory.getLogger(IndexingAnalyser.class);

    public IndexingAnalyser() {
        this(null, null, null, null);
    }

    public IndexingAnalyser(final DataSource dataSource, final InterpreterConfiguration interpreterConfiguration, final Document document, final AmIMonitoringConfiguration amiConfiguration) {
        this.dataSource = dataSource;
        this.interpreterConfiguration = interpreterConfiguration;
        this.document = document;
    }

    public final Document getDocument() {
        return this.document;
    }

    /**
     * Analyses the given input and returns the model or structure inside the
     * input.
     *
     * @param input the actual input to parsed.
     * @return the T-typed model or structure that was analysed from the input.
     */
    public final synchronized List<OutputType> analyse(final InputType input) {
        // some generic handling stuff could be done here
        // like indexing file creation, modification etc.

        return informAboutAnalysedModel(analyseObject(input, this.document));
    }

    /**
     * Abstract method to be implemented by the input specific analyser.
     *
     * @param input    the actual input type to analysed.
     * @param document the actual input to analysed.
     * @return the T-typed model that was analysed from the input.
     */
    protected abstract List<OutputType> analyseObject(InputType input, Document document);

    protected final List<OutputType> informAboutAnalysedModel(final List<OutputType> models) {
        boolean inform = false;
/* TODO		if (((models != null) && (models.size() > 0)) && inform) {

			IMonitoringDataRepositoryService<OutputType> monitoringDataReposotoryService = ServiceManager
					.getWebservice(SWServiceContainer.MonitoringDataRepositoryService);
			IAdapterService adapterService = ServiceManager.getWebservice(SWServiceContainer.AdapterService);
			if (ServiceManager.isPingable(monitoringDataReposotoryService)) {
				try {
					for (OutputType model : models) {
						monitoringDataReposotoryService.persist(model.toRdfString(), model.getImplementingClassName(),
								model.getApplicationScenario());
						if (ServiceManager.isPingable(adapterService)) {
							adapterService.informAboutMonitoredData(model.getApplicationScenario(), model.getImplementingClassName(),
									model.getIdentifier());
						} else {
							logger.warn("Could not inform Adapter about monitoring data, service is not reachable!");
						}
					}
				} catch (ContextFault e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				logger.warn("Could not persist Monitoring Data, service is not reachable!");
			}
		}
*/
        return models;
    }
}
