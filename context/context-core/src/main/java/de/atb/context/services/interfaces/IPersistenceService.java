package de.atb.context.services.interfaces;

/*
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
import de.atb.context.services.faults.ContextFault;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;

/**
 * IPersistenceService
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IPersistenceService {

	@WebMethod(operationName = "addPersistencePreProcessor")
	boolean addPersistencePreProcessor(
			@WebParam(name = "app-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "id") String id,
			@WebParam(name = "class-name") String className)
					throws ContextFault;

	@WebMethod(operationName = "addPersistencePostProcessor")
	boolean addPersistencePostProcessor(
			@WebParam(name = "app-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "id") String id,
			@WebParam(name = "class-name") String className)
					throws ContextFault;

	@WebMethod(operationName = "removePersistencePreProcessor")
	boolean removePersistencePreProcessor(
			@WebParam(name = "app-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "id") String id,
			@WebParam(name = "class-name") String className)
					throws ContextFault;

	@WebMethod(operationName = "removePersistencePostProcessor")
	boolean removePersistencePostProcessor(
			@WebParam(name = "app-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "id") String id,
			@WebParam(name = "class-name") String className)
					throws ContextFault;

	@WebMethod(operationName = "removePersistencePreProcessorWithoutClass")
	boolean removePersistencePreProcessor(
			@WebParam(name = "app-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "id") String id) throws ContextFault;

	@WebMethod(operationName = "removePersistencePostProcessorWithoutClass")
	boolean removePersistencePostProcessor(
			@WebParam(name = "app-scenario") ApplicationScenario applicationScenario,
			@WebParam(name = "id") String id) throws ContextFault;

	@WebMethod(operationName = "triggerPreProcessors")
	void triggerPreProcessors(
			@WebParam(name = "app-scenario") ApplicationScenario scenario,
			@WebParam(name = "object-string") String objectString,
			@WebParam(name = "object-class-name") String objectClassName)
					throws ContextFault;

	@WebMethod(operationName = "triggerPostProcessors")
	void triggerPostProcessors(
			@WebParam(name = "app-scenario") ApplicationScenario scenario,
			@WebParam(name = "object-string") String objectString,
			@WebParam(name = "object-class-name") String objectClassName)
					throws ContextFault;

}
