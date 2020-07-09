package de.atb.context.services.interfaces;

/*
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2016 - 2020 ATB
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
