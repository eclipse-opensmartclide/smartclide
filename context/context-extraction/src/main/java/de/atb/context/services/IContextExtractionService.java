package de.atb.context.services;

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


import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.TimeFrame;
import de.atb.context.extraction.ContextContainerWrapper;
import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.services.faults.ContextFault;
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.services.interfaces.IService;
import de.atb.context.tools.ontology.ContextExtractionConfiguration;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * IContextExtractionService
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
@WebService(name = "ContextExtractionService", targetNamespace = "http://www.atb-bremen.de")
@SOAPBinding(style = Style.DOCUMENT)
@XmlSeeAlso(ContextExtractionConfiguration.class)
public interface IContextExtractionService extends IService {

	@WebMethod(operationName = "extractContextFromMonitoringData") <T, D extends DataSource> ContextContainerWrapper extractContext(@WebParam(name = "monitoring-data") String rdfString,
			@WebParam(name = "clazz") String clazz, @WebParam(name = "application-scenario") ApplicationScenario applicationScenario)
			throws ContextFault;

	@WebMethod(operationName = "getLastContextIdsByCount") List<String> getLastContextsIds(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "count") int count) throws ContextFault;

	@WebMethod(operationName = "getLastContextIdsByTimeFrame") List<String> getLastContextsIds(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "time-frame") TimeFrame timeFrame) throws ContextFault;

	@WebMethod(operationName = "informAboutAdaptation") void informAboutAdaptation(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "identifier") String identifier) throws ContextFault;

    @WebMethod(operationName = "getReposData") ServiceInfo getReposData();
}
