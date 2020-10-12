package de.atb.context.services;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
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
import de.atb.context.services.faults.ContextFault;
import de.atb.context.services.interfaces.IRepositoryService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * IConfigurationRepositoryService
 * 
 * @author scholze
 * @version $LastChangedRevision: 176 $
 * 
 */
@WebService(name = "ConfigurationRService", targetNamespace = "http://atb-bremen.de")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IConfigurationRepositoryService extends
		IRepositoryService {

	@WebMethod(operationName = "getConfiguration")
    String getConfiguration(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario)
			throws ContextFault;

	@WebMethod(operationName = "getConfigurationWithClazzName")
    String getConfiguration(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "class-name") String clazzName)
			throws ContextFault;

	@WebMethod(operationName = "persistConfiguration")
    void persistConfiguration(
            @WebParam(name = "configuration") String serializeConfiguration,
            @WebParam(name = "class-name") String clazzName)
			throws ContextFault;

	@WebMethod(operationName = "clearBusinessCaseDirectory")
    boolean clearBusinessCaseDirectory(
            @WebParam(name = "business-case") BusinessCase businessCase)
			throws ContextFault;

	@WebMethod(operationName = "deleteApplicationScenarioConfiguration")
    boolean deleteApplicationScenarioConfiguration(
            @WebParam(name = "application-scenario") ApplicationScenario scenario)
			throws ContextFault;

	@WebMethod(operationName = "clearBaseDirectory")
    boolean clearBaseDirectory() throws ContextFault;

}
