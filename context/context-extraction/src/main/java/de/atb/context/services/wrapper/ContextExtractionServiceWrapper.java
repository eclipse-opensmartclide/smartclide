package de.atb.context.services.wrapper;

/*
 * #%L
 * ATB Context Extraction Core Service
 * %%
 * Copyright (C) 2018 - 2019 ATB
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import de.atb.context.extraction.ContextContainer;
import de.atb.context.extraction.ContextContainerWrapper;
import de.atb.context.services.IContextExtractionService;
import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.TimeFrame;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.services.faults.ContextFault;

import java.util.List;

/**
 * ContextExtractionServiceWrapper
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
public class ContextExtractionServiceWrapper extends ServiceWrapper<IContextExtractionService> {

	public ContextExtractionServiceWrapper(IContextExtractionService service) throws IllegalArgumentException {
		super(service);
	}

	public final synchronized ContextContainer extractContext(IMonitoringDataModel<?, ?> monitoringData) throws ContextFault {
		ContextContainerWrapper wrapper = service.extractContext(monitoringData.toRdfString(), monitoringData.getImplementingClassName(),
				monitoringData.getApplicationScenario());
		return wrapper.toContextContainer();
	}

	public final synchronized List<String> getLastContextsIds(ApplicationScenario applicationScenario, int count) throws ContextFault {
		return service.getLastContextsIds(applicationScenario, count);
	}

	public final synchronized List<String> getLastContextsIds(ApplicationScenario applicationScenario, TimeFrame timeFrame)
			throws ContextFault {
		return service.getLastContextsIds(applicationScenario, timeFrame);
	}

	public final synchronized void informAboutAdaptation(ApplicationScenario applicationScenario, String identifier) throws ContextFault {
		service.informAboutAdaptation(applicationScenario, identifier);
	}
}
