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
import pt.uninova.context.services.interfaces.IService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author scholze
 *
 */
@WebService(name = "MonitoringDataService", targetNamespace = "http://diversity-project.eu")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IMonitoringDataService extends IService {
	@WebMethod(operationName = "config")
	void config(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "Mode") String mode,
            @WebParam(name = "Algorithm") String algorithm) throws ContextFault;

	@WebMethod(operationName = "Proactvie_config")
	void proactiveConfig(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario)
					throws ContextFault;

	@WebMethod(operationName = "informAboutReferenceContext")
	boolean informAboutReferenceContext(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "identifier") String identifier)
					throws ContextFault;

	@WebMethod(operationName = "informAboutContextChange")
	boolean informAboutContextChange(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "identifier") String identifier)
					throws ContextFault;

	@WebMethod(operationName = "informAboutMonitoredData")
	boolean informAboutMonitoredData(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "class-name") String className,
            @WebParam(name = "identifier") String identifier)
					throws ContextFault;

}
