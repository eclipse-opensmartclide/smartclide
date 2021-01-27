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
import de.atb.context.common.util.BusinessCase;
import de.atb.context.common.util.TimeFrame;
import de.atb.context.extraction.ContextContainerWrapper;
import de.atb.context.services.faults.ContextFault;
import de.atb.context.services.interfaces.IPersistenceService;
import de.atb.context.services.interfaces.IRepositoryService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;

/**
 * IContextRepositoryService
 * 
 * @author scholze
 * @version $LastChangedRevision: 647 $
 * 
 */
@WebService(name = "ContextRService", targetNamespace = "http://www.atb-bremen.de")
@SOAPBinding(style = Style.DOCUMENT)
public interface IContextRepositoryService extends IPersistenceService, IRepositoryService {

	@WebMethod(operationName = "getDefaultModel") String getDefaultModel(@WebParam(name = "business-case") BusinessCase businessCase) throws ContextFault;

	@WebMethod(operationName = "persistContext") void persistContext(@WebParam(name = "context") ContextContainerWrapper context) throws ContextFault;

	@WebMethod(operationName = "getContextByApplicationScenarioAndId") ContextContainerWrapper getContext(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "context-id") String contextId) throws ContextFault;

	@WebMethod(operationName = "getContextByBusinessCaseAndId") ContextContainerWrapper getContext(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "context-id") String contextId) throws ContextFault;

	@WebMethod(operationName = "getRawContextByApplicationScenarioAndId") ContextContainerWrapper getRawContext(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "context-id") String contextId) throws ContextFault;

	@WebMethod(operationName = "getRawContextByBusinessCaseAndId") ContextContainerWrapper getRawContext(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "context-id") String contextId) throws ContextFault;

	@WebMethod(operationName = "executeSparqlAskQuery") Boolean executeSparqlAskQuery(@WebParam(name = "business-case") BusinessCase businessCase, @WebParam(name = "query") String query)
			throws ContextFault;

	@WebMethod(operationName = "executeSparqlSelectQuery") String executeSparqlSelectQuery(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "query") String query) throws ContextFault;

	@WebMethod(operationName = "executeSparqlDescribeQuery") String executeSparqlDescribeQuery(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "query") String query) throws ContextFault;

	@WebMethod(operationName = "executeSparqlConstructQuery") String executeSparqlConstructQuery(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "query") String query) throws ContextFault;

	@WebMethod(operationName = "executeSparqlAskQueryReasoner") Boolean executeSparqlAskQuery(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "query") String query, @WebParam(name = "user-reasoner") boolean useReasoner) throws ContextFault;

	@WebMethod(operationName = "executeSparqlSelectQueryReasoner") String executeSparqlSelectQuery(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "query") String query, @WebParam(name = "user-reasoner") boolean useReasoner) throws ContextFault;

	@WebMethod(operationName = "executeSparqlDescribeQueryReasoner") String executeSparqlDescribeQuery(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "query") String query, @WebParam(name = "user-reasoner") boolean useReasoner) throws ContextFault;

	@WebMethod(operationName = "executeSparqlConstructQueryReasoner") String executeSparqlConstructQuery(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "query") String query, @WebParam(name = "user-reasoner") boolean useReasoner) throws ContextFault;

	@WebMethod(operationName = "getLastContextIdsForBusinessCaseByCount") List<String> getLastContextsIds(@WebParam(name = "business-case") BusinessCase businessCase, @WebParam(name = "count") int count)
			throws ContextFault;

	@WebMethod(operationName = "getLastContextIdsForBusinessCaseByTimeFrame") List<String> getLastContextsIds(@WebParam(name = "business-case") BusinessCase businessCase,
			@WebParam(name = "time-frame") TimeFrame timeFrame) throws ContextFault;

	@WebMethod(operationName = "getLastContextIdsForApplicationScenarioByCount") List<String> getLastContextsIds(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "count") int count) throws ContextFault;

	@WebMethod(operationName = "getLastContextIdsForApplicationScenarioByTimeFrame") List<String> getLastContextsIds(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "time-frame") TimeFrame timeFrame) throws ContextFault;

	@WebMethod(operationName = "initializeRepository") void initializeRepository(@WebParam(name = "business-case") BusinessCase bc, @WebParam(name = "model-uri") String modelUri)
			throws ContextFault;

}
